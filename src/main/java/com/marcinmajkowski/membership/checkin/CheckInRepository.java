package com.marcinmajkowski.membership.checkin;

import com.marcinmajkowski.membership.customer.CustomerReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
class CheckInRepository {

    private final RowMapper<CustomerReference> customerReferenceRowMapper = (rs, rowNum) -> {
        Long customerId = rs.getLong("customer_id");
        if (rs.wasNull()) {
            return null;
        } else {
            CustomerReference customerReference = new CustomerReference();
            customerReference.setId(customerId);
            return customerReference;
        }
    };

    private final RowMapper<CheckIn> checkInRowMapper = (rs, rowNum) -> {
        CheckIn checkIn = new CheckIn();
        checkIn.setId(rs.getLong("id"));
        checkIn.setCustomer(customerReferenceRowMapper.mapRow(rs, rowNum));
        checkIn.setTimestamp(rs.getTimestamp("timestamp").toInstant());
        return checkIn;
    };

    private final SimpleJdbcInsert checkInInsert;

    private final JdbcTemplate jdbcTemplate;

    public CheckInRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.checkInInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("check_in")
                .usingGeneratedKeyColumns("id");
    }

    public List<CheckIn> findCheckInsByCustomerId(Long customerId) {
        String sql = "SELECT id, timestamp, customer_id FROM check_in WHERE customer_id = ? ORDER BY timestamp DESC";
        return jdbcTemplate.query(sql, checkInRowMapper, customerId);
    }

    public List<CheckIn> findAll() {
        String sql = "SELECT id, timestamp, customer_id FROM check_in ORDER BY timestamp DESC";
        return jdbcTemplate.query(sql, checkInRowMapper);
    }

    public void storeCheckIn(CheckIn checkIn) {
        if (checkIn.isNew()) {
            Number key = checkInInsert.executeAndReturnKey(createCheckInParameterSource(checkIn));
            checkIn.setId(key.longValue());
        } else {
            throw new UnsupportedOperationException("checkIn update not implemented");
        }
    }

    public void deleteCheckIn(Long checkInId) {
        String sql = "DELETE FROM check_in WHERE id = ?";
        jdbcTemplate.update(sql, checkInId);
    }

    private MapSqlParameterSource createCheckInParameterSource(CheckIn checkIn) {
        CustomerReference customer = checkIn.getCustomer();
        Long customerId = customer != null ? customer.getId() : null;
        return new MapSqlParameterSource()
                .addValue("id", checkIn.getId())
                .addValue("customer_id", customerId)
                .addValue("timestamp", Timestamp.from(checkIn.getTimestamp()));
    }
}
