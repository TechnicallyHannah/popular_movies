package com.example.a712948.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 */
public class MovieFragment extends Fragment {
    private ArrayAdapter<String> mMovieAdapter;

    public MovieFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.data_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            ReceiveData dataTask = new ReceiveData();
            dataTask.execute("popularity.asc");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ImageView imageView = (ImageView) container.findViewById(R.id.imageView);
        String[] data = {
                "Happy Gilmore", "Clueless", "Class of 92", "The Martian"
        };

        List<String> movieList = new ArrayList<>(Arrays.asList(data));

        mMovieAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_movie, R.id.list_item_movie_view, movieList);

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        //view = container.getRootView();
        // Picasso.with(view).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        ListView listView = (ListView) view.findViewById(R.id.listview_movies);
        listView.setAdapter(mMovieAdapter);
        return view;
    }
}

class ReceiveData extends AsyncTask<String, Void, String[]> {
    public String LOG = "LOG";

    private String[] getMovieData(String movieString)
            throws JSONException {
        final String RESULTS = "results";
        final String POSTER_PATH = "poster_path";
        final String ORG_TITLE = "original_title";
        final String SUMMARY = "overview";
        final String REL_DATE = "release_date";
        final String VOTE_AVG ="vote_average";

        JSONObject forecastJson = new JSONObject(movieString);
        JSONArray resultsArray = forecastJson.getJSONArray(RESULTS);
    }


    @Override
    protected String[] doInBackground(String... params) {
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


            //URL url = new URL("http://api.themoviedb.org/3/discover/movie?sorted_by=popularity.asc&api_key=fc47e47a86969055486f846572f8bf83");
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
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieString = buffer.toString();
            Log.e(LOG, "movieString" + movieString);
        } catch (IOException e) {
            Log.e(LOG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
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
            try {
                return getMovieData(movieString);
            } catch (JSONException e) {
                Log.e(LOG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

    }
}

