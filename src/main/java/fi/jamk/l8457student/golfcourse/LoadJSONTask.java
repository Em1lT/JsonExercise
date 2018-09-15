package fi.jamk.l8457student.golfcourse;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadJSONTask extends AsyncTask<String, Void, JSONObject>{

    // define callback listener
    private LoadJSONTaskListener mListener;

    public interface LoadJSONTaskListener {
        public void onPostExecuteConcluded(JSONObject result);
    }

    final public void setListener(LoadJSONTaskListener listener) {
        mListener = listener;
    }

    // asynctask background thread, load data
    @Override
    final protected JSONObject doInBackground(String... urls) {
        HttpURLConnection urlConnection = null;
        JSONObject json = null;
        try {
            URL url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            json = new JSONObject(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return json;
    }

    // data is loaded, send it back to caller
    final protected void onPostExecute(JSONObject json) {
        if (mListener != null) mListener.onPostExecuteConcluded(json);
    }

}