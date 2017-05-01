package com.sudokusolver.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import com.sudokusolver.config.Config.puzzleLevel;


public class Utilities {

	public Utilities () {

	}

    public int[] readFile (String filename, int random) {
		String line ;
		int[] puzzle = new int[81];
		puzzle = initPuzzle;
        
		try {
		    //File file = new File("d:\\wsMaven\\SudokuSolver\\SudokuSolver\\src\\100SudokuHard.txt");
		    File file = new File(filename);
		    Scanner scanner = new Scanner(file);
		    byte count = 0;
		    while (scanner.hasNextLine()) {
                count++;
		    	line = scanner.nextLine();
		    	if (count == random) {
	                int size = line.length();
                    for (int i=0; i<size; i++) {
		        	    char ch = line.charAt(i);
		        	    puzzle[i] = char2int(ch);
		            }
		        }
    	    }
		    scanner.close();
		} catch (FileNotFoundException e) {
		         e.printStackTrace();
		}
		return puzzle;
    }
	
	public int[][] initMatrix(int row, int col, int val) {
		int[][] tempArray = new int[row][col];
		for (int i = 0; i < row; i++) 
	        for (int k = 0; k < col; k++)
	        	tempArray[i][k]    = (int) val;
		
		return tempArray;
	}
	
	public int generateRandomNumber (int limit) {
		Random rand = new Random();
		return rand.nextInt(limit);
	}
	
	public int[][] convert2SudokuMatrix (int[] puzzle) {
		int[][] sudo = new int[10][10];
		int[] pos = new int [] {1,1}; // pos[0]=row, pos[1]=col
        for (int i=0;i< 81; i++) {
	        pos = getPos(i);
	        //System.out.println(pos[0]+":"+pos[1]+":"+sudo.puzzles[randNum][i]);
	        sudo[pos[0]][pos[1]] = puzzle[i];
        }
        
		return sudo;
	}
	
	public int mapActualPos2Possible (int row, int col) {
		int mapping[][] = new int [][]
		{
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0,11,12,13,14,15,16,17,18,19},
			{0,21,22,23,24,25,26,27,28,29},
			{0,31,32,33,34,35,36,37,38,39},
			{0,41,42,43,44,45,46,47,48,49},
			{0,51,52,53,54,55,56,57,58,59},
			{0,61,62,63,64,65,66,67,68,69},
			{0,71,72,73,74,75,76,77,78,79},
			{0,81,82,83,84,85,86,87,88,89},
			{0,91,92,93,94,95,96,97,98,99}
		};
		return mapping[row][col];
	}
	
	public void printSudoku (int[][] sudoku) {
		// Display input matrix into sudoku 
		System.out.println("------ Actuals ----------");
		for (int row = 1; row < 10; row++) {
		    for (int col = 1; col < 10; col++) {
    	    	if (sudoku[row][col] == 0) 
		    		System.out.print(".");
		    	else 
		    		System.out.print(sudoku[row][col]);
		    	
		        //Add formatting
                if (col == 3 || col == 6)
                	System.out.print(" ");
                else if (col == 9)
                	System.out.println("");
		    }
            if (row % 3 ==0)
            	System.out.println("");
		}
	}
	
	public int[] getPos (int k) {
		int row = (k / 9) + 1;
		int col = (k % 9 ) + 1;
		int[] coordinate = {row,col};
		return coordinate;
	}
	
    public String leveltoString(int level) {
    	switch ( level ) {
        case 1:
            return "Easy";
        case 2:
        	return "Medium";
        case 3:
        	return "Hard";
        case 4:
        	return "Evil";
        case 5: 
        	return "Killer";
	    }
		return "Unknown";
    }
	
    public int leveltoInt (puzzleLevel level){
    	switch ( level ) {
        case easy:
            return 1;
        case medium:
        	return 2;
        case hard:
        	return 3;
        case evil:
        	return 4;
        case killer: 
        	return 5;
	    }
		return 3;
    }
    
	public int noPuzzle [][] = new int [][] 
    		{ 
    		   {0,0,0,0,0,0,0,0,0,0},	 
    		   {0,1,1,1,2,2,2,3,3,3},
    		   {0,1,1,1,2,2,2,3,3,3},
    		   {0,1,1,1,2,2,2,3,3,3},
    		   {0,4,4,4,5,5,5,6,6,6},
    		   {0,4,4,4,5,5,5,6,6,6},
    		   {0,4,4,4,5,5,5,6,6,6},
    		   {0,7,7,7,8,8,8,9,9,9},
    		   {0,7,7,7,8,8,8,9,9,9},
    		   {0,7,7,7,8,8,8,9,9,9}
    		};
    
    public int[] initPuzzle = new int []
    		{
    		   1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,
    		   4,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,6,6,6,6,6,6,6,6,6,
    		   7,7,7,7,7,7,7,7,7,8,8,8,8,8,8,8,8,8,9,9,9,9,9,9,9,9,9
    		};
    		
    public int[] toInt(char[] data) {
    	int[] toRet = new int[data.length];
    	for(int i = 0; i < toRet.length; i++) {
    	    toRet[i] = char2int(data[i]);
    	}
    	return toRet;
   	}
	
    public int char2int (char ch) {
		switch ( ch ) {
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
            	return 4;
            case '5':
            	return 5;
            case '6':
            	return 6;
            case '7':
            	return 7;
            case '8':
            	return 8;
            case '9':
            	return 9;
        }
		return 0;
	}
    
	/**
	 * Current date/time in milliseconds
	 * @return Number of milliseconds
	 */
	public long currentTimeMillis() {
		return System.currentTimeMillis();
	}
	
// ==========================================
//          FileX
// ==========================================
	public String createTxtFile () {
		Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
		return (dateFormat.format(date) + ".txt");
	}
	
	public boolean writeTxtFile (String filename, String content) {
		
		File file = new File(filename);
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
		    try {
		    	out.write(content);
		    	out.newLine();
		    	out.flush();
     		} catch (IOException e) {
			    e.printStackTrace();
		    } finally {   
		    	out.close();
		    }
     	} catch (IOException e) {
		    e.printStackTrace();
	    }
		return true;
	}
	
	/**
	 * Writes the given string into the the given file, previous file
	 * content will be overwritten.
	 *
	 * @param file            File object containing file definition.
	 * @param content         String containing content to be written.
	 * @return                True if write was successful, otherwise
	 *                        returns false. Method do not throws
	 *                        IOException.
	 */	
	public static boolean writeFile(File file, String content) {
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(content);
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
