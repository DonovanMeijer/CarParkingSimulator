package CarParkingSimulator.Model;

import java.util.ArrayList;

/**
 * Created by wout on 4/6/2016.
 */
public class Finance
{
    private double amountPerHour = 5;

    public ArrayList<Payment> income = new ArrayList<Payment>();

    public void NormalPay(int totalTime)
    {
        double amountDue = totalTime * (amountPerHour / 60);

        income.add(new Payment(amountDue));
    }

    public void PassHolderPay(int totalTime)
    {
        income.add(new Payment(0, "passHolder"));
    }

    public double dailyRevenue()
    {
        double revenue = 0;

        for(Payment payment : income)
        {
            revenue += payment.getAmount();
        }

        return revenue;
    }
}
