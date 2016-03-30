package com.example.a712948.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.example.a712948.popularmovies.POJO.*;
import com.example.a712948.popularmovies.rest.RestClient;
import com.squareup.picasso.Picasso;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {
    //Todo make loading screen
    private final String MOVIE_ID = "MOVIEID";
    public static final String PREFS_NAME = "FAV_PREFS";
    public static final String FAVORITES = "Movie_Favorite";
    int id_to_ = 0;

    List<Genre> mGenres;
    Trailers mTrailers;
    String mMovieID;
    Reviews mReviews;
    MovieDetail mMovieDetail;
    private DBHelper mydb;
    Cursor mCursor;

    public MovieDetailFragment() {
        super();
    }

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_movie_detail, null);
        ButterKnife.inject(this, view);
        Intent intent = getActivity().getIntent();
        mydb = new DBHelper(getActivity());
        mMovieID = intent.getStringExtra(MOVIE_ID);
        if (mydb.numberOfRows() != 0) {
            mCursor = mydb.getData(Integer.parseInt(mMovieID));
        }
        Log.i("DB", mydb.getDatabaseName());
        getDetails(mMovieID);
        trailerView = (ViewGroup) view.findViewById(R.id.trailer_container);
        reviewView = (ViewGroup) view.findViewById(R.id.review_container);
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
        summary_text_view.setText(details.getOverview());
        release_text_view.setText(details.getReleaseDate());
        rate_text_view.setText(details.getVoteAverage() + "/10");
        Picasso.with(getView().getContext()).load("http://image.tmdb.org/t/p/w342/" + details.getPosterPath()).into(poster_view);
        fav_text_view.setText("Not Fav");
        fav_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fav_text_view.setText("Fav");
                if (mydb.insertFavorites(mMovieDetail.getId().toString(), mMovieDetail.getPosterPath())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Favorited", Toast.LENGTH_SHORT).show();
                }
                Log.i("Rows in DB", mydb.numberOfRows() + "");
                Log.i("DETAILS in DB", mydb.getAllFavorites() + "");

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
}