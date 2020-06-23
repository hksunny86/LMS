package com.example.lms;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Menu");
    }

    public void Student(View view) {

        Toast.makeText(this, "Student Module", Toast.LENGTH_SHORT).show();
        Intent std = new Intent(getApplicationContext(), NewActivity.class);
        startActivity(std);

    }

    public void Books(View view) {

        Toast.makeText(this, "Books Module", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Menu.this , Books.class);
        startActivity(intent);

    }
}
