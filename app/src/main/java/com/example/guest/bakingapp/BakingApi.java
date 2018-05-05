package com.example.guest.bakingapp;

import com.example.guest.bakingapp.mvp.model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by l1maginaire on 4/14/18.
 */

public interface BakingApi {
    @GET("baking.json")
    Observable<List<Recipe>> getRecieps();
}
