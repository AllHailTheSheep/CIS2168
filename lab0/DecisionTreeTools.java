package lab0;

import java.util.ArrayList;

/**
 *
 * @author Kevin Jang
 */
public class DecisionTreeTools{
    private ArrayList<BankObject> testing;
    private ArrayList<BankObject> training;
    
    
    DecisionTreeTools(ArrayList<BankObject> list1, ArrayList<BankObject> list2){
        this.testing = list2;
        this.training = list1;
        
    }
    
    
    public double getAvgAgeTraining(){
        double total = 0.0;
        for (BankObject account : this.training){
            total += account.toDouble(account.getAge());
        }
        return total/this.training.size();
    }
    
    public double getAvgAgeTesting(){
        double total = 0.0;
        for (BankObject account : this.testing){
            total += account.toDouble(account.getAge());
        }
        return total/this.testing.size();
    }
    
    public double getAvgBalanceTraining(){
        double total = 0.0;
        
        for(BankObject account: this.training ){
            total += account.getBalance();
        }
        
        return total/this.training.size();
    }
    
    public double getAvgBalanceTesting(){
        double total = 0.0;
        
        for(BankObject account: this.testing ){
            total += account.getBalance();
        }
        
        return total/this.testing.size();
    }
    
    public double getDefaultedEntropyTraining(){
        int count = 0;
        for(BankObject account: this.training){
            if (account.getDefaulted() != false){
                count += 1;
                
            }
        }
        return ((double)count/this.training.size());
    }
    
    public double getDefaultedEntropyTesting(){
        int count = 0;
        for(BankObject account: this.testing){
            if (account.getDefaulted() != false){
                count += 1;
            }
        }
        
        return (double)count/this.testing.size();
    }
    
    public double getHousingEntropyTraining(){
        int count = 0;
        for(BankObject account : this.training){
            if(account.getHousing() != false){
                count += 1;
            }
        }
        return (double)count/this.training.size();
    }
    
    public double getHousingEntropyTesting(){
        int count = 0;
        for(BankObject account : this.testing){
            if(account.getHousing() != false){
                count += 1;
            }
        }
                return (double) count/this.testing.size();
    }
    
    public double getLoanEntropyTraining(){
        int count = 0;
        
        for(BankObject account : this.training){
            if (account.getLoan() != false){
                count += 1;
            }
        }
        
        
        return (double) count/this.training.size();
    }
    
    public double getLoanEntropyTesting(){
        int count = 0;
        
        for(BankObject account : this.testing){
            if (account.getLoan() != false){
                count += 1;
            }
        }
        
        return (double) count/this.testing.size();
    }
    
    public int getTrainingSize(){
        return this.training.size();
    }
    
    public int getTestingSize(){
        return this.testing.size();
    }
    
    
    
}
