package com.example.a712948.popularmovies.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import butterknife.ButterKnife;
import com.example.a712948.popularmovies.Fragments.MovieDetailFragment;
import com.example.a712948.popularmovies.R;


public class MovieDetailActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_fragment, new MovieDetailFragment()).commit();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

}
