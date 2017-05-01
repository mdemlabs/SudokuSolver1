package com.sudokusolver.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import com.sudokusolver.controller.Controller;

public final class FileX {
	
	Controller controller;
	Utilities util;
	
	public FileX (Controller controller, Utilities util) {
		this.controller = controller;
		this.util = util;
	}

    public int[] readFile (String filename, int random) {
		String line ;
		int[] puzzle = new int[81];
		puzzle = util.initPuzzle;
        
		try {
		    //File file = new File("d:\\wsMaven\\SudokuSolver\\SudokuSolver\\src\\100SudokuHard.txt");
		    File file = new File(filename);
		    Scanner scanner = new Scanner(file);
		    byte count = 0;
		    while (scanner.hasNextLine()) {
                count++;
		    	line = scanner.nextLine();
		    	if (count == random) {
	                int size = line.length();
                    for (int i=0; i<size; i++) {
		        	    char ch = line.charAt(i);
		        	    puzzle[i] = util.char2int(ch);
		            }
		        }
    	    }
		    scanner.close();
		} catch (FileNotFoundException e) {
		         e.printStackTrace();
		}
		return puzzle;
    }	
	
	/**
	 * Reads file lines into separate strings stored in ArrayList.
	 *
	 * @param file            File object containing file definition.
	 * @return                If file reading was successful returns
	 *                        ArrayList String  containing each lines
	 *                        from the file content, otherwise
	 *                        returns null. Method do not throws
	 *                        IOException.
	 */
	public static final ArrayList<String> readFileLines2ArraList(File file) {
		ArrayList<String> fileContent = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			try {
				fileContent = new ArrayList<String>();
				String line = null;
				while ((line = br.readLine()) != null) {
					fileContent.add(line);
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileContent;
	}
	/**
	 * Reads file lines into separate strings stored in ArrayList.
	 *
	 * @param filePath        Full path to the file.
	 * @return                If file reading was successful returns
	 *                        ArrayList String  containing each lines
	 *                        from the file content, otherwise
	 *                        returns null. Method do not throws
	 *                        IOException.
	 */
	public static final ArrayList<String> readFileLines2ArraList(String filePath) {
		return readFileLines2ArraList(new File(filePath));
	}


	public String createTxtFile () {
		Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
		return (dateFormat.format(date) + ".txt");
	}
	
	public boolean writeTxtFile (String filename, ArrayList<String> content) {
		
		File file = new File(filename);
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter(file));
		    try {
		    	for (String item: content)
		            out.write(item);
		        out.close();
     		} catch (IOException e) {
			    e.printStackTrace();
		    }
     	} catch (IOException e) {
		    e.printStackTrace();
	    }
		return true;
	}
	
	/**
	 * Writes the given string into the the given file, previous file
	 * content will be overwritten.
	 *
	 * @param file            File object containing file definition.
	 * @param content         String containing content to be written.
	 * @return                True if write was successful, otherwise
	 *                        returns false. Method do not throws
	 *                        IOException.
	 */	
	public static boolean writeFile(File file, String content) {
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(content);
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Writes the given string into the the given file, previous file
	 * content will be overwritten.
	 *
	 * @param filePath        Full path to the file.
	 * @param content         String containing content to be written.
	 * @return                True if write was successful, otherwise
	 *                        returns false. Method do not throws
	 *                        IOException.
	 */
	public static boolean writeFile(String filePath, String content) {
		return writeFile( new File(filePath), content);
	}
	/**
	 * Removes file denoted as file path.
	 *
	 * @param filePath         File pathname.
	 * @return                 True if file was removed, false otherwise.
	 */
	public static final boolean removeFile(String filePath) {
		if (filePath == null) return false;
		if (filePath.length() == 0) return false;
		File file = new File(filePath);
		if ( file.isFile() == false) return false;
		return file.delete();
	}
	/**
	 * Returns temporary directory location.
	 * @return Temporary directory location.
	 */
	public static final String getTmpDir() {
		String tmpDir = System.getProperty("java.io.tmpdir");
		File tmp = new File(tmpDir);
		return tmp.getAbsolutePath() + File.separator;
	}
	/**
	 * Generates random file name.
	 * @param length    File name length (without extension).
	 * @param fileExt   File extension.
	 * @return          Random file name containing a-zA-Z0-9.
	 */
	public static final String genRndFileName(int length, String fileExt) {
		if (length <= 0) return null;
		if (fileExt == null) return null;
		return randomString(length) + "." + fileExt;
	}
	/**
	 * Random string generator.
	 * @param length    Length of random string.
	 * @return          Random string containing a-zA-Z0-9.
	 */
	public static final String randomString(int length) {
		if (length < 1) return "";
		char[] chars = {
							'z','x','c','v','b','n','m','a','s','d','f','g','h','j','k','l','q','w','e','r','t','y','u','i','o','p',
							'Z','X','C','V','B','N','M','A','S','D','F','G','H','J','K','L','Q','W','E','R','T','Y','U','I','O','P',
							'0','1','2','3','4','5','6','7','8','9'
				};
		String rndStr = "";
		for (int i = 0; i < length; i++)
			rndStr = rndStr + chars[ randomIndex(chars.length) ];
		return rndStr;
	}
	/* Random number generator for n returning random number between 0, 1, ... n-1.
	 *
	 * @param     n    The parameter influencing random number generation process.
	 * @return    Random number between 0, 1, ... n-1 if n is 1 or above, otherwise
	 *            error {@link ErrorCodes#SUDOKUSTORE_RANDOMINDEX_INCORRECT_PARAMETER}
	 *            is returned.
	 */
	public static final int randomIndex(int n) {
		return (int)Math.floor(Math.random() * n);
	}

}