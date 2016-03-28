package com.example.a712948.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    private final String MOVIE_TITLE = "MOVIE_TITLE";
    private final String MOVIE_REL = "MOVIE_REL";
    private final String MOVIE_SUM = "MOVIE_SUM";
    private final String MOVIE_RATE = "MOVIE_RATE";
    private final String MOVIE_POSTER = "MOVIE_POSTER";

    public MovieDetailFragment() {
    }

    @InjectView(R.id.movie_title_text)
    TextView title_text_view;
    @InjectView(R.id.movie_summary_text)
    TextView summary_text_view;
    @InjectView(R.id.movie_release_date)
    TextView release_text_view;
    @InjectView(R.id.movie_rate_text)
    TextView rate_text_view;
    @InjectView(R.id.movie_poster)
    ImageView poster_view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setRetainInstance(true);

        View view = inflater.inflate(R.layout.fragment_movie_detail, null);
        ButterKnife.inject(this, view);






        Intent intent = getActivity().getIntent();
        String poster = intent.getStringExtra(MOVIE_POSTER);
        String movie_title = intent.getStringExtra(MOVIE_TITLE);
        String movie_summary = intent.getStringExtra(MOVIE_SUM);
        String movie_release = intent.getStringExtra(MOVIE_REL);
        String movie_rate = intent.getStringExtra(MOVIE_RATE);
        title_text_view.setText(movie_title);
        summary_text_view.setText(movie_summary);
        release_text_view.setText(movie_release);
        rate_text_view.setText(movie_rate);

        Picasso.with(view.getContext()).load("http://image.tmdb.org/t/p/w185/" + poster).into(poster_view);

        return view;
    }
}