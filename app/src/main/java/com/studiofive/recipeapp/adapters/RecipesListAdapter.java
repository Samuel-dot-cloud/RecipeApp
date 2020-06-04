package com.studiofive.recipeapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.studiofive.recipeapp.R;
import com.studiofive.recipeapp.models.Hit;
import com.studiofive.recipeapp.models.Recipe;
import com.studiofive.recipeapp.models.Recipes;
import com.studiofive.recipeapp.ui.RecipeDetailsActivity;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesListAdapter  extends RecyclerView.Adapter<RecipesListAdapter.RecipeViewHolder> {
    private List<Hit> mRecipes;
    private Context mContext;

    public RecipesListAdapter(Context context, List<Hit> recipes){
        mContext = context;
        mRecipes = recipes;
    }





    @Override
    public RecipesListAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_list_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( RecipesListAdapter.RecipeViewHolder holder, int position) {
       holder.bindRecipe(mRecipes.get(position).getRecipe());
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.recipeImageView)
        ImageView mRecipeImageView;
        @BindView(R.id.recipeTextView)
        TextView mRecipeTextView;
        @BindView(R.id.calorieTextView)
        TextView mCalorieTextView;
        @BindView(R.id.totalTimeTextView)
        TextView mPreparationTextView;
        private Context mContext;

        public RecipeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener((View.OnClickListener) this);
        }

        public void bindRecipe(Recipe recipe){
            mCalorieTextView.setText("Total calories:" + recipe.getCalories() + "kcal");
            mPreparationTextView.setText("Preparation time:" + recipe.getTotalTime() + "hours");
            mRecipeTextView.setText(recipe.getLabel());
            Picasso.get().load(recipe.getImage()).into(mRecipeImageView);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("recipe", Parcels.wrap(mRecipes));
            mContext.startActivity(intent);
        }

    }
}
