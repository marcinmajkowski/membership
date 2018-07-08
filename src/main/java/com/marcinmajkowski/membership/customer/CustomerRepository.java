package com.marcinmajkowski.membership.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
class CustomerRepository {

    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, rowNum) -> {
        Customer customer = new Customer();
        customer.setId(rs.getLong("id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setCardCode(rs.getString("card_code"));
        return customer;
    };

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> findAll() {
        String sql = "SELECT id, first_name, last_name, card_code FROM customer";
        return jdbcTemplate.query(sql, CUSTOMER_ROW_MAPPER);
    }

    public Customer findById(Long id) {
        String sql = "SELECT id, first_name, last_name, card_code FROM customer WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, CUSTOMER_ROW_MAPPER, id);
    }

    public Long insert(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name, card_code) VALUES (?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getCardCode());
            return ps;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
