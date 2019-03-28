package app2.linuxduck.com.simpletipapp;

import java.util.Date;

public class Tips {
    // Date of transaction
    private Date date;
    // Store/restaurant where the tip occurred
    private String place;
    // Amount of the tip given
    private double tip;
    // Amount of the bill before tax
    private double bill;
    // Tax on the amount you paid
    private double tax;
    // Category for this tip
    private String category;

    public Tips(){}
    public Tips(Date newDate, String newPlace, double newBill, double newTax, double newTip, String newCategory){
        date = newDate;
        place = newPlace;
        tip = newTip;
        bill = newBill;
        tax = newTax;
        category = newCategory;
    }

    public String getDate() {
        return date.toString();
    }

    public String getPlace(){return place;}
    public double getBill(){return bill;}
    public double getTax(){return tax;}
    public String getCategory(){return category;}
    public double getTip(){return tip;}
    public double getTotal(){
        double total = calculateTipWithTax(tip);
        total += tax + bill;
        return total;
    }
    public void updateDate(Date newDate){date = newDate;}
    public void updatePlace(String newPlace){place = newPlace;}
    public void setTipChosen(double newTip){
        tip = newTip;
    }
    public void updateBill(double newBill){bill = newBill;}
    public void updateTax(double newTax){tax = newTax;}
    public void updateCategory(String newCat){category = newCat;}
    public double calculateTipWithTax(double rate){
        return rate * (bill + tax);
    }
    public double calculateTipWithNoTax(double rate){
        return rate * bill;
    }
}
