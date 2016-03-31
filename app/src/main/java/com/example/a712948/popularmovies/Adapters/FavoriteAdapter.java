package com.example.a712948.popularmovies.Adapters;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.InjectView;
import com.example.a712948.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @author Hannah Paulson
 * @since 3/31/16.
 */
public class FavoriteAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<String> moviesList;
    private ArrayList<String> titleList;
    @InjectView(R.id.movie_poster)
    ImageView poster_view;


    public FavoriteAdapter(Activity context, ArrayList moviesList, ArrayList titles) {
        super(context, 0, moviesList);
        this.context = context;
        this.moviesList = moviesList;
        this.titleList = titles;
    }

    public View getView(int position, View view, ViewGroup parent) {
        String poster = null;
        String title = null;
        if (moviesList.get(position) != null) {
            poster = moviesList.get(position);
        }
        if (titleList.get(position) != null) {
            title = titleList.get(position);
        }
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_movie_grid, parent, false);
        }
        ImageView poster_view = (ImageView) view.findViewById(R.id.image_holder);

        if (!isNetworkAvailable()) {
            Log.i("Party", "Party");
            TextView offline_text = (TextView) view.findViewById(R.id.offlineText);
            poster_view.setBackgroundResource(R.drawable.ic_action_placeholder);
            offline_text.setVisibility(View.VISIBLE);
            offline_text.setText(title);
        } else {
            Picasso.with(context).load("http://image.tmdb.org/t/p/w342/" + poster).into(poster_view);
            return view;
        }
        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManagers
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManagers.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
