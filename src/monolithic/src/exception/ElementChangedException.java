package exception;

/**
 * Created by stephan on 06/07/17.
 */
public class ElementChangedException extends Exception
{

    public String getLocalizedMessage() {
        return "The object was changed in the database or does not exist anymore\nPlease refresh.";
    }
}
