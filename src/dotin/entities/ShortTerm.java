package dotin.entities;

/**
 * Created by Dotin school 5 on 2/1/2015.
 * extends DepositType
 */
public class ShortTerm  extends DepositType {

    public final int INTEREST_RATE = 10;

    /**
     * initialize interestRate of Abstract DepositType
     */
    public ShortTerm()
    {
        setInterestRate(INTEREST_RATE);
    }

}
