package com.gitgood.buzzmovie.model;

/**
 * Created by Albert on 3/3/2016.
 */
public interface Callback<T> {
    void onSuccess(T result);
}
