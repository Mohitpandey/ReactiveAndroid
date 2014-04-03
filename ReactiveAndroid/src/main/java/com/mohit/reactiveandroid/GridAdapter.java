package com.mohit.reactiveandroid;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private static final NetworkManager networkManager = new NetworkManager();
    private static final String TAG = "r3ader";

    private List<Movie> movies = new ArrayList<Movie>();

    public GridAdapter(Context context,String source) {
        this.context = context;
        networkManager.getData(source)
        .flatMap(data -> new FeedParser().getMovies(data))
        .observeOn(AndroidSchedulers.mainThread())
        .onErrorFlatMap(e->Observable.empty())
        .subscribe((Movies m) -> {
            movies.clear();
            movies.addAll(m.movies);
            notifyDataSetChanged();
        });
    }

    /*
    .flatMap(new Func1<String, Observable<Movies>>() {
        @Override
        public Observable<Movies> call(String data) {
            return new FeedParser().getMovies(data);
        }
    })
    */
//    public void setAdapterSource(String url) {
//        Log.e(TAG,"Why is this getting called");
//        networkManager.getData(url)
//                .flatMap(data -> new FeedParser().getMovies(data))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((Movies m) -> {
//                    movies.clear();
//                    movies.addAll(m.movies);
//                    notifyDataSetChanged();
//                });
//    }

    public int getCount() {
        return movies.size();
    }

    public Movie getItem(int position) {
        return movies.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (convertView == null) {
            gridView = inflater.inflate(R.layout.item, null);
        } else {
            gridView = convertView;
        }

        Movie movie = getItem(position);
        TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
        ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
        Picasso.with(context).load(movie.getPosters().getProfile()).into(imageView);
        textView.setText(movie.getTitle());

        return gridView;
    }
}