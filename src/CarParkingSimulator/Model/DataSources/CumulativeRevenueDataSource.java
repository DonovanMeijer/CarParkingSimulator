package CarParkingSimulator.Model.DataSources;

import CarParkingSimulator.Model.*;

/**
 * Created by John on 11/04/16.
 */
public class CumulativeRevenueDataSource extends RevenueDataSource
{
    public CumulativeRevenueDataSource(Garage garage)
    {
        super(garage);
    }

    protected void addTransaction(Payment transaction)
    {
        int day = 1 + (transaction.getTime() / 60) / 24;

        if (data.size() - 1 < day)
        {
            data.add(day, data.get(day - 1));
        }

        data.set(day, data.get(day) + transaction.getAmount());

        determineHighestValue();

        createVisualisationData();
    }

    protected void determineHighestValue()
    {
        //You might wonder why this would be is a separate method and the answer is stored in a field.
        //The GraphView requests this information multiple times when rendering, so why not calculate it once every the data changes and store it.

        double lastValue = data.get(data.size() - 1);

        if (highestValue < lastValue)
        {
            highestValue = lastValue;
        }
    }
}
