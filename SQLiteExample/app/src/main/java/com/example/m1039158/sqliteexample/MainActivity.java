package com.example.m1039158.sqliteexample;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText etName, etCell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName=findViewById(R.id.etName);
        etCell=findViewById(R.id.etCell);
    }

    public void btnSubmit(View v){
        String name=etName.getText().toString().trim();
        //for empty entry, store null
        if(name.equals("") || name.isEmpty()){
            name = null;
        }

        //for empty entry, store null
        String cell=etCell.getText().toString().trim();
        if(cell.equals("") || cell.isEmpty()){
            cell = null;
        }

        try{
            ContactsDB db=new ContactsDB(this);

            db.open();
            long status_Code=db.createEntry(name,cell);
            db.close();

            // insert returns -1 if an error occurred
            if(status_Code!=-1){
                Toast.makeText(this, "Successfully saved!!", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etCell.setText("");
            }
            else{
                Toast.makeText(this, "Error while saving data", Toast.LENGTH_SHORT).show();
            }
        }catch(SQLException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void btnShowData(View v){
        startActivity(new Intent(this, Data.class));
    }

    public void btnEditData(View v){

        try{
            ContactsDB db=new ContactsDB(this);

            db.open();
            db.updateEntry("1","John","8101890007");
            db.close();

            Toast.makeText(this, "Successfully updated!", Toast.LENGTH_SHORT).show();
        }catch(SQLException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void btnDeleteData(View v){
        try{
            ContactsDB db=new ContactsDB(this);

            db.open();
            db.deleteEntry("1");
            db.close();

            Toast.makeText(this, "Successfully deleted!!!", Toast.LENGTH_SHORT).show();
        }catch(SQLException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
