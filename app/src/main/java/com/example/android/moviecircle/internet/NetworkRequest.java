package com.example.android.moviecircle.internet;

import android.content.Context;

import com.example.android.moviecircle.PopularMovie;
import com.example.android.moviecircle.R;
import com.example.android.moviecircle.model.SingleMovie;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NetworkRequest {
    private NetworkInterface networkInterface;
    private Context context;
    private String API_KEY,LANGUAGE,APPEND_TO_RESPONSE;

    public NetworkRequest(NetworkInterface networkInterface, Context context) {
        this.networkInterface = networkInterface;
        this.context = context;
        API_KEY = context.getResources().getString(R.string.api_key);
        LANGUAGE = context.getResources().getString(R.string.language);
        APPEND_TO_RESPONSE = context.getResources().getString(R.string.append_to_response);
    }

    public Observable<SingleMovie> getMovieById(int movieId){
        return networkInterface.getMovieById(movieId,API_KEY,LANGUAGE,APPEND_TO_RESPONSE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*
    public  Observable<Credits> getCastsById(int movieId){
        return networkInterface.getCastsById(movieId,API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/
    public Observable<PopularMovie> getPopularMovieList(){
        int page = 1;
        return networkInterface.getPopularMovieList(API_KEY,LANGUAGE,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
