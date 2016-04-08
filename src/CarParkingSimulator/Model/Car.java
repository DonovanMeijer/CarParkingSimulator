package CarParkingSimulator.Model;

public abstract class Car
{
    private Location location;
    private int minutesLeft;
    private int totalTime;
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

    public int getTotalTime()
    {
        return totalTime;
    }

    public void tick()
    {
        minutesLeft--;
        totalTime++;
    }

}