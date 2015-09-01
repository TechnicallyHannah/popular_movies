package com.example.a712948.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 */
public class MovieFragment extends Fragment {
    private MovieAdapter mMovieAdapter;

    public MovieFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        // Add this line in order for this fragment to handle menu events.
        this.setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_toprate) {
            updateMoviesHighRate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());
        View view = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridview_movies);
        gridView.setAdapter(mMovieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = (Movie) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                Log.i("Tag", movie.title);
                intent.putExtra("MOVIE_TITLE", movie.title);
                intent.putExtra("MOVIE_SUM", movie.summary);
                intent.putExtra("MOVIE_RATE", movie.vote_avg);
                intent.putExtra("MOVIE_REL", movie.release_date);
                intent.putExtra("MOVIE_POSTER", movie.poster);
                intent.putExtra("MOVIE_ID", movie.movieID);
                startActivity(intent);
            }

        });
        return view;
    }


    private void updateMovies() {
        ServiceHandler dataTask = new ServiceHandler(getActivity(), mMovieAdapter);
        dataTask.execute("popularity.asc");
    }

    private void updateMoviesHighRate() {
        ServiceHandler dataTask = new ServiceHandler(getActivity(), mMovieAdapter);
        dataTask.execute("vote_average.asc");
    }

    @Override
    public void onStart() {
        updateMovies();
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

}