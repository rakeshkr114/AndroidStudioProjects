package com.example.m1039158.rssfeed;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvRss;
    ArrayList<String> titles;
    ArrayList<String> links;
    private static final int  INTERNET_UNIQUE_CODE=7;  //custom code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvRss=findViewById(R.id.lvRss);
        titles=new ArrayList<>();
        links=new ArrayList<>();

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},INTERNET_UNIQUE_CODE);
        }

        //On Item click listener
        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Note: i is the index/position of the item clicked in list view
                Uri uri=Uri.parse(links.get(i));
                //To open the Uri
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);

            }
        });
        new ProcessInBackground().execute(1);
    }// End of onCreate()

    //Return data from the URL
    public InputStream getInputStream(URL url){
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer,Integer,Exception>{

        //ContentLoadingProgressBar progressBar=new ContentLoadingProgressBar(MainActivity.this);
        Exception exception=null;

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

          //  progressBar.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers) {

            try{

                /*
                * Url content:
                    <rss version="2.0">
                    <channel>
                    <title>Channel Entertainment</title>
                    <link>https://www.channel24.co.za/</link>
                    <generator>
                    Argotic Syndication Framework, http://www.codeplex.com/argotic
                    </generator>
                    <docs>http://www.rssboard.org/rss-specification</docs>
                    <lastBuildDate>Wed, 25 Jul 2018 08:34:27 +0200</lastBuildDate>
                    <pubDate>Wed, 25 Jul 2018 08:31:17 +0200</pubDate>
                    <item>
                        <title>
                        Channel24.co.za | UPDATE: Demi Lovato refused to tell paramedics what drug she took
                        </title>
                        <description>
                        Demi Lovato reportedly refused to disclose which drug caused her to overdose when paramedics responded to a 911 call at her home.
                        </description>
                        <link>
                        https://www.channel24.co.za/Gossip/News/update-demi-lovato-refused-to-tell-paramedics-what-drug-she-took-20180725
                        </link>
                        <pubDate>Wed, 25 Jul 2018 08:31:17 +0200</pubDate>
                        <enclosure url="https://scripts.24.co.za/img/sites/channel24.png" length="1" type="image/png"/>
                    </item>
                    <item>
                        <title>....
                * */
                // http://feeds.news24.com/articles/channel/topstories/rss
                URL url=new URL("http://feeds.news24.com/articles/news24/TopStories/rss");

                XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
                //To disable support for XML namespace
                factory.setNamespaceAware(false);

                XmlPullParser xpp=factory.newPullParser();

                xpp.setInput(getInputStream(url),"UTF_8");
                boolean insideItem=false;

                int evenType=xpp.getEventType();

                //Iterate until end of the document
                while(evenType!= XmlPullParser.END_DOCUMENT){

                    //If start of any tag
                    if(evenType==XmlPullParser.START_TAG){
                        if(xpp.getName().equalsIgnoreCase("item")){
                            insideItem=true;
                        }
                        else if(xpp.getName().equalsIgnoreCase("title")){
                            // if it's the <title> tag inside of <item> tag
                            if(insideItem){
                                titles.add(xpp.nextText().split("\\|")[1].trim()); //extract the headline
                            }
                        }
                        else if(xpp.getName().equalsIgnoreCase("link")){
                            if(insideItem){
                                links.add(xpp.nextText());
                            }
                        }
                    }
                    //if </item> tag closed --> reset the variable insideItem to false
                    else if(evenType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                        insideItem=false;
                    }
                    //get next tag and keep iterating
                    evenType=xpp.next();
                }

            }catch (MalformedURLException e){
                exception=e;
            }catch (XmlPullParserException e){
                exception=e;
            }catch (IOException e){
                exception=e;
            }
            catch (Exception e){
                exception =e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);

            ArrayAdapter<String> adapter =new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,titles);
            lvRss.setAdapter(adapter);
          //  progressBar.hide();
        }

    }


}
