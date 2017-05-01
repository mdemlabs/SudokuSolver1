package com.sudokusolver.others;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import junit.framework.TestCase;

public class HelloExampleLoggerTest extends TestCase {

	private static final Logger logger = LogManager.getLogger("HelloWorld");
	@Test
    public void testSomeAwesomeFeature() {
		logger.info("Hello, World!");
	}
}
