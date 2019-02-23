package com.example.android.moviecircle;


import com.example.android.moviecircle.internet.NetworkRequest;

import io.reactivex.Observable;

public class MovieListRepository {
    NetworkRequest networkRequest;

    public MovieListRepository(NetworkRequest networkRequest) {
        this.networkRequest = networkRequest;
    }

    public Observable<PopularMovie> getPopularMovieList(){
        Observable<PopularMovie> fromInternet = getMovieListFromInternet();
        return fromInternet;
    }

    public Observable<PopularMovie> getMovieListFromCache(){
        return null;
    }

    public Observable<PopularMovie> getMovieListFromInternet(){
        return networkRequest.getPopularMovieList();
    }

}
