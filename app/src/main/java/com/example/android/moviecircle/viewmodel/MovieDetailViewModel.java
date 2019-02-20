package com.example.android.moviecircle.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.moviecircle.MovieRepository;
import com.example.android.moviecircle.Resource;
import com.example.android.moviecircle.model.Credit;
import com.example.android.moviecircle.model.SingleMovie;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MovieDetailViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Resource> singleMovieMutableLiveData;
    private MutableLiveData<Resource> castsMutableLiveData;

    public MovieDetailViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<Resource> getMovieLiveData(int movieId){
        if(singleMovieMutableLiveData == null){
            singleMovieMutableLiveData = new MutableLiveData<>();
            fetchMovieById(movieId);
        }
        return singleMovieMutableLiveData;
    }

    public MutableLiveData<Resource> getCastsLiveData(int movieId){
        if(castsMutableLiveData==null){
            castsMutableLiveData = new MutableLiveData<>();
            fetchCastsById(movieId);
        }
        return castsMutableLiveData;
    }

    private void fetchCastsById(int movieId) {
        Disposable disposable = movieRepository.getCastsById(movieId)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        castsMutableLiveData.setValue(Resource.loading(null));
                    }
                })
                .subscribe(new Consumer<Credit>() {
                    @Override
                    public void accept(Credit credit) throws Exception {
                        List<Credit.Cast> casts = credit.getCast();
                        castsMutableLiveData.setValue(Resource.success(casts));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        castsMutableLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void fetchMovieById(int movieId){
        Disposable disposable = movieRepository.getMovieById(movieId)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        singleMovieMutableLiveData.setValue(Resource.loading(null));
                    }
                })
                .subscribe(new Consumer<SingleMovie>() {
                    @Override
                    public void accept(SingleMovie singleMovie) throws Exception {
                        singleMovieMutableLiveData.setValue(Resource.success(singleMovie));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        singleMovieMutableLiveData.setValue(Resource.error(throwable.getMessage(),null));
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
