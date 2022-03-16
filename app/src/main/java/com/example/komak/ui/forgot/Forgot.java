package com.example.komak.ui.forgot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.komak.DBHelper;
import com.example.komak.R;
import com.example.komak.ui.login.Login;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forgot extends AppCompatActivity {
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.forgot);

        db=new DBHelper(this);


        Button sendlink = (Button) findViewById(R.id.sendlink);
        sendlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText textemail = (EditText) findViewById(R.id.sendemail);
                String email = textemail.getText().toString();
                String url = "https://110f-2a01-388-27f-455-00-1-2b.ngrok.io/Komak/getUserEmail.php";


               boolean email1 =  db.checkemail(email);
               if(email.isEmpty()) {
                   Toast.makeText(Forgot.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
               }else if (email1 == false){
                       Toast.makeText(Forgot.this, "User Doesn't Exist", Toast.LENGTH_SHORT).show();
               }
               else{
                   Cursor cursor = db.UserName(email);
                   String name= null;
                   while(cursor.moveToNext()){
                       int index;
                       index = cursor.getColumnIndexOrThrow("firstname");
                       name = cursor.getString(index);
                   }
                   String finalName = name;
                   StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                           new Response.Listener<String>() {
                               @Override
                               public void onResponse(String response) {
                                   Toast.makeText(Forgot.this,response.trim(),Toast.LENGTH_LONG).show();

                               }
                           },
                           new Response.ErrorListener() {
                               @Override
                               public void onErrorResponse(VolleyError error) {
                                   Toast.makeText(Forgot.this,error.toString(),Toast.LENGTH_LONG).show();
                               }
                           }){
                       @Override
                       protected Map<String, String> getParams() {
                           Map<String,String> params = new HashMap<String, String>();
                           params.put("email",email);
                           params.put("name", finalName);

                           return params;
                       }
                   };
                   RequestQueue requestQueue = Volley.newRequestQueue(Forgot.this);
                   requestQueue.add(stringRequest);
               }
               }
        });


        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(Forgot.this, Login.class);
                Forgot.this.startActivity(go);
                finish();
            }
        });
    }
}