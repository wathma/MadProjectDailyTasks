package com.example.easy_app;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button add;
    private ListView listView;
    private TextView count;
    Context context;
    private DbHandler dbHandler;
    private List<Daily> dailies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        dbHandler = new DbHandler(context);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.todolist);
        count = findViewById(R.id.todocount);
        dailies = new ArrayList<>();

        dailies = dbHandler.getAllTasks();

        DailyTaskAdapter adapter = new DailyTaskAdapter(context,R.layout.single_daily_task, dailies);

        listView.setAdapter(adapter);

        //get task counts from the table
        int countTasks = dbHandler.countTasks();
        count.setText("Today total no.of tasks = "+countTasks+".");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddDailyTask.class));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Daily todo = dailies.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(todo.getTaskTitle());
                builder.setMessage(todo.getTaskDescription());

                builder.setPositiveButton("Completed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        todo.setFinished(System.currentTimeMillis());
                        dbHandler.updateSingleTask(todo);
                        startActivity(new Intent(context,MainActivity.class));
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteTasks(todo.getTaskID());
                        startActivity(new Intent(context,MainActivity.class));
                    }
                });
                builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, EditDailyTask.class);
                        intent.putExtra("id",String.valueOf(todo.getTaskID()));
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });

    }
}
