package com.example.easy_app;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditDailyTask extends AppCompatActivity {

    private EditText taskTitle, taskDescription;
    private Button editTaskButton;
    private DbHandler dbHandler;
    private Context context;
    private Long updateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_daily_task);

        context = this;
        dbHandler = new DbHandler(context);

        taskTitle = findViewById(R.id.editToDoTextTitle);
        taskDescription = findViewById(R.id.editToDoTextDescription);
        editTaskButton = findViewById(R.id.buttonEdit);

        final String id = getIntent().getStringExtra("id");
        Daily todo = dbHandler.getSingleTask(Integer.parseInt(id));

        taskTitle.setText(todo.getTaskTitle());
        taskDescription.setText(todo.getTaskDescription());

        editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateTaskTitle() | !validateTaskDescription()) {
                    return;
                }

                String titleText = taskTitle.getText().toString();
                String descriptionText = taskDescription.getText().toString();
                updateDate = System.currentTimeMillis();

                Daily daily = new Daily(Integer.parseInt(id), titleText, descriptionText, updateDate, 0);
                int state = dbHandler.updateSingleTask(daily);
                System.out.println(state);
                startActivity(new Intent(context, MainActivity.class));
            }
        });
    }

    //Validations
    private Boolean validateTaskTitle() {
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
