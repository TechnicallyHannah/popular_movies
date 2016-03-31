package com.example.a712948.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.a712948.popularmovies.POJO.MovieDetail;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavoriteActivityFragment extends Fragment {

    public FavoriteActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    private void populateDetails(final MovieDetail details) {


    }
}
