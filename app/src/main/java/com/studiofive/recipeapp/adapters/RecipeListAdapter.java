package com.studiofive.recipeapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.studiofive.recipeapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.studiofive.recipeapp.models.Hit;
import com.studiofive.recipeapp.models.Recipe;

public class RecipeListAdapter  extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private List mRecipes;
    private Context mContext;

    public RecipeListAdapter(Context context, ArrayList<Hit> recipes){
        mContext = context;
        mRecipes = Collections.singletonList(recipes);
    }

    @NonNull
    @Override
    public RecipeListAdapter.RecipeViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( RecipeListAdapter.RecipeViewHolder holder, int position) {
        holder.bindRecipe((Recipe) mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recipeImageView)
        ImageView mRecipeImageView;
        @BindView(R.id.recipeTextView)
        TextView mRecipeTextView;
        @BindView(R.id.calorieTextView)
        TextView mCalorieTextView;
        @BindView(R.id.preparationTextView)
        TextView mPreparationTextView;
        private Context mContext;

        public RecipeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindRecipe(Recipe recipe){
            mCalorieTextView.setText("Total calories:" + recipe.getCalories() + "kcal");
            mPreparationTextView.setText("Preparation time:" + recipe.getTotalTime() + "hours");
            mRecipeTextView.setText(recipe.getLabel());
            Picasso.get().load(recipe.getImage()).into(mRecipeImageView);
        }

    }
}
