package com.marcinmajkowski.membership.checkin;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
class CheckInRepository {

    private static final RowMapper<CheckIn> CHECK_IN_ROW_MAPPER = (rs, rowNum) -> {
        CheckIn checkIn = new CheckIn();
        checkIn.setId(rs.getLong("id"));
        checkIn.setCardCode(rs.getString("card_code"));
        checkIn.setCreationInstant(rs.getTimestamp("creation_instant").toInstant());
        return checkIn;
    };

    private final JdbcTemplate jdbcTemplate;

    public CheckInRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CheckIn> findAll() {
        String sql = "SELECT id, card_code, creation_instant FROM check_in ORDER BY creation_instant DESC";
        return jdbcTemplate.query(sql, CHECK_IN_ROW_MAPPER);
    }

    public CheckIn findById(Long id) {
        String sql = "SELECT id, card_code, creation_instant FROM check_in WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, CHECK_IN_ROW_MAPPER, id);
    }

    public Long insert(CheckIn checkIn) {
        String sql = "INSERT INTO check_in (card_code, creation_instant) VALUES (?, ?)";
        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, checkIn.getCardCode());
            ps.setTimestamp(2, Timestamp.from(checkIn.getCreationInstant()));
            return ps;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
