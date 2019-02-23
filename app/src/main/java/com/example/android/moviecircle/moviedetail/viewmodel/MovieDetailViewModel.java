package com.example.android.moviecircle.moviedetail.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.moviecircle.moviedetail.MovieRepository;
import com.example.android.moviecircle.moviedetail.Resource;
import com.example.android.moviecircle.model.SingleMovie;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MovieDetailViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Resource> singleMovieMutableLiveData;

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

    /*
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
                .subscribe(new Consumer<Credits>() {
                    @Override
                    public void accept(Credits credit) throws Exception {
                        List<Credits.Cast> casts = credit.getCast();
                        castsMutableLiveData.setValue(Resource.success(casts));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        castsMutableLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    }
                });
        compositeDisposable.add(disposable);
    }*/

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
