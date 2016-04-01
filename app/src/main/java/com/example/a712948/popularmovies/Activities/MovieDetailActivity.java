package com.example.a712948.popularmovies.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_fav).setVisible(false);
        menu.findItem(R.id.action_pop).setVisible(false);
        menu.findItem(R.id.action_toprate).setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_share) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

}
