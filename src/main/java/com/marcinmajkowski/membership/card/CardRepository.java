package com.marcinmajkowski.membership.card;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
class CardRepository {

    private static final RowMapper<Card> CARD_ROW_MAPPER = (rs, rowNum) -> {
        Card card = new Card();
        card.setCode(rs.getString("code"));
        card.setCustomerId(rs.getLong("customer_id"));
        return card;
    };

    private final JdbcTemplate jdbcTemplate;

    public CardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Card> findAll() {
        String sql = "SELECT code, customer_id FROM card";
        return jdbcTemplate.query(sql, CARD_ROW_MAPPER);
    }

    public Card findByCode(String code) {
        String sql = "SELECT code, customer_id FROM card WHERE code = ?";
        return jdbcTemplate.queryForObject(sql, CARD_ROW_MAPPER, code);
    }

    public void insert(Card card) {
        String sql = "INSERT INTO card (code, customer_id) VALUES (?, ?)";
        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, card.getCode());
            ps.setLong(2, card.getCustomerId());
            return ps;
        };
        jdbcTemplate.update(preparedStatementCreator);
    }
}
