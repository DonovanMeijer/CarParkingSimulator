package CarParkingSimulator.Model;

import java.util.ArrayList;

/**
 * Created by wout on 4/6/2016.
 */
public class Finance
{
    private double amountPerHour = 5;

    public ArrayList<Payment> parkingIncome;

    public Finance(Garage garage)
    {
        parkingIncome = new ArrayList<Payment>();
    }

    public void pay(int timeParked, int timeLeft)
    {
        double amountDue = Math.round((timeParked * (amountPerHour / 60)) * 100.0) / 100.0;

        parkingIncome.add(new Payment(amountDue, timeLeft, Payment.TransactionType.Normal));
    }

    public void payPassHolder(int timeParked, int timeLeft)
    {
        parkingIncome.add(new Payment(0, timeLeft, Payment.TransactionType.PassHolder));
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
}
