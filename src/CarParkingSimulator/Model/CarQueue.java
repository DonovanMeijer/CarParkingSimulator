package CarParkingSimulator.Model;

import java.util.*;

/**
 * Created by John on 29/03/16.
 */
public class CarQueue
{
    private Queue<Car> queue = new LinkedList<Car>();

    public boolean addCar(Car car)
    {
        System.out.println(car.getClass().getName());
        return queue.add(car);
    }

    public Car removeCar()
    {
        return queue.poll();
    }
}
