package com.example.m1039158.customtoast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(MainActivity.this, "Data successfully saved!", Toast.LENGTH_SHORT).show();

                showToast("Data successfully saved!");
            }
        });

    }

    public void showToast(String message){
        View toastView=getLayoutInflater().inflate(R.layout.toast, (ViewGroup) findViewById(R.id.linlay));

        TextView tvToast=toastView.findViewById(R.id.tvToast);
        tvToast.setText(message);

        Toast toast=new Toast(MainActivity.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);

        toast.setView(toastView);
        toast.show();
    }
}
