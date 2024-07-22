/**
 * Author: Kyle Lewis
 * Date: 08-06-2024
 *
 * This class defines intervals that can be compared by the main program.
 *
 * It can be used with generic types, and is used with type Time in this program.
 */

public class Interval<T extends Comparable<T>>
{
    private T start;
    private T end;

    Interval(T start, T end)
    {
        this.start = start;
        this.end = end;
    }

    /** Returns whether the point parameter is within this interval instance */
    public boolean within(T point)
    {
        return point.compareTo(start) >= 0 && point.compareTo(end) <= 0;
    }

    /** Returns whether this instance is within the interval parameter */
    public boolean subinterval(Interval<T> test)
    {
        return this.start.compareTo(test.start) >= 0 && this.end.compareTo(test.end) <= 0;
    }

    /** Returns whether the interval parameter overlaps the interval on which the method is invoked */
    public boolean overlaps(Interval<T> test)
    {
        return !(test.end.compareTo(start) < 0 || test.start.compareTo(end) > 0);
    }
}