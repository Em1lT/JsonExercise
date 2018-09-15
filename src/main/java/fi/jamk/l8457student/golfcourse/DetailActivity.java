package fi.jamk.l8457student.golfcourse;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // get and show course data
        try {
            // get json object from launching intent
            final JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("course"));
            // set title
            getSupportActionBar().setTitle("SGKY:"+jsonObject.getString("course"));
            // image
            ImageView courseImageView = (ImageView) findViewById(R.id.courseImageView);
            Glide.with(this).load("http://ptm.fi/jamk/android/golfcourses/"+jsonObject.getString("image")).into(courseImageView);
            // address
            TextView courseAddressTextView = (TextView) findViewById(R.id.courseAddressTextView);
            courseAddressTextView.setText(jsonObject.getString("address"));
            // phone
            TextView coursePhoneTextView = (TextView) findViewById(R.id.coursePhoneTextView);
            coursePhoneTextView.setText(jsonObject.getString("phone"));
            // email
            TextView courseEmailTextView = (TextView) findViewById(R.id.courseEmailTextView);
            courseEmailTextView.setText(jsonObject.getString("email"));
            // info
            TextView courseInfoTextView = (TextView) findViewById(R.id.courseInfoTextView);
            courseInfoTextView.setText(jsonObject.getString("text"));
            // web
            TextView courseWebTextView = (TextView) findViewById(R.id.courseWebTextView);
            courseWebTextView.setText(jsonObject.getString("web"));
            // map
            TextView courseMapTextView = (TextView) findViewById(R.id.courseMapTextView);
            courseMapTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            courseMapTextView.setText("Näytä kartalla");
            courseMapTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    try {
                        // we need to parse below string content
                        // Uri.parse("geo:<" + myLatitude  + ">,<" + myLongitude + ">?q=<" + myLatitude  + ">,<" + myLongitude + ">(" + labelLocation + ")"));
                        String lat = jsonObject.getString("lat");
                        String lng = jsonObject.getString("lng");
                        String course = jsonObject.getString("course");
                        intent.setData(Uri.parse("geo:<"+lat+">,<"+lng+">?q=<"+lat+">,<"+lng+">("+course+")"));
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click -> close this activity
        if (item.getItemId() == android.R.id.home) {
            // finish this activity
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}