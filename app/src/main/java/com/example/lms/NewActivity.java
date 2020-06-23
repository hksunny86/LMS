package com.example.lms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewActivity extends AppCompatActivity {

    EditText name, book_issue, date;
    Button add_btn;

    Student student;
    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        name = findViewById(R.id.n_name);
        book_issue = findViewById(R.id.n_book);
        date = findViewById(R.id.n_date);
        add_btn = findViewById(R.id.add_btn);
        dataBaseHelper = new DataBaseHelper(this);


        student = new Student();


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.name = name.getText().toString();
                student.book_issued = book_issue.getText().toString();
                student.date = date.getText().toString();

                if (student.name.isEmpty() || student.name.length()==0
                        ||student.name.equals("") || student.name==null
                        && student.book_issued.isEmpty() || student.book_issued.length()==0
                        ||student.book_issued.equals("") || student.book_issued==null
                        && student.date.isEmpty() || student.date.length()==0
                        ||student.date.equals("") || student.date==null){
                    name.setError("Enter Name");
                    book_issue.setError("Enter Book Name");
                    date.setError("Enter Date");

                    Toast.makeText(NewActivity.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
                }

                else {

                    try {
                        boolean is_successfull = dataBaseHelper.ADDDATA(student);
                        Toast.makeText(NewActivity.this, "Added Successfully! " + is_successfull, Toast.LENGTH_SHORT).show();
                        name.setText("");
                        book_issue.setText("");
                        date.setText("");

                    } catch (Exception e) {
                        Toast.makeText(NewActivity.this, "I am in add btn ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

         if(item.getItemId() == R.id.view) {
            Intent intent = new Intent(this, admin.class);
            startActivity(intent);
        }
        else {return super.onOptionsItemSelected(item);
        }

        return true;

    }


}
