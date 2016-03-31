package com.example.a712948.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.a712948.popularmovies.POJO.Movies;
import com.example.a712948.popularmovies.POJO.Result;
import com.example.a712948.popularmovies.rest.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 */
public class MovieFragment extends Fragment {
    public static final String PREFS_NAME = "FAV_PREFS";
    public MovieAdapter mMovieAdapter;
    public List<Result> mMovies;
    DBHelper mDBHelper;
    Cursor mCursor;

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
        mDBHelper = new DBHelper(getActivity());
        Log.i("Fav", mDBHelper.getAllFavorites() + "");

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
        if (id == R.id.action_pop) {
            updatePopularMovies();
        }
        if (id == R.id.action_fav) {
            //TODO: add fav here
            updateFav();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        updateMovies(view);
        return view;
    }


    private void updateMovies(final View view) {
        RestClient.get().getContent(new Callback<Movies>() {
            @Override
            public void success(Movies movies, Response response) {
                mMovies = movies.results;
                mMovieAdapter = new MovieAdapter(getActivity(), mMovies);
                GridView gridView = (GridView) view.findViewById(R.id.gridview_movies);
                gridView.setAdapter(mMovieAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Result movieClicked = (Result) adapterView.getItemAtPosition(i);
                        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                        intent.putExtra("MOVIEID", movieClicked.getId());
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Tag", " Error : " + error);
            }
        });

    }

    private void updateMoviesHighRate() {
        RestClient.get().getTopRated(new Callback<Movies>() {
            @Override
            public void success(Movies movies, Response response) {
                if (!mMovies.isEmpty()) {
                    mMovieAdapter.clear();
                    mMovieAdapter.addAll(movies.results);
                    mMovieAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Tag", " Error : " + error);
            }
        });

    }

    private void updatePopularMovies() {
        RestClient.get().getContent(new Callback<Movies>() {
            @Override
            public void success(Movies movies, Response response) {
                if (!mMovies.isEmpty()) {
                    mMovieAdapter.clear();
                    mMovieAdapter.addAll(movies.results);
                    mMovieAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Tag", " Error : " + error);
            }
        });

    }

    private void updateFav() {
//
//        Intent intent = new Intent(getActivity(), FavoriteActivity.class);
//        startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

}