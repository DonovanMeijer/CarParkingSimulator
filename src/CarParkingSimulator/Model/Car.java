package CarParkingSimulator.Model;
/** Class for creating objects of the class Car.
 * @param location      Which floor, row and place is the car located on.
 * @param minutesleft   Amount of minutes left.
 * @param timeEntered   The time when the car has entered the carpark.
 * @param isPaying
 */
public abstract class Car
{
    private Location location;
    private int minutesLeft;
    private int timeEntered;
    private boolean isPaying;

    /**
     * Constructor for objects of class Car
     */
    public Car()
    {

    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public int getMinutesLeft()
    {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft)
    {
        this.minutesLeft = minutesLeft;
    }
    
    public boolean getIsPaying()
    {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying)
    {
        this.isPaying = isPaying;
    }

    public int getTimeEntered()
    {
        return timeEntered;
    }

    public void setTimeEntered(int timeEntered)
    {
        this.timeEntered = timeEntered;
    }

    public void tick()
    {
        minutesLeft--;
    }
}