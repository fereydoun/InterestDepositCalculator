package dotin;

import dotin.entities.Deposit;
import dotin.entities.FileBuilder;
import dotin.entities.XMLReader;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Collections;
import java.util.LinkedList;


public class InterestDepositCalculator {

    public static void main(String[] args) {

        XMLReader xmlReader = new XMLReader();
        Deposit deposit = new Deposit();
        LinkedList<Deposit> deposits = new LinkedList<Deposit>();

        NodeList depositList;

        //handle exceptions using Exception propagation
        try {

            //read xml file and parse it for create objects using Reflection
            depositList = deposit.getDepositElements(xmlReader.getDocumentElement(xmlReader.parseXmlFile("conf/deposits.xml")));

            for (int i = 0; i < depositList.getLength(); i++) {
                Element depositNode = (Element) depositList.item(i);

                try {

                    //create object using reflection
                    deposits.add(deposit.createDepositObjectBaseOnDepositTag(depositNode));
                }
                catch (Exception ex) {
                    System.out.println();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {

            //calculate interest deposit of each deposit type
            deposit.calculateInterestDeposit(deposits);

            //sort object list by customized compareTo() method of interface Comparable
            Collections.sort(deposits);

           // create output file
            FileBuilder<Deposit> fileBuilder = new FileBuilder<Deposit>();
            fileBuilder.createOutputFile("CustomerInterestDeposit.txt", deposits);

            System.out.println("output file created");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}