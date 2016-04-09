package CarParkingSimulator.Model;

/**
 * Created by wout on 4/6/2016.
 */
public class Payment
{
    private double amount;
    private String transactionType;

    public Payment(double amount)
    {
        this.amount = amount;
    }

    public Payment(double amount, String transactionType) {this.amount = amount; this.transactionType = transactionType; }

    public double getAmount()
    {
        return amount;
    }

    public String getTransactionType() { return transactionType; }
}
