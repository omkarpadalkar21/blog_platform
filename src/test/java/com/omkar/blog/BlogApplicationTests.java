package com.omkar.blog;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.TimeZone;

@SpringBootTest
class BlogApplicationTests {

	@BeforeAll
	static void setUpTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		System.setProperty("user.timezone", "Asia/Kolkata");
	}

	@Test
	void contextLoads() {
	}

}
