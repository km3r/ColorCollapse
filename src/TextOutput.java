import java.io.PrintStream;

/**
 * Output the game board to a text source.
 *
 * @author Kyle Rosenthal
 * @version 4/6/2015
 */

public class TextOutput implements Output
{
    private PrintStream out;
    private int[][] board;
    private int numberRemaining;

    /**
     * Create a text output stream.
     * @param out the output stream
     */
    public TextOutput(PrintStream out)
    {
        this.out = out;
        board = new int[0][0];
    }

    /**
     * Update the output with a new set of data.
     * @param aBoard the board data
     * @param numRemaining how many tiles remain
     * @param turns moves the player has made
     */
    @Override
    public void pushUpdate(int[][] aBoard, int numRemaining, int turns)
    {
        this.board = aBoard;
        this.numberRemaining = numRemaining;
        if (numberRemaining != 0)
        {
            print("Number Remaining: " + numberRemaining + "\tMoves: " + turns);
        }
        else
        {
            print("You win!" + "\t Moves: " + turns);
        }
    }

    /**
     * Convey errors to the output.
     * @param error
     */
    @Override
    public void pushError(String error)
    {
        print(error);
    }

    /**
     * Print out the board with a message at the bottom.
     * @param message string of the message
     */
    private void print(String message)
    {
        out.print("   ");
        for (int col = 0; col < board[0].length; col++)
        {
            if (col < 9)
            {
                out.print((col + 1) + "  ");
            }
            else
            {
                out.print((col + 1) + " ");
            }
        }
        out.print("\n");
        for (int row = 0; row < board.length; row++)
        {
            out.print(((char) (row + 'A')) + ": ");
            for (int col = 0; col < board[0].length; col++)
            {
                switch (board[row][col])
                {
                    case 0:
                        out.print("   ");
                        break;
                    case 1:
                        out.print("+  ");
                        break;
                    case 2:
                        out.print("X  ");
                        break;
                    case 3:
                        out.print("%  ");
                        break;
                    default:
                        out.print("*  ");
                        break;
                }
            }
            out.print("\n");
        }
        out.print(" -");
        for (int col = 0; col < board[0].length; col++)
        {
            out.print("---");
        }
        out.print("\n");
        out.print(message);
        out.print("\n");
    }
}
