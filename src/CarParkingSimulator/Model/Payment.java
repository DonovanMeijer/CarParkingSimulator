package CarParkingSimulator.Model;

/**
 * Created by wout on 4/6/2016.
 */
public class Payment
{
    private double amount;
    private int time;
    private TransactionType type;

    public enum TransactionType
    {
        Normal,
        PassHolder
    }

    public Payment(double amount, int time, TransactionType type)
    {
        this.amount = amount;
        this.time = time;
        this.type = type;
    }

    public double getAmount()
    {
        return amount;
    }

    public TransactionType getType()
    {
        return type;
    }

    public int getTime()
    {
        return time;
    }
}
