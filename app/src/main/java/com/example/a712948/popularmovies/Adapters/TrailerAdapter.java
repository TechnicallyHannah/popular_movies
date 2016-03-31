package com.example.a712948.popularmovies.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.a712948.popularmovies.POJO.Youtube;
import com.example.a712948.popularmovies.R;

import java.util.List;

/**
 * @author Hannah Paulson
 * @since 3/24/16.
 */
public class TrailerAdapter extends BaseAdapter {

    private Context context;
    private List<Youtube> moviesList;


    public TrailerAdapter(Context context, List<Youtube> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }
    @Override
    public int getCount() {
        Log.i("Count",moviesList.size()+"");
        return moviesList.size();
    }
    @Override
    public Object getItem(int pos) {
        return moviesList.get(pos);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.trailers_listview, parent, false);
        }
        Log.i("Inside Adapter",moviesList.get(position)+"");

        TextView tv = (TextView) view.findViewById(R.id.trailer_title);
        tv.setText(moviesList.get(position).getName());
        return view;
    }

}
