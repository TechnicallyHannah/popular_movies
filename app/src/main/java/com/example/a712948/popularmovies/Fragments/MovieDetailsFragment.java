package com.example.a712948.popularmovies.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.example.a712948.popularmovies.DBHelper;
import com.example.a712948.popularmovies.POJO.*;
import com.example.a712948.popularmovies.R;
import com.example.a712948.popularmovies.rest.RestClient;
import com.squareup.picasso.Picasso;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {
    //Todo make loading screen
    private final String MOVIE_ID = "MOVIEID";

    List<Genre> mGenres;
    Trailers mTrailers;
    String mMovieID;
    Reviews mReviews;
    MovieDetail mMovieDetail;
    private DBHelper mydb;
    Cursor mCursor;

    @InjectView(R.id.movie_summary_text)
    TextView summary_text_view;
    @InjectView(R.id.movie_release_date)
    TextView release_text_view;
    @InjectView(R.id.movie_rate_text)
    TextView rate_text_view;
    @InjectView(R.id.movie_poster)
    ImageView poster_view;
    @InjectView(R.id.review_author_text)
    TextView review_author;
    @InjectView(R.id.review_content)
    TextView review_text;
    @InjectView(R.id.movie_favorite)
    TextView fav_text_view;

    ViewGroup trailerView;
    ViewGroup reviewView;


    public MovieDetailsFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
//        // Add this line in order for this fragment to handle menu events.
//        this.setRetainInstance(true);
        setHasOptionsMenu(true);
        // mDBHelper = new DBHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.setRetainInstance(true);

        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.inject(this, view);
        Intent intent = getActivity().getIntent();
        mydb = new DBHelper(getActivity());
        mMovieID = intent.getStringExtra(MOVIE_ID);
        trailerView = (ViewGroup) view.findViewById(R.id.trailer_container);
        reviewView = (ViewGroup) view.findViewById(R.id.review_container);
        if (!isNetworkAvailable()) {
            updateFav(mMovieID);
        } else {
            getDetails(mMovieID);
        }
        return view;
    }

    private void getDetails(String mMovieID) {
        RestClient.get().getDetails(mMovieID, new Callback<MovieDetail>() {
            @Override
            public void success(MovieDetail detail, Response response) {
                getActivity().setTitle(detail.getTitle());
                mMovieDetail = detail;
                mGenres = detail.getGenres();
                mTrailers = detail.getTrailers();
                mReviews = detail.getReviews();
                populateDetails(mMovieDetail);
                populateTrailers(mTrailers);
                populateReviews(mReviews);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Tag", " Error : " + error);
            }
        });
    }


    private void populateDetails(final MovieDetail details) {
        final ArrayList mFavList = mydb.getAllFavorites();

        summary_text_view.setText(details.getOverview());
        release_text_view.setText(details.getReleaseDate());
        rate_text_view.setText(details.getVoteAverage() + "/10");
        Picasso.with(getView().getContext()).load("http://image.tmdb.org/t/p/w342/" + details.getPosterPath()).into(poster_view);
        if (mFavList.contains(mMovieID)) {
            fav_text_view.setText("Fav");
        } else {
            fav_text_view.setText("Not fav");
        }

        fav_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("arrayList", "" + mFavList);
                if (!mFavList.contains(mMovieID)) {
                    fav_text_view.setText("Fav");
                    if (mydb.insertFavorite(mMovieDetail.getId().toString(), mMovieDetail.getTitle(), mMovieDetail.getPosterPath(), mMovieDetail.getOverview(), mMovieDetail.getReleaseDate(), mMovieDetail.getVoteAverage())) {
                        Toast.makeText(getActivity().getApplicationContext(), "Favorited", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    fav_text_view.setText("unFav");
                    mydb.removeFavorite(mMovieID);
                    Log.i("REmove Fav", mydb.getAllFavorites() + "");
                    Toast.makeText(getActivity().getApplicationContext(), "Removed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void populateTrailers(Trailers trailers) {
        // Didnt include QuickTime because https://www.themoviedb.org/talk/5322d1fcc3a36828ba0035d5
        List<Youtube> youtube = trailers.getYoutube();
        for (int i = 0; i < youtube.size(); i++) {
            Youtube trailer = youtube.get(i);
            trailerView.addView(createTrailer(trailer));
        }
    }

    public View createTrailer(final Youtube youtube) {
        View trailerView = getActivity().getLayoutInflater().inflate(R.layout.trailers_listview, null);
        final TextView title = (TextView) trailerView.findViewById(R.id.trailer_title);
        title.setText(youtube.getName());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtube.getSource())));
            }
        });
        return trailerView;
    }

    private void populateReviews(Reviews reviews) {
        List<ResultPages> firstReview = reviews.getResults();
        //set up onclick
        // Only show first review
        if (!reviews.getResults().isEmpty()) {
            review_author.setText(firstReview.get(0).getAuthor());
            review_text.setText(firstReview.get(0).getContent());
        }
    }

    private void updateFav(String movieID) {
        //title, poster, synopsis, user rating, release date
        String movieSummary = null;
        String movieTitle = null;
        String movieRating = null;
        String releaseDate = null;

        mCursor = mydb.getFavorite(movieID);
        if (mCursor != null && mCursor.moveToFirst()) {
            movieTitle = mCursor.getString(mCursor.getColumnIndex(DBHelper.FAVORITES_COLUMN_MOVIE_TITLE));
            movieSummary = mCursor.getString(mCursor.getColumnIndex(DBHelper.FAVORITES_COLUMN_SUMMARY));
            movieRating = mCursor.getString(mCursor.getColumnIndex(DBHelper.FAVORITES_COLUMN_RATE));
            releaseDate = mCursor.getString(mCursor.getColumnIndex(DBHelper.FAVORITES_COLUMN_POSTER_RELEASE));
        }
        // getActivity().setTitle(movieTitle);
        summary_text_view.setText(movieSummary);
        rate_text_view.setText(movieRating);
        release_text_view.setText(releaseDate);
        poster_view.setBackgroundResource(R.drawable.ic_action_placeholder);
        poster_view.setMinimumHeight(350);
        poster_view.setMinimumWidth(250);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, mMovieDetail.getTitle());
        share.putExtra(Intent.EXTRA_TEXT, "http://www.youtube.com/watch?v=" + mMovieDetail.getTrailers().getYoutube().get(0).getSource());

        startActivity(Intent.createChooser(share, "Share Movie Trailer!"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_share) {
            shareTextUrl();
        }

        return super.onOptionsItemSelected(item);
    }
}
