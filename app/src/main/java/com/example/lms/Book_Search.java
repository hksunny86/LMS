package com.example.lms;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Book_Search extends AppCompatActivity {
    EditText text;
    Button search;
    String data = null;
    BookDataBaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_searching);

        mydb = new BookDataBaseHelper(this);
        text = findViewById(R.id.search);
        search = findViewById(R.id.search_btn);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = text.getText().toString();
                if(id.equals("")){
                    text.setError("Invalid ID");
                    return;
                }
                Cursor cursor = mydb.search(id);
                data  = null;
                if (cursor.moveToFirst()) {

                    data = "ID:" + cursor.getString(0) + "\n" +
                            "Book Name :" + cursor.getString(1) + "\n\n" +
                            "Author Name :" + cursor.getString(2) + "\n\n";
                }
                showMessage("LMS", data);

            }
        });

    }



    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
