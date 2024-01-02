package com.epam.upskill.springcore;

import com.epam.upskill.springcore.service.impl.GenerateDateTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringCoreTaskApplicationTests {

	@Autowired
	private GenerateDateTest generateDateTest;

	@Test
	void contextLoads() {
//		generateDateTest.generate();
	}

}
