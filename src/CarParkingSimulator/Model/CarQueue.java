package CarParkingSimulator.Model;

import java.util.*;

/**
 * Class for making a LinkedList which contains elements of Cars.
 */
public class CarQueue
{
    private Queue<Car> queue = new LinkedList<Car>();

    /**
     * This method appends the specified element to the end of this list.
     */
    public boolean addCar(Car car)
    {
        return queue.add(car);
    }

    /**
     * This method retrieves and removes the head (first element) of this list.
     */
    public Car removeCar()
    {
        return queue.poll();
    }
}
