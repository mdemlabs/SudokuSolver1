package com.sudokusolver.others;

import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelloExampleLogger{
	
	private static final Logger logger = LogManager.getLogger("HelloWorld");
	
	public static void main(String[] args) {
		logger.info("Hello, World!");
		HelloExampleLogger obj = new HelloExampleLogger();
		obj.runMe("mkyong");
		

	}
	
	private void runMe(String parameter){

		if(logger.isDebugEnabled()){
			logger.debug("This is debug : " + parameter);
		}
		
		if(logger.isInfoEnabled()){
			logger.info("This is info : " + parameter);
		}
		
		logger.warn("This is warn : " + parameter);
		logger.error("This is error : " + parameter);
		logger.fatal("This is fatal : " + parameter);
		
	}
	
}