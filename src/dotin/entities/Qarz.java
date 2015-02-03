package dotin.entities;

/**
 * Created by Dotin school 5 on 2/1/2015.
 * extends DepositType
 */
public class Qarz  extends DepositType {

    public final int INTEREST_RATE = 0;

    /**
     * initialize interestRate of Abstract DepositType
     */
    public Qarz()
    {
        setInterestRate(INTEREST_RATE);
    }

}
