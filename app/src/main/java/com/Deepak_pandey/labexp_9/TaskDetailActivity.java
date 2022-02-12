package com.Deepak_pandey.labexp_9;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.widget.DatePicker;

import com.google.developer.taskmaker.R;

public class TaskDetailActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Task must be passed to this activity as a valid provider Uri
        final Uri taskUri = getIntent().getData();

        // Display attributes of the provided task in the UI
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_detail, menu);
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Handle date selection from a DatePickerFragment
    }
}
