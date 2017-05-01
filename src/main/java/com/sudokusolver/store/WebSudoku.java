package com.sudokusolver.store;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sudokusolver.config.Config;
import com.sudokusolver.controller.Controller;
import com.sudokusolver.puzzle.Sudoku;
import com.sudokusolver.utils.Utilities;

public class WebSudoku {
	Controller controller;
    Utilities util;
    Config config;
    Sudoku sudoku;
    
	public int[] sudokuArr;
    
    public WebSudoku(Controller controller, Utilities utl, Config config1, Sudoku sudoku) {
    	this.controller = controller;
    	this.util = utl;
    	this.config = config1;
	}

	public void getNewSudoku(int level) throws Exception {
        
    	// Frist get the requested level of puzzle from websudoku page
    	// Level = 1/Easy, 2/Medium, 3/Hard, 4/Evil
		String link = "http://show.websudoku.com/?level="+level;
		System.out.println(link);
        Document doc = Jsoup.connect(link).get();
    	Document pdoc = Jsoup.parse(doc.toString());
    	String stPtrn = pdoc.getElementById("cheat").attr("value");
    	String stMask = pdoc.getElementById("editmask").attr("value");
    	System.out.println(stPtrn);
    	System.out.println(stMask);
    	
    	// Convert String to byte[] for furher processing
    	char[] chPtrn = pdoc.getElementById("cheat").attr("value").toCharArray();
    	char[] chMask = pdoc.getElementById("editmask").attr("value").toString().toCharArray();
    	int[] intPtrn = util.toInt(chPtrn);
    	int[] intMask = util.toInt(chMask);
    	sudokuArr = new int[intPtrn.length];
    	for (byte i=0; i<intPtrn.length; i++) 
    		if (intMask[i] == 0)
    		    sudokuArr[i] = (byte) intPtrn[i];
    		else sudokuArr[i] = 0;
    	
     	//  Update actuals and store solution 
    	sudoku.setActuals(util.convert2SudokuMatrix(sudokuArr));
    	sudoku.setSolution(util.convert2SudokuMatrix(intPtrn));
    	
    }

	public boolean netIsAvailable() {                                                                                                                                                                                                 
	    try {                                                                                                                                                                                                                                 
	        final URL url = new URL("http://www.google.com");                                                                                                                                                                                 
	        final URLConnection conn = url.openConnection();                                                                                                                                                                                  
	        conn.connect();                                                                                                                                                                                                                   
	        return true;                                                                                                                                                                                                                      
	    } catch (MalformedURLException e) {                                                                                                                                                                                                   
	        throw new RuntimeException(e);                                                                                                                                                                                                    
	    } catch (IOException e) {                                                                                                                                                                                                             
	        return false;                                                                                                                                                                                                                     
	    }                                                                                                                                                                                                                                     
	}    
	
    public void getPuzzleFromDisk (int level) {
        int[][] puzzles = new int [100][81];
        String line ;
        String filename = getPuzzleFileName(level);
        
		try {
		    //File file = new File("d:\\wsMaven\\SudokuSolver\\SudokuSolver\\src\\100SudokuHard.txt");
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
		    byte count = 0;
		    while (scanner.hasNextLine() && count < 100) {
		    	//System.out.println();
		    	line = scanner.nextLine();
		        byte size = (byte) line.length();
		        //System.out.println(count);
		        for (byte i=0; i<size; i++) {
		        	char ch = line.charAt(i);
		        	//System.out.print(ch);
		        	puzzles[count][i] = util.char2int(ch);
		        	//System.out.print(puzzles[count][i]);
		        }
		        count++;
     	    }
		    scanner.close();
		} catch (FileNotFoundException e) {
		         e.printStackTrace();
		         sudoku.setActuals (util.noPuzzle.clone());
		}
    }	
	
    public String getPuzzleFileName (int level) {
    	switch ( level ) {
            case 1:
                //return 1;
            case 2:
                //return 2;
            case 3:
                //return 3;
            case 4:
            	//return 4;
            case 5:
            	return config.killerPuzzles;
    	}
    	return " "; 
    }

    
}

  
