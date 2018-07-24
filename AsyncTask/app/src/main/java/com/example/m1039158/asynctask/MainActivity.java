package com.example.m1039158.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText etNumber;
    Button btnRollDice;
    TextView tvResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber=findViewById(R.id.etNumber);
        tvResults=findViewById(R.id.tvResults);
        btnRollDice=findViewById(R.id.btnRollDice);

        btnRollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int noOfTimes=Integer.parseInt(etNumber.getText().toString().trim());
                //AsyncTask.execute() will start executing the methods
                new ProcessDiceInBackground().execute(noOfTimes);

                /*int ones=0, twos=0, threes=0, fours=0, fives=0, sixes=0, randomNumber;
                String results;
                Random random=new Random();

                for(int i=0;i<noOfTimes;i++){
                    randomNumber=random.nextInt(6)+1;
                    switch (randomNumber){
                        case 1:
                            ones++; break;
                        case 2:
                            twos++; break;
                        case 3:
                            threes++; break;
                        case 4:
                            fours++; break;
                        case 5:
                            fives++; break;
                        default:
                            sixes++; break;
                    }
                }
                results="Results: \n1: "+ones+"\n2: "+twos+"\n3: "+threes+"\n4: "+fours+"\n5: "+fives+"\n6: "+sixes;
                tvResults.setText(results);*/


            }
        });
    }

    //inner class extending AsyncTask class.  AsyncTask<DataTypePassedIn_doInBackground(),DataTypePassedIn_onProgressUpdate(),String>
    public class ProcessDiceInBackground extends AsyncTask<Integer,Integer,String>{

        ProgressBar progressBar;

        @Override
        protected void onPreExecute() { // Runs on Main thread
            super.onPreExecute();

            progressBar=findViewById(R.id.progressBar);
            progressBar.setMax(Integer.parseInt(etNumber.getText().toString().trim()));
            progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar

        }

        //accept an array of the specified data type, Note: see the use of integers[0] in the loop
        @Override
        protected String doInBackground(Integer... integers) {  // Runs on other thread

            int ones=0, twos=0, threes=0, fours=0, fives=0, sixes=0, randomNumber;

            String results;
            Random random=new Random();

            double currentProgress=0;
            double previousProgress=0;

            for(int i=0;i<integers[0];i++){

                // https://www.udemy.com/learn-android-application-development-y/learn/v4/t/lecture/7811358?start=0
                currentProgress=(double)i/integers[0];
                if(currentProgress-previousProgress>=0.02){
                    //publishProgress() calls the background method onProgressUpdate()
                    publishProgress(i);
                    previousProgress=currentProgress;
                }

                randomNumber=random.nextInt(6)+1;
                switch (randomNumber){
                    case 1:
                        ones++; break;
                    case 2:
                        twos++; break;
                    case 3:
                        threes++; break;
                    case 4:
                        fours++; break;
                    case 5:
                        fives++; break;
                    default:
                        sixes++; break;
                }
            }
            results="Results: \n1: "+ones+"\n2: "+twos+"\n3: "+threes+"\n4: "+fours+"\n5: "+fives+"\n6: "+sixes;

            //results will be returned to onPostExecute()
            return results;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {  // Runs on other thread
            super.onProgressUpdate(values);

            //set the progress by the specified value
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {   // Runs on Main thread
            super.onPostExecute(s);

            // To Hide ProgressBar
            progressBar.setVisibility(View.GONE);

            //set the results to text view
            tvResults.setText(s);
            Toast.makeText(MainActivity.this, "Process completed!", Toast.LENGTH_SHORT).show();

        }
    } // end of class ProcessDiceInBackground
}
