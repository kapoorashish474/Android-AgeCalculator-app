package com.ashishkapoor.agecalculator;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.IntegerRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private Calendar calendar;
    private int year, month, day;
    private TextView ageYears, ageMonths, ageDays, nxtMonths, nxtDays, totalYears, totalMonths, totalWeeks, totalDays, totalHours, totalMins, totalSecs;
    private String todaydate, dob;
    DateFormat df;
    Date df1, df2, df3;
    long diff, days, temp_month, temp_days;
    public String res1, res2, res3, res4, res5, res6, res7, res8, res9, res10, res11;
    private EditText dateBirth, todayDate;
    private String day1, month1;
    int default1, default2, default3;
    private String default_1, default_2;
    LinearLayout resultLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        defaultTodayDate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#0a5796"));
        }
        ActionBar mActionBar = getSupportActionBar();
        LayoutInflater mInflater = LayoutInflater.from(this);
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0a5796")));
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar_blue, null);

    }

    public void OnClear(View view) {
        defaultTodayDate();
        dateBirth.setText("");
        ageYears.setText(R.string.default_text);
        ageMonths.setText(R.string.default_text);
        ageDays.setText(R.string.default_text);
        nxtMonths.setText(R.string.default_text);
        nxtDays.setText(R.string.default_text);
        totalYears.setText(R.string.default_text);
        totalMonths.setText(R.string.default_text);
        totalWeeks.setText(R.string.default_text);
        totalDays.setText(R.string.default_text);
        totalHours.setText(R.string.default_text);
        totalMins.setText(R.string.default_text);
        totalSecs.setText(R.string.default_text);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#0a5796"));
        }
        ActionBar mActionBar = getSupportActionBar();
        LayoutInflater mInflater = LayoutInflater.from(this);
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0a5796")));
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar_blue, null);
    }

    private void defaultTodayDate() {
        default1 = calendar.get(Calendar.DAY_OF_MONTH);
        default2 = calendar.get(Calendar.MONTH) + 1;
        default3 = calendar.get(Calendar.YEAR);

        if ((0 < default1) && (default1 < 10)) {
            default_1 = "0" + default1;
        } else {
            default_1 = String.valueOf(default1);
        }
        if ((0 < default2) && (default2 < 10)) {
            default_2 = "0" + default2;
        } else {
            default_2 = String.valueOf(default2);
        }
        todayDate.setText(new StringBuilder().append(default_1).append("/").append(default_2).append("/").append(default3));
    }

    private void init() {
        resultLayout = (LinearLayout) findViewById(R.id.resultLayout);
        ageYears = (TextView) findViewById(R.id.age_years);
        ageMonths = (TextView) findViewById(R.id.age_months);
        ageDays = (TextView) findViewById(R.id.age_days);
        nxtMonths = (TextView) findViewById(R.id.nxt_months);
        nxtDays = (TextView) findViewById(R.id.nxt_days);
        totalYears = (TextView) findViewById(R.id.total_years);
        totalMonths = (TextView) findViewById(R.id.total_months);
        totalWeeks = (TextView) findViewById(R.id.total_weeks);
        totalDays = (TextView) findViewById(R.id.total_days);
        totalHours = (TextView) findViewById(R.id.total_hours);
        totalMins = (TextView) findViewById(R.id.total_mins);
        totalSecs = (TextView) findViewById(R.id.total_secs);
        dateBirth = (EditText) findViewById(R.id.date_birth);
        todayDate = (EditText) findViewById(R.id.today_date);


        dateBirth.addTextChangedListener(new EditTextListener());
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        df = new SimpleDateFormat("dd/MM/yyyy");

    }

    private class EditTextListener implements TextWatcher {
        int prevL = 0;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            prevL = dateBirth.getText().toString().length();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if ((prevL < s.length()) && (s.length() == 2 || s.length() == 5)) {
                String data = dateBirth.getText().toString();
                dateBirth.setText(data + "/");
                dateBirth.setSelection(s.length() + 1);
            }
        }

    }

    public void calculate(View view) {
        todaydate = todayDate.getText().toString();
        dob = dateBirth.getText().toString();

        if (!todayDate.equals("") && !todayDate.equals(null)) {
            try {
                df1 = df.parse(todaydate);
                df2 = df.parse(dob);
                df3 = df.parse("01/01/1990");

                if (df1.compareTo(df2) > 0) {

                    int a = Integer.parseInt(dob.substring(0, 2));
                    int b = Integer.parseInt(dob.substring(3, 5));
                    int c = Integer.parseInt(dob.substring(6, 10));

                    if (a != 0 && b != 0) {


                        if (a < 32 && b < 13 && c > 1900) {

                            diff = df1.getTime() - df2.getTime();
                            days = (TimeUnit.DAYS.convert(diff, TimeUnit.DAYS)) / (24 * 60 * 60 * 1000);
                            res11 = String.valueOf(days / 30);
                            if ((days / 30) < 10) {
                                res11 = "0" + res11;
                            }

                            res6 = String.valueOf(days / 7);
                            if ((days / 7) < 10) {
                                res6 = "0" + res6;
                            }
                            res7 = String.valueOf(days);
                            if (days < 10) {
                                res7 = "0" + res7;
                            }
                            res8 = String.valueOf(days * 24);
                            res9 = String.valueOf(days * 24 * 60);
                            res10 = String.valueOf(days * 24 * 60 * 60);

                            if (days > 365) {
                                res1 = String.valueOf(days / 365);
                                if ((days / 365) < 10) {
                                    res1 = "0" + res1;
                                }
                                temp_month = days % 365;
                                res2 = String.valueOf(temp_month / 30);
                                if ((temp_month / 30) < 10) {
                                    res2 = "0" + res2;
                                }
                                temp_days = (temp_month % 30);
                                res3 = String.valueOf(temp_days);
                                if (temp_days < 10) {
                                    res3 = "0" + res3;
                                }
                                res4 = String.valueOf((11 - (temp_month / 30)));
                                if ((temp_month / 30) < 10) {
                                    res4 = "0" + res4;
                                }
                                res5 = String.valueOf(30 - temp_days);
                                if ((30 - temp_days) < 10) {
                                    res5 = "0" + res5;
                                }

                            } else {
                                res1 = "00";
                                if (days > 30) {
                                    res2 = String.valueOf(days / 30);
                                    if ((days / 30) < 10) {
                                        res2 = "0" + res2;
                                    }
                                    temp_days = days % 30;
                                    res3 = String.valueOf(temp_days);
                                    if (temp_days < 10) {
                                        res3 = "0" + res3;
                                    }
                                    res4 = String.valueOf(11 - (days / 30));
                                    if ((11 - (days / 30)) < 10) {
                                        res4 = "0" + res4;
                                    }
                                    res5 = String.valueOf((30 - temp_days));
                                    if ((30 - temp_days) < 10) {
                                        res5 = "0" + res5;
                                    }

                                } else {
                                    res2 = "00";
                                    res3 = String.valueOf(days);
                                    if (0 < days && days < 10) {
                                        res3 = "0" + res3;
                                    }
                                    res4 = "11";
                                    res5 = String.valueOf(30 - days);
                                    if (20 < days && days < 30) {
                                        res5 = "0" + res5;
                                    }

                                }
                            }

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Window window = getWindow();
                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                window.setStatusBarColor(Color.parseColor("#1b662c"));
                            }
                            ActionBar mActionBar = getSupportActionBar();
                            LayoutInflater mInflater = LayoutInflater.from(this);
                            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b662c")));
                            View mCustomView = mInflater.inflate(R.layout.custom_actionbar_green, null);

                            ageYears.setText(res1);
                            ageMonths.setText(res2);
                            ageDays.setText(res3);
                            nxtMonths.setText(res4);
                            nxtDays.setText(res5);
                            totalYears.setText(res1);
                            totalMonths.setText(res11);
                            totalWeeks.setText(res6);
                            totalDays.setText(res7);
                            totalHours.setText(res8);
                            totalMins.setText(res9);
                            totalSecs.setText(res10);

                        } else {

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            alertDialogBuilder.setTitle("Buzz...");
                            alertDialogBuilder
                                    .setMessage("Please enter correct date")
                                    .setCancelable(false)
                                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        }
                    } else {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setTitle("Buzz...");
                        alertDialogBuilder
                                .setMessage("Day or Month cannot be zero")
                                .setCancelable(false)
                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                } else if (df1.compareTo(df2) < 0) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setTitle("Buzz...");
                    alertDialogBuilder
                            .setMessage("Date of birth cannot be in future")
                            .setCancelable(false)
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                } else if (df1.compareTo(df2) == 0) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setTitle("Buzz...");
                    alertDialogBuilder
                            .setMessage("Date of birth cannot be same as Today's date")
                            .setCancelable(false)
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setTitle("Buzz...");
                    alertDialogBuilder
                            .setMessage("Some Error Occured")
                            .setCancelable(false)
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }


            } catch (ParseException e) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Buzz...");
                alertDialogBuilder
                        .setMessage("Please enter mandatory values")
                        .setCancelable(false)
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }

        } else {
            Toast.makeText(MainActivity.this, "Please enter Mandatory fields", Toast.LENGTH_LONG).show();

        }
    }

    @SuppressWarnings("deprecation")
    public void Calender(View view) {
        showDialog(999);
    }

    @SuppressWarnings("deprecation")
    public void Calender1(View view) {
        showDialog(1000);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        } else if (id == 1000) {
            return new DatePickerDialog(this, myDateListener1, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        if (0 < day && day < 9) {
            day1 = "0" + String.valueOf(day);
        } else {
            day1 = String.valueOf(day);
        }
        if (0 < month && month < 9) {
            month1 = "0" + String.valueOf(month);
        } else {
            month1 = String.valueOf(month);
        }

        todayDate.setText(new StringBuilder().append(day1).append("/").append(month1).append("/").append(year));
    }

    private DatePickerDialog.OnDateSetListener myDateListener1 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate1(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate1(int year, int month, int day) {
        if (0 < day && day < 9) {
            day1 = "0" + String.valueOf(day);
        } else {
            day1 = String.valueOf(day);
        }
        if (0 < month && month < 9) {
            month1 = "0" + String.valueOf(month);
        } else {
            month1 = String.valueOf(month);
        }
        dateBirth.setText(new StringBuilder().append(day1).append("/").append(month1).append("/").append(year));
    }

}
