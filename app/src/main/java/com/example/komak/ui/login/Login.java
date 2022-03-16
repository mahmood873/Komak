package com.example.komak.ui.login;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.komak.Session;
import com.example.komak.DBHelper;
import com.example.komak.MainActivity;
import com.example.komak.R;
import com.example.komak.ui.forgot.Forgot;
import com.example.komak.ui.signup.Signup;

public class Login extends AppCompatActivity {

    EditText email, pass;
    DBHelper db;
    Session session;
    SharedPreferences sharedPreferences;
    public static final String mysession = "mysession";
    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.login);
        email = (EditText) findViewById(R.id.emaillogin);
        pass = (EditText) findViewById(R.id.passlogin);
        db = new DBHelper(this);
        session = new Session(this);



        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString();
                String password = pass.getText().toString();

                if (email1.equals("") || password.equals("")){
                    Toast.makeText(Login.this,"Please Enter all the Fields", Toast.LENGTH_LONG).show();
                }
                else {
                    boolean checkemailpassword = db.checkemailpassword(email1, password);
                    if (checkemailpassword==true){
                        session.setemail(email1);
                        Toast.makeText(Login.this, "Log in Successfully", Toast.LENGTH_SHORT).show();
                        Intent go = new Intent(Login.this, MainActivity.class);
                        Login.this.startActivity(go);
                        finish();
                    }else {
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        TextView forgot = (TextView) findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go1 = new Intent(Login.this, Forgot.class);
                Login.this.startActivity(go1);
            }
        });

        Button signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go2 = new Intent(Login.this, Signup.class);
                Login.this.startActivity(go2);
            }
        });

    }

    }