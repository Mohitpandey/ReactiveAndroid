package com.mohit.reactiveandroid;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.util.async.Async;

public class FeedParser {

    public Observable<Movies> getMovies(final String json) {
        return Async.start(() -> parseJson(json), Schedulers.io());
    }

    private Movies parseJson(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, Movies.class);
    }


}
