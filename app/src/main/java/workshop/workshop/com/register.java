package workshop.workshop.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class register extends AppCompatActivity {


    private String value = "";
    private Integer count = 0;
    private Helperclass helperclass;

    /*
    By Abhishek Mishra
    Date 23 May 2019
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        try {
            //Shared preference

            final SharedPreferences sharedPreferences = getSharedPreferences("workshop.com", MODE_PRIVATE);
            helperclass = new Helperclass(this);
            //GETTING ID OF ATTRIBUTE


            final EditText email = (EditText) findViewById(R.id.email);
            final EditText password = (EditText) findViewById(R.id.password);
            final EditText name = (EditText) findViewById(R.id.fullname);
            final EditText phonenumber = (EditText) findViewById(R.id.phonenumber);
            Button signin = (Button) findViewById(R.id.signin);
            Button signup = (Button) findViewById(R.id.email_signup);
            Button home_register = (Button) findViewById(R.id.home_regsiter);

            Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);


            String[] items = new String[]{"Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh"
                    , "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Maharastra", "Madhya Pradesh", "Mizoram", "Meghalaya",
                    "Manipur", "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tripura", "Tamil Nadu", "Telangana", "Uttrakhand",
                    "Uttar Pradesh", "West Bengal"};

            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            spinner1.setAdapter(adapter);

            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    value = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    value = "";
                }
            });

            //REGISTER BUTTON


            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //VALIDATION
                    if (!email.getText().toString().equals("") && !password.getText().toString().equals("") && !name.getText().toString().equals("") && !phonenumber.getText().toString().equals("") && !value.equals("")) {
                        if (phonenumber.getText().toString().length() == 10) {
                            helperclass.insertuser(email.getText().toString(), password.getText().toString(), name.getText().toString(), phonenumber.getText().toString(), value);
                            sharedPreferences.edit().putString("email", email.getText().toString()).apply();
                            sharedPreferences.edit().putString("password", password.getText().toString()).apply();
                            sharedPreferences.edit().putString("name", name.getText().toString()).apply();
                            sharedPreferences.edit().putString("phone", phonenumber.getText().toString()).apply();
                            sharedPreferences.edit().putString("state", value).apply();
                            sharedPreferences.edit().putString("login", "1").apply();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid phonenumber", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Blank Fields", Toast.LENGTH_LONG).show();
                    }
                }
            });

            ///LOGIN PAGE BUTTON

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(getApplicationContext(), login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            home_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "It seems that we had some error.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        count++;
        if (count == 1) {
            Toast.makeText(getApplicationContext(), "Press one more time to exit.", Toast.LENGTH_LONG).show();
        } else if (count == 2) {
            finishAffinity();
        }
    }
}
