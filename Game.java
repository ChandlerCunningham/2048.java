import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import api.Direction;
import api.Move;
import api.TilePosition;


public class Game
{
	
	private int[][] array;
	private int size;
	private int value;
	private Direction direction;
	private boolean undoTrue = false;
	private int[][] arrayCopy;
	private boolean pendingMove = false;
	private int score;
  public Game(int givenSize, GameUtil givenConfig)
  {
    // just call the other constructor
    this(givenSize, givenConfig, new Random());
  }
 
  public Game(int givenSize, GameUtil givenConfig, Random givenRandom)
  {
    array = givenConfig.initializeNewGrid(givenSize, givenRandom);
    size = givenSize;
    arrayCopy = new int[size][size];
    value = hw3.GameUtil.generateRandomTileValue(new Random());
    // initializes game and updates instance variables
   
    
  }

  public int getCell(int row, int col)
  {
    return array[row][col];
    // store the value of the selected cell on the grid
  }

  public void setCell(int row, int col, int value)
  {
    array[row][col] = value;
    // updates the value of the selected cell to the selected value
  }
  

  public int getSize()
  {
    return size;
  }

  public int getScore()
  {
	  score = hw3.GameUtil.calculateTotalScore(array);
    return score;
  }

  public int[] copyRowOrColumn(int rowOrColumn, Direction dir)
  {
	  // copies the given column or row of the given array
    int[] newRowOrColumn = new int[size];

    if(dir == api.Direction.UP) {    	    
    	for(int i = 0; i <= size - 1; i++) {        	
    	newRowOrColumn[i] = getCell(i, rowOrColumn);   
        }
    }
    if(dir == api.Direction.DOWN) {
    	int x = size - 1;
    	for(int i = 0; i <= size - 1; i++) {
    	newRowOrColumn[i] = getCell(x, rowOrColumn);
    	x = x - 1;
    	}
    }
    
    if(dir == api.Direction.LEFT) {    	
    	for(int i = 0; i <= size; i++) {        	
        newRowOrColumn[i] = getCell(rowOrColumn, i);                 
        }
    }
    if(dir == api.Direction.RIGHT) {
    	int x = size - 1;
    	for(int i = 0; i <= size - 1; i++) {
    	newRowOrColumn[i] = getCell(rowOrColumn, x);
    	x = x - 1;
    	}
    }
    return newRowOrColumn;
  }
    
  
  public void updateRowOrColumn(int[] arr, int rowOrColumn, Direction dir){
	  // updates the grid column or row to the given array
	    	    
	    	if(dir == api.Direction.UP) {
	    		for(int i = 0; i <= size - 1; i++) {
	    			setCell(i, rowOrColumn, arr[i]);
	    		}
	    	}
	        if(dir == api.Direction.DOWN) {
	        	int x = size - 1;
	        	for(int i = 0; i <= size - 1; i++) {
	        		setCell(x, rowOrColumn, arr[i]);
	        		x = x - 1;
	        	}
	        
	    	}
	        if(dir == api.Direction.LEFT) {
	        	for(int i = 0; i <= size - 1; i++) {
	        		setCell(rowOrColumn, i, arr[i]);
	        	}
	        }
	        if(dir == api.Direction.RIGHT) {
	        	int x = size - 1;
	        	for(int i = 0; i <= size - 1; i++) {
	        	 setCell(rowOrColumn, x, arr[i]);
	        	 x = x - 1;
	        	}
	        }
  }

  public ArrayList<Move> shiftGrid(Direction dir)
  {
	  // shifts the entire grid in the given direction, moving and merging all necessary tiles
	  for(int i = 0; i <= size - 1; i++) {
		  for(int j= 0; j <= size - 1; j++) {
			  arrayCopy[i][j] = array[i][j];
		  }
	  }
	ArrayList<Move> movelist = new ArrayList<Move>();	
    direction = dir;
    if(dir == api.Direction.UP) {
    	for(int i = 0; i <= size - 1; i++) {
        int[] upArray = copyRowOrColumn(i, api.Direction.UP);
        ArrayList<Move> list = new ArrayList<Move>();
        list.addAll(GameUtil.shiftArray(upArray));             
        for(int x = 0; x <= list.size() - 1; x++) {
        	Move move = list.get(x);
        (move).setDirection(i, api.Direction.UP);
        movelist.add(move);
        }
        updateRowOrColumn(upArray, i, api.Direction.UP);
    	}
    }
    if(dir == api.Direction.DOWN) {
    	for(int i = 0; i <= size - 1; i++) {
            int[] downArray = copyRowOrColumn(i, api.Direction.DOWN);
            ArrayList<Move> list = new ArrayList<Move>();
            list.addAll(GameUtil.shiftArray(downArray));             
            for(int x = 0; x <= list.size() - 1; x++) {
            	Move move = list.get(x);
            (move).setDirection(i, api.Direction.DOWN);
            movelist.add(move);
            }
            updateRowOrColumn(downArray, i, api.Direction.DOWN);
        	
    }
    }
    if(dir == api.Direction.LEFT) {
    	for(int i = 0; i <= size - 1; i++) {
            int[] leftArray = copyRowOrColumn(i, api.Direction.LEFT);
            ArrayList<Move> list = new ArrayList<Move>();
            list.addAll(GameUtil.shiftArray(leftArray));             
            for(int x = 0; x <= list.size() - 1; x++) {
            	Move move = list.get(x);
            (move).setDirection(i, api.Direction.LEFT);
            movelist.add(move);
            }
            updateRowOrColumn(leftArray, i, api.Direction.LEFT);
        	}
    }
    if(dir == api.Direction.RIGHT) {
    	for(int i = 0; i <= size - 1; i++) {
            int[] rightArray = copyRowOrColumn(i, api.Direction.RIGHT);
            ArrayList<Move> list = new ArrayList<Move>();
            list.addAll(GameUtil.shiftArray(rightArray));             
            for(int x = 0; x <= list.size() - 1; x++) {
            	Move move = list.get(x);
            (move).setDirection(i, api.Direction.RIGHT);
            movelist.add(move);
            }
            updateRowOrColumn(rightArray, i, api.Direction.RIGHT);
        	}
    }
    pendingMove = true;
	return movelist;
  }

 
  public boolean undo()
  {
	  // resets the grid to before the previous shiftGrid()
	  if(pendingMove = true) {
	  array = arrayCopy;
	  pendingMove = false;
	  }
    return false;
  }
  
  
  public TilePosition newTile()
  {	 
  // generates a random valued and positioned tile; updates score
		if(pendingMove == true) {
	  TilePosition position;
	 position = hw3.GameUtil.generateRandomTilePosition(array, new Random(), direction);
	 array[position.getRow()][position.getCol()] = value;
     value = hw3.GameUtil.generateRandomTileValue(new Random()); 
     pendingMove = false; 
     score = hw3.GameUtil.calculateTotalScore(array);
		}		
	
  return null;
  }

  public int getNextTileValue()
  {
	  // updates value for the upcoming nextTile() 
    return value;
  
  }
  

}
