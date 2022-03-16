package com.example.komak.ui.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.komak.DBHelper;
import com.example.komak.R;

import com.example.komak.User;
import com.example.komak.ui.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText firstname, lastname, email, password;
    private Button signup;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firstname= (EditText) findViewById(R.id.firstname);
        lastname= (EditText) findViewById(R.id.lastname);
        email= (EditText) findViewById(R.id.emailsignup);
        password= (EditText) findViewById(R.id.signuppassword);
        signup= (Button) findViewById(R.id.signupup);
        db= new DBHelper(this);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn = firstname.getText().toString().trim();
                String ln = lastname.getText().toString().trim();
                String email1 = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
               // String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String passwordVal = "^" +
                        //"(?=.*[0-9])" +         //at least 1 digit
                        //"(?=.*[a-z])" +         //at least 1 lower case letter
                        //"(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +      //any letter
                        "(?=.*[@#$%^&+=.])" +    //at least 1 special character
                        "(?=\\S+$)" +           //no white spaces
                        ".{4,}" +               //at least 4 characters
                        "$";

                if (fn.equals("")) {
                    firstname.setError("First name is Required!");
                    firstname.requestFocus();
                } else if (ln.equals("")) {
                    lastname.setError("Last name is Required!");
                    lastname.requestFocus();
                } else if (email1.equals("")) {
                    email.setError("Email is Required!");
                    email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                    email.setError("Please provide a valid Email Address!");
                    email.requestFocus();
                } else if (pass.equals("")) {
                    password.setError("Password is Required!");
                    password.requestFocus();
                } else if (!pass.matches(passwordVal)) {
                    password.setError("Password is too Week!");
                    password.requestFocus();
                }
//                else{
//
////                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString().trim();
////                    writeNewUserWithTaskListeners(uid, fn, ln, email1, pass);
//
////                    mAuth.createUserWithEmailAndPassword(email1, pass)
////                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
////                        @Override
////                        public void onComplete(@NonNull Task<AuthResult> task) {
////                            if(task.isSuccessful()){
////                            User user = new User(fn, ln, email1, pass);
////                            mDatabase = FirebaseDatabase.getInstance().getReference();
////                            mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
////                                    .setValue(user).addOnCompleteListener(Signup.this, new OnCompleteListener<Void>() {
////                                @Override
////                                public void onComplete(@NonNull Task<Void> task) {
////                                    if (task.isSuccessful()){
////                                    Toast.makeText(Signup.this, "User successfully Registered", Toast.LENGTH_LONG).show();
////                                   Intent go = new Intent(Signup.this, Login.class);
////                                   Signup.this.startActivity(go);
////                                   finish();
////                                }
////                                    else{
////                                        Toast.makeText(Signup.this, "Registration Failed! Please Try Again", Toast.LENGTH_LONG).show();
////                                    }
////                                }
////                            });
////                        }
////                            else{
////                                Toast.makeText(Signup.this, "User Already Registered!", Toast.LENGTH_LONG).show();
////                            }
////                        }
////                    });
//                }
                    //sigup with db helper code (SQLite)
                else {
                    boolean checkuser = db.checkemail(email1);
                    if(checkuser==false){
                        boolean register = db.register(fn,ln, email1, pass);
                        if(register==true){
                            Toast.makeText(Signup.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent go1 = new Intent(Signup.this, Login.class);
                            Signup.this.startActivity(go1);
                            finish();
                        }else{
                            Toast.makeText(Signup.this, "Registration Failed! Please try Again", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(Signup.this, "User Already Exist! Please Signin", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


//        public void writeNewUserWithTaskListeners(String uid, String frtname, String lstname ,String emil2, String pssword) {
//            User user = new User(frtname,lstname, emil2, pssword);
//
//            // [START rtdb_write_new_user_task]
//            mDatabase.child("users").child(uid).setValue(user)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            // Write was successful!
//                            // ...
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            // Write failed
//                            // ...
//                        }
//                    });
//            // [END rtdb_write_new_user_task]
//        }


        TextView member = (TextView) findViewById(R.id.member);
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(Signup.this, Login.class);
                Signup.this.startActivity(go);
                finish();
            }
        });
    }

}