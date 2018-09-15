package fi.jamk.l8457student.golfcourse;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private JSONArray mCourses;
    private Context mContext;

    public CourseAdapter(JSONArray courses, Context context) {
        this.mCourses = courses;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mCourses.length();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        try {
            JSONObject jsonObject = mCourses.getJSONObject(position);
            // images are in ptm.fi web server
            Glide.with(mContext).load("http://ptm.fi/jamk/android/golfcourses/"+jsonObject.getString("image")).into(viewHolder.courseImageView);
            viewHolder.courseNameTextView.setText(jsonObject.getString("course"));
            viewHolder.courseAddressTextView.setText(jsonObject.getString("address"));
            viewHolder.coursePhoneTextView.setText(jsonObject.getString("phone"));
            viewHolder.courseEmailTextView.setText(jsonObject.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView courseImageView;
        public TextView courseNameTextView;
        public TextView courseAddressTextView;
        public TextView coursePhoneTextView;
        public TextView courseEmailTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            courseImageView = (ImageView) itemView.findViewById(R.id.courseImageView);
            courseNameTextView = (TextView) itemView.findViewById(R.id.courseNameTextView);
            courseAddressTextView = (TextView) itemView.findViewById(R.id.courseAddressTextView);
            coursePhoneTextView = (TextView) itemView.findViewById(R.id.coursePhoneTextView);
            courseEmailTextView = (TextView) itemView.findViewById(R.id.courseEmailTextView);
            // item click handler
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    try {
                        JSONObject jsonObject = mCourses.getJSONObject(position);
                        // open DetailActivity
                       /* Intent intent = new Intent(mContext,DetailActivity.class);
                        intent.putExtra("course", jsonObject.toString());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}