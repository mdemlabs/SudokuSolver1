package com.sudokusolver.app;

import com.sudokusolver.controller.Controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
	
	//---Controller class 
	Controller controller;
	
	//---Main method
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {

    	controller = new Controller(this);    	
 	
    	controller.loadBoard(primaryStage);
    }
}