package com.example.a712948.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.a712948.popularmovies.POJO.Youtube;

import java.util.List;

/**
 * @author Hannah Paulson
 * @since 3/24/16.
 */
public class TrailerAdapter extends BaseAdapter {
    private Context mContext;
    private List<Youtube> trailersList;


    public TrailerAdapter(Context context,List<Youtube> trailersList) {
        super();
        this.trailersList = trailersList;
    }

    @Override
    public int getCount() {
        return trailersList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     //   convertView = inflater.inflate(R.layout.trailers_listview, null);

//        Log.i("InsideTrailerAdap", trailersList.get(position).getName() + "");
//        TextView textView = (TextView) convertView.findViewById(R.id.trailer_title);
//        textView.setText(trailersList.get(position).getName());

        Log.i("InsideTrailerAdap", trailersList + "");
        return convertView;
    }

}
