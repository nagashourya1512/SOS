package com.example.prem.trials;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TrustedContacts extends AppCompatActivity {
    DBHelper helper;
    SQLiteDatabase db;
    ListView ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_contacts);
        setTitle("Trusted Contacts");
        helper=new com.example.prem.trials.DBHelper(this);
        db=helper.getWritableDatabase();
        readInfo();
    }
    public void readInfo(){

        //Get Cursor object from query() method
        Cursor c=db.query("sample",null,null,null,null,null,null);

        //Fetching   the records one by one ---> use <Cursor object>.moveToNext() method
        ArrayList<String> s=new ArrayList<String>();
        while (c.moveToNext())
        {
            s.add(c.getString(0));

            Log.i("conts",c.getString(0));

        }
        final ArrayAdapter adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,s);
       ls = (ListView) findViewById(R.id.trusted_Contacts);
        ls.setAdapter(adapter1);
        ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = (String)adapter1.getItem(i);
                Toast.makeText(getApplicationContext(), value+" deleted", Toast.LENGTH_SHORT).show();
                deleteRecord((String)adapter1.getItem(i));

                return true;
            }
        });

    }
    public void deleteRecord(String S){
        db.delete("sample","name='"+S+"'",null);
        readInfo();
    }
}
