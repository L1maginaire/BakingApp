package com.example.guest.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.base.LikeSyncActivity;
import com.example.guest.bakingapp.base.SingleFragmentActivity;
import com.example.guest.bakingapp.data.remote.pojo.StepRemote;

import java.util.ArrayList;

public class PagerActivity extends SingleFragmentActivity {
    public static final String ID = "steps_id";
    public static final String POSITION = "steps_pos";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_fragment;
    }

    @Override
    protected Fragment getFragment() {
        return PagerFragment.newInstance(getIntent().getIntExtra(ID, 0), getIntent().getIntExtra(POSITION, 0));
    }

    public static Intent newIntent(Context context, int recipeId, int position) {
        Intent intent = new Intent(context, PagerActivity.class);
        intent.putExtra(ID, recipeId);
        intent.putExtra(POSITION, position);
        return intent;
    }
}
