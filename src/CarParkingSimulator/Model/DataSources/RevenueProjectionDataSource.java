package CarParkingSimulator.Model.DataSources;

import CarParkingSimulator.Model.*;

/**
 * Created by John on 11/04/16.
 */
public class RevenueProjectionDataSource implements DataSource
{
    private Finance finances;

    public RevenueProjectionDataSource(Garage garage)
    {

    }

    @Override
    public double[] getDataSource()
    {
        return new double[0];
    }

    @Override
    public double getHighestValue()
    {
        return 0;
    }
}