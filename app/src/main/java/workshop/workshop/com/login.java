package workshop.workshop.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class login extends AppCompatActivity {

    private Integer count = 0;
    private Helperclass helperclass;
    private ArrayList<userdetails> userdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {

            helperclass = new Helperclass(this);
            final SharedPreferences sharedPreferences = getSharedPreferences("workshop.com", MODE_PRIVATE);
            userdetail = new ArrayList<>();
            final EditText email_login = (EditText) findViewById(R.id.email_login);
            final EditText password_login = (EditText) findViewById(R.id.password_login);


            Button login = (Button) findViewById(R.id.go);
            Button get_started = (Button) findViewById(R.id.get_started);
            Button home_login = (Button) findViewById(R.id.home_login);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!email_login.getText().toString().equals("") && !password_login.getText().toString().equals("")) {
                        userdetail.add(helperclass.getuserdetails(email_login.getText().toString()));
                        if (userdetail.size() > 0) {
                            if (userdetail.get(0).getPassword().equals(password_login.getText().toString())) {
                                sharedPreferences.edit().putString("email", email_login.getText().toString()).apply();
                                sharedPreferences.edit().putString("password", password_login.getText().toString()).apply();
                                sharedPreferences.edit().putString("name", userdetail.get(0).getName().toString()).apply();
                                sharedPreferences.edit().putString("phone", userdetail.get(0).getPhone().toString()).apply();
                                sharedPreferences.edit().putString("login", "1").apply();
                                sharedPreferences.edit().putString("address", userdetail.get(0).getAddress().toString()).apply();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid password.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No user found.please register", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Blank Fields", Toast.LENGTH_LONG).show();
                    }
                }
            });

            get_started.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(getApplicationContext(), register.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            home_login.setOnClickListener(new View.OnClickListener() {
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
