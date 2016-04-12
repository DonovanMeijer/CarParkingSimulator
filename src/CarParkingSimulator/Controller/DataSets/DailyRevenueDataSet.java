package CarParkingSimulator.Controller.DataSets;

import CarParkingSimulator.Model.*;

/**
 * Created by John on 11/04/16.
 */
public class DailyRevenueDataSet extends RevenueDataSet
{
    public DailyRevenueDataSet(Garage garage)
    {
        super(garage);
    }

    protected void addTransaction(Payment transaction)
    {
        int day = 1 + (transaction.getTime() / 60) / 24;

        if (data.size() - 1 < day)
        {
            data.add(day, 0.0);
        }

        data.set(day, data.get(day) + transaction.getAmount());

        determineHighestValue();

        createVisualisationData();
    }

    protected void determineHighestValue()
    {
        for (double entry : data)
        {
            if (highestValue < entry)
            {
                highestValue = entry;
            }
        }
    }
}
