package com.marcinmajkowski.membership.checkin;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

@Repository
class CheckInRepository {

    private final JdbcTemplate jdbcTemplate;

    public CheckInRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CheckIn findById(Long id) {
        String sql = "SELECT id, card_code, creation_instant FROM check_in WHERE id = ?";
        RowMapper<CheckIn> rowMapper = (rs, rowNum) -> {
            CheckIn checkIn = new CheckIn();
            checkIn.setId(rs.getLong("id"));
            checkIn.setCardCode(rs.getString("card_code"));
            checkIn.setCreationInstant(rs.getTimestamp("creation_instant").toInstant());
            return checkIn;
        };
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
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
