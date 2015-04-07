import java.io.PrintStream;

/**
 * Output the game board to a text source.
 *
 * @author Kyle Rosenthal
 * @version 4/6/2015
 */

public class TextOutput implements Output
{
    PrintStream out;
    int[][] board;
    int numberRemaining;

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
     * @param board the board data
     * @param numberRemaining how many tiles remain
     * @param turns moves the player has made
     */
    @Override
    public void pushUpdate(int[][] board, int numberRemaining, int turns)
    {
        this.board = board;
        this.numberRemaining = numberRemaining;
        if (numberRemaining != 0){
            print("Number Remaining: "+ numberRemaining + "\t Moves: " + turns);
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

    private void print(String message){
        out.print("   ");
        for (int col = 0; col < board[0].length; col++)
        {
            if (col < 9)
            {
                out.print((col+1)+"  ");
            } else
            {
                out.print((col+1)+" ");
            }
        }
        out.print("\n");
        for (int row = 0; row < board.length; row++)
        {
            out.print(((char) (row+'A'))+": ");
            for (int col = 0; col < board[0].length; col++)
            {
                switch (board[row][col]){
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
            out.print("--");
        }
        out.print("\n");
        out.print(message);
        out.print("\n");
    }
}
