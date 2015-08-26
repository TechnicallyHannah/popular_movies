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
        final String MOVIE_ID = "id";

        ArrayList<Movie> movies = new ArrayList<>();
        JSONObject movieJson = new JSONObject(movieString);
        JSONArray resultsArray = movieJson.getJSONArray(RESULTS);

        for (int i = 0; i < resultsArray.length(); i++) {
            String title;
            String summary;
            String release_date;
            String vote_avg;
            String poster;
            String movie_id;

            //gets i within array
            JSONObject movie = resultsArray.getJSONObject(i);
            movie_id = movie.getString(MOVIE_ID);
            title = movie.getString(ORG_TITLE);
            summary = movie.getString(SUMMARY);
            release_date = getDate(movie.getString(REL_DATE));
            vote_avg = movie.getString(VOTE_AVG);
            poster = movie.getString(POSTER_PATH);

            movies.add(new Movie(movie_id, title, summary, release_date, vote_avg, poster));

        }
        return movies;
    }

    private String getDate(String str) {
        String[] strings = str.split("-");
        String month = strings[1];
        String day = strings[2];
        String year = strings[0];
        switch (month) {
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "July";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
                break;
        }
        String date = month + " " + day + " " + year;
        Log.i("DATE", date);
        return date;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        // Start of getting data back
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String movieString = null;
        String api = "";

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

