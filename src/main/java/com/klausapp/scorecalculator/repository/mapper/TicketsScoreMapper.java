package com.klausapp.scorecalculator.repository.mapper;

import com.klausapp.scorecalculator.repository.TicketScore;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketsScoreMapper implements RowMapper<TicketScore> {
    private static final TicketsScoreMapper INSTANCE = new TicketsScoreMapper();

    public static TicketsScoreMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public TicketScore mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer score = rs.getInt("score");
        Integer ticketId = rs.getInt("ticketId");

        return new TicketScore() {

            @Override
            public Integer getTicketId() {
                return ticketId;
            }

            @Override
            public Integer getScore() {
                return score;
            }
        };
    }
}
