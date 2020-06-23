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

public class Books extends AppCompatActivity {

    EditText book_name , book_author;
    Button submit_book;
    BookDataBaseHelper bookDataBaseHelper;
    BookGetSet bookGetSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        book_name = findViewById(R.id.book_title);
        book_author = findViewById(R.id.book_author);
        submit_book = findViewById(R.id.submit_book);

        bookDataBaseHelper = new BookDataBaseHelper(this);


        bookGetSet = new BookGetSet();


        submit_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bookGetSet.name= book_name.getText().toString();
                bookGetSet.author= book_author.getText().toString();

                if (bookGetSet.name.isEmpty() || bookGetSet.name.length()==0
                        ||bookGetSet.name.equals("") || bookGetSet.name==null
                        && bookGetSet.author.isEmpty() || bookGetSet.author.length()==0
                        ||bookGetSet.author.equals("") || bookGetSet.author==null){
                    book_name.setError("Enter Book Name");
                    book_author.setError("Enter Book Author");

                    Toast.makeText(Books.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
                }

                else {
                    try {

                        boolean is_successfull = bookDataBaseHelper.ADDBOOK(bookGetSet);
                        Toast.makeText(Books.this, "Added Successfully! " + is_successfull, Toast.LENGTH_SHORT).show();
                        book_name.setText("");
                        book_author.setText("");

                    } catch (Exception e) {
                        Toast.makeText(Books.this, "I am in add btn ", Toast.LENGTH_SHORT).show();
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

             Intent i = new Intent(Books.this,AdminBook.class);
            startActivity(i);
        }
        else {return super.onOptionsItemSelected(item);
        }

        return true;

    }
}
