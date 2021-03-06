package com.example.guest.bakingapp.di.components;

/**
 * Created by l1maginaire on 4/14/18.
 */

import com.example.guest.bakingapp.di.modules.BakingModule;
import com.example.guest.bakingapp.di.scope.PerFragment;
import com.example.guest.bakingapp.ui.MainFragment;

import dagger.Component;

@PerFragment
@Component(modules = BakingModule.class, dependencies = ApplicationComponent.class)
public interface BakingComponent {
    void inject(MainFragment fragment);
}