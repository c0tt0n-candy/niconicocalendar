package com.niconicocalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... strings) throws Exception {

		jdbcTemplate.execute("drop table user_tbl if exists");
		jdbcTemplate.execute("create table user_tbl(userId serial, userName varchar)");
		String[] users = {"yutaka", "naoki", "terahide"};
		jdbcTemplate.update("insert into user_tbl(userId, userName) values(?,?)", 1, users[0]);
		jdbcTemplate.update("insert into user_tbl(userId, userName) values(?,?)", 2, users[1]);
		jdbcTemplate.update("insert into user_tbl(userId, userName) values(?,?)", 3, users[2]);
		
		jdbcTemplate.execute("drop table feelings_tbl if exists");
		jdbcTemplate.execute("create table feelings_tbl(feelingsNum serial, feelings varchar)");
		String[] feelingss = {"niconico","iraira","futsu","shikushiku","utouto"};
		jdbcTemplate.update("insert into feelings_tbl(feelingsNum, feelings) values(?,?)", 0, feelingss[0]);
		jdbcTemplate.update("insert into feelings_tbl(feelingsNum, feelings) values(?,?)", 1, feelingss[1]);		
		jdbcTemplate.update("insert into feelings_tbl(feelingsNum, feelings) values(?,?)", 2, feelingss[2]);
		jdbcTemplate.update("insert into feelings_tbl(feelingsNum, feelings) values(?,?)", 3, feelingss[3]);
		jdbcTemplate.update("insert into feelings_tbl(feelingsNum, feelings) values(?,?)", 4, feelingss[4]);

		jdbcTemplate.execute("drop table feelings_history_tbl if exists");
		jdbcTemplate
				.execute("create table feelings_history_tbl(feelingsId integer, userId integer, year integer, month integer, day integer, feelingsNum integer)");
		jdbcTemplate.update("insert into feelings_history_tbl(feelingsId, userId, year, month, day, feelingsNum) values(?,?,?,?,?,?)", 1, 1, 2016, 4, 29, 4);
		jdbcTemplate.update("insert into feelings_history_tbl(feelingsId, userId, year, month, day, feelingsNum) values(?,?,?,?,?,?)", 2, 2, 2016, 4, 29, 2);
		jdbcTemplate.update("insert into feelings_history_tbl(feelingsId, userId, year, month, day, feelingsNum) values(?,?,?,?,?,?)", 3, 3, 2016, 4, 29, 0);
	}
}
