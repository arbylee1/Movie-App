package com.gitgood.buzzmovie;

/**
 * Created by Albert on 3/3/2016.
 */
public interface Callback {
    void onSuccess(String result);
    void onFailure(String result);
}
