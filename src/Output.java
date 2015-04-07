/**
 * A generic output interface to
 * allow different methods of output.
 *
 * @author Kyle Rosenthal
 * @version 4/6/2015
 */

public interface Output
{
   void pushUpdate(int[][] board, int numberRemaining, int turns);
   void pushError(String error);
}
