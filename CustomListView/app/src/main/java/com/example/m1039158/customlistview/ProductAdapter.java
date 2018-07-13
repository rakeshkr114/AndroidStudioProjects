package com.example.m1039158.customlistview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product>{

    private final Context context; // The activity that will use this array adapter

    //To set it to our List View
    private final ArrayList<Product> values;

    public ProductAdapter(@NonNull Context context, ArrayList<Product> list) {
        super(context, R.layout.row_layout, list);
        this.context = context;
        this.values = list;
    }

    @NonNull
    @Override
    //Will run for every object passed in the list --> Set the items in the row_layout we created
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //rowView will now have a link to row_layout
        View rowView=inflater.inflate(R.layout.row_layout,parent,false);

        TextView tvProduct=rowView.findViewById(R.id.tvProduct);
        TextView tvPrice=rowView.findViewById(R.id.tvPrice);
        TextView tvDescription=rowView.findViewById(R.id.tvDescription);

        ImageView ivProduct=rowView.findViewById(R.id.ivProduct);
        ImageView ivSale=rowView.findViewById(R.id.ivSale);

        //Setting text values
        tvProduct.setText(values.get(position).getTitle());
        tvPrice.setText("R"+values.get(position).getPrice());
        tvDescription.setText(values.get(position).getDescription());

        //setting imageViews
        //If on sale
        if(values.get(position).getSale()){
            ivSale.setImageResource(R.mipmap.sale);
        }
        else{
            ivSale.setImageResource(R.mipmap.best_price);
        }

        if(values.get(position).getType().equals("Laptop")){
            ivProduct.setImageResource(R.mipmap.laptop);
        }
        else if(values.get(position).getType().equals("Memory")){
            ivProduct.setImageResource(R.mipmap.memory);
        }
        else if(values.get(position).getType().equals("Screen")){
            ivProduct.setImageResource(R.mipmap.screen);
        }
        else{
            ivProduct.setImageResource(R.mipmap.hdd);
        }

        //return the rowView
        return rowView;
    }
}
