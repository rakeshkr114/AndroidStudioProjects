package com.example.m1039158.appwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int noOfwidget=appWidgetIds.length;
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            //Get connection to the widget that we want to update
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.new_app_widget);

            Intent intent=new Intent(context,NewsService.class);

            intent.putExtra("appWidgetId",appWidgetId);
            //Start the service with the intent
            context.startService(intent);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

