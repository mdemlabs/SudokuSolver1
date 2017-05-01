package com.sudokusolver.puzzle;

import com.sudokusolver.board.Board;
import com.sudokusolver.utils.Utilities;

public class Sudoku {

	//Controller controller;
	Utilities util;
	Board board;
	
	private int[][] originals;
	private int[][] saved;
    private int[][] actuals;
	private int[][] possibles;
	private int[][] solution;
	
	// getter-setter methods
	public int[][] getOriginals() {
		return originals;
	}
	public int getOriginals(int row, int col) {
		return originals[row][col];
	}
	public void setOriginals(int[][] originals) {
		this.originals = originals;
	}
	public void setOriginals (int row, int col, int val) {
		this.originals[row][col] = val;
	}
	public int[][] getSaved() {
		return saved;
	}
	public int getSaved(int row, int col) {
		return saved[row][col];
	}
	public void setSaved(int[][] saved) {
		this.saved = saved;
	}
	public void setSaved(int row, int col, int val) {
		this.saved[row][col] = val;
	}
	public int[][] getActuals() {
		return actuals;
	}
	public int getActuals(int row, int col) {
		return this.actuals[row][col];
	}
	public void setActuals(int[][] actuals) {
		this.actuals = actuals;
	}	
	public void setActuals(int row, int col, int val) {
		this.actuals[row][col] = val;
	}	
	public int[][] getPossibles() {
		return possibles;
	}
	public int getPossibles(int row, int col) {
		return this.possibles[row][col];
	}
	public void setPossibles(int[][] possibles) {
		this.possibles = possibles;
	}
	public void setPossibles(int row, int col, int val) {
		this.possibles[row][col] = val;
	}
	public int[][] getSolution() {
		return solution;
	}
	public int getSolution(int row, int col) {
		return solution[row][col];
	}
	public void setSolution(int[][] solution) {
		this.solution = solution;
	}
	public void setSolution(int row, int col, int val) {
		this.solution[row][col] = val;
	}

	// -------------------------
	public void updateActuals() {
		// Check each cell for possibles at first then display
		for (int row = 1; row < 10; row++) 
		    for (int col = 1; col < 10; col++)
		        if (board.textF[row][col].getText().isEmpty())
		    	    setActuals(row,col,0);
		        else 
		        	setActuals(row,col,Integer.parseInt(board.textF[row][col].getText()));
	}

	public void initBuffers () {
		setSaved(util.initMatrix(10,10, 0));
		setActuals(util.initMatrix(10,10, 0));
		setSolution(util.initMatrix(10,10, 0));
		setPossibles(util.initMatrix(100,10, 1));
	}
	
	// Constructor
	public Sudoku (Utilities util, Board board) {
		this.util = util;
		this.board = board;
		// Define and init buffers
		originals    = new int[10][10];
		saved        = new int[10][10];
		actuals      = new int[10][10];
		solution     = new int[10][10];
		possibles    = new int[100][10];
		initBuffers();
		setOriginals(util.initMatrix(10,10, 0));
	}
}
