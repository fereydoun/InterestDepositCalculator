package dotin.entities;


import dotin.exceptions.DepositTypeException;

public abstract class DepositType {

    private int interestRate;

    public int getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }

    public static DepositType getInstance(String className) throws DepositTypeException
    {
//        try {
            return (DepositType)createObject(className);
//        }
//        catch (DepositTypeException ex)
//        {
//            throw new DepositTypeException(ex.getMessage());
//        }
    }

    private static Object createObject(String className) throws DepositTypeException
    {


        try {
            Class classDefinition = Class.forName(className);
            return classDefinition.newInstance();

        } catch (Exception ex)
        {
            throw new DepositTypeException("unknown Deposit Type");
        }
    }



}
