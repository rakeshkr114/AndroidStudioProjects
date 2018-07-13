package com.example.m1039158.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ListFrag.ChessPieceListener{

    TextView tvDetails;
    FragmentManager manager=this.getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting TextView from DetailFrag
        tvDetails=findViewById(R.id.tvDetails);


        //If phone is in portrait mode --> hide the detail fragment and show list fragment
        if(findViewById(R.id.layout_default)!=null){

            //FragmentManager manager=this.getSupportFragmentManager();
            manager.beginTransaction()
                    .hide(manager.findFragmentById(R.id.detailFrag))
                    .show(manager.findFragmentById(R.id.listFrag))
                    .commit();
        }

        //If phone is in Landscape mode --> show both the list fragment and detail fragment
        if(findViewById(R.id.layout_land)!=null){
            //FragmentManager manager=this.getSupportFragmentManager();
            manager.beginTransaction()
                    .show(manager.findFragmentById(R.id.listFrag))
                    .show(manager.findFragmentById(R.id.detailFrag))
                    .commit();
        }
    }

    //Implementing the method onChessPieceSelected() of interface ChessPieceListener
    @Override
    public void onChessPieceSelected(int index) {

        //If phone is in portrait mode then on click --> hide the list fragment and show detail fragment and add to back stack to go back to the fragment
        if(findViewById(R.id.layout_default)!=null){
            //FragmentManager manager=this.getSupportFragmentManager();
            manager.beginTransaction()
                    .hide(manager.findFragmentById(R.id.listFrag))
                    .show(manager.findFragmentById(R.id.detailFrag))
                    .addToBackStack(null)
                    .commit();
        }

        //Reading the String Array "description" from the res/values/arrays.xml
        String[] descriptions=getResources().getStringArray(R.array.description);
        //Setting description in the text view using indexed passed by the List Fragment.
        tvDetails.setText(descriptions[index]);
    }
}
