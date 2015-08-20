package com.example.a712948.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 */
public class MovieFragment extends Fragment {
    private ArrayAdapter<Movie> mMovieAdapter;

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
        final String TAG = "Tag";

        mMovieAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_movie, R.id.list_item_movie_view, new ArrayList<Movie>());

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listview_movies);
        listView.setAdapter(mMovieAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = (Movie) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), MovieDetail.class)
                        .putExtra(Intent.EXTRA_TEXT, movie.title);
                Log.i(TAG,movie.title);
                Log.i(TAG,movie.summary);
                Log.i(TAG,movie.release_date);
                startActivity(intent);
            }
        });
        return view;
    }

    private void updateMovies() {
        ServiceHandler dataTask = new ServiceHandler(getActivity(),mMovieAdapter);
        dataTask.execute("popularity.asc");
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }
}