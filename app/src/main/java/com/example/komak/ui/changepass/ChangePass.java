package com.example.komak.ui.changepass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.komak.DBHelper;
import com.example.komak.MainActivity;
import com.example.komak.R;
import com.example.komak.Session;
import com.example.komak.databinding.ChangepasswordBinding;
import com.example.komak.ui.profile.ProfileFragment;
import com.example.komak.ui.signup.Signup;

public class ChangePass extends AppCompatActivity {

    private ChangepasswordBinding binding;
    EditText pass, newpass, renewpass;
    DBHelper db;
    Session session;
    Button changepassword;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.changepassword);
        db = new DBHelper(this);
        session = new Session(this);
        pass = (EditText) findViewById(R.id.changepass);
        newpass = (EditText) findViewById(R.id.changenewpass);
        renewpass = (EditText) findViewById(R.id.changenewpass2);
        changepassword = (Button) findViewById(R.id.btnchangepass);


        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = pass.getText().toString();
                String newpassword = newpass.getText().toString();
                String renewpassword = renewpass.getText().toString();
                String oldPassword = null;
                String passwordVal = "^" +
                        //"(?=.*[0-9])" +         //at least 1 digit
                        //"(?=.*[a-z])" +         //at least 1 lower case letter
                        //"(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +      //any letter
                        "(?=.*[@#$%^&+=.])" +    //at least 1 special character
                        "(?=\\S+$)" +           //no white spaces
                        ".{4,}" +               //at least 4 characters
                        "$";


                if (password.equals("") || newpassword.equals("") || renewpassword.equals("")){
                    Toast.makeText(ChangePass.this, "Please Enter all fields", Toast.LENGTH_SHORT).show();
                }
                else if(!newpassword.matches(passwordVal)){
                    Toast.makeText(ChangePass.this, "Password is too week", Toast.LENGTH_LONG).show();
                }
                else {
                    String email = session.getemail();
                    Cursor passcheck = db.checkpass(email);
                    while(passcheck.moveToNext()){
                        int index;
                        index = passcheck.getColumnIndexOrThrow("password");
                        oldPassword = passcheck.getString(index);

                    }
                   if (password.equals(oldPassword)) {
                        if (newpassword.equals(renewpassword)) {
                            db.updatepass(newpassword, email);
                            Toast.makeText(ChangePass.this, "Password Updated", Toast.LENGTH_LONG).show();
                            pass.setText("");
                            newpass.setText("");
                            renewpass.setText("");
                            Intent go = new Intent(ChangePass.this, MainActivity.class);
                            ChangePass.this.startActivity(go);
                            finish();
                        } else {
                            Toast.makeText(ChangePass.this, "New Password Not Matching! Please try Again", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ChangePass.this, "Old Password not Matching! Please try Again", Toast.LENGTH_LONG).show();
                    }
                }
                }
        });


    }
}