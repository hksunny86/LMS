package com.example.lms;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentList extends ArrayAdapter<Student> {
    private Context context;
    public int Resource;
    public String name_s, book_s, date_s;
    public int id_s;



    static class ViewHolder {
        TextView name, id, book, date;
    }

    public StudentList(Context context, int resource, ArrayList<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        Resource = resource;

    }

    public ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        id_s = getItem(position).getId();
        name_s = getItem(position).getName();
        book_s = getItem(position).getBook_issued();
        date_s = getItem(position).getDate();


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(Resource, parent, false);

            holder = new ViewHolder();
            holder.id = convertView.findViewById(R.id.s_id);
            holder.name = convertView.findViewById(R.id.s_name);
            holder.book = convertView.findViewById(R.id.s_book);
            holder.date = convertView.findViewById(R.id.s_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        try {
            holder.id.setText(String.valueOf(id_s));

        } catch (Exception e) {
            Toast.makeText(context, "Athy error vy id ty", Toast.LENGTH_SHORT).show();
        }

        holder.name.setText(name_s);
        holder.book.setText(book_s);
        holder.date.setText(date_s);

        return convertView;
    }
}
