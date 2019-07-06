package com.example.prem.trials;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prem.trials.contactsList.Contact;
import com.example.prem.trials.contactsList.ContactFetcher;
import com.example.prem.trials.contactsList.ContactPhone;
import com.example.prem.trials.contactsList.ContactsAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class ContactsActivity extends AppCompatActivity {
    com.example.prem.trials.DBHelper helper;
    EditText et;
    SQLiteDatabase db;
    ArrayList<Contact> listContacts;
    ListView lvContacts;
    ContactsAdapter adapterContacts;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS=100;
    LinkedHashSet<Contact> al=new LinkedHashSet<Contact>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setTitle("My Contacts");
        lvContacts = (ListView) findViewById(R.id.lvContacts);
        et=(EditText)findViewById(R.id.search);
        lvContacts.setTextFilterEnabled(true);
        showContacts();

        helper=new com.example.prem.trials.DBHelper(this);
        db=helper.getWritableDatabase();
        lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Contact value = adapterContacts.getItem(i);

                ArrayList<ContactPhone> lis=value.getNumbers();
                TreeSet<String> tr=new TreeSet<String>();
                for(ContactPhone cp: lis){
                    String s=cp.number.replaceAll(" ","");
                    tr.add(s);
                }
                Iterator<String> it=tr.iterator();
                while(it.hasNext()) {
                    //Log.i("testing",value.name+" "+tr.size());
                    insertRecord(view,value.name,it.next());

                }
                return true;
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ContactsActivity.this.adapterContacts.getFilter().filter(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
;
    }
    public void insertRecord(View v,String value,String number) {

        // Get the text component from Edit text and place in a string

        //Bind the data to ContentValue objet use <contentvalueobjrct>.put()

        ContentValues cv = new ContentValues();
        cv.put("name",value);
        cv.put("number", number);



        //Calling insert method
        long l = db.insert("sample", null, cv);
        Toast.makeText(getApplication(), value+" added to My Contacts", Toast.LENGTH_SHORT).show();
    }
    private void showContacts(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            listContacts = new ContactFetcher(this).fetchAll();
            adapterContacts = new ContactsAdapter(this, listContacts);
            lvContacts.setAdapter(adapterContacts);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                //Log.e("test","hello");
                listContacts = new ContactFetcher(this).fetchAll();
                ContactsAdapter adapterContacts = new ContactsAdapter(this, listContacts);
                lvContacts.setAdapter(adapterContacts);
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
