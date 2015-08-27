package com.example.a712948.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Intent intent = getActivity().getIntent();
        String poster = intent.getStringExtra(MOVIE_POSTER);
        String movie_title = intent.getStringExtra(MOVIE_TITLE);
        TextView movieTitle = (TextView) view.findViewById(R.id.movie_title_text);
        movieTitle.setText(movie_title);
        movieTitle.setBackgroundColor(R.color.teal);

        ((TextView) view.findViewById(R.id.movie_summary_text)).setText("Summary : " + intent.getStringExtra(MOVIE_SUM));
        ((TextView) view.findViewById(R.id.movie_release_date)).setText("Release Date : " + intent.getStringExtra(MOVIE_REL));
        ((TextView) view.findViewById(R.id.movie_rate_text)).setText("Rating : " + intent.getStringExtra(MOVIE_RATE));
        ImageView posterView = ((ImageView) view.findViewById(R.id.movie_poster));
        Picasso.with(view.getContext()).load("http://image.tmdb.org/t/p/w342/" + poster).into(posterView);

        return view;
    }
}
