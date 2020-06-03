package com.studiofive.recipeapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import com.studiofive.recipeapp.models.Recipe;
import com.studiofive.recipeapp.ui.RecipeDetailFragment;

public class RecipePagerAdapter extends FragmentPagerAdapter {
    private List<Recipe> mRecipes;

    public RecipePagerAdapter(FragmentManager fm, int behavior, List<Recipe> recipes){
        super(fm, behavior);
        mRecipes = recipes;
    }
    @NonNull
    @Override
    public RecipeDetailFragment getItem(int position) {
        return RecipeDetailFragment.newInstance(mRecipes.get(position));
    }

    @Override
    public int getCount() {
        return mRecipes.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRecipes.get(position).getLabel();
    }
}
