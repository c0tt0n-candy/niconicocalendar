package com.niconicocalendar.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.niconicocalendar.Feelings;

@Component
public class FeelingsManager {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// 余計なの含む
	public List<Feelings> getList(){
		List<Feelings> feelingsList = jdbcTemplate.query("select feelingsNum, feelings from feelings_tbl", new FeelingsListRowMapper());
		return feelingsList;
	}
	
	// 余計なの含む
	public List<Feelings> getAllFeelings() {
		List<Feelings> allFeelings = jdbcTemplate.query("select feelingsId, userId, year, month, day, feelingsNum from feelings_history_tbl", new FeelingsRowMapper());
		return allFeelings;
	}
	
	// 余計なの含む いらないかも
	public Feelings getOneFeelings(Integer feelingsId) {
		Feelings oneFeelings = jdbcTemplate.queryForObject("select * from feelings_history_tbl where feelingsId=?", new FeelingsRowMapper(), feelingsId);
		return oneFeelings;
	}
	
	public Feelings findFeelings(Integer userId, Integer year, Integer month, Integer day) {
		Feelings selectedFeelings = null;
		int count = jdbcTemplate.queryForObject("select count(*) from feelings_history_tbl where userId=? and year=? and month=? and day=?",
				Integer.class, userId, year, month, day);
		if (count != 0) {
			selectedFeelings = jdbcTemplate.queryForObject("select feelingsId, userId, year, month, day, feelingsNum from feelings_history_tbl where userId=? and year=? and month=? and day=?",
				new FeelingsRowMapper(), userId, year, month, day);
		}
		return selectedFeelings;
	}
	
	public void registerFeelings(Feelings feelings) {
		int count = jdbcTemplate.queryForObject("select count(*) from feelings_history_tbl where userId=? and year=? and month=? and day=?",
				Integer.class, feelings.getUserId(), feelings.getYear(), feelings.getMonth(), feelings.getDay());
		if (count == 0) {
			int maxFeelingsId = jdbcTemplate.queryForObject("select max(feelingsId) from feelings_history_tbl", Integer.class);
			jdbcTemplate.update("insert into feelings_history_tbl(feelingsId, userId, year, month, day, feelingsNum) VALUES (?,?,?,?,?,?)",
					 maxFeelingsId+1, feelings.getUserId(), feelings.getYear(), feelings.getMonth(), feelings.getDay(), feelings.getFeelingsNum());
		} else {
			int feelingsId = jdbcTemplate.queryForObject("select feelingsId from feelings_history_tbl where userId=? and year=? and month=? and day=?",
					Integer.class, feelings.getUserId(), feelings.getYear(), feelings.getMonth(), feelings.getDay());
			jdbcTemplate.update("update feelings_history_tbl set feelingsNum=? where feelingsId=?", feelings.getFeelingsNum(), feelingsId);
		}
	}
	
	public void deleteFeelings(Integer feelingsId) {
		jdbcTemplate.update("delete from feelings_history_tbl where feelingsId=?", feelingsId);
	}
	
	private class FeelingsListRowMapper implements RowMapper<Feelings>{
		@Override
		public Feelings mapRow(ResultSet rs, int rowNum) throws SQLException {
			int feelingsNum = rs.getInt("feelingsNum");
			String feelings = rs.getString("feelings");
			return new Feelings(feelingsNum, feelings);
		}
	}
	
	private class FeelingsRowMapper implements RowMapper<Feelings>{
		@Override
		public Feelings mapRow(ResultSet rs, int rowNum) throws SQLException {
			int feelingsId = rs.getInt("feelingsId");
			int userId = rs.getInt("userId");
			int year = rs.getInt("year");
			int month = rs.getInt("month");
			int day = rs.getInt("day");
			int feelingsNum = rs.getInt("feelingsNum");
			return new Feelings(feelingsId, userId, year, month, day, feelingsNum);
		}
	}
}