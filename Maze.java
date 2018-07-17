/** Starter file for a maze.

    @author Jed Yang, 2017-01-14
    @author Yingqi Ding
    @author Erika Mitchell
*/

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

/** Starter code for a maze.

    TODO: Eventually it will be able to load a maze and solve it.  Currently
    the print() method should work once getMazeSquare() is implemented.  The
    load() method already reads the first three lines of maze files, but it
    does not process the remaining information regarding walls.

    @author Jed Yang, 2017-01-14
    @author Yingqi Ding
    @author Erika Mitchell
*/
public class Maze
{
   // private instance variables
   // number of columns and rows in the maze
   private int numCols;
   private int numRows;

   // coordinates for start/end square positions
   private int startCol, startRow;
   private int endCol, endRow;
   private List<List<MazeSquare>> mazeField;

    public Maze()
   {
      //instance variables
       numCols = 0;
       numRows = 0;
       startCol = 0;
       startRow = 0;
       endCol = 0;
       endRow = 0;
       mazeField = new ArrayList<List<MazeSquare>>();
   }

   /** Load the maze from a file.
       @param String fileName
       @return True if successful. */
    public boolean load(String fileName)
   {
      File inputFile = new File(fileName);
      Scanner scanner = null;

      try
      {
         scanner = new Scanner(inputFile);
      }
      catch (FileNotFoundException e)
      {
         System.err.println("Maze file not found: " + fileName);
         return false;
      }
      try
      {
         // read the first three lines of the maze file
         numCols = scanner.nextInt();
         numRows = scanner.nextInt();
         startCol = scanner.nextInt();
         startRow = scanner.nextInt();
         endCol = scanner.nextInt();
         endRow = scanner.nextInt();
         
         scanner.nextLine();
         List<String> mazeBoard = new ArrayList<String>();
         while (scanner.hasNextLine())
         {
             String line = scanner.nextLine();
             mazeBoard.add(line);
         }

         for (int i = 0; i < numRows; i++)
         {
             List<MazeSquare> subList = new ArrayList<MazeSquare>();
             String row = mazeBoard.get(i);
             for (int j = 0; j < numCols; j++)
             {
                 char ch = row.charAt(j);
                 MazeSquare block = new MazeSquare(j, i, ch);
                 subList.add(block);
             }
             mazeField.add(subList);
         }  
      }
      catch (InputMismatchException e)
      {
         System.err.println("Malformed maze file header.");
         return false;
      }

      // check that start/end coordinates are valid
      if (!inRange(startCol, 0, numCols)
         || !inRange(startRow, 0, numRows)
         || !inRange(endCol, 0, numCols)
         || !inRange(endRow, 0, numRows))
      {
         System.err.println("Start or end goal out of bounds.");
         return false;
      }              
      return true;
   }

   /** Checks if num is in the range lower (inclusive) to upper (exclusive).
       @param num
       @param lower
       @param upper
       @return True if lower <= num < upper
   */
   private static boolean inRange(int num, int lower, int upper)
   {
      return lower <= num && num < upper;
   }
   
   /** Prints the Maze in a pretty way. */
   public void print()
   {
      // top border
      for (int col = 0; col < numCols; col++)
      {
         System.out.print("+");
         System.out.print("---");
      }
      System.out.println("+");

      // one row at a time
      for (int row = 0; row < numRows; row++)
      {
         // the row of squares with vertical dividing walls
         for (int col = 0; col < numCols; col++)
         {
            // retrieve the square to be printed; this is what allows the
            // print() function to not care about how the squares are stored
            MazeSquare square = getMazeSquare(col, row);

            // left wall of a square
            if (square.hasLeftWall())
            {
               System.out.print("|");
            }
            else
            {
               System.out.print(" ");
            }

            System.out.print(" ");

            // square with possible designation of start/finish
            if (col == startCol && row == startRow)
            {
               System.out.print("S");
            }
            else if (col == endCol && row == endRow)
            {
               System.out.print("F");
            }
            else if (square.getStatus())
            {
                System.out.print("*");
            }
            else
            {
               System.out.print(" ");
            }

            System.out.print(" ");
         }
         System.out.println("|"); // right-most wall

         // horizontal walls below the row just printed
         for (int col = 0; col < numCols; col++)
         {
            MazeSquare square = getMazeSquare(col, row);
            System.out.print("+");
            if (square.hasBottomWall())
               System.out.print("---");
            else
               System.out.print("   ");
         }
         System.out.println("+"); // right-most wall
      } // end for
   } // end print()

   /** This method allows the print() method to not care about how you
       decide to store the MazeSquare's that you loaded.
       @param two integers specifying column and row
       @return The MazeSquare specified by column and row.
   */
   private MazeSquare getMazeSquare(int col, int row)
   {
       return mazeField.get(row).get(col);
   }

    /** Computes and returns a solution to this maze. If there are multiple
    solutions, only one is returned, and getSolution() makes no guarantees
    about which one.  However, the returned solution will not include visits to
    dead ends or any backtracks, even if backtracking occurs during the
    solution process. 

    @return a stack of MazeSquare objects containing the sequence of squares
    visited to go from the start square (bottom of the stack) to the finish
    square (top of the stack). If there is no solution, an empty stack is
    returned.
    */
  /** Checks to see if all four directions are blocked
  @param MazeSquare object
  @return Returns true if all sides are blocked */
    private boolean allBlocked(MazeSquare s)
    {
      if (upBlocked(s) && downBlocked(s) && leftBlocked(s) && rightBlocked(s))
      {
        return true;
      }
      return false;
    }

