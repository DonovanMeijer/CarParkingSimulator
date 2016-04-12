package CarParkingSimulator.Model;

/**
 * A (static) class containing the simulators time and logic.
 * @author Donovan Meijer
 * @version 1.0
 */
public class SimulatorTime
{
    public static int step = 0;

    public static int day = 0;
    public static int hour = 0;
    public static int minute = 0;

    public static void incrementTime()
    {
        // Advance the time by one minute.
        minute++;

        while (minute > 59)
        {
            minute -= 60;
            hour++;
        }

        while (hour > 23)
        {
            hour -= 24;
            day++;
        }

        while (day > 6)
        {
            day -= 7;
        }
    }

    public static String getReadableDate()
    {
        return new String();
    }
}
