package model;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import backtracker.Configuration;

public class SolutionSolver implements Configuration {
                /*
                choose first spot randomly out of uncovered spots
                check spots next to it
                add no-mine spot to solution List
                check around it
                add mines to mine list
                add no-mine to solution list
                jump randomly 
                check self and surrounding
                add mines to minelist and not to solution

                dequeue for output
                */

    
    public final char MINE = 'M'; 
    public final char COVERED = '-';


    private int x;
    private int y;
    private String[][] board;
    private int xJump;
    private int yJump;
    int rows;
    int cols;

    Random r = new Random();
    
    Collection<Configuration> notMines = new LinkedList<>();
    Collection<Configuration> Mines = new LinkedList<>();
    Collection<Configuration> ALLL = new LinkedList<>();
    
    public SolutionSolver(int x, int y,String[][] board){
        this.x = x;
        this.y = y;
        this.board = board;
        this.rows = board.length;
        this.cols = board[0].length;
    }
    @Override
    public Collection<Configuration> getSuccessors() {    
        
        while(isGoal() != true){
            xJump = r.nextInt(rows);
            yJump = r.nextInt(cols);
            for(int x = -1; x < 2; x++ ){
                for(int y = -1; y < 2; y++){                   
                    if (isValid()){
                        
                        Configuration e = new SolutionSolver((xJump+x),(yJump+y),board);
                        System.out.println(notMines);
                        if(board[(xJump+x)][(yJump+y)] == "[" + COVERED +  "]"){
                            notMines.add(e);
                            System.out.println(notMines);
                        }
                        if(board[(xJump+x)][(yJump+y)] == "[" + MINE +  "]"){ 
                            Mines.add(e);
                        }
                        ALLL.add(e);
                    }
                    
                }
            }
        }
        return notMines;
    }



    @Override
    public boolean isValid() {
        if (xJump < rows-1 && xJump >0 && yJump < cols-1 && yJump>0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean isGoal() {
        if(ALLL.size() > rows*cols){
            return true;
        }
        return false;
    }

    public void output(){
        System.out.println("AAAAAAAAAAAAAAAAA");
        
        for(Configuration out : notMines){
            System.out.println("Make a move at " + out);
        }

    }
    @Override
    public String toString() {
       output();
        return null;
    }
}
