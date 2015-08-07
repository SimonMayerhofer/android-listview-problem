package de.mayerhofersimon.listviewproblem;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 */
public class MyWidgetProvider extends AppWidgetProvider {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d("MyWidgetProvider", "appWidgetIds.lenght:" + appWidgetIds.length);
        for (int appWidgetId : appWidgetIds) {
            Intent svcIntent = new Intent(context, MyWidgetService.class);
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget_root);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                widget.setRemoteAdapter(R.id.listView_widget, svcIntent);
            else
                widget.setRemoteAdapter(appWidgetId, R.id.listView_widget, svcIntent);

            /*
            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent clickPI = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setPendingIntentTemplate(R.id.listView_widget, clickPI);*/

            appWidgetManager.updateAppWidget(appWidgetId, widget);
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