    /** Checks if the upwards direction is blocked
    @param MazeSquare object
    @return Returns true if top side is blocked*/
    private boolean upBlocked(MazeSquare s)
    {
      int col = s.getCol();
      int row = s.getRow();
      if (inRange(row - 1, 0, numRows))
      {
        MazeSquare up = getMazeSquare(col, row - 1);
        if (up.getStatus() || up.hasBottomWall())
        {
          return true;
        }
        return false;
      }
      return true;
    }

    /** Checks if the downwards direction is blocked
    @param MazeSquare object
    @return Returns true if bottom side is blocked*/
    private boolean downBlocked(MazeSquare s)
    {
      int col = s.getCol();
      int row = s.getRow();
      if (inRange(row + 1, 0, numRows))
      {
        MazeSquare down = getMazeSquare(col, row + 1);
        if (down.getStatus() || s.hasBottomWall())
        {
          return true;
        }
        return false;
      }
      return true;
    }

    /** Checks if the left direction is blocked
    @param MazeSquare object
    @return Returns true if left side is blocked*/
    private boolean leftBlocked(MazeSquare s)
    {
      int col = s.getCol();
      int row = s.getRow();
      if (inRange(col - 1, 0, numCols))
      {
        MazeSquare left = getMazeSquare(col - 1, row);
        if (left.getStatus() || s.hasLeftWall())
        {
          return true;
        }
        return false;
      }
      return true;
    }

    /** Checks if the right direction is blocked
    @param MazeSquare object
    @return Returns true if right side is blocked*/
    private boolean rightBlocked(MazeSquare s)
    {
      int col = s.getCol();
      int row = s.getRow();
      if (inRange(col + 1, 0, numCols))
      {
        MazeSquare right = getMazeSquare(col + 1, row);
        if (right.getStatus() || right.hasLeftWall())
        {
          return true;
        }
        return false;
      }
      return true;
    }

    /** Returns the first accesable neighbor of the TargetSquare
    @param MazeSquare object
    @return MazeSquare object*/
    private MazeSquare selectNeighbor(MazeSquare s)
    {
      int col = s.getCol();
      int row = s.getRow();
      if (!upBlocked(s))
      {
        return getMazeSquare(col, row - 1);
      }
      else if (!downBlocked(s))
      {
        return getMazeSquare(col, row + 1);
      }
      else if (!leftBlocked(s))
      {
        return getMazeSquare(col - 1, row);
      }
      else if (!rightBlocked(s))
      {
        return getMazeSquare(col + 1, row);
      }
      return s;
    }

    /** Solves the maze
    @return a stack of MazeSquares that is the solution */
    public Stack<MazeSquare> getSolution()
    {   //First unmark all the squares
        for (int i = 0; i < numRows; i++)
        {
          for (int j = 0; j < numCols; j++)
          {
            getMazeSquare(j, i).setStatus(false);
          }
        }
        
        Stack<MazeSquare> mazeStack = new CarlStack<MazeSquare>();
        MazeSquare startSquare = getMazeSquare(startCol, startRow);
        startSquare.setStatus(true);
        mazeStack.push(startSquare);
        MazeSquare endSquare = getMazeSquare(endCol, endRow);
        List<MazeSquare> deadEnds = new ArrayList<MazeSquare>();// save deadends

        while(!mazeStack.isEmpty())
        {
            MazeSquare targetSquare = mazeStack.peek();
            int row = targetSquare.getRow();
            int col = targetSquare.getCol();
            if ((row == endRow) && (col == endCol))
            {
                //unmark all the deadends
                for (MazeSquare d:deadEnds)
                {
                  d.setStatus(false);
                }
                return mazeStack;
            }
            if (allBlocked(targetSquare))
            {
              MazeSquare dead = mazeStack.pop();
              deadEnds.add(dead);
            }
            else
            {
              MazeSquare neighbor = selectNeighbor(targetSquare);
              neighbor.setStatus(true);
              mazeStack.push(neighbor);
            }
        }
        System.out.println("The maze is unsolvable.");
        return mazeStack;
    }
        
   /** This is the main function
   @args A filename, and string input indicating a solution is requested(optional)
   */
   public static void main(String[] args)
   {
        if (args.length == 1)
        {
          String fileName = args[0];
          Maze maze1 = new Maze();
          maze1.load(fileName);
          maze1.print();
        }
        else
        {
          String fileName = args[0];
          String inputValue = args[1];
          Maze maze1 = new Maze();
          maze1.load(fileName);
           if (inputValue.equals("--solve"))
           {
               System.out.println("Is the maze stack empty?");
               System.out.println(maze1.getSolution().isEmpty());;
               maze1.print();
               System.out.println("Let's get soluion again!");
               maze1.getSolution();
               maze1.print();
           }
           else
           {
            System.out.println("You should type --solve if you want to solve.");
            System.exit(1);
           }
        }
  }
}
