package com.marcinmajkowski.membership.checkin;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
class CheckInRepository {

    private final RowMapper<CheckIn> checkInRowMapper = (rs, rowNum) -> {
        CheckIn checkIn = new CheckIn();
        checkIn.setId(rs.getLong("id"));
        checkIn.setTimestamp(rs.getTimestamp("timestamp").toInstant());
        return checkIn;
    };

    private final RowMapper<CheckInCustomer> checkInCustomerRowMapper = new BeanPropertyRowMapper<>(CheckInCustomer.class);

    private final SimpleJdbcInsert checkInInsert;

    private final JdbcTemplate jdbcTemplate;

    public CheckInRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.checkInInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("check_in")
                .usingGeneratedKeyColumns("id");
    }

    public List<CheckIn> findCheckInsByCustomerId(Long customerId) {
        String sql = "SELECT id, timestamp FROM check_in WHERE customer_id = ? ORDER BY timestamp DESC";
        List<CheckIn> checkIns = jdbcTemplate.query(sql, checkInRowMapper, customerId);
        // FIXME N+1
        checkIns.forEach(this::loadCustomer);
        return checkIns;
    }

    public List<CheckIn> findAll() {
        String sql = "SELECT id, timestamp FROM check_in ORDER BY timestamp DESC";
        List<CheckIn> checkIns = jdbcTemplate.query(sql, checkInRowMapper);
        // FIXME N+1
        checkIns.forEach(this::loadCustomer);
        return checkIns;
    }

    public void storeCheckIn(CheckIn checkIn) {
        if (checkIn.isNew()) {
            Number key = checkInInsert.executeAndReturnKey(createCheckInParameterSource(checkIn));
            checkIn.setId(key.longValue());
            loadCustomer(checkIn);
        } else {
            throw new UnsupportedOperationException("checkIn update not implemented");
        }
    }

    public void deleteCheckIn(Long checkInId) {
        String sql = "DELETE FROM check_in WHERE id = ?";
        jdbcTemplate.update(sql, checkInId);
    }

    private void loadCustomer(CheckIn checkIn) {
        String sql = "SELECT customer.id as id, first_name, last_name FROM customer INNER JOIN check_in ON check_in.customer_id = customer.id WHERE check_in.id = ?";
        CheckInCustomer customer = jdbcTemplate.queryForObject(
                sql,
                checkInCustomerRowMapper,
                checkIn.getId()
        );
        checkIn.setCustomer(customer);
    }

    private MapSqlParameterSource createCheckInParameterSource(CheckIn checkIn) {
        return new MapSqlParameterSource()
                .addValue("id", checkIn.getId())
                .addValue("customer_id", checkIn.getCustomer().getId())
                .addValue("timestamp", Timestamp.from(checkIn.getTimestamp()));
    }
}
