package com.sudokusolver.config;

import com.sudokusolver.controller.Controller;

public class Config {
	
	
	Controller controller;
	public enum puzzleLevel {easy, medium, hard, evil, killer};
	public String easyPuzzles = "d:\\wsMaven\\SudokuSolver\\SudokuSolver\\src\\easySudoku.txt";
	public String mediumPuzzles = "d:\\wsMaven\\SudokuSolver\\SudokuSolver\\src\\mediumSudoku.txt";
	public String hardPuzzles = "d:\\wsMaven\\SudokuSolver\\SudokuSolver\\src\\hardSudoku.txt";
	public String evilPuzzles = "d:\\wsMaven\\SudokuSolver\\SudokuSolver\\src\\evilSudoku.txt";
	public String killerPuzzles = "d:\\wsMaven\\SudokuSolver\\SudokuSolver\\src\\100SudokuHard.txt";
	public String[] puzzleSource = new String[]{"file", "web"};
	public int killerLimit = 100;
	public int exampleLimit = 160; // Example puzzles listed in SudokuPuzzles class
	public int limit = 1000;
	
	public Config(Controller controller) {
		this.controller = controller;
	}

	public String getFilename (puzzleLevel level) {
		
		switch ( level ) {
        case easy:
            return easyPuzzles;
        case medium:
        	return mediumPuzzles;
        case hard:
        	return hardPuzzles;
        case evil:
        	return evilPuzzles;
        case killer: 
        	return killerPuzzles;
	    }
		return hardPuzzles;
	}	
}
