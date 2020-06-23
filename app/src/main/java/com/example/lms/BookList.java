package com.example.lms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookList extends ArrayAdapter<BookGetSet> {

    private Context context;
    public int Resource;
    public String book_tile, book_author;


    static class ViewHolder {
        TextView title,author;
    }

    public BookList(Context context, int resource, ArrayList<BookGetSet> objects) {
        super(context, resource, objects);
        this.context = context;
        Resource = resource;

    }

    public BookList.ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        book_tile = getItem(position).getName();
        book_author = getItem(position).getAuthor();



        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(Resource, parent, false);

            holder = new BookList.ViewHolder();
            holder.title= convertView.findViewById(R.id.title);
            holder.author = convertView.findViewById(R.id.author);

            convertView.setTag(holder);
        } else {
            holder = (BookList.ViewHolder) convertView.getTag();

        }

        holder.title.setText(book_tile);
        holder.author.setText(book_author);

        return convertView;
    }
}
