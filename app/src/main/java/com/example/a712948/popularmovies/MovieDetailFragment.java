package com.example.a712948.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.example.a712948.popularmovies.POJO.*;
import com.example.a712948.popularmovies.rest.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {
    private final String MOVIE_ID = "MOVIEID";
    List<Genre> mGenres;
    List<Youtube> YTTrailers;
    Trailers mTrailers;
    Reviews mReviews;
    MovieDetail mMovieDetail;


    public MovieDetailFragment() {
    }

    @InjectView(R.id.movie_summary_text)
    TextView summary_text_view;
    @InjectView(R.id.movie_release_date)
    TextView release_text_view;
    @InjectView(R.id.movie_rate_text)
    TextView rate_text_view;
    @InjectView(R.id.movie_poster)
    ImageView poster_view;
    ViewGroup trailerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setRetainInstance(true);

        View view = inflater.inflate(R.layout.fragment_movie_detail, null);
        ButterKnife.inject(this, view);
        Intent intent = getActivity().getIntent();
        String movieID = intent.getStringExtra(MOVIE_ID);
        getDetails(movieID);
        trailerView = (ViewGroup) view.findViewById(R.id.trailer_contailer);

        //summary_text_view.setText(mMovieDetail.getOverview());

        if (mMovieDetail != null) {
            Log.i("MovieDetailOnePojo", "" + mMovieDetail);
        }
        return view;
    }


    private void getDetails(String mMovieID) {
        RestClient.get().getDetails(mMovieID, new Callback<MovieDetail>() {
            @Override
            public void success(MovieDetail detail, Response response) {
                Log.i("InsideCallBack", "" + detail);
                getActivity().setTitle(detail.getTitle());
                mMovieDetail = detail;
                mGenres = detail.getGenres();
                mTrailers = detail.getTrailers();
                mReviews = detail.getReviews();

                populateDetails(mMovieDetail);
                populateTrailers(mTrailers);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Tag", " Error : " + error);
            }
        });
    }


    private void populateDetails(MovieDetail details) {
        summary_text_view.setText(details.getOverview());
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
        TextView title = (TextView) trailerView.findViewById(R.id.trailer_title);
        title.setText(youtube.getName());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+youtube.getSource())));
            }
        });
        return trailerView;
    }
}