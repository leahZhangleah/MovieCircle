package com.example.android.moviecircle;

import com.example.android.moviecircle.internet.NetworkRequest;
import com.example.android.moviecircle.model.SingleMovie;

import io.reactivex.Observable;

public class MovieRepository {

    NetworkRequest networkRequest;

    public MovieRepository(NetworkRequest networkRequest) {
        this.networkRequest = networkRequest;
    }

    public Observable<SingleMovie> getMovieById(int movieId){
        Observable<SingleMovie> fromCache = getMovieByIdFromCache(movieId);
        Observable<SingleMovie> fromInternet = getMovieByIdFromInternet(movieId);
        //Maybe<SingleMovie> singleMovie = Observable.concat(fromCache,fromInternet).firstElement();
        return fromInternet;
    }

    public Observable<SingleMovie> getMovieByIdFromCache(int movieId){
        //todo
        return null;
    }

    public Observable<SingleMovie> getMovieByIdFromInternet(int movieId){
        return networkRequest.getMovieById(movieId);
    }

    /*
    public Observable<Credits> getCastsById(int movieId) {
        Observable<Credits> fromCache = getCastsByIdFromCache(movieId);
        Observable<Credits> fromInternet = getCastsByIdFromInternet(movieId);
        //Maybe<SingleMovie> singleMovie = Observable.concat(fromCache,fromInternet).firstElement();
        return fromInternet;
    }

    public Observable<Credits> getCastsByIdFromCache(int movieId){
        //todo
        return null;
    }

    public  Observable<Credits> getCastsByIdFromInternet(int movieId){
        return networkRequest.getCastsById(movieId);
    }*/

}
