package com.example.lms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    String uname, upass;
    Intent intent;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context ;
    public static final String MyPref = "My Prefernces";
    public static final String UName = "Username";
    public static final String UPass = "Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.u_name);
        password = findViewById(R.id.u_pass);

        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(UName,uname);
        editor.putString(UPass,upass);
        editor.commit();

    }

    public void log_in(View view) {


        uname = username.getText().toString();
        upass = password.getText().toString();


       if (uname.equals("admin") && upass.equals("admin"))
       {


            Toast.makeText(this, "Successfully Login", Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this, Menu.class);
            startActivity(intent);

       }
       else if ((uname.equals("") || uname.length()==0 || uname.isEmpty() || uname==null )&& upass.equals("admin"))
       {
           username.setError("Enter Username");
           Toast.makeText(MainActivity.this, "Enter  Username", Toast.LENGTH_SHORT).show();

       }
       else if ((upass.equals("") || upass.length()==0 || upass.isEmpty() || upass==null) && uname.equals("admin"))
       {
           password.setError("Enter Password");
           Toast.makeText(MainActivity.this, "Enter  Password", Toast.LENGTH_SHORT).show();
       }
       else if ((uname.equals("") && upass.equals("")) || (uname.isEmpty() && upass.isEmpty()))
       {
           username.setError("Enter Username");
           password.setError("Enter Password");
           Toast.makeText(MainActivity.this, "Username/Password must be entered ", Toast.LENGTH_SHORT).show();
       }
        else
        {
           Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
        }
    }
}
