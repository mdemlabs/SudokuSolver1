package com.sudokusolver.board;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.sudokusolver.controller.*;

public class Board {

	Controller controller;
	
// ------- GUI Definitions -----------
	
	// Pane/scene definitions
	public HBox mainPane;
	public Scene scene;
	public GridPane grid;
    public VBox controlPane;

    // Cell definitions
    public VBox[][] cells;
	public TextField[][] textF;
	public Text[][] text;

	// Button definitions
	public Button easyBtn;
	public Button mediumBtn;
	public Button hardBtn;
	public Button evilBtn;
	public Button killerBtn;
	public Button keepOriginalsBtn;
	public Button back2OriginalsBtn;
	public Button saveBtn;
	public Button back2SaveBtn;
	public Button resetBtn;
	public Button hintBtn;
	public Button showPossibles;
	public Button hidePossibles;
	public boolean keepOriginalsBtnPressed = false;
	
	public Board (Controller controller) {
        this.controller = controller;
	}
	
	public void load (Stage primaryStage) {
    	// Set title at first
    	primaryStage.setTitle("-- Sudoku Solver --");
        
    	// Create a main container pane as HBox
    	mainPane = new HBox();
    	setupMainPane(mainPane);
    	
    	// Locate pane to a scene and connect to stage
        scene = new Scene(mainPane, 720, 600);
        primaryStage.setScene(scene);
              
        // lets start show !!!
        primaryStage.show();
    }        
    
    //---------------------------------------------------------------------------
    // here is the layers;
    //     cells      |  TextField + Text         |                            |
    // unit container |          VBox             | Button's (control buttons) |
    // containers     | (sudoku matrix) GridPane  |  VBox (controlPane)        |
    // mainPane       ~~~~~~~~~~~~~~~~~~~~~~~~  HBox  ~~~~~~~~~~~~~~~~~~~~~~~~~~
    //
    //      ------------------------------------------------------ 
    //      | HBox                                               |
    //      |   ------------------------------  ---------------- |  
    //      |   | GridPane                   |  | VBox         | |
    //      |   |    ------------------      |  |  ----------- | | 
    //      |   |    | -------------- |      |  |  |  Button | | |
    //      |   |    | |  TextField | |      |  |  ----------- | |
    //      |   |    | |  - Digit - | |      |  |       .      | |
    //      |   |    | -------------- |      |  |       .      | |
    //      |   |    | -------------- | .... |  |       .      | |
    //      |   |    | |    Text    | |      |  |              | |
    //      |   |    | |  Possibles | |      |  |              | |
    //      |   |    | -------------- |      |  |              | |
    //      |   |    ------------------      |  |              | |
    //      |   ------------------------------  ---------------- |
    //      |                                                    |
    //      ------------------------------------------------------
    //
     //----------------------------------------------------------------------------
    private void setupMainPane (HBox mainPane) {
    	// Declare container grid pane
        grid = new GridPane();
        setupGridPane(grid);
        // and declare menu/button container as VBox
        controlPane = new VBox(10);
        setupControlPane(controlPane);
 
        mainPane.getChildren().addAll(grid,controlPane);
    }
    
    private void setupGridPane (GridPane grid) {
        // Details and format of grid pane
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(4);
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.setMinSize(600, 600);
        grid.setMaxSize(600, 600);
        grid.setStyle("-fx-background-color: gray");
        
        //Declare cells in the container grid pane 
        cells = new VBox[10][10];
        textF = new TextField[10][10];
        text = new Text[10][10];
        
        for (int row = 1; row < 10; row++) 
        	for (int col = 1; col < 10; col++) {
                VBox template = new VBox();
                template.setAlignment(Pos.TOP_CENTER);
                template.setPadding(new Insets(5, 5, 5, 5));
                template.setStyle("-fx-background-color: gold");
                cells[row][col] = template;
                
                TextField tempTextF = new TextField();
                tempTextF.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
                textF[row][col] = tempTextF;
        	    
                Text tempText = new Text();
        	    tempText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        	    tempText.setFill(Color.BROWN);
        	    tempText.setText("");
        	    text[row][col] = tempText;
        	    
        	    cells[row][col].getChildren().addAll(textF[row][col],text[row][col]);
           		if ((col > 3 && col < 7) && (row < 4 || row > 6))
        			cells[row][col].setStyle("-fx-background-color: orange");
        		if ((row > 3 && row < 7) && (col < 4 || col > 6))
        			cells[row][col].setStyle("-fx-background-color: orange");
        		grid.add(cells[row][col], col, row);
            }
    }   
    
    private void setupControlPane (VBox controlPane) {
        // details and format of vPane
        controlPane.setAlignment(Pos.CENTER);
        controlPane.setPadding(new Insets(5,5,5,5));
        controlPane.setMinSize(120, 600);
        controlPane.setMaxSize(120, 600);
        controlPane.setStyle("-fx-background-color: brown");     
        
        easyBtn      = new Button("   Easy   ");
        easyBtn.setMinSize(100, 20);
        easyBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Easy selected...");
				controller.actionEasy();
			}
		});
        
        mediumBtn      = new Button("  Medium  ");
        mediumBtn.setMinSize(100, 20);
        mediumBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				controller.actionMedium();
			}
		});
        
        hardBtn      = new Button("   Hard   ");
        hardBtn.setMinSize(100, 20);
        hardBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				controller.actionHard();
			}
		});
        
        evilBtn      = new Button("   Evil   ");
        evilBtn.setMinSize(100, 20);
        evilBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				controller.actionEvil();
			}
		});
        
        killerBtn      = new Button("  Killer  ");
        killerBtn.setMinSize(100, 20);
        killerBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				controller.actionKiller();
			}
		});
    
        back2OriginalsBtn      = new Button("Back2Originals");
        back2OriginalsBtn.setMinSize(100, 20);
        back2OriginalsBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				controller.actionBack2Originals();
			}
		});
        
        saveBtn      = new Button("Save         ");
        saveBtn.setMinSize(100, 20);
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				controller.actionSave();
			}
		});
        
        back2SaveBtn      = new Button("Back2Saved    ");
        back2SaveBtn.setMinSize(100, 20);
        back2SaveBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				controller.actionBack2SavedNumbers();
			}
		});
        
        resetBtn      = new Button("Reset         ");
        resetBtn.setMinSize(100, 20);
        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
                controller.actionReset();
			}
		});

        showPossibles = new Button("Show Possibles");
        showPossibles.setMinSize(100, 20);
        showPossibles.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				controller.actionShowPossibles();
			}
        });
        
        hidePossibles = new Button("Hide Possibles");
        hidePossibles.setMinSize(100, 20);
        hidePossibles.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				for (int row = 1; row < 10; row++) 
			        for (int col = 1; col < 10; col++)
				        text[row][col].setText("");
			}
        });

        hintBtn       = new Button("  SolveIt!  ");
        hintBtn.setMinSize(100, 20);
        hintBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				controller.actionSolveIt();
		    }
        }); 
        
        controlPane.getChildren().addAll(easyBtn, 
        		                         mediumBtn, 
        		                         hardBtn, 
        		                         evilBtn,
        		                         killerBtn,
        		                         back2OriginalsBtn,
        		                         saveBtn,
        		                         back2SaveBtn,
        		                         resetBtn, 
        		                         showPossibles, 
        		                         hidePossibles,
        		                         hintBtn);                    
    
	}
	
}
