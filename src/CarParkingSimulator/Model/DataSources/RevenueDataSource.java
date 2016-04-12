package CarParkingSimulator.Model.DataSources;

import CarParkingSimulator.Model.*;

import java.util.*;

/**
 * Created by John on 11/04/16.
 */
public abstract class RevenueDataSource implements DataSource
{
    private Finance finances;

    protected ArrayList<Double> data;

    private double[] visualisationData;

    protected double highestValue = 0;

    public RevenueDataSource(Garage garage)
    {
        finances = garage.getFinances();

        initiateDataSource();

        finances.addListener(new Finance.TransactionListener()
        {
            @Override
            public void TransactionCompleted(Payment transaction)
            {
                addTransaction(transaction);
            }
        });
    }

    private void initiateDataSource()
    {
        data = new ArrayList<Double>();

        data.add(0, 0.0);

        for (int index = 0; index < finances.getTransactions().size(); index += 1)
        {
            addTransaction(finances.getTransactions().get(index));
        }

        determineHighestValue();

        createVisualisationData();
    }

    //You might wonder why this would be is a separate method and the answer is stored in a field.
    //The GraphView requests this information multiple times when rendering, so why not calculate it once every the data changes and store it.
    protected abstract void determineHighestValue();

    protected void createVisualisationData()
    {
        visualisationData = new double[data.size()];

        for (int index = 0; index < data.size(); index += 1)
        {
            visualisationData[index] = data.get(index);
        }
    }

    protected abstract void addTransaction(Payment transaction);

    @Override
    public double[] getDataSource()
    {
        return visualisationData;
    }

    @Override
    public double getHighestValue()
    {
        return highestValue;
    }
}