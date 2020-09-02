import java.util.*;

public class Main
{	
	//Welcome user, take user input for initial fixed values of board
	public static void main(String[] args){
		
		//Main sudoku = new Main();
		
		System.out.println("\nWelcome to Sodoku! ");
		int [][] board = enterBoard();

		System.out.println("\nSearching: ");
		if(!isValid(board)){
			System.out.println("The input is not valid");
		}
		else if(fillBoard(board)){
			System.out.println("\nSolved Board: ");
			printBoard(board);
		}
		else{
			System.out.println("This board has no solutions");
		}
	}

	public static int [][] enterBoard(){
		Scanner input=new Scanner(System.in);
    	System.out.println("\nEnter a sudoku puzzle! ");
		System.out.println("\nDigits from 1-9 and '0' to indicate empty cells.");
		//create a double array, 9x9 board
		int[][] board = new int [9][9];
		//nested for loop to go over all cells and insert value as user inputs
        System.out.println("\nEnter values: ");	
		for(int i=0; i<9; i++){
			System.out.println("\nRow "+ (i+1));
			for(int j=0; j<9; j++){
				int value=input.nextInt();
				board[i][j] = value;
			}
		}
		System.out.println("\nGiven board: ");
		printBoard(board);
		return board;
	}
	//Get cells that are empty '.'
	private static int[][] getEmptyCells( int [][] board){
		int amountOfEmptyCells = 0;
		//nested for loop to go through all cells of double array
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(board[i][j] == 0){
					amountOfEmptyCells++;
				}
			}
		}

		//store positions of empty cells into a list
		int [][] emptyCells = new int[amountOfEmptyCells][2];
		int counter = 0;
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(board[i][j] == '.'){
					emptyCells[counter][0] = i;
					emptyCells[counter++][1] = j;
				}
			}
		}
		return emptyCells;
	}
	public static boolean fillBoard(int [][] board){
		return search(board);

	}
	//Search for a solution
	public static boolean search(int[][] board){
		int[][] emptyCells = getEmptyCells(board);
		return search(0, emptyCells, board);//start searching at emptycell 0
	}
	private static boolean search(int k, int[][] emptyCells, int[][] board){
		if(k < emptyCells.length){
			int row = emptyCells[k][0];
			int column = emptyCells[k][1];

			for(int i=1; i<10; i++){
				board[row][column] = i;
				//Recursion:
				if(isValid(row, column, board) && search(k+1, emptyCells, board)){
					return true;
				}
				else{
					//Backtracking
					//if there is no solution at current position, go back to cell before to try to find solution
					board[row][column] = 0;
					return false;
				}
			}
			
		}
		else{
			//All free cells are already solved
			return true;
		}
	}

	
	//check if position board[i][j] is valid
	private static boolean isValid(int i, int j, int[][] board){
		//check if value we want to put in position board[i][j] is not already in any cell in that row
		for(int col=0; col<9; col++){
			//compare value of each cell in that row to the desired value
			if(col!=j && board[i][col] == board[i][j]){
				return false;
			}
		}
		//check if value we want to put in position board[i][j] is not already in any cell in that column
		for(int row=0; row<9; row++){
			//compare value of each cell in that column to the desired value
			if(row!=i && board[row][j] == board[i][j]){
				return false;
			}
		}
		//check if value we want to put in position board[i][j] is not already in any cell in that 3x3 box
		//nested for loop to go over all cells in the 3x3 box
		for(int row=(i/3)*3; row<(i/3)*3+3; row++){
			for(int col=(j/3)*3; col<(j/3)*3+3; col++){
				if(row!=i && col!=j && board[row][col]==board[i][j]) {
					return false;
				}
			}
		}
		//if value in especific cell passes all three conditions, then it is valid in that position
		return true;
	}
	//check that the entered values are valid in their position
	public static boolean isValid(int [][] board){
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(board[i][j] != 0 && !isValid(i, j, board)){
					return false;
				}
			}
		}
		return true;//this proves the entered cells are valid
	}
	//Function to print the solved Sudoku Puzzle
	//solution numbers in red
	public static void printBoard(int[][] board){
		System.out.println("\n |-------|-------|-------|");

		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(j%3 == 0)
				{
					System.out.print(" |");
				}
				System.out.printf("%2d", board[i][j]);
				if(j == 8)
				{
					System.out.print(" |");
				}			
			}
			if(i == 2 || i == 5 || i == 8)
			{
				System.out.print("\n |-------|-------|-------|");
			}
			System.out.println();
		}
		System.out.println();
	}
}
