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
 * @since 8/14/15.
 */
public class ServiceHandler extends AsyncTask<String, Void, ArrayList<Movie>> {
    public String LOG = "LOG";

    private ArrayAdapter<Movie> mMovieAdapter;
    private final Context mContext;

    public ServiceHandler(Context context, ArrayAdapter<Movie> movieAdapter) {
        mContext = context;
        mMovieAdapter = movieAdapter;
    }

    private ArrayList<Movie> getMovieData(String movieString)
            throws JSONException {


        final String RESULTS = "results";
        final String POSTER_PATH = "poster_path";
        final String ORG_TITLE = "original_title";
        final String SUMMARY = "overview";
        final String REL_DATE = "release_date";
        final String VOTE_AVG = "vote_average";

        ArrayList<Movie> movies = new ArrayList<>();
        JSONObject movieJson = new JSONObject(movieString);
        JSONArray resultsArray = movieJson.getJSONArray(RESULTS);

        String[] resultStrs = new String[resultsArray.length()];
        for (int i = 0; i < resultsArray.length(); i++) {
            String title;
            String summary;
            String release_date;
            String vote_avg;
            String poster;

            //gets i within array
            JSONObject movie = resultsArray.getJSONObject(i);
            title = movie.getString(ORG_TITLE);
            summary = movie.getString(SUMMARY);
            release_date = movie.getString(REL_DATE);
            vote_avg = movie.getString(VOTE_AVG);
            poster = movie.getString(POSTER_PATH);

            movies.add(new Movie(title, summary, release_date, vote_avg, poster));

        }
        return movies;
    }


    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        // Start of getting data back
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String movieString = null;
        String api = "fc47e47a86969055486f846572f8bf83";

        try {

            final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
            final String SORTED_PARAM = "sorted_by";
            final String API_PARAM = "api_key";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(SORTED_PARAM, params[0])
                    .appendQueryParameter(API_PARAM, api)
                    .build();


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
            return getMovieData(movieString);
        } catch (JSONException e) {
            Log.e(LOG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        if (movies != null && mMovieAdapter != null) {
            mMovieAdapter.clear();
            mMovieAdapter.addAll(movies);

        }
    }
}

