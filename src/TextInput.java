import java.io.InputStream;
import java.util.Scanner;

/**
 * Pull input from a text source.
 *
 * @author Kyle Rosenthal
 * @version 4/6/15
 */

public class TextInput implements Input
{
    private Scanner scanner;

    /**
     * Create a Text input from a source.
     * @param source the place to pull text input from
     */
    public TextInput(InputStream source)
    {
        this.scanner = new Scanner(source);
    }

    /**
     * Get the next action from the Input.
     * @return the string of the next action
     * in for letter-number (ie a4, B1, etc).
     */
    @Override
    public String getNextAction()
    {
        return scanner.nextLine();
    }

    /**
     * Check if the input has another input.
     * @return true if there is another input
     */
    @Override
    public boolean hasNextAction()
    {
        return scanner.hasNext();
    }
}
