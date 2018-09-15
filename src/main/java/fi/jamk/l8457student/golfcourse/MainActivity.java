package fi.jamk.l8457student.golfcourse;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // define Recyclerview, adapter and manager
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set main layout
        setContentView(R.layout.activity_main);

        // set toolbar title
        CollapsingToolbarLayout collapsingToolbarLayout =  (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        collapsingToolbarLayout.setTitle("SGKY - Kentät");

        // define LoadJSONTask object
        LoadJSONTask loadJSONTask = new LoadJSONTask();
        // define listener
        loadJSONTask.setListener(new LoadJSONTask.LoadJSONTaskListener() {
            @Override
            public void onPostExecuteConcluded(JSONObject json) {
                if (json == null) {
                    Log.d("JSON", "MainActivity coursesLoaded called. Courses == null!");
                } else {
                    try {
                        JSONArray courses = json.getJSONArray("courses");
                        Log.d("JSON", "MainActivity coursesLoaded called. Courses length = " + courses.length());
                        mRecyclerView = (RecyclerView) findViewById(R.id.courseRecyclerView);
                        mLayoutManager = new LinearLayoutManager(MainActivity.this);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mAdapter = new CourseAdapter(courses, getApplicationContext());
                        mRecyclerView.setAdapter(mAdapter);
                    } catch (JSONException e) {
                        Log.e("JSON", "Error getting courses data.");
                    }
                }
            }
        });
        // start loading JSON data
        loadJSONTask.execute("http://ptm.fi/materials/golfcourses/golf_courses.json");
    }

}