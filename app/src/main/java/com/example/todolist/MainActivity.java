/*
Assignment 3
Title: ToDoList application using option menu and listview
Name: Joshua Anudu
Class: CS680
        */

package com.example.todolist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public final ArrayList<String> options = new ArrayList<String>();
    public CustomAdapter adapter;
    public ListView lv;
    public EditText nameTxt;
    private int getSelectedIndex = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.list);
        nameTxt = (EditText) findViewById(R.id.todotext);
        adapter = new CustomAdapter(this, options);
        lv.setAdapter(adapter);
        options.add("study");
        options.add("shop");
        options.add("sleep");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
                MainActivity.this.getSelectedIndex = position;
                nameTxt.setText(options.get(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void clear() {
        adapter.clear();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                String option = nameTxt.getText().toString();
                if (!option.isEmpty() && option.length() > 0) {
                    adapter.add(option); //add
                    adapter.notifyDataSetChanged(); //refresh
                    nameTxt.setText("");
                }
                return true;

            case R.id.delete:
                if (this.getSelectedIndex != -1) { //avoid app crash
                    options.remove(this.getSelectedIndex); // deleting item
                    this.getSelectedIndex = -1;
                    adapter.notifyDataSetChanged();//updating adapter
                    lv.setAdapter(adapter);
                    nameTxt.setText("");//clear text
                }
                return true;

            case R.id.update:
                String option2 = nameTxt.getText().toString();
                if (!option2.isEmpty() && option2.length() > 0) {
                    options.remove(this.getSelectedIndex);// removing item within position
                    adapter.insert(option2, getSelectedIndex);//inserting
                    adapter.notifyDataSetChanged();//refresh
                    nameTxt.setText(""); //clear text
                }
                return true;

            case R.id.close:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

class CustomAdapter extends ArrayAdapter<String> //customizing adapter
{
    Context context;
    ArrayList<String> options;


    CustomAdapter(Context c, ArrayList<String> title) {

        super(c, R.layout.row, title);
        this.context = c;
        this.options = title;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { // remodifying adapter to include numbering

        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = vi.inflate(R.layout.row, parent, false);
        TextView titlee = (TextView) row.findViewById(R.id.number);
        int pos = position + 1;
        titlee.setText(+pos + ". " + options.get(position)); //adding numbers
        pos++;
        return row;
    }

}
