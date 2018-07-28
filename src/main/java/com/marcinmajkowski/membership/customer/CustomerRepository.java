package com.marcinmajkowski.membership.customer;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
class CustomerRepository {

    private final RowMapper<Customer> customerRowMapper = new BeanPropertyRowMapper<>(Customer.class);

    private final RowMapper<Card> cardRowMapper = new BeanPropertyRowMapper<>(Card.class);

    private final SimpleJdbcInsert customerInsert;

    private final SimpleJdbcInsert cardInsert;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.customerInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
        this.cardInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .usingGeneratedKeyColumns("id")
                .withTableName("card");
    }

    public List<Customer> getCustomers() {
        String sql = "SELECT id, first_name, last_name FROM customer";
        List<Customer> customers = getJdbcTemplate().query(sql, customerRowMapper);
        // FIXME N+1
        customers.forEach(this::loadCards);
        return customers;
    }

    public List<Customer> getCustomers(Set<Long> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        String sql = "SELECT id, first_name, last_name FROM customer WHERE id IN (:ids)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("ids", ids);
        List<Customer> customers = namedParameterJdbcTemplate.query(sql, parameterSource, customerRowMapper);
        // FIXME N+1
        customers.forEach(this::loadCards);
        return customers;
    }

    public Customer getCustomer(Long id) {
        String sql = "SELECT id, first_name, last_name FROM customer WHERE id = ?";
        Customer customer = getJdbcTemplate().queryForObject(sql, customerRowMapper, id);
        loadCards(customer);
        return customer;
    }

    public List<Customer> findCustomersByCardCode(String cardCode) {
        String sql = "SELECT customer.id AS id, first_name, last_name FROM customer INNER JOIN card ON card.customer_id = customer.id WHERE card.code = ?";
        List<Customer> customers = getJdbcTemplate().query(sql, customerRowMapper, cardCode);
        customers.forEach(this::loadCards);
        return customers;
    }

    public void storeCustomer(Customer customer) {
        if (customer.isNew()) {
            Number key = customerInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(customer));
            customer.setId(key.longValue());
        } else {
            throw new UnsupportedOperationException("customer update not implemented");
        }
    }

    public void storeCard(Card card) {
        if (card.isNew()) {
            Number key = cardInsert.executeAndReturnKey(createCardParameterSource(card));
            card.setId(key.longValue());
        } else {
            throw new UnsupportedOperationException("card update not implemented");
        }
    }

    public void deleteCustomer(Long id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        getJdbcTemplate().update(sql, id);
    }

    private void loadCards(Customer customer) {
        List<Card> cards = getJdbcTemplate().query(
                "SELECT id, code FROM card WHERE customer_id = ?",
                cardRowMapper,
                customer.getId()
        );
        customer.setCards(new HashSet<>(cards));
    }

    private MapSqlParameterSource createCardParameterSource(Card card) {
        return new MapSqlParameterSource()
                .addValue("id", card.getId())
                .addValue("code", card.getCode())
                .addValue("customer_id", card.getCustomer().getId());
    }

    private JdbcTemplate getJdbcTemplate() {
        return namedParameterJdbcTemplate.getJdbcTemplate();
    }
}
