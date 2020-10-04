package com.example.easy_app;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DailyTaskAdapter extends ArrayAdapter<Daily> {

    private Context context;
    private int resource;
    List<Daily> todos;

    DailyTaskAdapter(Context context, int resource, List<Daily> todos){
        super(context,resource,todos);
        this.context = context;
        this.resource = resource;
        this.todos = todos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource,parent,false);

        TextView title = row.findViewById(R.id.title);
        TextView description = row.findViewById(R.id.description);
        ImageView imageView = row.findViewById(R.id.onGoing);


        Daily daily = todos.get(position);
        title.setText(daily.getTaskTitle());
        description.setText(daily.getTaskDescription());
        imageView.setVisibility(row.INVISIBLE);

        if(daily.getFinished() > 0){
            imageView.setVisibility(View.VISIBLE);
        }
        return row;
    }
}
