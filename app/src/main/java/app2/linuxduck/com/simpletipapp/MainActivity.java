package app2.linuxduck.com.simpletipapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // Ints
    private int LONGduration;

    // ImageButtons
    private ImageButton moreOptionsImageButton;
    private ImageButton newTipImageButton;
    private ImageButton newCategoryImageButton;

    // Buttons
    private Button newCategorySubmitButton;
    private Button newTipSubmitButton;


    // LinearLayouts
    private LinearLayout newCategoryLinearLayout;
    private LinearLayout newTipLinearLayout;
    private LinearLayout tipsLinearLayout;

    // ListViews
    private ListView categoriesListView;

    // Array of tips and categories
    private ArrayList<Tips> allTips;
    private ArrayList<String> categories;

    // Toasts! Flying Toasters!
    private Toast newTipToast;

    // Spinners
    private Spinner percentagesSpinner;
    private Spinner categoriesSpinner;

    // ArrayAdapters
    private ArrayAdapter<CharSequence> percentagesSpinnerAdapter;
    private ArrayAdapter<String> categoriesSpinnerAdapter;
    private ArrayAdapter categoriesAdapter;


    // Files
    private File path;
    private File[] files;

    // EditTexts
    private EditText newCategoryEditText;
    private EditText newTitleEditText;
    private EditText newPlaceEditText;
    private EditText newBillEditText;
    private EditText newTaxEditText;

    // Booleans for Visibility or not
    private boolean moreOptionsBoolean;
    private boolean newTipBoolean;
    private boolean newCategoryBoolean;

    // Date
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ints
        LONGduration = Toast.LENGTH_LONG;

        // ImageButtons
        moreOptionsImageButton = findViewById(R.id.moreoptionsimagebutton);
        newTipImageButton = findViewById(R.id.newtipimagebutton);
        newCategoryImageButton = findViewById(R.id.newcategoryimagebutton);

        // Buttons
        newCategorySubmitButton = findViewById(R.id.catsubmitbutton);
        newTipSubmitButton = findViewById(R.id.submitbutton);

        // Linear Layouts
        newCategoryLinearLayout = findViewById(R.id.newcategorylinearlayout);
        newTipLinearLayout = findViewById(R.id.createtiplinearlayout);
        tipsLinearLayout = findViewById(R.id.tipsmadelistlinearlayout);

        // ListViews
        categoriesListView = findViewById(R.id.categorylistview);

        // Array of tips and categories
        allTips = new ArrayList<>();
        categories = new ArrayList<>();

        // Toasts! Flying Toasters!
        newTipToast = Toast.makeText(this, R.string.you_didnt_fill_everything_out, LONGduration);

        // Spinners
        percentagesSpinner = findViewById(R.id.percentagesspinner);
        categoriesSpinner = findViewById(R.id.categoryspinner);

        // Files
        path = getFilesDir();

        // EditTexts
        newCategoryEditText = findViewById(R.id.newcategoryedittext);
        newTitleEditText = findViewById(R.id.titleedittext);
        newPlaceEditText = findViewById(R.id.placeedittext);
        newBillEditText = findViewById(R.id.billedittext);
        newTaxEditText = findViewById(R.id.taxedittext);

        loadTips();

        // ArrayAdapeters
        percentagesSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.percentage, android.R.layout.simple_spinner_item);
        percentagesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        percentagesSpinner.setAdapter(percentagesSpinnerAdapter);

        categoriesSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        categoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(categoriesSpinnerAdapter);

        categoriesAdapter = new ArrayAdapter<>(this, R.layout.activity_category_list, categories);
        categoriesListView.setAdapter(categoriesAdapter);

        // Booleans for Visibility or not
        moreOptionsBoolean = false;
        newTipBoolean = false;
        newCategoryBoolean = false;

        newTipImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipsLinearLayout.setVisibility(View.GONE);
                if(!newTipBoolean) {
                    newTipBoolean = true;
                    moreOptionsBoolean = false;
                    newCategoryBoolean = false;
                    // Hiding other views
                    newCategoryLinearLayout.setVisibility(View.GONE);
                    categoriesListView.setVisibility(View.GONE);

                    // Displaying newTip view
                    newTipLinearLayout.setVisibility(View.VISIBLE);

                    newTipSubmitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean tipsExists = false;
                            // Hide ourselves
                            moreOptionsBoolean = false;
                            newTipBoolean = false;
                            newCategoryBoolean = false;
                            newTipLinearLayout.setVisibility(View.GONE);

                            try {
                                Date tempDate = new Date();
                                String tempTitle = newTitleEditText.getText().toString();
                                newTitleEditText.getText().clear();
                                String tempPlace = newPlaceEditText.getText().toString();
                                newPlaceEditText.getText().clear();
                                double tempBill = Double.valueOf(newBillEditText.getText().toString());
                                newBillEditText.getText().clear();
                                double tempTax = Double.valueOf(newTaxEditText.getText().toString());
                                newTaxEditText.getText().clear();
                                String tempCategory = categoriesSpinner.getSelectedItem().toString();
                                double tempTip = Double.valueOf(percentagesSpinner.getSelectedItem().toString());

                                Tips tempTips = new Tips(tempDate, tempTitle, tempPlace, tempBill, tempTax, tempTip, tempCategory);

                                files = path.listFiles();
                                for (File eachFile : files) {
                                    if (eachFile.getName().equals("tips.txt")) {
                                        try {
                                            FileOutputStream catOut = openFileOutput("tips.txt", MODE_APPEND);
                                            catOut.write((sdf.format(tempDate) + "," + tempTitle + "," + tempPlace + "," +
                                                    String.valueOf(tempBill) + "," + String.valueOf(tempTax) + "," +
                                                    String.valueOf(tempTip) + "," + tempCategory + "\n").getBytes());
                                            allTips.add(tempTips);
                                            catOut.close();
                                            tipsExists = true;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                if (!tipsExists) {
                                    // Create new settings
                                    FileOutputStream newCategoryFile;
                                    try {
                                        newCategoryFile = openFileOutput("tips.txt", Context.MODE_PRIVATE);
                                        newCategoryFile.write((sdf.format(tempDate) + "," + tempTitle + "," + tempPlace + "," +
                                                String.valueOf(tempBill) + "," + String.valueOf(tempTax) + "," +
                                                String.valueOf(tempTip) + "," + tempCategory + "\n").getBytes());
                                        allTips.add(tempTips);
                                        newCategoryFile.close();
                                        tipsExists = true;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Exception e){
                                newTipToast.show();
                            }
                        }
                    });
                } else {
                    newTipBoolean = false;
                    newTipLinearLayout.setVisibility(View.GONE);
                }
            }
        });

        moreOptionsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!moreOptionsBoolean) {
                    moreOptionsBoolean = true;
                    newTipBoolean = false;
                    newCategoryBoolean = false;
                    // Hiding other views
                    newTipLinearLayout.setVisibility(View.GONE);
                    newCategoryLinearLayout.setVisibility(View.GONE);

                    categoriesListView.setAdapter(categoriesAdapter);

                    // Display categories view
                    categoriesListView.setVisibility(View.VISIBLE);

                    categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            tipsLinearLayout.removeAllViewsInLayout();
                            for(Tips eachTip : allTips){
                                if(eachTip.getCategory().equals(categories.get(position))){
                                    LayoutParams lparams = new LayoutParams(
                                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                    LayoutParams lparamsTip = new LayoutParams(
                                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                                    lparamsTip.setMargins(0,0,0,40);

                                    TextView title = new TextView(getApplicationContext());
                                    title.setLayoutParams(lparams);
                                    title.setText(eachTip.getTitle());
                                    title.setTextSize(28);
                                    title.setTypeface(null, Typeface.BOLD);

                                    TextView date = new TextView(getApplicationContext());
                                    date.setLayoutParams(lparams);
                                    date.setText(eachTip.getDate());
                                    date.setTextSize(12);

                                    TextView place = new TextView(getApplicationContext());
                                    place.setLayoutParams(lparams);
                                    place.setText(eachTip.getPlace());
                                    place.setTextSize(16);

                                    TextView bill = new TextView(getApplicationContext());
                                    bill.setLayoutParams(lparams);
                                    String tempBill = getResources().getString(R.string.bill) + ":  " + getResources().getString(R.string.dollarsign)
                                            + String.valueOf(eachTip.getBill());
                                    bill.setText(tempBill);
                                    bill.setTextSize(16);

                                    TextView tax = new TextView(getApplicationContext());
                                    tax.setLayoutParams(lparams);
                                    String tempTax = getResources().getString(R.string.tax) + ": " + getResources().getString(R.string.dollarsign)
                                            + String.valueOf(eachTip.getTax());
                                    tax.setText(String.valueOf(tempTax));
                                    tax.setTextSize(16);

                                    TextView tip = new TextView(getApplicationContext());
                                    tip.setLayoutParams(lparamsTip);
                                    String tempTip = getResources().getString(R.string.tip) + ": " + getResources().getString(R.string.dollarsign)
                                            + String.valueOf(eachTip.getTip());
                                    tip.setText(tempTip);
                                    tip.setTextColor(getResources().getColor(R.color.green));
                                    tip.setTextSize(24);

                                    if(tipsLinearLayout != null) {
                                        tipsLinearLayout.addView(title);
                                        tipsLinearLayout.addView(date);
                                        tipsLinearLayout.addView(place);
                                        tipsLinearLayout.addView(bill);
                                        tipsLinearLayout.addView(tax);
                                        tipsLinearLayout.addView(tip);
                                    }
                                }
                            }

                            // Hide Categories List View
                            categoriesListView.setVisibility(View.GONE);
                        }
                    });
                } else {
                    categoriesListView.setVisibility(View.GONE);
                    moreOptionsBoolean = false;
                }
            }
        });

        newCategoryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!newCategoryBoolean) {
                    newCategoryBoolean = true;
                    newTipBoolean = false;
                    moreOptionsBoolean = false;

                    // Hide other views
                    newTipLinearLayout.setVisibility(View.GONE);
                    categoriesListView.setVisibility(View.GONE);

                    // Make 'Create a new category' visible
                    newCategoryLinearLayout.setVisibility(View.VISIBLE);

                    newCategorySubmitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean catExists = false;
                            // Hide 'Create a new category' elements
                            newCategoryLinearLayout.setVisibility(View.GONE);
                            moreOptionsBoolean = false;
                            newTipBoolean = false;
                            newCategoryBoolean = false;

                            String newCat = newCategoryEditText.getText().toString();
                            newCategoryEditText.getText().clear();
                            files = path.listFiles();
                            for (File eachFile : files) {
                                if (eachFile.getName().equals("cat.txt") && !categories.contains(newCat)) {
                                    try {
                                        FileOutputStream catOut = openFileOutput("cat.txt", MODE_APPEND);
                                        catOut.write((newCat + ",").getBytes());
                                        categories.add(newCat);
                                        catOut.close();
                                        catExists = true;
                                        categoriesListView.setAdapter(categoriesAdapter);
                                        categoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        categoriesSpinner.setAdapter(categoriesSpinnerAdapter);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            if (!catExists) {
                                // Create new settings
                                FileOutputStream newCategoryFile;
                                try {
                                    newCategoryFile = openFileOutput("cat.txt", Context.MODE_PRIVATE);
                                    newCategoryFile.write((newCat + ",").getBytes());
                                    categories.add(newCat);
                                    newCategoryFile.close();
                                    catExists = true;
                                    categoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    categoriesSpinner.setAdapter(categoriesSpinnerAdapter);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    newCategoryBoolean = false;
                    newCategoryLinearLayout.setVisibility(View.GONE);
                    newCategoryEditText.getText().clear();
                }
            }
        });
    }

    public void loadTips(){
        files = path.listFiles();
        for (File eachFile : files){
            // This builds an array of all our tip data
            if(eachFile.getName().equals("tips.txt")) {
                try {
                    FileInputStream settingsFile = openFileInput("tips.txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(settingsFile)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        String lineData[];
                        lineData = line.split(",");

                        // Get the date from the file :D
                        Date tempDate = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss").parse(lineData[0]);
                        String tempTitle = lineData[1];
                        String tempPlace = lineData[2];
                        double tempTip = Double.parseDouble(lineData[3]);
                        double tempBill = Double.parseDouble(lineData[4]);
                        double tempTax = Double.parseDouble(lineData[5]);
                        String tempCat = lineData[6];
                        Tips temp = new Tips(tempDate, tempTitle, tempPlace, tempTip, tempBill, tempTax, tempCat);
                        allTips.add(temp);
                    }
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Build an array of each category
            } else if(eachFile.getName().equals("cat.txt")){
                try {
                    FileInputStream settingsFile = openFileInput("cat.txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(settingsFile)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        String lineData[];
                        lineData = line.split(",");
                        for(String eachCategory : lineData){
                            categories.add(eachCategory);
                        }
                    }
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
