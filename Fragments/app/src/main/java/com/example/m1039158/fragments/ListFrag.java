package com.example.m1039158.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFrag extends ListFragment {


    private ChessPieceListener chessPieceListener;

    public ListFrag() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Array adapter to get data from the source and display on the List fragment
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.pieces));
        setListAdapter(adapter);

        //phone in landscape mode
        if(this.getActivity().findViewById(R.id.layout_land)!=null){
            //calling method which is implemented on Main Activity--> To show description of element at index 0 in the when app launched
            chessPieceListener.onChessPieceSelected(0);
        }

    }

    //creating interface which will be implemented by MainActivity --> Forcing to override our method onChessPieceSelected
    public interface ChessPieceListener{
        void onChessPieceSelected(int index);
    }

    //When the List fragment gets attached to any activity(MainActivity here), this method will run
    @Override
    public void onAttach(Context context) {
        super.onAttach(context); //Note: context --> Refers to the activity that's hosting this fragment => MainActivity

        try{
            //Typecasting the activity which is hosting this fragment --> To check if MainActivity is implementing the interface chessPieceListener
            //Note: If MainActivity doesn't implements the interface ChessPieceListener, then it will not have access to the variable chessPieceListener
            chessPieceListener = (ChessPieceListener) context;
        }
        catch (ClassCastException e){
            // trow exception if not implemented
            throw new ClassCastException(context.toString()+ " must implement the interface called ChessPieceListener");
        }
    }

    //Run method on click of any item from the list of this fragment
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //passing clicked position/index to the implementation of this method in MainActivity
        chessPieceListener.onChessPieceSelected(position);
    }
}
