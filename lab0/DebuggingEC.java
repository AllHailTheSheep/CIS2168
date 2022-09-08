package lab0;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


/**
 *
 * @author Kevin Jang
 */
public class DebuggingEC {

    
    public static void main(String[] args) {
        //initialize variables
        Scanner fileRead = null;
        ArrayList<BankObject> bankObjectListTraining = new ArrayList<>();
        ArrayList<BankObject> bankObjectListTesting = new ArrayList<>();
        ArrayList<List> dataToSend = new ArrayList<>();
        
        //check to make sure we can open file
        try { 
            fileRead = new Scanner(new File("lab0/bank-full.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("File Location unknown.");
        }
        
        //skip first line since it is nothing but headers
        String line = fileRead.nextLine();
        

        while (fileRead.hasNextLine()) {
            //initliaze temporary variables
            line = fileRead.nextLine();
            List<String> tempAccount = new ArrayList<>();
            
            //split each line into a String array of values
            String[] lineSplit = line.split(";");
            
            //read in lineSplit into an arraylist object
            ArrayList<String> accountList = new ArrayList<String>(Arrays.asList(lineSplit));

            //for each item in accountList, we use regex to remove double quotes, then add into tempAccount. This creates an ArrayList object that holds all the relevant information of the first 8 columns.
            for(int i = 0; i < 8; i++){
                String temp = accountList.get(i).toString().toLowerCase();
                temp = temp.replaceAll("^\"|\"$", "");
                tempAccount.add(temp);
            }
            
            //add the last column of the relevant data from our lineSplit String Array
            String lastElement = accountList.get(accountList.size()-1);
            lastElement = lastElement.replaceAll("^\"|\"$", "");
            tempAccount.add(lastElement);
            
            //rather than send each information to create a bank object, we add to an ArrayList object called dataToSend to send all relevant data at once.           
            dataToSend.add(tempAccount);
        }

        //close fileRead since we are done with its usage at this time. We have all relevant data necessary.
        fileRead.close();
        

        //for each item in dataToSend, we create a bankObject with its information. Add this to a list 
        for (List account : dataToSend){
            
            BankObject tempBankObject = new BankObject(account);
            bankObjectListTraining.add(tempBankObject);

        }

        //select a random assortment for the training set and testing set.
        Random randomSelector = new Random();
        int randomSize = randomSelector.nextInt(6000 - 4000 + 1) + 4000;
        for(int k = 0; k < randomSize; k++){
            int randomIndex = randomSelector.nextInt(bankObjectListTraining.size());
            BankObject tempHolder = bankObjectListTraining.get(randomIndex);
            bankObjectListTesting.add(tempHolder);
            bankObjectListTraining.remove(randomIndex);
        }
        
        //create DecisionTreeTools Object to make things easier to access and read.
        DecisionTreeTools treeTools = new DecisionTreeTools(bankObjectListTesting, bankObjectListTraining);
        
        //print results.
        System.out.println("The size of the training set is: " + treeTools.getTrainingSize());
        System.out.println("The size of the testing set is: " + treeTools.getTestingSize());
        System.out.println("The average age in the training set is: " + treeTools.getAvgAgeTraining());
        System.out.println("The average age in the testing set is: " + treeTools.getAvgAgeTesting());
        System.out.println("The average balance in the training set is: " + treeTools.getAvgBalanceTraining());
        System.out.println("The average balance in the testing set is: " + treeTools.getAvgBalanceTesting());
        System.out.println("The percentage of defaulted applicants in the training set is: " + treeTools.getDefaultedEntropyTraining());
        System.out.println("The percentage of defaulted applicants in the testing set is: " + treeTools.getDefaultedEntropyTesting());
        System.out.println("The percentage of applicants with housing in the training set is: " + treeTools.getHousingEntropyTraining());
        System.out.println("The percentage of applicants with housing in the testing set is: " + treeTools.getHousingEntropyTesting());
        System.out.println("The percentage of applicants with loans in the training set is: " + treeTools.getLoanEntropyTraining());
        System.out.println("The percentage of applicants with loans in the testing set is: " + treeTools.getLoanEntropyTesting());     
    }

}
