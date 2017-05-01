package com.sudokusolver.solver;

import java.util.*;

import com.sudokusolver.audit.Audit;
import com.sudokusolver.controller.Controller;
import com.sudokusolver.puzzle.Sudoku;
import com.sudokusolver.utils.Utilities;

public class SudokuSolver {

	Utilities util;
	Sudoku sudoku;
	Audit audit;
	Controller controller;
	
	public class Item {
		int pos;
		int val;
	}
	
	Stack<Item> stack;
	ArrayList<Integer> emptyCells;
	int cursor;
	
	int numberOfPush;
	int numberOfPop;
	
	private long solvingStartTime;
	private long solvingEndTime;
	private double computingTime;
	
	//private String filename;
	
	// test data 1
    String solution  = "286197543154863927937524618528976134793451286641382795819735462372649851465218379";
    String mask      = "111011110001110011011100111110111101010101010101111011111001110110011100011110111";

	// test data 2
    //String solution  = "478125693219376845653849712931587426724613589865294137196452378547938261382761954";
    //String mask      = "001101111111110010110111101010011111101101101111110010101111011010011111111101100";

	// test data 3
    String testPuzzle1 = "407000805032008047000700010020437060090080400046012008200603070500200000104070203";
    
    //test data 4
    String testPuzzle2 = "400000508030000000000700000020000060000050800000010000000603070500200000108000000";
    
	//int[][] puzzle;

	private int level;
    public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	// ------ CONSTRUCTOR -------------	
	public SudokuSolver (Controller controller, 
			             Audit audit, 
			             Sudoku sudoku,
			             Utilities util) {
		this.util = util;
        this.sudoku = sudoku;
		this.audit = audit;
		this.controller = controller;
	}
	

    //----- SOLVE methods -------------	
	public int solve() {
		initSolve();
		if (emptyCells.size() == 0)
			return 10;
		solvingStartTime = util.currentTimeMillis();
		Item item = new Item();
		setLevel(0);
		boolean success = false;
		int firstFree = emptyCells.get(level);
		int row = firstFree / 10;
		int col = firstFree % 10;
		item.pos = firstFree;
		audit.checkPossibles();
		int[] num = str2IntArray(audit.printPossiblesByCellAndReturn(row, col));
		for (int i=0; i < num.length; i++) {
			item.val = num[i];
			if (sudoku.getPossibles(firstFree, num[i]) == 1) {
				setLevel(getLevel()+1);
				stack.push(item);
				numberOfPush++;
				//System.out.println("push: "+item.pos+" "+item.val+" - StackSize: "+stack.size());
				//util.writeTxtFile(filename,("push: "+item.pos+" "+item.val+" - StackSize: "+stack.size()));
				sudoku.setActuals(row,col,num[i]);
					
			    if (solve(getLevel()) ){
				    success = true;
			        break;	
			    }
				else {
				    //Item temp = new Item();
				    //temp = stack.pop();
				    numberOfPop++;
				    //System.out.println("pop : "+temp.pos+" "+temp.val+" - StackSize: "+stack.size());
				    //util.writeTxtFile(filename,("pop: "+item.pos+" "+item.val+" - StackSize: "+stack.size()));
				    setLevel(0);
				    sudoku.setActuals(row,col,0);
			    }
		    }
		}
		if (success == true) {
			solvingEndTime = util.currentTimeMillis();
			computingTime = (solvingEndTime - solvingStartTime) / 1000.0;
            printResults();
            sudoku.setSolution(sudoku.getActuals());
            return 1;
		}
		return 11;
	}
	
	public boolean solve(int stage) {
		if (getLevel() == emptyCells.size())
			return true;
		Item item = new Item();
		int freeCell = emptyCells.get(stage);
        int row = freeCell / 10;
		int col = freeCell % 10;
		item.pos = freeCell;
		audit.checkPossibles();
		int[] num = str2IntArray(audit.printPossiblesByCellAndReturn(row, col));
		for (int i=0; i < num.length; i++) {
			item.val = num[i];
			if (sudoku.getPossibles(freeCell, num[i]) == 1) {
				setLevel(getLevel()+1);
				stack.push(item);
				numberOfPush++;
				//System.out.println("push: "+item.pos+" "+item.val+" - StackSize: "+stack.size());
				//util.writeTxtFile(filename,("push: "+item.pos+" "+item.val+" - StackSize: "+stack.size()));
				sudoku.setActuals(row,col,num[i]);
				if (solve(getLevel())) 
					return true;
				else {
					setLevel(getLevel()-1);
				    //Item temp = new Item();
				    //temp = stack.pop();
				    numberOfPop++;
				    //System.out.println("pop : "+temp.pos+" "+temp.val+" - StackSize: "+stack.size());
				    //util.writeTxtFile(filename,("pop: "+item.pos+" "+item.val+" - StackSize: "+stack.size()));
					sudoku.setActuals(row,col,0);
				}
			}	
		}
		return false;
	}
	
	public void printResults() {
		System.out.println("Solution was found successfully...");
		printMatrix(sudoku.getActuals());
		System.out.println("--------------------");
		System.out.println("# of Given Cells: "+(81 - emptyCells.size()));
		System.out.println("# of Free Cells: "+emptyCells.size());
		System.out.println("Number of PUSH: "+numberOfPush);
		System.out.println("Number of POP : "+numberOfPop);
		System.out.println("Computing Time: "+computingTime+" sec");
		System.out.println("--------------------");
	}
	
	public int[] str2IntArray (String str) {
		int length = str.length();
		char[] list = str.toCharArray();
		int[] num = new int[length];
		for (int i = 0; i < length; i++) {
		    num[i] = list[i] - '0';
		}
		
		return num;
	}
	
	public void initSolve() {
		sudoku.updateActuals();
		emptyCells = new ArrayList<>();
		updateEmptyCells();
		System.out.println(emptyCells.size());
		System.out.println(emptyCells);
		
		stack = new Stack<>();
		numberOfPush = 0;
		numberOfPop = 0;
		
		computingTime = 0.0;
		
		//filename = util.createTxtFile();
	}

	public void updateEmptyCells() {
		for (int row = 1; row < 10; row++) {
		    for (int col = 1; col < 10; col++) {
		    	if (sudoku.getActuals(row,col) == 0)
		    		emptyCells.add(mapActualPos2Possible(row,col));
		    }
		}
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

	public int[] getPos (int k) {
		int row = (k / 9) + 1;
		int col = (k % 9 ) + 1;
		int[] coordinate = {row,col};
		return coordinate;
	}

//--------- Print Methods --------------------------
	public void printMatrix(int[][] sudoku) {
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

	
	
	
}
