package com.example.a712948.popularmovies;

import android.app.Activity;
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
    public MovieTrailerAdapter(Activity context, List<MovieTrailer> movies) {
        super(context, 0, movies);
        Log.i("Tag", " in the adapter");
    }

    public View getView(int position, View view, ViewGroup parent) {
//        MovieTrailer trailer = getItem(position);
//        String trailers = null;
//        Context context = getContext();
//
//        if (trailer.trailerName != null) {
//            trailers = trailer.trailerName;
//        }
//        if (view == null) {
//            // This a new view we inflate the new layout
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.fragment_trailer_list, parent, false);
//        }
//        Log.i("Tag", " in the adapter");
//        //TextView tv = (TextView) view.findViewById(R.id.TrailerTextView);
//        //tv.setText(trailers);

        return null;
    }
}


