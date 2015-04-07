/**
 * Controls the board for the Collapse game.
 *
 * @author Kyle Rosenthal
 * @version 4/6/2015
 */

public class CollapseBoard
{
    private Input input;
    private Output output;
    private int[][] boardData;
    private int[][] startBoard;
    private int numberRemaining;
    private final int kMaxData;
    private int turns;

    /**
     * Initialize a board with an input stream,
     * and output stream, a size, and the number of colors.
     * @param input input stream
     * @param output output stream
     * @param size size of board
     * @param kMaxData number of types of tiles
     */
    public CollapseBoard(Input input, Output output, int size, int kMaxData)
    {
        this.input = input;
        this.output = output;
        numberRemaining = size * size;
        this.kMaxData = kMaxData;
        turns = 0;
        boardData = new int[size][size];
        startBoard = new int[size][size];
        reset();
        Updater updater = new Updater();
        Thread thread = new Thread(updater);
        thread.start();
        output.pushUpdate(boardData, numberRemaining, turns);
    }

    private void update(String nextAction)
    {
        nextAction.replaceAll("^![a-zA-Z0-9]", "");
        if (nextAction.length() > 0)
        {
            if (nextAction.length() == 1)
            {
                switch (nextAction)
                {
                    case "n":
                    case "N":
                        reset();
                        break;
                    case "r":
                    case "R":
                        restart();
                        break;
                    case "c":
                    case "C":
                        cheat();
                        break;
                    case "q":
                    case "Q":
                        System.exit(0);
                        break;
                    default:
                        output.pushError("Bad input.");
                        break;
                }
                output.pushUpdate(boardData, numberRemaining, turns);
            }
            else
            {

                int row = nextAction.charAt(0) - 'a';
                System.out.println(row);
                if (row < 0)
                {
                    System.out.println("here");
                    row += 'a' - 'A';
                }
                System.out.println(row);
                int col = Integer.parseInt(nextAction.substring(1)) - 1;
                if (col < boardData[0].length && col >= 0)
                {
                    if (row - ('z' - 'a') < boardData.length && row >= 0)
                    {
                        if (boardData[row][col] > 0)
                        {
                            turns++;
                            press(row, col);
                        }
                    }
                    else
                    {
                        output.pushError("Bad input.");
                    }
                }
                else
                {
                    output.pushError("Bad input.");
                }
            }
        }
        else
        {
            output.pushError("Bad input.");
        }
    }

    private void restart()
    {
        for (int row = 0; row < startBoard.length; row++)
        {
            boardData[row] = startBoard[row].clone();
        }
        numberRemaining = boardData.length * boardData[0].length;
        turns = 0;
    }

    private void press(int row, int col)
    {
        if (boardData[row][col] != 0)
        {
            if ((row - 1 >= 0 && boardData[row][col] == boardData[row - 1][col])
                    ||(row + 1 < boardData.length && boardData[row][col] == boardData[row + 1][col])
                    ||(col - 1 >= 0 && boardData[row][col] == boardData[row][col - 1])
                    ||(col + 1 < boardData[0].length && boardData[row][col] == boardData[row][col + 1]))
            {
                pressHelper(row, col, boardData[row][col]);
                shift();
            }
            else
            {
                turns--;
                output.pushError("Only one tile there.");
            }
        }
        else
        {
            turns--;
            output.pushError("Empty location.");
        }
    }

    private void shift()
    {
        for (int col = 0; col < boardData[0].length; col++)
        {
            drop(boardData.length - 1,col);
        }
        shiftL(boardData[0].length / 2 - 1);
        shiftR(boardData[0].length / 2);
        output.pushUpdate(boardData, numberRemaining, turns);
    }

    private void drop(int row, int col){
        if (row >= 0)
        {
            if (boardData[row][col] == 0)
            {
                int space = 1;
                while (row - space >= 0 && boardData[row-space][col] == 0 )
                {
                    space++;
                }
                if (row - space != -1){
                    boardData[row][col] = boardData[row - space][col];
                    boardData[row - space][col] = 0;
                }
            }
            drop(row - 1, col);
        }
    }

    private void shiftR(int col){
        if (col < boardData[0].length ){
            if (boardData[boardData.length-1][col] == 0)
            {
                int space = 1;
                while (col + space < boardData[0].length && boardData[boardData[0].length - 1][col + space] == 0)
                {
                    space++;
                }
                if (col + space != boardData[0].length)
                {
                    for (int row = 0; row < boardData.length; row++)
                    {
                        boardData[row][col] = boardData[row][col + space];
                        boardData[row][col + space] = 0;
                    }
                }
            }
            shiftR(col + 1);
        }
    }
    private void shiftL(int col){
        if (col >= 0){
            if (boardData[boardData.length-1][col] == 0)
            {
                int space = 1;
                while (col - space >= 0 && boardData[boardData[0].length - 1][col - space] == 0)
                {
                    space++;
                }
                if (col - space != -1)
                {
                    for (int row = 0; row < boardData.length; row++)
                    {
                        boardData[row][col] = boardData[row][col - space];
                        boardData[row][col - space] = 0;
                    }

                }
            }
            shiftR(col - 1);
        }
    }

    private void pressHelper(int row, int col, int data)
    {
        if (row < boardData.length
                && col < boardData[0].length
                && row >= 0
                && col >= 0
                && data != 0)
        {
            if (boardData[row][col] == data)
            {
                boardData[row][col] = 0;
                numberRemaining--;
                pressHelper(row - 1, col, data);
                pressHelper(row, col - 1, data);
                pressHelper(row + 1, col, data);
                pressHelper(row, col + 1, data);
            }
        }
    }

    private void cheat()
    {
        for (int row = 0; row < boardData.length; row++)
        {
            for (int col = 0; col < boardData[0].length; col++)
            {
                boardData[row][col] = 0;
            }
        }
        boardData[0][0] = 1;
        boardData[0][1] = 1;
        numberRemaining = 2;
        output.pushUpdate(boardData, numberRemaining, turns);
    }

    private void reset()
    {
        for (int row = 0; row < boardData.length; row++)
        {
            for (int col = 0; col < boardData[0].length; col++)
            {
                //0 is empty, add one to ensure > 0,
                // subtract one to prevent going over max,
                // + .5 to round properly
                boardData[row][col]
                        = 1 + (int) (Math.random() * (kMaxData - 1) + .5);
            }
        }
        //store the new starting board
        for (int row = 0; row < boardData.length; row++)
        {
            startBoard[row] = boardData[row].clone();
        }
        numberRemaining = boardData.length*boardData[0].length;
        turns = 0;
    }

    /**
     * An inner class that handles checking for
     * input constantly.
     */
    class Updater implements Runnable
    {

        @Override
        public void run()
        {
            loop();
        }

        public void loop()
        {
            if (input.hasNextAction())
            {
                update(input.getNextAction());
            }
            loop();
        }
    }
}
