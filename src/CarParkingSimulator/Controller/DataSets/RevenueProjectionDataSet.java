package CarParkingSimulator.Controller.DataSets;

import CarParkingSimulator.Model.*;

/**
 * Created by John on 11/04/16.
 */
public class RevenueProjectionDataSet implements DataSet
{
    private Finance finances;

    public RevenueProjectionDataSet(Garage garage)
    {

    }

    @Override
    public double[] getDataSet()
    {
        return new double[0];
    }

    @Override
    public double getHighestValue()
    {
        return 0;
    }
}