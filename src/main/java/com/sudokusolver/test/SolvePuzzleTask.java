package com.sudokusolver.test;

public class SolvePuzzleTask implements Runnable {

    private String command;

    public SolvePuzzleTask(String s){
        this.command=s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" Start. Command = "+command);
        processCommand();
        System.out.println(Thread.currentThread().getName()+" End.");
    }

    private void processCommand() {
        try {
        	System.out.println(Thread.currentThread().getName()+" Working on Command="+command);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //@Override
    //public String toString(){
    //    return this.command;
    //}
	
	/*	public String sudo;
	public SolvePuzzleTask (String command) {
		System.out.println(Thread.currentThread().getName()+" Created...Command="+command);
	}
	
	@Override
	public void run() {
					
		try {		
		   // while (true) {
		   // 	if (TestThread.buffer.isEmpty())  {
		   // 		System.out.println("Buffer is empty !");
		   // 	    Thread.sleep((int)(1000));
		   // 	}
		   // 	else  {
		    		sudo = TestThread.buffer.take();
		    		//TestSudokuSolver tss = new TestSudokuSolver(sudo);
		    		System.out.println("Q remove - Buffer size: "+TestThread.buffer.size()+" - "+sudo);
		    		Thread.sleep(1000);
		    		//tss.printMatrix(tss.puzzle);
					//tss.solve();
		   // 	}
		//    }
	    } catch (InterruptedException ex) {
	        ex.printStackTrace();
	    }	
	}
	*/
}