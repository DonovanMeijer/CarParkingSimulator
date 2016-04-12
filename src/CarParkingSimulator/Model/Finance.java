package CarParkingSimulator.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by wout on 4/6/2016.
 */
public class Finance
{
    private Garage garage;

    private double amountPerHour = 5;

    private ArrayList<Payment> parkingIncome;

    public Finance(Garage garage)
    {
        this.garage = garage;

        parkingIncome = new ArrayList<Payment>();
    }

    public void pay(int timeParked, int timeLeft, Payment.TransactionType transactionType)
    {
        Payment transaction = new Payment(calculateAmountDue(timeParked, transactionType), timeLeft, transactionType);

        parkingIncome.add(transaction);

        //Trigger all associated event listeners.
        for (TransactionListener listener : eventListeners)
        {
            listener.TransactionCompleted(transaction);
        }
    }

    public double calculateAmountDue(int timeParked, Payment.TransactionType transactionType)
    {
        double amountDue = 0.00;

        if (transactionType == Payment.TransactionType.Normal)
        {
            amountDue = Math.round((timeParked * (amountPerHour / 60)) * 100.0) / 100.0;
        }
        else
        {
            amountDue = 0;
        }

        return amountDue;
    }

    public ArrayList<Payment> getTransactions()
    {
        return parkingIncome;
    }

    public double getRevenue()
    {
        return getRevenue(0, Integer.MAX_VALUE);
    }

    public double getRevenue(int beginTime, int endTime)
    {
        double revenue = 0.00;

        for(Payment payment : parkingIncome)
        {
            if (payment.getTime() >= beginTime && payment.getTime() <= endTime)
            {
                revenue += payment.getAmount();
            }
        }

        return revenue;
    }

    public double getRevenueProjection()
    {
        double revenue = 0.00;

        for (int floor = 0; floor < garage.getNumberOfFloors(); floor++)
        {
            for (int row = 0; row < garage.getNumberOfRows(); row++)
            {
                for (int place = 0; place < garage.getNumberOfPlaces(); place++)
                {
                    Location location = new Location(floor, row, place);

                    Car car = garage.getCarAt(location);

                    if (car != null)
                    {
                        revenue += SimulatorTime.step + car.getMinutesLeft() - car.getTimeEntered();
                    }
                }
            }
        }

        return revenue;
    }

    //region Finance events
    private List<TransactionListener> eventListeners = new ArrayList<TransactionListener>();

    public void addListener(TransactionListener listenerToAdd)
    {
        eventListeners.add(listenerToAdd);
    }

    public void removeListener(TransactionListener listenerToRemove)
    {
        eventListeners.remove(listenerToRemove);
    }

    public interface TransactionListener
    {
        void TransactionCompleted(Payment transaction);
    }
    //endregion
}
