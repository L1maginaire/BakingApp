package com.example.guest.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.MainListAdapter;
import com.example.guest.bakingapp.base.BaseFragment;
import com.example.guest.bakingapp.data.Repository;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.di.components.DaggerBakingComponent;
import com.example.guest.bakingapp.di.modules.BakingModule;
import com.example.guest.bakingapp.mvp.presenters.MainPresenter;
import com.example.guest.bakingapp.mvp.view.MainView;
import com.example.guest.bakingapp.utils.SimpleIdlingResource;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;
import static com.example.guest.bakingapp.utils.NetworkChecker.isNetAvailable;

/**
 * Created by l1maginaire on 4/26/18.
 */

public class MainFragment extends BaseFragment implements MainView {
    private static final String TAG = MainFragment.class.getSimpleName();
    private SimpleIdlingResource idlingResource;

    @Inject
    protected MainPresenter presenter;
    @BindView(R.id.main_recycler)
    protected RecyclerView recyclerView;
    @BindView(R.id.error_layout_frame)
    protected FrameLayout errorLayout;
    @BindView(R.id.btn_repeat)
    protected Button repeatButton;

    private MainListAdapter adapter;
    private Callbacks callbacks;
    Unbinder unbinder;

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    public void setIdleResource(SimpleIdlingResource idleResource){
        this.idlingResource = idleResource;
    }

    public void setFab(FloatingActionButton fab, int position) {
        adapter.setFab(fab, position);
    }

    public void notifyItemSetChanged(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callbacks = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement onItemClicked()");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, v);
        setupAdapter();
        loadNew();
        return v;
    }

    private void setupAdapter() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MainListAdapter(getActivity(), callbacks);
        recyclerView.setAdapter(adapter);
    }

    private void loadNew() {
        if (isNetAvailable(getActivity())) {
            presenter.getRecieps(idlingResource);
            errorLayout.setVisibility(View.INVISIBLE);
        } else {
            errorLayout.setVisibility(VISIBLE);
            repeatButton.setOnClickListener(v -> loadNew());
        }
    }

    @Override
    public void onRecipesLoaded(List<RecipeRemote> recipeRemotes) {
        Log.d(TAG, "Loaded " + recipeRemotes.size() + " elements.");
        Repository.get().setRecipes(recipeRemotes);
        onClearItems();
        adapter.addRecieps(recipeRemotes);
    }

    @Override
    public void onClearItems() {
        adapter.clearItems();
    }

    protected void resolveDaggerDependencies() {
        DaggerBakingComponent.builder()
                .applicationComponent(getApplicationComponent(getActivity()))
                .bakingModule(new BakingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsibscibe();
        adapter.unsubscibe();
        unbinder.unbind();
    }

    public interface Callbacks {
        void onItemClicked(int recipeId, int position);
    }
}
