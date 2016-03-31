package CarParkingSimulator;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by John on 29/03/16.
 */
public class CarQueue
{
    private Queue<Car> queue = new LinkedList<Car>();

    public boolean addCar(Car car)
    {
        return queue.add(car);
    }

    public Car removeCar()
    {
        return queue.poll();
    }
}
