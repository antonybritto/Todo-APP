package com.flipkart.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by antony.britto on 11/12/15.
 */
public class TaskAdapter extends BaseAdapter {
    Context context;
    ArrayList<Task> tasks;

    //LruCache<String, Bitmap> cache;

    public TaskAdapter(Context context, ArrayList<Task> countries) {
        this.context = context;
        this.tasks = countries;
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
        Task task = tasks.get(position);


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
        return position;
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }
}
