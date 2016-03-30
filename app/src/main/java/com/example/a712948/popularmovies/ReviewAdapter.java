package com.example.a712948.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.a712948.popularmovies.POJO.ResultPages;

import java.util.List;

/**
 * @author Hannah Paulson
 * @since 3/28/16.
 */
public class ReviewAdapter extends BaseAdapter{

    private Context context;
    private List<ResultPages> reviewList;


    public ReviewAdapter(Context context, List<ResultPages> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }
    @Override
    public int getCount() {
        Log.i("Review Count", reviewList.size() + "");
        return reviewList.size();
    }
    @Override
    public Object getItem(int pos) {
        return reviewList.get(pos);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {


//        if (view == null) {
//            view = LayoutInflater.from(context).inflate(R.layout.review_listview, parent, false);
//        }
//        Log.i("Inside Review Adapter",reviewList.get(position).author+"");
//
//        TextView tv = (TextView) view.findViewById(R.id.review_author_text);
//        tv.setText(reviewList.get(position).author);
        return view;
    }
}
