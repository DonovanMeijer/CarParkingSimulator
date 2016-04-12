package CarParkingSimulator.Model;

/** Class for Location of cars.
 */
public class Location
{
    // Floor, row and place positions
    private int floor;
    private int row;
    private int place;
    private boolean isReserved;

    public Location(int floor, int row, int place)
    {
        this.floor = floor;
        this.row = row;
        this.place = place;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof Location)
        {
            Location other = (Location) obj;

            return floor == other.getFloor() && row == other.getRow() && place == other.getPlace();
        }
        else
        {
            return false;
        }
    }

    public String toString()
    {
        return floor + "," + row + "," + place;
    }

    /**
     * Use the 10 bits for each of the floor, row and place
     * values. Except for very big car parks, this should give
     * a unique hash code for each (floor, row, place) tupel.
     * @return A hashcode for the location.
     */
    public int hashCode()
    {
        return (floor << 20) + (row << 10) + place;
    }

    /**
     * @return The floor.
     */
    public int getFloor()
    {
        return floor;
    }

    /**
     * @return The row.
     */
    public int getRow()
    {
        return row;
    }

    /**
     * @return The place.
     */
    public int getPlace()
    {
        return place;
    }

    /**
     * @return Reservation
     */
    public boolean getReservation()
    {
        return isReserved;
    }

    public void setReservation(boolean value)
    {
        isReserved = value;
    }
}