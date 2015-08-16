package com.example.a712948.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.TextView;


public class MovieDetail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieDetailFragment())
                    .commit();
        }
    }

    public static class MovieDetailFragment extends Fragment {
        private String LOG = " LOG";

        public MovieDetailFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                String movieDetails = intent.getStringExtra(intent.EXTRA_TEXT);
                ((TextView) rootView.findViewById(R.id.detail_text)).setText(movieDetails);
            }

            return rootView;
        }
    }
}
