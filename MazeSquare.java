/** Starter file for a square in a maze.

    @author Jed Yang, 2017-01-14
    @author Yingqi Ding
    @author Erika Mitchell
*/
public class MazeSquare
{
   // private instance variables, final as they won't change after constructed
   private final int col;
   private final int row;
   private final boolean hasBottom;
   private final boolean hasLeft;
   private boolean isMarked;

   /** Constructs a new maze square with the walls configured by the descriptor
       and located at a specific column and row.
       @param col 0-indexed column number.
       @param row 0-indexed row number.
       @param desc Descriptor of the walls.
   */
   public MazeSquare(int col, int row, char desc)
   {
      this.col = col;
      this.row = row;
      this.isMarked = false;

      if (desc == 'L')
      {
         hasBottom = true;
         hasLeft = true;
      }
      else if (desc == '|')
      {
         hasBottom = false;
         hasLeft = true;
      }
      else if (desc == '_')
      {
         hasBottom = true;
         hasLeft = false;
      }
      else if (desc == '.')
      {
         hasBottom = false;
         hasLeft = false;
      }
      else
      {
         System.err.println("MazeSquare encountered bad descriptor: " + desc);
         // initialize anyway or else compiler would complain
         hasBottom = false;
         hasLeft = false;
      }
   }

   /** Returns the 0-indexed column number of this square.
       @return Column index.
   */
   public int getCol()
   {
      return col;
   }

   /** Returns the 0-indexed row number of this square.
       @return Row index.
   */
   public int getRow()
   {
      return row;
   }

    /** Returns if this square is marked.
       @return boolean Mark Status.
   */
    public boolean getStatus()
   {
      return isMarked;
   }

   /** Sets if the MazeSquare is marked
       @param boolean input*/
    public void setStatus(boolean input)
   {
      isMarked = input;
   }
    
   /** Checks whether the square has a bottom wall.
       @return True if the square has a bottom wall.
   */
   public boolean hasBottomWall()
   {
      return hasBottom;
   }

   /** Checks whether the square has a left wall.
       @return True if the square has a left wall.
   */
   public boolean hasLeftWall()
   {
      return hasLeft;
   }

   /** Checks whether a charactor is a valid descriptor for a square in the
       maze.

       @param desc Descriptor of a square.
       @return True if one of L, |, _, or .
   */
   public static boolean validDescriptor(char desc)
   {
      return "L|_.".indexOf(desc) >= 0;
   }
}
