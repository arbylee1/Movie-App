package com.gitgood.buzzmovie;

/**
 * Created by Albert on 3/3/2016.
 */
public interface Callback<T> {
    void onSuccess(T result);
    void onFailure(T result);
}
