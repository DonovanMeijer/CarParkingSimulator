package CarParkingSimulator.Model;

/**
 * Created by wout on 4/6/2016.
 */
public class Payment
{
    private double amount;
    private int time;
    private String transactionType;

    public Payment(double amount, int time, String transactionType)
    {
        this.amount = amount;
        this.time = time;
        this.transactionType = transactionType;
    }

    public double getAmount()
    {
        return amount;
    }

    public String getTransactionType()
    {
        return transactionType;
    }

    public int getTime()
    {
        return time;
    }
}
