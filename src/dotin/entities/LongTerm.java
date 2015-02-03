package dotin.entities;

/**
 * Created by Dotin school 5 on 2/1/2015.
 * extends DepositType
 */
public class LongTerm extends DepositType {

    public final int INTEREST_RATE = 20;

    /**
     * initialize interestRate of Abstract DepositType
     */
    public LongTerm()
    {
        setInterestRate(INTEREST_RATE);
    }

}
