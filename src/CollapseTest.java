/**
 * Testing the collapse game
 * using system.out and system.in.
 *
 * @author Kyle Rosenthal
 * @version 4/6/2015
 */

public class CollapseTest
{
    /**
     * Test class
     * @param args not used
     */
    public static void main(String[] args)
    {
        Output output = new TextOutput(System.out);
        Input input = new TextInput(System.in);
        CollapseBoard collapseBoard = new CollapseBoard(input, output, 8, 3);
    }
}
