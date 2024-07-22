/**
 * Author: Kyle Lewis
 * Date: 08-06-2024
 *
 * This class is used for error handling due to the invalid use of a Time constructor.
 */
public class InvalidTime extends Exception
{
    String message;
    InvalidTime(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
