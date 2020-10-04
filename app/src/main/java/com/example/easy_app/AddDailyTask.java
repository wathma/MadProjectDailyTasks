package com.example.easy_app;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddDailyTask extends AppCompatActivity {

    private EditText taskTitle, taskDescription;
    private Button addTaskButton;
    private DbHandler dbHandler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_daily_task);

        taskTitle = findViewById(R.id.editTextTitle);
        taskDescription = findViewById(R.id.editTextDescription);
        addTaskButton = findViewById(R.id.buttonAdd);
        context = this;

        dbHandler = new DbHandler(context);


        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateTaskTitle() | !validateTaskDescription()) {
                    return;
                }
                String userTitle = taskTitle.getText().toString();
                String userDescription = taskDescription.getText().toString();
                long started = System.currentTimeMillis();

                Daily daily = new Daily(userTitle, userDescription, started, 0);
                dbHandler.addTasks(daily);

                startActivity(new Intent(context, MainActivity.class));
            }
        });
    }
    // Validations
    public Boolean validateTaskTitle() {
        String value = taskTitle.getText().toString();
        if (value.isEmpty()) {
            taskTitle.setError("This field can not be empty!");
            return false;
        } else {
            taskTitle.setError(null);
            return true;
        }
    }

    private Boolean validateTaskDescription() {
        String value = taskDescription.getText().toString();
        if (value.isEmpty()) {
            taskDescription.setError("This field can not be empty!");
            return false;
        } else {
            taskDescription.setError(null);
            return true;
        }

    }
}