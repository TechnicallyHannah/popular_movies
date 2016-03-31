package com.example.a712948.popularmovies.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import butterknife.ButterKnife;
import com.example.a712948.popularmovies.Fragments.MovieFragment;
import com.example.a712948.popularmovies.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new MovieFragment()).commit();
    }

}