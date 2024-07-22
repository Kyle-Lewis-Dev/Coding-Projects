/**
 * Author: Kyle Lewis
 * Date: 08-06-2024
 *
 * This class stores a time in the format HH:MM AM/PM.
 */

public class Time implements Comparable<Time>
{
    private int hours;
    private int minutes;
    private String meridian;

    Time(int hours, int minutes, String meridian) throws InvalidTime
    {
        if(hours > 0 && hours <= 12) this.hours = hours;
        else throw new InvalidTime("Entered an invalid hours value.");

        if(minutes >= 0 && minutes < 60) this.minutes = minutes;
        else throw new InvalidTime("Entered an invalid minutes value.");

        if(meridian.equals("AM") || meridian.equals("PM")) this.meridian = meridian;
        else throw new InvalidTime("Please enter AM or PM.");
    }

    Time(String time) throws InvalidTime
    {
        // String white space from time string
        String cleanTime = time.strip();

        // Error handling for too many characters
        if(cleanTime.length() > 8) throw new InvalidTime("Time entered contained too many characters.");

        // Assignment and error handling for hours
        try
        {
            if(cleanTime.charAt(2) == ':') hours = Integer.parseInt(cleanTime.substring(0, 2));
            else hours = Integer.parseInt(cleanTime.substring(0, 1));
        }
        catch(NumberFormatException e)
        {
            throw new InvalidTime("Entered an invalid hours value.");
        }
        if(hours <= 0 || hours > 12) throw new InvalidTime("Entered an invalid hours value.");

        // Assignment and error handling for minutes
        try
        {
            minutes = Integer.parseInt(cleanTime.substring(cleanTime.length() - 5, cleanTime.length() - 3));
        }
        catch(NumberFormatException e)
        {
            throw new InvalidTime("Entered an invalid minutes value.");
        }
        if(minutes < 0 || minutes >= 60) throw new InvalidTime("Entered an invalid minutes value.");

        // Assignment and error handling for meridian
        int meridianStartIndex = cleanTime.length() - 2;
        String meridianPart = cleanTime.substring(meridianStartIndex);
        if(meridianPart.equals("AM") || meridianPart.equals("PM")) this.meridian = meridianPart;
        else throw new InvalidTime("Please enter AM or PM.");
    }

    public int getHours()
    {
        return hours;
    }

    public int getMinutes()
    {
        return minutes;
    }

    public String getMeridian()
    {
        return meridian;
    }

    @Override
    public String toString() {
        return hours + ":" + (minutes < 10 ? "0" : "") + minutes + " " + meridian;
    }

    @Override
    public int compareTo(Time time)
    {
        // Compare meridian
        if(meridian.equals("AM") && time.getMeridian().equals("PM")) return -1;
        else if(meridian.equals("PM") && time.getMeridian().equals("AM")) return 1;
        else
        {
            // Temporary variables for reformatting hours
            int thisHours = (hours == 12) ? 0 : hours;
            int hoursToCompare = (time.getHours() == 12) ? 0 : time.getHours();

            // Compare hours
            if(thisHours < hoursToCompare) return -1;
            else if(thisHours > hoursToCompare) return 1;
            else
            {
                // Compare minutes
                return Integer.compare(minutes, time.getMinutes());
            }
        }
    }
}
