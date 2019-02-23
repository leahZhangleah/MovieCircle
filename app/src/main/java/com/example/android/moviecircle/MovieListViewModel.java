package com.example.android.moviecircle;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.moviecircle.moviedetail.Resource;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MovieListViewModel extends ViewModel {
    MovieListRepository movieListRepository;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Resource> movieListMutableLiveData;
    public static final String POPULAR_MOVIE_TYPE = "popular";
    public static final String LATEST_MOVIE_TYPE = "latest";
    public static final String NOW_PLAYING_MOVIE_TYPE = "now_playing";
    public static final String TOP_RATED_MOVIE_TYPE = "top_rated";
    public static final String UPCOMING_MOVIE_TYPE = "upcoming";

    public MovieListViewModel(MovieListRepository movieListRepository) {
        this.movieListRepository = movieListRepository;
        compositeDisposable = new CompositeDisposable();

    }

    public MutableLiveData<Resource> getMovieListLiveData(String movieType){
        if(movieListMutableLiveData == null){
            movieListMutableLiveData = new MutableLiveData<>();
            fetchMovieList(movieType);
        }
        return movieListMutableLiveData;
    }

    private void fetchMovieList(String movieType) {
        if(movieType == POPULAR_MOVIE_TYPE){
            Disposable disposable = movieListRepository.getPopularMovieList()
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            movieListMutableLiveData.setValue(Resource.loading(null));
                        }
                    })
                    .subscribe(new Consumer<PopularMovie>() {
                        @Override
                        public void accept(PopularMovie popularMovie) throws Exception {
                            movieListMutableLiveData.setValue(Resource.success(popularMovie));
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            movieListMutableLiveData.setValue(Resource.error(throwable.getMessage(),null));
                        }
                    });
            compositeDisposable.add(disposable);
        }

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
