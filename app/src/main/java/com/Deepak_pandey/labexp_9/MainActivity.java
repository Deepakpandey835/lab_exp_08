package com.Deepak_pandey.labexp_9;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.developer.taskmaker.R;
import com.Deepak_pandey.labexp_9.data.DatabaseContract;
import com.Deepak_pandey.labexp_9.data.TaskAdapter;
import com.Deepak_pandey.labexp_9.data.TaskUpdateService;

public class MainActivity extends AppCompatActivity implements
        TaskAdapter.OnItemClickListener,
        View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private TaskAdapter mAdapter;
    RecyclerView recyclerView;
    String currValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        try {
            currValue = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.pref_sortBy_key), "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // get loader manager object
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Click events in Floating Action Button */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    /* Click events in RecyclerView items */
    @Override
    public void onItemClick(View v, int position) {
        // Handle list item click event

    }

    /* Click events on RecyclerView item checkboxes */
    @Override
    public void onItemToggled(boolean active, int position) {
        // Handle task item checkbox event
        ContentValues values;
        values = new ContentValues();
        values.put(DatabaseContract.TaskColumns.DESCRIPTION, mAdapter.getItem(position).description);
        values.put(DatabaseContract.TaskColumns.IS_PRIORITY, mAdapter.getItem(position).isPriority);
        values.put(DatabaseContract.TaskColumns.IS_COMPLETE, active ? 1 : 0);
        values.put(DatabaseContract.TaskColumns.DUE_DATE, mAdapter.getItem(position).dueDateMillis);

        TaskUpdateService.updateTask(getApplicationContext(), Uri.withAppendedPath(DatabaseContract.CONTENT_URI, String.valueOf(mAdapter.getItem(position).id)), values);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection =
                {
                        DatabaseContract.TaskColumns._ID,
                        DatabaseContract.TaskColumns.DESCRIPTION,
                        DatabaseContract.TaskColumns.IS_COMPLETE,
                        DatabaseContract.TaskColumns.IS_PRIORITY,
                        DatabaseContract.TaskColumns.DUE_DATE
                };


        if (currValue.equalsIgnoreCase("default") || currValue.equalsIgnoreCase("")) {
            return new CursorLoader(this, DatabaseContract.CONTENT_URI, projection, null, null, "is_priority desc,due_date asc,is_complete asc");
        } else {
            return new CursorLoader(this, DatabaseContract.CONTENT_URI, projection, null, null, "due_date asc,is_priority desc,is_complete asc");
        }
//        return new CursorLoader(this, DatabaseContract.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter = new TaskAdapter(data);
        mAdapter.setOnItemClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
