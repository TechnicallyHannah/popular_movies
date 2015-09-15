package com.example.a712948.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.a712948.popularmovies.rest.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<DiscoverMovieResponse>());
        View view = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridview_movies);
        gridView.setAdapter(mMovieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MovieResponse movieResponse = (MovieResponse) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                // Log.i("Tag", discoverMovieResponse.title);
//                intent.putExtra("MOVIE_TITLE", discoverMovieResponse.title);
//                intent.putExtra("MOVIE_SUM", discoverMovieResponse.summary);
//                intent.putExtra("MOVIE_RATE", discoverMovieResponse.vote_avg);
//                intent.putExtra("MOVIE_REL", discoverMovieResponse.release_date);
//                intent.putExtra("MOVIE_POSTER", discoverMovieResponse.poster);
//                startActivity(intent);
            }

        });
        return view;
    }


    private void updateMovies() {
        RestClient.get().getContent(new Callback<DiscoverMovieResponse>() {
            @Override
            public void success(DiscoverMovieResponse discoverMovieResponse, Response response) {
                Log.i("Tag", discoverMovieResponse.results + " Movies Returned");
                mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<DiscoverMovieResponse>());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    private void updateMoviesHighRate() {
        //  ServiceHandler dataTask = new ServiceHandler(getActivity(), mMovieAdapter);
        // dataTask.execute("vote_average.asc");
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