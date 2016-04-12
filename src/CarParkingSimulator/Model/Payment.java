package CarParkingSimulator.Model;

/**
 * Class for storing transaction data.
 * @author Wout Feringa, Donovan Meijer
 * @version 1.0
 */
public class Payment
{
    private double amount;
    private int time;
    private TransactionType type;

    /**
     * List of constants of transaction types.
     */
    public enum TransactionType
    {
        Normal,
        PassHolder
    }

    /**
     * Class for different kind of payments.
     * @param amount amount to pay.
     * @param time amount of time.
     * @param type type of transaction.
     */
    public Payment(double amount, int time, TransactionType type)
    {
        this.amount = amount;
        this.time = time;
        this.type = type;
    }

    /**
     * @return The amount.
     */
    public double getAmount()
    {
        return amount;
    }

    /**
     * @return The type of transaction.
     */
    public TransactionType getType()
    {
        return type;
    }

    /**
     * @return The time.
     */
    public int getTime()
    {
        return time;
    }
}
