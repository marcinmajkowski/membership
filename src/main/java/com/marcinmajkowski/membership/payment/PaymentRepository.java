package com.marcinmajkowski.membership.payment;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
class PaymentRepository {

    private final RowMapper<Payment> paymentRowMapper = (rs, rowNum) -> {
        Payment payment = new Payment();
        payment.setId(rs.getLong("id"));
        payment.setAmount(rs.getBigDecimal("amount"));
        payment.setTimestamp(rs.getTimestamp("timestamp").toInstant());
        return payment;
    };

    private final RowMapper<PaymentCustomer> paymentCustomerRowMapper = new BeanPropertyRowMapper<>(PaymentCustomer.class);

    private final SimpleJdbcInsert paymentInsert;

    private final JdbcTemplate jdbcTemplate;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.paymentInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("payment")
                .usingGeneratedKeyColumns("id");
    }

    public List<Payment> findAll() {
        String sql = "SELECT id, amount, timestamp FROM payment ORDER BY timestamp DESC";
        List<Payment> payments = jdbcTemplate.query(sql, paymentRowMapper);
        // FIXME N+1
        payments.forEach(this::loadCustomer);
        return payments;
    }

    public List<Payment> findPaymentsByCustomerId(Long customerId) {
        String sql = "SELECT id, amount, timestamp FROM payment WHERE customer_id = ? ORDER BY timestamp DESC";
        List<Payment> payments = jdbcTemplate.query(sql, paymentRowMapper, customerId);
        // FIXME N+1
        payments.forEach(this::loadCustomer);
        return payments;
    }

    public void storePayment(Payment payment) {
        if (payment.isNew()) {
            Number key = paymentInsert.executeAndReturnKey(createPaymentParameterSource(payment));
            payment.setId(key.longValue());
            loadCustomer(payment);
        } else {
            throw new UnsupportedOperationException("payment update not implemented");
        }
    }

    private void loadCustomer(Payment payment) {
        String sql = "SELECT customer.id as id, first_name, last_name FROM customer INNER JOIN payment ON payment.customer_id = customer.id WHERE payment.id = ?";
        PaymentCustomer customer;
        try {
            customer = jdbcTemplate.queryForObject(
                    sql,
                    paymentCustomerRowMapper,
                    payment.getId()
            );
        } catch (EmptyResultDataAccessException e) {
            customer = null;
        }
        payment.setCustomer(customer);
    }

    private MapSqlParameterSource createPaymentParameterSource(Payment payment) {
        return new MapSqlParameterSource()
                .addValue("id", payment.getId())
                .addValue("customer_id", payment.getCustomer().getId())
                .addValue("amount", payment.getAmount())
                .addValue("timestamp", Timestamp.from(payment.getTimestamp()));
    }
}
