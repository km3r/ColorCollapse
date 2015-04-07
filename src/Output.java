/**
 * A generic output interface to
 * allow different methods of output.
 *
 * @author Kyle Rosenthal
 * @version 4/6/2015
 */

public interface Output
{
   public void pushUpdate(int[][] board, int numberRemaining, int turns);
   public void pushError(String error);
}
