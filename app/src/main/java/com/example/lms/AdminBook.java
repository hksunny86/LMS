package com.example.lms;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminBook extends AppCompatActivity {

    ListView book_list;
    BookList adapter_book;
    public ArrayList<BookGetSet> list_book;
    public static BookDataBaseHelper bookDataBaseHelper;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Record List");

        searchView = findViewById(R.id.app_bar_search);

        bookDataBaseHelper = new BookDataBaseHelper(this);


        list_book = new ArrayList<>();
        adapter_book = new BookList(this, R.layout.book_list, list_book);
        book_list = findViewById(R.id.book_list);


        book_list.setAdapter(adapter_book);

        //get data from DataBase
        Cursor cursor = bookDataBaseHelper.get_Book_Data();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String book_name = cursor.getString(1);
            String book_author = cursor.getString(2);
//            Log.d("Value123",cursor.getString(1));

            list_book.add(new BookGetSet(id,book_name, book_author));
        }
        adapter_book.notifyDataSetChanged();


        book_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final CharSequence [] items = {"Update","Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(AdminBook.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        try {
                            if (i==0){

                                Cursor c = bookDataBaseHelper.get_data("SELECT ID FROM Book");
                                ArrayList<Integer> arrID = new ArrayList<Integer>();
                                while (c.moveToNext()){
                                    arrID.add(c.getInt(0));
                                }
                                showDialogUpdate(AdminBook.this,arrID.get(position));
                            }
                        } catch (Exception e){
                            Toast.makeText(AdminBook.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if(i==1){
                            Cursor c1 = bookDataBaseHelper.get_data("SELECT ID FROM Book");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c1.moveToNext()){
                                arrID.add(c1.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });

                dialog.show();
                return true;
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.update_data,menu);


        return  true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.app_bar_search)
        {
            Intent go = new Intent(AdminBook.this,Book_Search.class);
            startActivity(go);
        }

        else
        {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void showDialogDelete(final int idRecord) {

        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(AdminBook.this);
        dialogDelete.setTitle("Warning");
        dialogDelete.setMessage("Are You Sure To Want Delete This Record?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    Log.e("Delete or not?","DELETE HERE");
                    bookDataBaseHelper.delete(idRecord);

                    Toast.makeText(AdminBook.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("Deletd",e.getMessage());
                }

                UpdateRecordList();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        dialogDelete.show();
    }


    private void showDialogUpdate(Activity activity , final int post){

        final Button update;
        final Dialog dialog = new Dialog(activity);

        dialog.setContentView(R.layout.update_book);
        dialog.setTitle("Update");

        final EditText title = dialog.findViewById(R.id.up_book_title);
        final EditText author = dialog.findViewById(R.id.up_book_author);
        update = dialog.findViewById(R.id.update_book);

        //get data from DB and set in fields of clicked row
        Cursor cursor = bookDataBaseHelper.get_data("SELECT * FROM Book WHERE ID="+post);
        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String book_name = cursor.getString(1);
            title.setText(book_name);
            String book_author = cursor.getString(2);
            author.setText(book_author);

            list_book.add(new BookGetSet(id,book_name, book_author));
        }



        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels*0.7);

        dialog.getWindow().setLayout(width,height);

        dialog.show();


        try {


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (title.getText().toString().trim().isEmpty() ||
                        title.getText().toString().trim().length()==0 ||
                        title.getText().toString().equals("") || title.getText().toString().trim()==null
                    &&  author.getText().toString().trim().isEmpty() ||
                    author.getText().toString().trim().length()==0 ||
                    author.getText().toString().equals("") ||
                    author.getText().toString().trim()==null){
                        Toast.makeText(AdminBook.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    }

                    else {
                    try {

                        bookDataBaseHelper.update(title.getText().toString().trim(),
                                author.getText().toString().trim(), post);
                        dialog.dismiss();
                        Toast.makeText(AdminBook.this, "Updated Successfuly", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Log.e("Update Error", e.getMessage());
                    }

                    UpdateRecordList();
                }
                }
            });

        } catch (Exception e){
            Toast.makeText(AdminBook.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void UpdateRecordList(){

        Cursor cursor = bookDataBaseHelper.get_data("SELECT * FROM Book");
        list_book.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String author = cursor.getString(2);

            list_book.add(new BookGetSet(id,title,author));
        }

        adapter_book.notifyDataSetChanged();
    }
}
