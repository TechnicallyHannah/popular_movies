package com.example.a712948.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

/**
 * @author Hannah Paulson
 * @since 8/24/15.
 */
public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mData;
    private MovieAdapter mMovie;

    private String[] paths = {
            "L17yufvn9OVLyXYpvtyrFfak.jpg",
            "/7SGGUiTE6oc2fh9MjIk5M00dsQd.jpg",
            "/xxOKDTQbQo7h1h7TyrQIW7u8KcJ.jpg",
            "/5JU9ytZJyR3zmClGmVm9q4Geqbd.jpg",
            "/uXZYawqUsChGSj54wcuBtEdUJbh.jpg",
            "/s5uMY8ooGRZOL0oe4sIvnlTsYQO.jpg",
            "/6PgpI2Uj4s1solkGWaYXP5QgO1I.jpg",
            "/aBBQSC8ZECGn6Wh92gKDOakSC8p.jpg",
            "/t90Y3G8UGQp0f0DrP60wRu9gfrH.jpg",
            "/A7HtCxFe7Ms8H7e7o2zawppbuDT.jpg",
            "/ktyVmIqfoaJ8w0gDSZyjhhOPpD6.jpg",
            "/g23cs30dCMiG4ldaoVNP1ucjs6.jpg",
            "/8x7ej0LnHdKUqilNNJXYOeyB6L9.jpg",
            "/m7Nt4uSkAeeX6jNF3jUp6zHdGwD.jpg",
            "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
            "/coss7RgL0NH6g4fC2s5atvf3dFO.jpg",
            "/3zQvuSAUdC3mrx9vnSEpkFX0968.jpg",
            "/saF3HtAduvrP9ytXDxSnQJP3oqx.jpg",
            "/9gm3lL8JMTTmc3W4BmNMCuRLdL8.jpg",
            "/2i0JH5WqYFqki7WDhUW56Sg0obh.jpg"

    };

    public GridAdapter(Context c, String[] data) {
        mContext = c;
        mData = data;
        // mMovie = data;
        // Log.i("IN ADAPTER", "" + mData[0].toString());
    }

    @Override
    public int getCount() {
        return mData.length;
        //return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
        //return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ImageView imageView;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_movie, parent, false);
        }

        ImageView posterView = (ImageView) view.findViewById(R.id.list_item_movie_view);
        Log.i("Tag", "URL " + mData[position]);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + mData[position]).into(posterView);

        return view;
    }
}
