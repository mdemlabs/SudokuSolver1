package com.sudokusolver.audit;


import com.sudokusolver.puzzle.Sudoku;
import com.sudokusolver.utils.Utilities;

public class Audit {

	Sudoku sudoku;
	Utilities util;
	
	public Audit(Utilities util, Sudoku sudoku) {
		this.util = util;
		this.sudoku = sudoku;
	}
	
	public void checkPossibles() {
		sudoku.setPossibles(util.initMatrix(100,10,1));
		for (int row = 1; row < 10; row++) 
	        for (int col = 1; col < 10; col++) {
	        	setActual2Possibles(row,col);
	        	if (sudoku.getActuals(row,col) != 0)
	        		continue;
	        	
	        	if (!doSquareCheck(row,col))
	        		System.out.println("Square check failed !!!");
	        		        	
	        	if (!doRowCheck(row,col)) 
	        		System.out.println("Row check failed !!!");
	        	
	        	if (!doColCheck(row,col))
	        	    System.out.println("Col check failed !!!");
	        }
	}
	
	public boolean doSquareCheck(int ro, int co) {
		int[] checkNum = new int [] {0,1,1,1,1,1,1,1,1,1};
		int[] origin = getSquareStartCoordinate(getSquare(ro,co));
		for (int row = origin[0]; row < origin[0]+3; row++)
			for (int col = origin[1]; col < origin[1]+3; col++)
				if (sudoku.getActuals(row,col) != 0)
				    checkNum[sudoku.getActuals(row,col)] = 0;

		updateCellPossibles(ro,co,checkNum);
    	return true;
	}
	
	public boolean doRowCheck(int row, int col) {
		int[] checkNum = new int [] {0,1,1,1,1,1,1,1,1,1};
		for (int i=1; i<10;i++)
			if (sudoku.getActuals(row,i) != 0)
			    checkNum[sudoku.getActuals(row,i)] = 0;

		updateCellPossibles(row,col,checkNum);
		return true;
	}
	
	public boolean doColCheck(int row, int col) {
		int[] checkNum = new int [] {0,1,1,1,1,1,1,1,1,1};
		for (int i=1; i<10;i++)
			if (sudoku.getActuals(i,col) != 0)
			    checkNum[sudoku.getActuals(i,col)] = 0;

		updateCellPossibles(row,col,checkNum);
		return true;
	}	

	public void updateCellPossibles (int row, int col, int[] data) {
        int pos = util.mapActualPos2Possible(row,col);
    	for (int k=1; k<10; k++) {
       	    if (sudoku.getPossibles(pos,k) != data[k]) {
       	    	sudoku.setPossibles(pos,k,0);
       	    }
       	}
	}

//----------------------------------------------------------
//----------------------------------------------------------
	
	public void printPossibles() {
		String str = "";
		System.out.println("  Position  > SqChk RowChk ColChk ");
		System.out.println("----------------------------------");
		for (int row = 1; row < 10; row++) 
	        for (int col = 1; col < 10; col++) {
	        	System.out.print("row="+row+" col="+col+" > ");
	        	str = printPossiblesByCellAndReturn(row,col);
	        	System.out.println(str);
	        }
	}
	
	public String printPossiblesByCellAndReturn(int row, int col) {
		String str = "";
		int pos = util.mapActualPos2Possible(row,col);
		if (sudoku.getPossibles(pos,0) == 0)
		    for (int i=1; i<10; i++) {
			    if (sudoku.getPossibles(pos,i) == 1) {
			        str = str + i;
			    }
		    }
		return str;
	}
	
	public void setActual2Possibles(int row, int col) {
		// Map actual coordinate to possible coordinate
		int pos = util.mapActualPos2Possible(row,col);
		sudoku.setPossibles(pos,0,sudoku.getActuals(row,col));
	}
		
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
