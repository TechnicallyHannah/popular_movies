package com.example.a712948.popularmovies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 */
public class MovieFragment extends Fragment {

    private ArrayAdapter<String> mMovieAdapter;

    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] data = {
                "Happy Gilmore", "Clueless", "Class of 92", "The Martian"
        };
        List<String> movieList = new ArrayList<>(Arrays.asList(data));

        mMovieAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_movie, R.id.list_item_movie_view, movieList);

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listview_movies);
        listView.setAdapter(mMovieAdapter);
        return view;
    }
}
