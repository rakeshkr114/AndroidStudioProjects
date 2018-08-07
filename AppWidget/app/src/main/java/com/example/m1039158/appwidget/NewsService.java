package com.example.m1039158.appwidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.AppLaunchChecker;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class NewsService extends Service {

    ArrayList<String> descriptions;
    ArrayList<String> links;
    ArrayList<String> titles;
    Intent intent;

    public NewsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //After onCreate(), It is the first method that runs inside the service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        descriptions=new ArrayList<>();
        links=new ArrayList<>();
        titles=new ArrayList<>();

        //get the intent that started this specific service
        this.intent=intent;

        if(ApplicationClass.connectionAvailable(getApplicationContext())){
            //RemoteViews helps in updating the specified layout
            RemoteViews views=new RemoteViews("com.example.m1039158.appwidget",R.layout.new_app_widget);
            views.setTextViewText(R.id.tvTitle,"Busy retrieving data...");
            views.setTextViewText(R.id.tvDescription,"Busy retrieving data...");

            //Update the widget
            AppWidgetManager.getInstance(getApplicationContext()).updateAppWidget(intent.getIntExtra("appWidgetId",0),views);

            //executing async methods
            new GetStoriesInBackground().execute();
        }
        else{
            Toast.makeText(getApplicationContext(), "Please make sure your phone have an active Internet connection!", Toast.LENGTH_SHORT).show();
            //stop the service if no internet connection
            stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    //Execute in background
    public class GetStoriesInBackground extends AsyncTask<Integer, Integer, String>
    {

        @Override
        protected String doInBackground(Integer... params) {

            try {

                URL url = new URL("http://feeds.news24.com/articles/news24/TopStories/rss");

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");

                /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
                 * However, we should take in consideration that the rss feed name also is enclosed in a "<title>" tag.
                 * As we know, every feed begins with these lines: "<channel><title>Feed_Name</title>...."
                 * so we should skip the "<title>" tag which is a child of "<channel>" tag,
                 * and take in consideration only "<title>" tag which is a child of "<item>"
                 *
                 * In order to achieve this, we will make use of a boolean variable.
                 */
                boolean insideItem = false;

                // Returns the type of current event: START_TAG, END_TAG, etc..
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem)
                                titles.add(xpp.nextText()); //extract the headline
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem)
                                links.add(xpp.nextText()); //extract the link of article
                        }
                        else if (xpp.getName().equalsIgnoreCase("description")) {
                            if (insideItem)
                                descriptions.add(xpp.nextText()); //extract the link of article
                        }
                    }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                        insideItem=false;
                    }

                    eventType = xpp.next(); //move to next element
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Returning null, as titles,links and descriptions are decleared globally, hence onPostExecute will have access to them
            return null;

        }


        @Override
        protected void onPostExecute(String result) {
            Random random=new Random();
            int randomValue=random.nextInt(titles.size());

            RemoteViews views=new RemoteViews("com.example.m1039158.appwidget",R.layout.new_app_widget);
            views.setTextViewText(R.id.tvTitle,titles.get(randomValue));
            views.setTextViewText(R.id.tvDescription,descriptions.get(randomValue));

            Uri uri=Uri.parse(links.get(randomValue));
            //To view a web page
            Intent linkIntent=new Intent(Intent.ACTION_VIEW,uri);

            //Pending intent as name suggests will be waiting to get called
            PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,linkIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            //Call or start pending intent when clicked on text view tvDescription => Launch web page
            views.setOnClickPendingIntent(R.id.tvDescription,pendingIntent);

            //PendingIntent.FLAG_UPDATE_CURRENT --> If Intent is active, update the content
            PendingIntent pendingIntentSync=PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            //Call or start pending intent pendingIntentSync when clicked on Image view id=ivSync
            views.setOnClickPendingIntent(R.id.ivSync,pendingIntentSync);

            //Finally update the widget
            AppWidgetManager.getInstance(getApplicationContext()).updateAppWidget(intent.getIntExtra("appWidgetId",0),views);

        }

    }
}
