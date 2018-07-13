package com.example.m1039158.actionbar;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();


        actionBar.setIcon(R.mipmap.chrome);
        actionBar.setTitle(" Welcome");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.download:
                Toast.makeText(this,"Download Clicked!",Toast.LENGTH_SHORT).show();
                break;

            case R.id.send:
                Toast.makeText(this,"Send Clicked!",Toast.LENGTH_SHORT).show();

                //start 2nd activity on click of send action button
                startActivity(new Intent(this,Main2Activity.class));
                break;

            case R.id.refresh:
                Toast.makeText(this,"Refresh Clicked!",Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
