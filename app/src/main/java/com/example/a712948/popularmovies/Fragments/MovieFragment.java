package com.example.a712948.popularmovies.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.example.a712948.popularmovies.Activities.MovieDetails;
import com.example.a712948.popularmovies.Adapters.FavoriteAdapter;
import com.example.a712948.popularmovies.Adapters.MovieAdapter;
import com.example.a712948.popularmovies.DBHelper;
import com.example.a712948.popularmovies.POJO.Movies;
import com.example.a712948.popularmovies.POJO.Result;
import com.example.a712948.popularmovies.R;
import com.example.a712948.popularmovies.rest.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 */
public class MovieFragment extends Fragment {
    public MovieAdapter mMovieAdapter;
    public FavoriteAdapter mFavoriteAdapter;
    public List<Result> mMovies;
    public Boolean mNetwork;
    GridView mGridView;
    DBHelper mDBHelper;
    Cursor mCursor;

    public MovieFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
//        // Add this line in order for this fragment to handle menu events.
//        this.setRetainInstance(true);
//        setHasOptionsMenu(true);
        mDBHelper = new DBHelper(getActivity());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_toprate) {
            if (!mNetwork) {
                Toast.makeText(getActivity().getApplicationContext(), "You are offline", Toast.LENGTH_SHORT).show();
            } else {
                updateMoviesHighRate();
            }
        }
        if (id == R.id.action_pop) {
            if (!mNetwork) {
                Toast.makeText(getActivity().getApplicationContext(), "You are offline", Toast.LENGTH_SHORT).show();
            } else {
                updatePopularMovies();
            }
        }
        if (id == R.id.action_fav) {
            updateFav();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_grid, container, false);
     //   ButterKnife.inject(this, view);
        mGridView = (GridView) view.findViewById(R.id.gridview_movies);
        mNetwork = isNetworkAvailable();
        if (!mNetwork) {
            updateFav();
        } else {
            updatePopularMovies();
        }
        return view;
    }


    private void updateMovies(final View view, List<Result> movies, String title) {
        getActivity().setTitle(title);
        if (mFavoriteAdapter != null) {
            mFavoriteAdapter.clear();
        }
        mMovies = movies;
        mMovieAdapter = new MovieAdapter(getActivity(), mMovies);
        mGridView.setAdapter(mMovieAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Result movieClicked = (Result) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), MovieDetails.class);
                intent.putExtra("MOVIEID", movieClicked.getId());
                startActivity(intent);

            }
        });
        mMovieAdapter.notifyDataSetChanged();

    }

    private void updateMoviesHighRate() {
        RestClient.get().getTopRated(new Callback<Movies>() {
            @Override
            public void success(Movies movies, Response response) {
                updateMovies(getView(), movies.results, "Highest Rated");
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
                updateMovies(getView(), movies.results, "Popular Movies");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Tag", " Error : " + error);
            }
        });

    }

    private void updateFav() {
        getActivity().setTitle("Favorites");
        ArrayList array_list = mDBHelper.getAllFavorites();
        ArrayList<String> moviePaths = new ArrayList();
        final ArrayList<String> movieIDs = new ArrayList();
        final ArrayList<String> movieTitles = new ArrayList();
        for (int i = 0; i < array_list.size(); i++) {
            final String movieID = array_list.get(i).toString();
            mCursor = mDBHelper.getFavorite(movieID);
            if (mCursor != null && mCursor.moveToFirst()) {
                moviePaths.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.FAVORITES_COLUMN_POSTER_PATH)));
                movieIDs.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.FAVORITES_COLUMN_MOVIEID)));
                movieTitles.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.FAVORITES_COLUMN_MOVIE_TITLE)));

            }
            if (mMovieAdapter != null) {
                mMovieAdapter.clear();
            }
            mFavoriteAdapter = new FavoriteAdapter(getActivity(), moviePaths, movieTitles);
            mGridView.setAdapter(mFavoriteAdapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    movieIDs.get(i).toString();
                    Intent intent = new Intent(getActivity(), MovieDetails.class);
                    intent.putExtra("MOVIEID", movieIDs.get(i).toString());
                    startActivity(intent);
                }
            });
            mFavoriteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}