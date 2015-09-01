package com.example.a712948.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * @author Hannah Paulson
 * @since 8/27/15.
 */
public class MovieTrailerAdapter extends ArrayAdapter<MovieTrailer> {
    public MovieTrailerAdapter(Activity context, List<MovieTrailer> trailers) {
        super(context, 0, trailers);
    }

    public View getView(int position, View view, ViewGroup parent) {
        MovieTrailer trailers = getItem(position);
        String trailer = null;
        Context context = getContext();

    //    if (trailers.trailerName != null) {
  //          trailer = trailers.trailerName;
//        }

        Log.i("TRAILER NAME", trailers.trailerName);
     //   TextView trailerView = (TextView) view.findViewById(R.id.trailer_holder);
      //  trailerView.setText(trailers.trailerName);
        return view;
    }
}
