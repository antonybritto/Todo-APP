package com.flipkart.todo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by antony.britto on 11/12/15.
 */
public class TaskAdapter extends BaseAdapter {
    Context context;
    Map<String, OrderBy> sortPriority = null;
    Map<String, String> attributeValuePair = null;



    private static final String  TAG = "TaskAdapter";
    //LruCache<String, Bitmap> cache;

    public TaskAdapter(Context context,  Map<String, OrderBy> sortPriority, Map<String, String> attributeValuePair) {
        this.context = context;
        this.sortPriority = new HashMap<String, OrderBy>();
        this.attributeValuePair = new HashMap<String, String>();
        if (sortPriority != null) {
            this.sortPriority.putAll(sortPriority);
        }
        if (attributeValuePair != null) {
            this.attributeValuePair.putAll(attributeValuePair);
        }

        /*final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        cache = new LruCache<String, Bitmap>(maxMemory/8){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };*/
    }

    static class ViewHolder {
        TextView taskPriority;
        TextView title;
        TextView dueDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mainView = null;
        if(convertView == null) {
            //Log.i("CounterAdapter", "getView(" + position + ")");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mainView = inflater.inflate(R.layout.task, null);

            ViewHolder vh = new ViewHolder();
            vh.taskPriority = (TextView)mainView.findViewById(R.id.priority);
            vh.title = (TextView)mainView.findViewById(R.id.title);
            vh.dueDate = (TextView)mainView.findViewById(R.id.duedate);

            mainView.setTag(vh);
        } else {
            mainView = convertView;
        }
        Task task = TaskTable.getTask(position, sortPriority, attributeValuePair);

        Log.i(TAG, position + " :" + task.toString());


        ViewHolder vh = (ViewHolder)mainView.getTag();

        vh.taskPriority.setText(task.priority);
        vh.title.setText(task.title);
        vh.dueDate.setText(task.dueDate);

        /*String flagPath = "flags-32/" + country.title + ".png";

        Bitmap image = cache.get(flagPath);
        if(image == null) {
            AssetManager manager = context.getAssets();
            try {
                InputStream instr = manager.open(flagPath);
                Bitmap img = BitmapFactory.decodeStream(instr);
                if(img != null) {
                    cache.put(flagPath, img);
                    vh.countryIV.setImageBitmap(img);
                }
            } catch (IOException e) {
                vh.countryIV.setImageResource(R.drawable.android_logo);
                e.printStackTrace();
            }
        } else {
            vh.countryIV.setImageBitmap(image);
        }*/

        return mainView;
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(TaskTable.getTask(position, sortPriority, attributeValuePair).getId());
    }

    @Override
    public Object getItem(int position) {
        return TaskTable.getTask(position, sortPriority, attributeValuePair);
    }

    @Override
    public int getCount() {
        return TaskTable.getCount(attributeValuePair);
    }

    public Map<String, OrderBy> getSortPriority() {
        return sortPriority;
    }

    public void setSortPriority(Map<String, OrderBy> sortPriority) {
        this.sortPriority = sortPriority;
    }
}
