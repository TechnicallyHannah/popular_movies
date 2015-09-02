package com.example.a712948.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Hannah Paulson
 * @since 8/27/15.
 */
public class MovieTrailerHandler extends AsyncTask<String, Void, ArrayList<MovieTrailer>> {


    public String LOG = "LOG";

    private ArrayAdapter<MovieTrailer> mTrailerArrayAdapter;
    private final Context mContext;

    public MovieTrailerHandler(Context context, ArrayAdapter<MovieTrailer> trailerAdapter) {
        mContext = context;
        mTrailerArrayAdapter = trailerAdapter;
    }


    private ArrayList<MovieTrailer> getTrailerData(String movieString)
            throws JSONException {


        final String RESULTS = "results";
        final String KEY = "key";
        final String TRAILER_NAME = "name";
        final String TYPE = "type";

        ArrayList<MovieTrailer> movies = new ArrayList<>();
        JSONObject movieJson = new JSONObject(movieString);
        JSONArray resultsArray = movieJson.getJSONArray(RESULTS);

        for (int i = 0; i < resultsArray.length(); i++) {
            String key;
            String trailerName;
            String type;

            //gets i within array
            JSONObject movie = resultsArray.getJSONObject(i);
            key = movie.getString(KEY);
            trailerName = movie.getString(TRAILER_NAME);
            type = movie.getString(TYPE);

            movies.add(new MovieTrailer(key, trailerName, type));

        }
        return movies;
    }


    protected ArrayList<MovieTrailer> doInBackground(String... params) {
        // Start of getting data back
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String movieString = null;
        String api = "fc47e47a86969055486f846572f8bf83";

        try {

            final String BASE_URL = "http://api.themoviedb.org/3/movie/";
            final String MOVIE_PARAM = "videos";
            final String API_PARAM = "api_key";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(params[0])
                    .appendPath(MOVIE_PARAM)
                    .appendQueryParameter(API_PARAM, api)
                    .build();
            Log.i("URI FOR TRAILER", builtUri.toString());

            URL url = new URL(builtUri.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            movieString = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG, "Error ", e);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG, "Error closing stream", e);
                }
            }
        }
        try {
            Log.i("tag", params[0]);
            return getTrailerData(movieString);
        } catch (JSONException e) {
            Log.e(LOG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieTrailer> movies) {
        if (movies != null && mTrailerArrayAdapter != null) {
            mTrailerArrayAdapter.clear();
            mTrailerArrayAdapter.addAll(movies);
        }
    }
}
