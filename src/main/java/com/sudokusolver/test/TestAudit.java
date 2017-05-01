package com.sudokusolver.test;

import com.sudokusolver.puzzle.Sudoku;
import com.sudokusolver.utils.Utilities;

public class TestAudit {

	Sudoku sudoku;
	Utilities util;
	
	public TestAudit(Utilities util, Sudoku sudoku) {
		this.util = util;
		this.sudoku = sudoku;
	}
	
	public void ShowPossibles () {
		//System.out.println("Show Possibles button was clicked...");
		
		//At first get actuals and print to the screen
		//updateActuals();
		printSudoku(sudoku.getActuals());
		
		//Then find possibles by checking each cell applying square/row/col tests
		checkPossibles();
		
		//Now we know possibles and we can print 
		printPossibles();
		
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
	
	public void checkPossibles() {
	   	// Check each cell for possibles at first then display
		//System.out.println("  Position  > SqChk RowChk ColChk ");
		//System.out.println("----------------------------------");
		sudoku.setPossibles(util.initMatrix(100,10,1));
		for (int row = 1; row < 10; row++) 
	        for (int col = 1; col < 10; col++) {
	        	//System.out.print("row="+row+" col="+col+" > ");
	        	setActual2Possibles(row,col);
	        	if (sudoku.getActuals(row,col) != 0) {
	        		//System.out.println("  "+ sudoku.getActuals(row,col));
	        		continue;
	        	}
	        	
	        	if (!doSquareCheck(row,col))
	        		System.out.println("Square check failed !!!");
	        		        	
	        	if (!doRowCheck(row,col)) 
	        		System.out.println("Row check failed !!!");
	        	
	        	if (!doColCheck(row,col))
	        	    System.out.println("Col check failed !!!");
	        	
	        	//Print possibles to the screen
	        	//printPossiblesByCell(row, col);
	        	//System.out.println("");
	        	//doCrossCheck
		        //...
	        }
	}
	
	public void printPossibles() {
		System.out.println("  Position  > SqChk RowChk ColChk ");
		System.out.println("----------------------------------");
		for (int row = 1; row < 10; row++) 
	        for (int col = 1; col < 10; col++) {
	        	System.out.print("row="+row+" col="+col+" > ");
	        	printPossiblesByCell(row,col);
	        }
	}
	
	public void printPossiblesByCell(int row, int col) {
		String str = "";
		int pos = mapActualPos2Possible(row,col);
		if (sudoku.getPossibles(pos,0) == 0)
		    for (int i=1; i<10; i++) {
			    if (sudoku.getPossibles(pos,i) == 1) {
			        str = str + i;
			    }
		    }
		//board.text[row][col].setText(str);
		System.out.println(str);
	}

	public String printPossiblesByCellAndReturn(int row, int col) {
		String str = "";
		int pos = mapActualPos2Possible(row,col);
		if (sudoku.getPossibles(pos,0) == 0)
		    for (int i=1; i<10; i++) {
			    if (sudoku.getPossibles(pos,i) == 1) {
			        str = str + i;
			    }
		    }
		//board.text[row][col].setText(str);
		System.out.println(str);
		return str;
	}
	
	
	public void setActual2Possibles(int row, int col) {
		// Map actual coordinate to possible coordinate
		int pos = mapActualPos2Possible(row,col);
		sudoku.setPossibles(pos,0,sudoku.getActuals(row,col));
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
	
	public boolean doSquareCheck(int ro, int co) {
		int[] checkNum = new int [] {0,1,1,1,1,1,1,1,1,1};
		int[] origin = getSquareStartCoordinate(getSquare(ro,co));
		for (int row = origin[0]; row < origin[0]+3; row++)
			for (int col = origin[1]; col < origin[1]+3; col++)
				if (sudoku.getActuals(row,col) != 0)
				    checkNum[sudoku.getActuals(row,col)] = 0;

		//for (int i=1; i<10; i++) 
			//System.out.print(checkNum[i]);

		//System.out.print("  ");
		updateCellPossibles(ro,co,checkNum);

    	return true;
	}
	
	public boolean doRowCheck(int row, int col) {
		int[] checkNum = new int [] {0,1,1,1,1,1,1,1,1,1};
		for (int i=1; i<10;i++)
			if (sudoku.getActuals(row,i) != 0)
			    checkNum[sudoku.getActuals(row,i)] = 0;

		//System.out.print("  ");
		//for (int i=1; i<10; i++) 
		//	  System.out.print(checkNum[i]);

		//System.out.print("  ");
		updateCellPossibles(row,col,checkNum);
		return true;
	}
	
	public boolean doColCheck(int row, int col) {
		int[] checkNum = new int [] {0,1,1,1,1,1,1,1,1,1};
		for (int i=1; i<10;i++)
			if (sudoku.getActuals(i,col) != 0)
			    checkNum[sudoku.getActuals(i,col)] = 0;

		//System.out.print("  ");
		//for (int i=1; i<10; i++) 
		//	  System.out.print(checkNum[i]);
		
		//System.out.print("  ");
		updateCellPossibles(row,col,checkNum);
		return true;
	}	

	public void updateCellPossibles (int row, int col, int[] data) {
        int pos = mapActualPos2Possible(row,col);
    	for (int k=1; k<10; k++) {
       	    if (sudoku.getPossibles(pos,k) != data[k]) {
       	    	sudoku.setPossibles(pos,k,0);
       	    }
       	    //System.out.print(sudoku.getPossibles(pos,k));
       	}
	}

	/*
	public void updateActuals() {
		// Check each cell for possibles at first then display
		for (int row = 1; row < 10; row++) 
		    for (int col = 1; col < 10; col++)
		        if (board.textF[row][col].getText().isEmpty())
		    	    sudoku.setActuals(row,col,0);
		        else 
		        	sudoku.setActuals(row,col,Integer.parseInt(board.textF[row][col].getText()));
	}
	*/
	
	public int getSquare (int row, int col) {
		int squarePosMatrix [][] = new int [][] 
		{ 
			{0, 0,0,0, 0,0,0, 0,0,0},
			{0, 1,1,1, 2,2,2, 3,3,3},
		    {0, 1,1,1, 2,2,2, 3,3,3},
		    {0, 1,1,1, 2,2,2, 3,3,3},
		    
		    {0, 4,4,4, 5,5,5, 6,6,6},
		    {0, 4,4,4, 5,5,5, 6,6,6},
		    {0, 4,4,4, 5,5,5, 6,6,6},
		    
		    {0, 7,7,7, 8,8,8, 9,9,9},
		    {0, 7,7,7, 8,8,8, 9,9,9},
		    {0, 7,7,7, 8,8,8, 9,9,9}
		};
		
		return squarePosMatrix [row][col];
	}
	
	public int[] getSquareStartCoordinate (int squareNumber) {
		int[] startCoordinates = null;
		switch ( squareNumber )
        {
            case 1:
                startCoordinates = new int[]{1,1};
                break;
            case 2:
                startCoordinates = new int[]{1,4};
                break;
            case 3:
                startCoordinates = new int[]{1,7};
                break;
            case 4:
                startCoordinates = new int[]{4,1};
                break;
            case 5:
                startCoordinates = new int[]{4,4};
                break;
            case 6:
                startCoordinates = new int[]{4,7};
                break;
            case 7:
                startCoordinates = new int[]{7,1};
                break;
            case 8:
                startCoordinates = new int[]{7,4};
                break;
            case 9:
                startCoordinates = new int[]{7,7};
                break;
        }
		return startCoordinates;
	}
}

