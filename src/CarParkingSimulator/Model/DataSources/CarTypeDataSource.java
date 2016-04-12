package CarParkingSimulator.Model.DataSources;

import CarParkingSimulator.Model.*;

import static CarParkingSimulator.Model.Garage.*;

/**
 * Created by John on 11/04/16.
 */
public class CarTypeDataSource implements DataSource
{
    private Garage garage;

    private double[] data = new double[2];

    public CarTypeDataSource(Garage garage)
    {
        this.garage = garage;

        initiateDataSource();

        garage.addListener(new GarageListener()
        {

            @Override
            public void CarEntered(Car car)
            {

                addCar(car);
            }

            @Override
            public void CarExited(Car car)
            {
                removeCar(car);
            }
        });
    }

    private void initiateDataSource()
    {
        for (int floor = 0; floor < garage.getNumberOfFloors(); floor++)
        {
            for (int row = 0; row < garage.getNumberOfRows(); row++)
            {
                for (int place = 0; place < garage.getNumberOfPlaces(); place++)
                {
                    Location location = new Location(floor, row, place);

                    if (garage.getCarAt(location) != null)
                    {
                        Car car = garage.getCarAt(location);

                        addCar(car);
                    }
                }
            }
        }
    }

    private void addCar(Car car)
    {

        if(car instanceof NormalCar)
        {
            data[0] += 1;
        }
        else if(car instanceof PassHolderCar)
        {
            data[1] += 1;
        }
    }

    private void removeCar(Car car)
    {
        if(car instanceof NormalCar)
        {
            data[0] -= 1;
        }
        else if(car instanceof PassHolderCar)
        {
            data[1] -= 1;
        }
    }

    @Override
    public double[] getDataSource()
    {
        return data;
    }

    @Override
    public double getHighestValue()
    {
        return 0;
    }
}
