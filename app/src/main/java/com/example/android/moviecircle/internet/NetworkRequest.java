package com.example.android.moviecircle.internet;

import android.content.Context;

import com.example.android.moviecircle.R;
import com.example.android.moviecircle.model.SingleMovie;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NetworkRequest {
    private NetworkInterface networkInterface;
    private Context context;
    private String API_KEY,LANGUAGE;

    public NetworkRequest(NetworkInterface networkInterface, Context context) {
        this.networkInterface = networkInterface;
        this.context = context;
        API_KEY = context.getResources().getString(R.string.api_key);
        LANGUAGE = context.getResources().getString(R.string.language);
    }

    public Observable<SingleMovie> getMovieById(int movieId){
        return networkInterface.getMovieById(movieId,API_KEY,LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
