package com.sudokusolver.others;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
//import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CreateSudokuDB {

	public static PreparedStatement preparedStatement;
	//public enum puzzleLevel {easy, medium, hard, evil, killer};
	//static Document doc, pdoc;
	
    public static void createNewTable() throws ClassNotFoundException, IOException
    {
        // load the sqlite-JDBC driver using the current class loader
    	//Class.forName("org.sqlite.JDBC");
    	
    	// SQLite connection string
        String url = "jdbc:sqlite:D://sqlite/db/tests.db";
       
        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            
        	// create sudoku table
            stmt.execute("drop table if exists sudoku");
        	stmt.execute("create table sudoku "
            		+ "(id integer PRIMARY KEY, level text string NOT NULL, pattern text string NOT NULL, mask text string NOT NULL)");

        	// create tuple template and statement for insertion
        	String tuple = "insert into sudoku values (?, ?, ?, ?)";
        	
        	// Create a statement
            preparedStatement = conn.prepareStatement(tuple);
        	
        	//get puzzle from web and insert into DB
        	String[] level = {" ", "Easy","Medium","Hard","Evil"};
        	int counter = 0;
        	for (int p=1; p<=4; p++) {
        		for (int i=1; i<=100; i++) {
        			counter++;
        			// Frist get the requested level of puzzle from websudoku page
        	    	// Level = 1/Easy, 2/Medium, 3/Hard, 4/Evil
        			String link = "http://show.websudoku.com/?level="+p;
        			System.out.println("-"+level[p]+"- Iteration number: "+counter);
        			
        			//new Thread(new WebBot(link)).start();
        			
        	        Document doc = Jsoup.connect(link).get();
        	    	Document pdoc = Jsoup.parse(doc.toString());
        	    	String strPtrn = pdoc.getElementById("cheat").attr("value");
        	    	String strMask = pdoc.getElementById("editmask").attr("value");
        	    	System.out.println(strPtrn);
        	    	System.out.println(strMask);
        	    	
        	    	// Now fill the template
        	    	preparedStatement.setInt(1, counter);
        	    	preparedStatement.setString(2, level[p]);
        	    	preparedStatement.setString(3, strPtrn);
        	    	preparedStatement.setString(4, strMask);
        	    		    	
        	    	// add tuples into table
        	    	preparedStatement.executeUpdate();
        	    	//int result = preparedStatement.executeUpdate();
        	    	//System.out.println("Result: "+result);
        	    	//System.out.println("--------------------------------------");
        		}
        	}
            
            // display entries in a file
        	// first, execute query for getting everything
            ResultSet rs = stmt.executeQuery("select * from sudoku");
            
            // second create a file
            File file = new File("d:\\wsMaven\\SudokuSolver\\SudokuSolver\\src\\SudokuBank.txt");
            PrintWriter output = new PrintWriter(file);
            
            // now lets write data from DB to file
            output.println("  id  "+" Level "+" Pattern "+" Mask ");
            output.println("-------------------------------------");
            while(rs.next()) {
            	// write to file
            	output.print(rs.getInt("id")+";  ");
            	output.print(rs.getString("level")+";  ");
            	output.print(rs.getString("pattern")+";  ");
            	output.println(rs.getString("mask"));
            	// write to screen for verification
            	System.out.print(rs.getInt("id")+"; ");
            	System.out.print(rs.getString("level")+";  ");
            	System.out.print(rs.getString("pattern")+";  ");
            	System.out.println(rs.getString("mask"));
            }
            
            // close file i/o
            output.close();
         
        } catch (SQLException e) {
        	System.out.println(e.toString());
        	System.out.println(e.getMessage());
        }
    }

/*    
    public static class WebBot implements Runnable {
    	private String link;
    	
    	public WebBot (String l) {
    		link = l;
    	}
    	
    	@Override
    	public void run () {
    		try {
				doc = Jsoup.connect(link).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
*/    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
			createNewTable();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

}
