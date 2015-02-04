package dotin.entities;

import dotin.exceptions.DepositBalanceException;
import dotin.exceptions.DepositTypeException;
import dotin.exceptions.DurationInDaysException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.LinkedList;


public class Deposit implements Comparable<Deposit>{

    public final int CONST_NUMBER_IN_DENOMINATOR_OF_FORMULA = 36500;
    private String customerNumber;
    private BigDecimal depositBalance;
    private int durationInDays;
    private DepositType depositType;
    private BigDecimal interestDeposit;

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public BigDecimal getDepositBalance() {
        return depositBalance;
    }

    public void setDepositBalance(BigDecimal depositBalance) {
        this.depositBalance = depositBalance;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays)     {
        this.durationInDays = durationInDays;
    }

    public DepositType getDepositType() {
        return depositType;
    }


    public BigDecimal getInterestDeposit() {
        return interestDeposit;
    }

    public void setInterestDeposit(BigDecimal interestDeposit) {
        this.interestDeposit = interestDeposit;
    }

    /**
     *بر طبق مقدار خوانده شده از تگ
     * "depositType"
     * کلاس مربوط به نوع سپرده مورد نظر ایاد میشود و نرخ سپرده مقداردهی میشود
     * @param className deposit name
     * @throws Exception if deposit type not exist
     */
    public void setDepositType(String className) throws DepositTypeException{

            this.depositType = DepositType.getInstance(("dotin.entities."+className).trim());

    }


    public Deposit()
    {

    }

    // implement interest deposit formula
    public void calculateInterestDeposit(Deposit deposit) throws  Exception {

        try {
            BigDecimal interestDeposit = (new BigDecimal(deposit.getDepositType().getInterestRate())
                    .multiply(deposit.getDepositBalance())
                    .multiply(new BigDecimal(deposit.getDurationInDays())));

            interestDeposit = interestDeposit.divide(new BigDecimal(deposit.CONST_NUMBER_IN_DENOMINATOR_OF_FORMULA), 2);
            deposit.setInterestDeposit(interestDeposit);

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void calculateInterestDeposit(LinkedList<Deposit> deposits) throws Exception {

        try {

            for(Deposit deposit: deposits)
            {
                calculateInterestDeposit(deposit);
            }

//            for(Iterator iterator = deposits.iterator();iterator.hasNext();)
//            {
//                calculateInterestDeposit((Deposit)iterator.next());
//            }
//            while (iterator.hasNext()) {
//                calculateInterestDeposit(iterator.next());
//            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }


    //implementation compareTo of Comparable Class
    //list sort in descending on interest deposit value
    @Override
    public int compareTo(Deposit deposit) {

        if (this.interestDeposit.compareTo(deposit.getInterestDeposit()) == 1)
            return -1;
        else if (this.interestDeposit.compareTo(deposit.getInterestDeposit()) == -1)
            return 1;
        else
            return this.interestDeposit.compareTo(deposit.getInterestDeposit());

    }

    public NodeList getDepositElements(Element docElement) throws Exception
    {
        XMLReader xmlReader=new XMLReader();

        try {
            return xmlReader.getElementsByTagName(docElement,"Deposit");
        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }

    }


    public Deposit createDepositObjectBaseOnDepositTag(Element element) throws Exception
    {
        Deposit deposit;
        try{
            deposit = (Deposit)getInstance(("dotin.entities."+element.getTagName()).trim());

            initializeDepositObjectByElementValues(element,deposit);

        }
        catch (Exception ex){
            throw new Exception(ex.getMessage());
        }

        return deposit;
    }

    // create object using reflection
    public static Object getInstance(String className) throws Exception
    {
            return createObject(className);

    }

    private static Object createObject(String className) throws Exception
    {

        try {
            Class classDefinition = Class.forName(className);
            return classDefinition.newInstance();

        } catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }
    }

    // if deposit balance is negative Or duration in days is zero or negative
    private void initializeDepositObjectByElementValues(Element element,Deposit deposit) throws Exception
    {
        try{
        deposit.setCustomerNumber(XMLReader.getTextValue(element, "customerNumber"));

        if (XMLReader.getBigDecimalValue(element, "depositBalance").compareTo(BigDecimal.ZERO) == -1)
            throw new DepositBalanceException("customer"+deposit.getCustomerNumber()+"#deposit balance is negative");


        deposit.setDepositBalance(XMLReader.getBigDecimalValue(element, "depositBalance"));

        if ( XMLReader.getIntValue(element, "durationInDays") <= 0)
            throw new DurationInDaysException("customer"+deposit.getCustomerNumber()+"#duration in days is not positive");

        deposit.setDurationInDays(XMLReader.getIntValue(element, "durationInDays"));
        deposit.setDepositType(XMLReader.getTextValue(element,"depositType"));
        }catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }
    }

}
