package CarParkingSimulator.Model;

import java.util.ArrayList;

/**
 * Created by wout on 4/6/2016.
 */
public class Finance
{
    private double amountPerHour = 2.5;

    public ArrayList<Payment> income = new ArrayList<Payment>();

    public void pay(int totalTime)
    {
        //determine the amount of quarters and pay for each parker quarter instead of each minure or hour
        int quarters = totalTime / 15;
        double amountDue = quarters * (amountPerHour / 4);
        income.add(new Payment(amountDue));
    }

    public double dailyRevenue()
    {
        double revenue = 0;
        for(Payment payment : income)
        {
            revenue += payment.amount;
        }
        return revenue;
    }
}
