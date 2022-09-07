package model;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Minesweeper {
    
    

    private int rRow;
    private int rCol;
    private int rows;
    private int cols;
    private int Countmine;
    private int currentScore = 0;
    protected GameState StateofGame = GameState.NOT_STARTED;

    public final char MINE = 'M'; 
    public final char COVERED = '-';
    public final char UNCOVERED = ' ';
    public String[][] board;
    public char[][] symbol;

    public Minesweeper(int rows, int cols, int mineCount){
        //board w/all being covered - Tucker
        String[][] board = new String[rows][cols];
        char[][] symbol = new char[rows][cols];
        for(int c = 0; c<cols;c++){
            for(int r = 0; r<rows; r++){
                board[r][c] = "[ ]";
                symbol[r][c] = COVERED;
            }
        }
        //adds the mines to the board not properly but its a start to see where they go - Tucker
        Random random = new Random(0);
        for(int i = 0; i<mineCount; i++){
            rCol = random.nextInt(cols);
            rRow = random.nextInt(rows);
            board[rRow][rCol] = "[" + MINE +  "]";}
            symbol[rRow][rCol] = MINE;  //WHY DOESNT THIS WORK
        this.board = board;
        this.symbol = symbol;
        this.cols = cols;
        this.rows = rows;
        this.StateofGame = GameState.IN_PROGRESS;
    }

    public int getCol(){return this.cols;}
    public int getRow(){return this.rows;}
    public int getMineCount(){return this.Countmine;}
    public GameState getGameState(){return this.StateofGame;}

    public char getSymbol(Location location){
        int r = location.getRow()-1;
        int c = location.getCol()-1;
        return symbol[r][c];
    }
    public boolean VerifyCovered(Location location){
        int col = location.getCol();
        int row = location.getRow();

        if (board[col][row] == "[" + COVERED +  "]"){
            return true;
        }

        return false;
        
    }

    public void makeSelection(Location location)throws MinesweeperException{
        int col = location.getCol();
        int row = location.getRow();
        if (col > this.getCol() -1 || col < 0 || row > this.getRow() -1 || row < 0){
            System.out.println("Your move was illegal");
        }
        else{
            if (this.board[col][row] == "[" + MINE +  "]"){
                this.StateofGame = GameState.LOST; //no need to return anything since after this code in the makeMove function, it checks if we lost or not.
                System.out.println(board);
            }
            else if (this.board[col][row] == "[" + COVERED +  "]"){
                System.out.println("You already covered this coordinate on the board");}
            // else if (this.board[col][row] == "[" + UNCOVERED +  "]"){
            //     System.out.println("You already uncovered this coordinate on the board");
            // }
            else {
                this.board[col][row] = "[" + COVERED +  "]";
                currentScore++;
                System.out.println(board);
                if (currentScore == ((this.getCol() * this.getRow()) - this.getMineCount())){
                    this.StateofGame = GameState.WON;}}}       
}
    
    
    
    public Collection<Location> getPossibleSelections(){ 
        List<Location> e = new ArrayList<>();
        // board[0][0] = "[" + COVERED +  "]"; ////for testing
        for(int c = 0; c<cols;c++){
            for(int r = 0; r<rows; r++){
                if (board[r][c] != "[" + COVERED +  "]"){
                    e.add(new Location(r, c));}
            }}
        return e;
    }
    

    @Override
    //Fixed this dont try to do anything else with strings :) -tucker
    public String toString() { //author: Tucker
        for(int r = 0; r<rows;r++){
            System.out.print("\n");
            for(int c = 0; c<cols; c++){
                System.out.print(board[r][c]);
            }
        }
        return "";
    }

    public static void makeMove(Minesweeper game) throws MinesweeperException{ //author:Ivan
        if (game.getGameState() == GameState.LOST){
            System.out.println("You Lost");
            return;
        }
        else{

        Scanner sc = new Scanner(System.in);
        System.out.println("Make your move: ");
        String input = sc.nextLine();
        String[] aStringArray = input.split(" ");
        String choice = aStringArray[0]; //im not sure why this code doesn't work, it works when im debugging it but when I'm compiling it, it just gives runs the else (nothing true) option.
        if (choice.equals("help")){ 
            System.out.println("help - Displays a list of commands.\n pick row col - Attempts to uncover a cell at location row, col.\n hint - Displays avaliable moves.\n reset - resets to a new game with same board size. \n quit - displays a goodbye message and the method exists.");
            makeMove(game);}

        else if (choice.equals("pick")){
            game.makeSelection(new Location(Integer.parseInt(aStringArray[1]), Integer.parseInt(aStringArray[2])));
            makeMove(game);}

        else if (choice.equals("hint")){
            System.out.println("Here are the avaliable moves");
            System.out.println(game.getPossibleSelections());
            makeMove(game);
        }
        else if (choice.equals("reset")){
            System.out.println("Game has been reset");
            game = new Minesweeper(game.getRow(), game.getCol(), game.getMineCount());
            makeMove(game);
        }
        else if (choice.equals("quit")){
            System.out.println("You have successfully quit the game");
            sc.close();
        }
        else{
            System.out.println("Invalid choice, try again");
            makeMove(game);
        }}
    }

    private Set<MinesweeperObserver> Observers;
    public void register(MinesweeperObserver observer){ 
        Observers.add(observer);
    }
    protected void notifyObserver(Location location){
        for(MinesweeperObserver observer: Observers){observer.cellUpdated(location);}
    }
    public boolean isCovered(Location location){
        int r = location.getRow();
        int c = location.getCol();
        return symbol[r][c] == COVERED;
    }

    public int nextSquares(Location location){
        int minecounter = 0;
        int r = location.getRow();
        int c = location.getCol();
        for(int x = -1; x < 2; x++ ){
            for(int y = -1; y < 2; y++){
                if (r+x < rows+1 && r+x>-1 && c+y < cols+1 && c+y>-1){
                    if(board[r+x][c+y] == "[" + MINE +  "]"){
                        minecounter += 1;
                    }
                }
            }
        }
        return minecounter;
    }


    public static void main(String[] args) throws MinesweeperException {
        Minesweeper test = new Minesweeper(5, 5, 5);
        
        //System.out.println(test.getPossibleSelections()); 
        System.out.print(test);
        Location location = new Location(3,3);
        //System.out.println(test.getSymbol(location));
        
        System.out.println(test.nextSquares(location));
        
        //System.out.println(test.getGameState());
    
        SolutionSolver ss = new SolutionSolver(5,5,test.board);
        System.out.println(ss);
       
        // test.StateofGame = GameState.LOST;
        makeMove(test);
        System.out.println(test);
        // System.out.println(test.getPossibleSelections()); 
        // System.out.print(test);
        // Location location = new Location(3, 3);
        // System.out.println(test.nextSquares(location));
        // System.out.println(test.getGameState());
        // Minesweeper test2 = new Minesweeper(10, 8, 4);
        // System.out.println(test2.getPossibleSelections()); 
        // System.out.print(test2);
        // Location location = new Location(3, 3);
        // System.out.println(test2.getSymbol(location));
        // System.out.println(test2.nextSquares(location));
        // System.out.println(test2.getGameState());
    
        // SolutionSolver ss = new SolutionSolver(5,5,test2.symbol);
        // System.out.println(ss);
    }
}
