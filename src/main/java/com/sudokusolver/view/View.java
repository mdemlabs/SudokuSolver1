package com.sudokusolver.view;

import com.sudokusolver.board.Board;
import com.sudokusolver.controller.Controller;
import com.sudokusolver.puzzle.Sudoku;
import com.sudokusolver.utils.Utilities;

public class View {
	
	Controller controller;
	Sudoku sudoku;
	Board board;
	Utilities util;
	
	public View (Controller controller, Sudoku sudoku, Board board, Utilities util) {
		this.controller = controller;
		this.sudoku = sudoku;
		this.board = board;
		this.util = util;
	}

	public void updateCells(int[][] matrix) {
		for (int row = 1; row < 10; row++) 
	        for (int col = 1; col < 10; col++) {
	        	board.text[row][col].setText("");
	        	if (matrix[row][col] != 0)
	        	    board.textF[row][col].setText(String.valueOf(matrix[row][col]));
	        	else
	        		board.textF[row][col].setText("");
	        }
	}
	
	public void resetCells() {
		for (int row = 1; row < 10; row++) 
	        for (int col = 1; col < 10; col++) {
	        	board.textF[row][col].clear();
	        	board.text[row][col].setText("");        			
	        }
	}
	
	public void hidePossiblesView () {
		for (int row = 1; row < 10; row++) 
	        for (int col = 1; col < 10; col++)
		        board.text[row][col].setText(""); 
	}
	
	public void updateDigitsByCell (int row, int col) {
		
	}
	
	public void updatePossiblesView () {
		for (int row = 1; row < 10; row++) 
	        for (int col = 1; col < 10; col++)
	        	updatePossiblesViewByCell(row,col);
	}
	
	public void updatePossiblesViewByCell (int row, int col) {
		String str = "";
		int pos = util.mapActualPos2Possible(row,col);
		if (sudoku.getPossibles(pos,0) == 0)
		    for (int i=1; i<10; i++) 
			    if (sudoku.getPossibles(pos,i) == 1) 
			        str = str + i;
		board.text[row][col].setText(str);
	}
}
