package com.example.a712948.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ImageView imageView = (ImageView) container.findViewById(R.id.imageView);

        updateMovies();

        mMovieAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_movie, R.id.list_item_movie_view, new ArrayList<String>());

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listview_movies);
        listView.setAdapter(mMovieAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String movie = mMovieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetail.class)
                        .putExtra(Intent.EXTRA_TEXT, movie);
                startActivity(intent);
            }
        });
        return view;
    }

    private void updateMovies() {
        ReceiveData dataTask = new ReceiveData();
        dataTask.execute("popularity.asc");

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
            final String VOTE_AVG = "vote_average";

            JSONObject forecastJson = new JSONObject(movieString);
            JSONArray resultsArray = forecastJson.getJSONArray(RESULTS);

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

                resultStrs[i] = title + " - " + summary + " - " + release_date + " - " + vote_avg + " - " + poster;

            }
            return resultStrs;
        }


        @Override
        protected String[] doInBackground(String... params) {
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
            // This will only happen if there was an error getting or parsing the data.
            return null;
        }

        @Override
        protected void onPostExecute(String[] results) {
            if (results != null) {
                mMovieAdapter.clear();
                for (String movieResults : results) {
                    mMovieAdapter.add(movieResults);
                }
            }
        }
    }
}