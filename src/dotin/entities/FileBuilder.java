package dotin.entities;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class FileBuilder<T> {

    //create random access file
    public void createOutputFile(String filePath,List<T> list) throws IOException
    {
        RandomAccessFile file = new RandomAccessFile(filePath,"rw");


        for(T t:list)
        {
            //if throw exception in a line of list goto next line
            try {
                writeIntoFile(file, t);

            } catch (Exception ex) {
                System.out.println();
            }
        }

        file.close();
    }

    private void writeIntoFile(RandomAccessFile file,T iterator) throws Exception
    {
        StringBuffer stringBuffer = new StringBuffer();
        if(iterator instanceof Deposit)
        {
            writeDepositObject((Deposit)iterator,stringBuffer);
        }

        file.writeBytes(stringBuffer.toString());
    }

    //create file content from two property(customerName,interestDeposit) of Deposit class
    private void writeDepositObject(Deposit deposit,StringBuffer stringBuffer) throws Exception
    {


        //stringBuffer.setLength(deposit.getCustomerNumber().length() + deposit.getInterestDeposit().toString().length()+1);

        if (deposit.getCustomerNumber() !=null )
            stringBuffer.append(deposit.getCustomerNumber());

        stringBuffer.append("#");

        if (deposit.getInterestDeposit() !=null )
            stringBuffer.append(deposit.getInterestDeposit());
        stringBuffer.append("\n");

    }

}
