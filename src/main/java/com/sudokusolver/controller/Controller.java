package com.sudokusolver.controller;

import com.sudokusolver.app.App;
import com.sudokusolver.audit.Audit;
import com.sudokusolver.config.Config;
import com.sudokusolver.config.Config.puzzleLevel;
import com.sudokusolver.utils.Utilities;
import com.sudokusolver.view.View;
import com.sudokusolver.board.Board;
import com.sudokusolver.puzzle.Sudoku;
import com.sudokusolver.solver.SudokuSolver;
import com.sudokusolver.store.SudokuPuzzles;

import javafx.stage.Stage;

public class Controller {
	
	App app;
	Board board;
	Utilities util;
	Config config;
	Sudoku sudoku;
	Audit audit;
	SudokuSolver solver;
	View view;
	
	int[][] puzzleX;
	
// --------------- Constructor -------------------
	public Controller(App app) {
    	this.app = app;		
		config = new Config(this);
    	util   = new Utilities();
    	board  = new Board(this);
    	sudoku = new Sudoku(util, board);
    	audit  = new Audit(util, sudoku);
    	view   = new View(this, sudoku, board, util);
    	solver = new SudokuSolver(this, audit, sudoku,util);
	}
	
// -------------- Load Sudoku Board ----------------
	public void loadBoard (Stage primaryStage) {
		board.load(primaryStage);
	}
	
// -------- Load Puzzle ---------------------------- 
	private void loadPuzzle (puzzleLevel level) {
		load(level);
		view.updateCells(sudoku.getActuals());
	}

    private void load (puzzleLevel level) {
        if (level == puzzleLevel.killer) {
    	    System.out.println("Killer puzzle is loaded from disk...");
            int randNum = util.generateRandomNumber(config.killerLimit);
            System.out.println(randNum);
            sudoku.setActuals(util.convert2SudokuMatrix(util.readFile(config.getFilename(level),randNum)));
            displayPuzzle();
        }  
        else {
	        System.out.println("Example puzzle is loaded...");
            int randNum = util.generateRandomNumber(config.exampleLimit);
 	        sudoku.setActuals(adaptExamplePuzzle2SudokuMatrix(SudokuPuzzles.getPuzzleExample(randNum)));
 	        displayPuzzle();
        }
    }
    
    public void displayPuzzle () {
    	int[][] generated = sudoku.getActuals();
		for (int row = 1; row < generated.length; row++)
		    for (int col=1; col < generated[row].length; col++)
		        System.out.print(generated[row][col]);
    }

	public int[][] adaptExamplePuzzle2SudokuMatrix (int[][] p) {
		int[][] z = new int[10][10];
		for (int i = 0; i < 9; i++) 
		    for (int k = 0; k < 9; k++)
		       	z[i+1][k+1] = p[i][k];
		return z;
	}
// -------- Action procedures ----------------------
	
	public void actionEasy () {
		actionReset();
		loadPuzzle(puzzleLevel.easy);
	}
	
	public void actionMedium () {
		actionReset();
		loadPuzzle(puzzleLevel.medium);
	}
	
	public void actionHard () {
		actionReset();
		loadPuzzle(puzzleLevel.hard);
	}
	
	public void actionEvil () {
		actionReset();
		loadPuzzle(puzzleLevel.evil);
	}
	
	public void actionKiller () {
		actionReset();
		loadPuzzle(puzzleLevel.killer);
	}
	
	public void actionBack2Originals() {
		view.resetCells();
        sudoku.setActuals(sudoku.getOriginals());
        view.updateCells(sudoku.getActuals());
	}
	
	public void actionSave() {
		for (int row = 1; row < 10; row++) 
	        for (int col = 1; col < 10; col++) {
	        	// any positive or negative integer or not !
	        	if (board.textF[row][col].getText().matches("-?\\d+"))  { 
                    sudoku.setSaved(row,col,Integer.parseInt(board.textF[row][col].getText()));
	        	}
	        	else
	        		sudoku.setSaved(row,col,0);     			
	        }
	}
	public void actionBack2SavedNumbers() {
		sudoku.setActuals(sudoku.getSaved());
		view.updateCells(sudoku.getActuals());
		
	}
	
	public void actionReset() {
		sudoku.initBuffers();
		view.resetCells();
	}
	
	public void actionShowPossibles () {
		audit.checkPossibles();
		view.updatePossiblesView();
	}
	
	public void actionSolveIt () {
		solver.solve();
		view.updateCells(sudoku.getSolution());
	}

}
