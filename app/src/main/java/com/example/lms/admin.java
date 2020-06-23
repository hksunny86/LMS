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
import android.widget.Toast;

import java.util.ArrayList;

public class admin extends AppCompatActivity {


    ListView slist;
    StudentList adapter;
    public ArrayList<Student> list;
    DataBaseHelper dataBaseHelper;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Record List");

        dataBaseHelper = new DataBaseHelper(this);


        list = new ArrayList<>();
        adapter = new StudentList(this, R.layout.student_list, list);
        slist = findViewById(R.id.s_list);

        slist.setAdapter(adapter);

        //get data from DataBase
        Cursor cursor = dataBaseHelper.get_Data();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String book_issue = cursor.getString(2);
            String date = cursor.getString(3);

            list.add(new Student(id, name, book_issue, date));
        }
        adapter.notifyDataSetChanged();


        slist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final CharSequence[] items = {"Update", "Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(admin.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        try {
                            if (i == 0) {

                                Cursor c = dataBaseHelper.get_data("SELECT ID FROM Student");
                                ArrayList<Integer> arrID = new ArrayList<>();
                                while (c.moveToNext()) {
                                    arrID.add(c.getInt(0));
                                }
                                showDialogUpdate(admin.this, arrID.get(position));
                            }
                        } catch (Exception e) {
                            Toast.makeText(admin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (i == 1) {
                            Cursor c1 = dataBaseHelper.get_data("SELECT ID FROM Student");
                            ArrayList<Integer> arrID = new ArrayList<>();
                            while (c1.moveToNext()) {
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

        getMenuInflater().inflate(R.menu.update_data, menu);


        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.app_bar_search) {
            Intent go = new Intent(admin.this, Searching.class);
            startActivity(go);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void showDialogDelete(final int idRecord) {

        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(admin.this);
        dialogDelete.setTitle("Warning");
        dialogDelete.setMessage("Are You Sure To Want Delete This Record?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    Log.e("Delete or not?", "DELETE HERE");
                    dataBaseHelper.delete(idRecord);

                    Toast.makeText(admin.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("Deleted", e.getMessage());
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


    private void showDialogUpdate(Activity activity, final int post) {

        final Button updateStudent;
        final Dialog dialog = new Dialog(activity);

        dialog.setContentView(R.layout.update_student);
        dialog.setTitle("Update");

        final EditText name = dialog.findViewById(R.id.name_s);
        final EditText bookname = dialog.findViewById(R.id.bookname_s);
        final EditText issuedate = dialog.findViewById(R.id.issuedate_s);

        updateStudent = dialog.findViewById(R.id.update_s);

        //get data from DB and set in fields of clicked row

        Cursor cursor = dataBaseHelper.get_data("SELECT * FROM Student WHERE ID=" + post);
        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String name_s = cursor.getString(1);
            name.setText(name_s);
            String book_issue = cursor.getString(2);
            bookname.setText(book_issue);
            String date = cursor.getString(3);
            issuedate.setText(date);

            list.add(new Student(id, name_s, book_issue, date));


        }


        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);

        dialog.getWindow().setLayout(width, height);

        dialog.show();

        try {


            updateStudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (name.getText().toString().trim().isEmpty() || name.getText().toString().trim().length() == 0
                            || name.getText().toString().trim().equals("") || name.getText().toString().trim() == null
                            && bookname.getText().toString().trim().isEmpty() || bookname.getText().toString().trim().length() == 0
                            || bookname.getText().toString().trim().equals("") || bookname.getText().toString().trim() == null
                            && issuedate.getText().toString().trim().isEmpty() || issuedate.getText().toString().trim().length() == 0
                            || issuedate.getText().toString().trim().equals("") || issuedate.getText().toString().trim() == null) {

                        Toast.makeText(admin.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
                    } else {
                        try {

                            dataBaseHelper.update(name.getText().toString().trim(),
                            bookname.getText().toString().trim(),
                            issuedate.getText().toString().trim(), post);
                            dialog.dismiss();
                            Toast.makeText(admin.this, "Updated Successfuly", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.e("Update Error", e.getMessage());
                        }

                        UpdateRecordList();
                    }
                }
            });

        } catch (Exception e) {
            Toast.makeText(admin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void UpdateRecordList() {

        Cursor cursor = dataBaseHelper.get_data("SELECT * FROM Student");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String book_issue = cursor.getString(2);
            String date = cursor.getString(3);

            list.add(new Student(id, name, book_issue, date));
        }

        adapter.notifyDataSetChanged();
    }


}
