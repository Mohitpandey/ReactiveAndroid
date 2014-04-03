package com.mohit.reactiveandroid;

import android.util.Log;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.OkHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

import dagger.Module;
import dagger.Provides;
import rx.util.async.Async;

import javax.inject.Inject;
import javax.inject.Singleton;

public class NetworkManager {

    private OkHttpClient client = new OkHttpClient();

    private static final String TAG = "r3ader";

    public NetworkManager() {
    }

    public Observable<String> getData(final String url) {

        /*
        .onErrorReturn(e->{
            return "{movies:[]}";
        });
         */
        return  Async.start(()->getContent(url),Schedulers.io());
//        return Observable.create((Observer<? super String> observer) ->{
//                try {
//                    Log.d("r3ader", "downloading on "+Thread.currentThread().toString());
//                    String data = getContent(url);
//                    Log.d("r3ader", "done downloading on "+Thread.currentThread().toString());
//                    observer.onNext(data);
//                    observer.onCompleted();
//                } catch (Exception e) {
//                    observer.onError(e);
//                }
//
//                return Subscriptions.empty();
//        })
//        .subscribeOn(Schedulers.io());
    }

    public String getContent(String urlString) {
        Log.e(TAG,"calling "+urlString);
        try {
            HttpURLConnection connection = client.open(new URL(urlString));
            byte [] bytes = readData(connection.getInputStream(), connection);
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            Log.e(TAG,"What!! be specific in exception handling",e);
            return "{movies:[]}";
        }
    }

    private byte[] readData(InputStream inputStream, HttpURLConnection connection) throws IOException {
        ByteArrayOutputStream outputStream = null;

        try {
            byte[] data = new byte[0];
            final int contentLength = connection.getContentLength();
            if (contentLength > 0) {
                Log.d(TAG,"length "+contentLength);
                outputStream = new ByteArrayOutputStream(contentLength);
            } else {
                outputStream = new ByteArrayOutputStream();
            }
            IOUtils.copy(inputStream, outputStream);

            data = outputStream.toByteArray();
            return data;
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
}
