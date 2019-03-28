package app2.linuxduck.com.simpletipapp;

import java.util.Date;

public class Tips {
    // Date of transaction
    private Date date;
    // Title of the tip you created
    private String title;
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
    public Tips(Date newDate, String newTitle, String newPlace, double newTip, double newBill, double newTax, String newCategory){
        date = newDate;
        title = newTitle;
        place = newPlace;
        tip = newTip;
        bill = newBill;
        tax = newTax;
        category = newCategory;
    }

    public String getDate() {
        return date.toString();
    }

    public String getTitle(){return title;}
    public String getPlace(){return place;}
    public double getBill(){return bill;}
    public double getTax(){return tax;}
    public String getCategory(){return category;}
    public double getTip(){return tip;}
    public void updateDate(Date newDate){date = newDate;}
    public void updateTitle(String newTitle){title = newTitle;}
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
