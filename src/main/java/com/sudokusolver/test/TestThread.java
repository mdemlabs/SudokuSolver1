package com.sudokusolver.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TestThread {

	private static final int maxSize = 100;
	public static ArrayBlockingQueue<String> buffer =
		    new ArrayBlockingQueue<String>(maxSize);
	
	public static void main (String[] args) {
		
		//First upload puzzles from file to buffer
		getPuzzleTask();
		
		//Now it's the time to create threads and solve the puzzles...
		ExecutorService executor = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 100; i++) {
            Runnable worker = new SolvePuzzleTask("" + i);
            executor.execute(worker);
          }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
  
        System.out.println("Finished all threads...mission completed [:)");
	}
	
	private static void getPuzzleTask () {
		String line;
	    File file = new File("d:\\wsMaven\\SudokuSolver\\SudokuSolver\\src\\100SudokuHard.txt");
	    Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		    int count = 0;
		    while (scanner.hasNextLine()) {
	            count++;
		    	line = scanner.nextLine();
		    	char[] array = line.toCharArray();
		    	for (int i = 0; i < line.length(); i++)
					if (String.valueOf(line.charAt(i)) == ".")
						array[i]= 0;	
		    	line = String.valueOf(array);
				buffer.put(line);
		    	System.out.println("Q add: "+count+" size: "+buffer.size()+"- "+line);
		    	//Thread.sleep((int)(100));
		    }
		    scanner.close();
		} catch (InterruptedException ex) {
		    ex.printStackTrace();
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} 
	}	
}

