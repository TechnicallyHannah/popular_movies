package com.example.a712948.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
            dataTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] data = {
                "Happy Gilmore", "Clueless", "Class of 92", "The Martian"
        };

        List<String> movieList = new ArrayList<>(Arrays.asList(data));

        mMovieAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_movie, R.id.list_item_movie_view, movieList);

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listview_movies);
        listView.setAdapter(mMovieAdapter);
        return view;
    }
}

class ReceiveData extends AsyncTask<Void, Void, Void> {
    public String LOG = "LOG";
    protected Void doInBackground(Void... params) {
        // Start of getting data back
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        String movieString = null;

        try {
            URL url = new URL("http://api.themoviedb.org/3/discover/movie?sorted_by=popularity.asc&api_key=fc47e47a86969055486f846572f8bf83");
            Log.i(LOG, "GOT URL" + url);
            connection = (HttpURLConnection) url.openConnection();
            Log.i(LOG, "open Connection");
            connection.setRequestMethod("GET");
            Log.i(LOG, "Requested Get");
            connection.connect();
            Log.i(LOG, "OPENED CONNECTION");
            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            Log.i(LOG, "GOT STRING  " + buffer);

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
        }
        return null;

    }
}

