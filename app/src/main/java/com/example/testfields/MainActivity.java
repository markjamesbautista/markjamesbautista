package com.example.testfields;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatePickerDialog datepicker;
    private MainActivity binding;



    @BindView(R.id.fNameTXT)            EditText fNameTXT;
    @BindView(R.id.emailTXT)            EditText emailTXT;
    @BindView(R.id.numberTXT)           EditText numberTXT;
    @BindView(R.id.ageTXT)              TextView ageTXT;
    @BindView(R.id.gender)              Spinner gender;
    @BindView(R.id.birthdayTXT)         TextView birthdayTXT;
    @BindView(R.id.submitBTN)           Button submitBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        onViewReady();
    }

    private void onViewReady() {
        submitBTN.setOnClickListener(this);
        birthdayTXT.setOnClickListener(this);

    }

    private void onSubmitClick() {
        if (!isCharAllowed(fNameTXT.getText().toString())){
            Toast.makeText(this,"Name is text and characters only", Toast.LENGTH_SHORT).show();
        }else if(!isValidEmail(convertNullToBlankString(emailTXT.getText().toString()))){
            Toast.makeText(this,"Invalid email format!", Toast.LENGTH_SHORT).show();
        }else if(!isValidMobileNumber(convertNullToBlankString(numberTXT.getText().toString()))){
            Toast.makeText(this,"Invalid phone number!", Toast.LENGTH_SHORT).show();
        }else if(Double.parseDouble(convertNullToZeroString(ageTXT.getText().toString())) < 18){
            Toast.makeText(this,"Age must be 18 and above!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Successfully Saved!", Toast.LENGTH_SHORT).show();
        }

    }

    public static String convertNullToBlankString(String raw) {
        if (raw == null) {
            return "";
        }
        return raw;
    }
    public static String convertNullToZeroString(String raw) {
        if (raw == null || convertNullToBlankString(raw).equalsIgnoreCase("")) {
            return "0";
        }
        return raw;
    }

    private boolean isCharAllowed(String c) {
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(c);
        return ms.matches();
    }
    private boolean isValidMobileNumber(String c) {
        Pattern ps = Pattern.compile("^(09|\\+639)\\d{9}$");
        Matcher ms = ps.matcher(c);
        return ms.matches();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void openDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year1 = cldr.get(Calendar.YEAR);
        // date picker dialog
        datepicker = new DatePickerDialog(MainActivity.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    birthdayTXT.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    ageTXT.setText("Age: " + String.valueOf(year1 - year));
                }, year1, month, day);
        datepicker.show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submitBTN:
                onSubmitClick();
                break;
            case R.id.birthdayTXT:
                openDatePicker();
                break;
        }
    }
}