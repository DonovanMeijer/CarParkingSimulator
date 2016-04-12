package CarParkingSimulator.Model;

import java.util.*;

/**
 * Abstract class describing a basic form for a data view.
 * @author Donovan Meijer
 * @version 1.0
 */
public class Garage
{
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;

    private Car[][][] cars;

    private Finance finances;

    public Garage(int numberOfFloors, int numberOfRows, int numberOfPlaces)
    {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;

        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        finances = new Finance(this);
    }

    /**
     * @return The number of floors.
     */
    public int getNumberOfFloors()
    {
        return numberOfFloors;
    }

    /**
     * @return The number of rows.
     */
    public int getNumberOfRows()
    {
        return numberOfRows;
    }

    /**
     * @return The number of places.
     */
    public int getNumberOfPlaces()
    {
        return numberOfPlaces;
    }

    public int getTotalAmountOfParkingPlaces()
    {
        return numberOfFloors * numberOfPlaces * numberOfRows;
    }

    public Car getCarAt(Location location)
    {
        if (!locationIsValid(location))
        {
            return null;
        }

        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public boolean setCarAt(Location location, Car car)
    {
        if (!locationIsValid(location))
        {
            return false;
        }

        Car oldCar = getCarAt(location);

        if (oldCar == null)
        {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;

            car.setLocation(location);

            //Trigger all associated event listeners.
            for (GarageListener listener : eventListeners)
            {
                listener.CarEntered(car);
            }

            return true;
        }

        return false;
    }

    public Car removeCarAt(Location location)
    {
        if (!locationIsValid(location))
        {
            return null;
        }

        Car car = getCarAt(location);

        if (car == null)
        {
            return null;
        }

        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;

        car.setLocation(null);

        //Trigger all associated event listeners.
        for (GarageListener listener : eventListeners)
        {
            listener.CarExited(car);
        }

        return car;
    }

    public Location getFirstFreeLocation()
    {
        for (int floor = 0; floor < getNumberOfFloors(); floor++)
        {
            for (int row = 0; row < getNumberOfRows(); row++)
            {
                for (int place = 0; place < getNumberOfPlaces(); place++)
                {
                    Location location = new Location(floor, row, place);

                    if (getCarAt(location) == null)
                    {
                        return location;
                    }
                }
            }
        }

        return null;
    }

    public Car getFirstLeavingCar()
    {
        for (int floor = 0; floor < getNumberOfFloors(); floor++)
        {
            for (int row = 0; row < getNumberOfRows(); row++)
            {
                for (int place = 0; place < getNumberOfPlaces(); place++)
                {
                    Location location = new Location(floor, row, place);

                    Car car = getCarAt(location);

                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying())
                    {
                        return car;
                    }
                }
            }
        }

        return null;
    }

    public Finance getFinances()
    {
        return finances;
    }

    public void tick()
    {
        for (int floor = 0; floor < getNumberOfFloors(); floor++)
        {
            for (int row = 0; row < getNumberOfRows(); row++)
            {
                for (int place = 0; place < getNumberOfPlaces(); place++)
                {
                    Location location = new Location(floor, row, place);

                    Car car = getCarAt(location);

                    if (car != null)
                    {
                        car.tick();
                    }
                }
            }
        }
    }

    private boolean locationIsValid(Location location)
    {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();

        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces)
        {
            return false;
        }

        return true;
    }

    //region Garage events
    private List<GarageListener> eventListeners = new ArrayList<GarageListener>();

    public void addListener(GarageListener listenerToAdd)
    {
        eventListeners.add(listenerToAdd);
    }

    public void removeListener(GarageListener listenerToRemove)
    {
        eventListeners.remove(listenerToRemove);
    }

    public interface GarageListener
    {
        void CarEntered(Car car);

        void CarExited(Car car);
    }
    //endregion
}
