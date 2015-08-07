package de.mayerhofersimon.listviewproblem;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static ArrayList<Item> items = new ArrayList<>();
    private static int itemnr = 0;
    private static int subitemnr = 0;
    private int appWidgetId;
    private Context context;

    public MyWidgetViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);


        //Some random data to display
        for (int i = 0; i < 10; i++) {
            Item item = new Item(String.valueOf(itemnr++));

            for (int j = 0; j < 3; j++) {
                String[] subitem = {String.valueOf(subitemnr++), String.valueOf(subitemnr++), String.valueOf(subitemnr++)};
                item.addSubitem(subitem);
            }

            items.add(item);
        }
    }

    @Override
    public void onCreate() {
        // no-op
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d("MyWidgetViewsFactory", "getViewAt(" + position + "):" + items.get(position));
        Item item = items.get(position);

        RemoteViews itemView = new RemoteViews(context.getPackageName(), R.layout.widget_listview_item);

        itemView.setTextViewText(R.id.textView_itemnr, item.getItemNr());

        for (String[] s : item.getSubitems()) {
            Log.d("MyWidgetViewsFactory", "subitem:" + s[0] + "|" + s[1] + "|" + s[2]);
            RemoteViews subitem = new RemoteViews(context.getPackageName(), R.layout.widget_listview_subitem);

            subitem.setTextViewText(R.id.textView_1, s[0]);
            subitem.setTextViewText(R.id.textView_2, s[1]);
            subitem.setTextViewText(R.id.textView_3, s[2]);

            itemView.addView(R.id.linearLayout_item_body, subitem);
        }
        return itemView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return (null);
    }

    @Override
    public int getViewTypeCount() {
        return (1);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public boolean hasStableIds() {
        return (true);
    }

    @Override
    public void onDataSetChanged() {
        // no-op
    }

    class Item {
        private ArrayList<String[]> subitems = new ArrayList<>();
        private String itemnr = "";

        Item(String itemnr) {
            this.itemnr = itemnr;
        }

        Item() {
        }

        ;


        public void addSubitem(String[] subitem) {
            this.subitems.add(subitem);
        }

        public ArrayList<String[]> getSubitems() {
            return subitems;
        }

        public String getItemNr() {
            return itemnr;
        }

        public void setItemNr(String itemnr) {
            this.itemnr = itemnr;
        }
    }
}