import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import api.Direction;
import api.Move;
import api.TilePosition;
import java.math.*;
import api.Move;



public class GameUtil
{  

 
  public GameUtil()
  {
    // do nothing
  }
  
  public int mergeValues(int a, int b)
  {
    if (a > 0 && b > 0 &&
        (a + b == 3) ||
        (a >= 3 && b == a))
    {
      return a + b;
    }
    else
    {
      return 0;
    }
  }

  public static int getScoreForValue(int value) { 
	  // calculates score per tile
	 int num = 3;
	 int num2 = 0;
	 int num4 = value;
	 
	 Double num3 = 3.0;
	 if(value > 3) {
		 while (num4 != 3) {
			 num4 = num4 / 2;
			 num2 = num2 + 1;
		 }
	 while (value != num) {
		  num = Math.multiplyExact(num, 2); 
		   num3 = Math.pow(3.0, num2 + 1);
		   
	  }
	 return num3.intValue();
	 }
	  // 3 to the power of value divided by a variable that starts at 3 and goes up by one every loop
	 if(value == 3) {
		 return 3;
	 }
	  
	  return 0;
  }

  public int[][] initializeNewGrid(int size, Random rand)
  {
    int[][] grid = new int[size][size];
    int numCells = size * size;
    
    // To select two distinct cells, think of the numCells cells as ordered
    // left to right within rows, with the rows ordered top to bottom.
    // First select two distinct integers between 0 and numCells
    int first = rand.nextInt(numCells);
    int second = rand.nextInt(numCells - 1);
    if (second >= first)
    {
      second += 1;
    }
    
    // Then, divide by size to get the row, mod by size to get column,
    // put a 1 in the first and a 2 in the other
    grid[first / size][first % size] = 1;
    grid[second / size][second % size] = 2;
    
    return grid;
  }

  
  public static int calculateTotalScore(int[][] grid)
  {
	  // calculates score per grid
    int total = 0;
    for (int row = 0; row < grid.length; row++)
    {
      for (int col = 0; col < grid[0].length; col++)
      {
        total += getScoreForValue(grid[row][col]);
      }
    }
    return total;
    // done
  }
  
 
  public int[][] copyGrid(int[][] grid)
  {
    int[][] ret = new int[grid.length][grid[0].length];
    for (int row = 0; row < grid.length; ++row)
    {
      for (int col = 0; col < grid[0].length; ++col)
      {
        ret[row][col] = grid[row][col];
      }
    }
    return ret;
    // great, I discover this exists a millisecond after I already wrote it from scratch -_-
  }
  
  
  
  public static int generateRandomTileValue(Random rand)
  {
	 // generates a random tile value between 1 and 3; Shouldn't it be 1 and 4? Either the answer is no, or I have bad luck
	 // and have yet to have seen a 4 show up
	int value = rand.nextInt(4);
	while(value == 0) {
		value = rand.nextInt(4);
	}
    return value;
    // done
  }
  
  
   public static TilePosition generateRandomTilePosition(int[][] grid, Random rand, Direction lastMove)
  {
	   // generates a new tile in a random position relative to the direction of the last move
	   int col = 0;
	   int row = 0;
	   int size = grid.length;
	   if(lastMove == api.Direction.DOWN) {
		   row = 0;
		   col = rand.nextInt(size);
	   }
	   if(lastMove == api.Direction.UP) {
		   row = size;
		   col = rand.nextInt(size);
	   }
	   if(lastMove == api.Direction.LEFT) {
		   row = rand.nextInt(size);
		   col = 0;
	   }
	   if(lastMove == api.Direction.RIGHT) {
		   row = rand.nextInt(size);
		   col = size;
		   
	   }
       TilePosition tile = new TilePosition(row, col, 0);
	   return tile;
	   // should be done
  }
   
  
  public static ArrayList<Move> shiftArray(int[] arr)
  {
	  // shifts a chosen array to the left; returns a string set of moves and merges for the array
	  int x = 0;
	  int y = 1;
	  ArrayList<Move> movelist = new ArrayList<Move>();
	  int[] array = arr;
	  int z = 1;	  
	    int x1 = 0;
	    int t = 0;
		int y1 = 1;
		int z1 = 1;
		while(z1 != array.length) {
			if(array[t] != 0) {
			if(array[x1] == 2 && array[y1] == 1 || array[x1] == 1 && array[y1] == 2) {
				array[x1] = 3;
				array[y1] = 0;
				if(x1 != 0) {
					Move move = new Move(y1, x1, array[x1], array[x1], array[x1] * 2);
					movelist.add(move);
					}
				
			}
			if (array[x1] == array[y1]) {
				array[x1] = array[x1] * 2;
				array[y1] = 0;
				if(array[x1] != 0) {
				Move move = new Move(y1, x1, array[x1], array[x1], array[x1] * 2);
				movelist.add(move);
				}
			}			
		}
			x1 = x1 + 1;
			y1 = y1 + 1;
			z1 = z1 + 1;	
			t = x1 - 1;
		}	
	  while(z != array.length) {
		  if(array[x] == 0) {
			  if(array[y] != 0) {
			  array[x] = array[y];
			  array[y] = 0;				
		    Move move = new Move(y, x, array[y]);
			movelist.add(move);
			  }
		  }
		  z = z + 1;
		  x = x + 1;
		  y = y + 1;
	  }	
    return movelist;    
 }
  
}
