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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.studiofive.recipeapp.models.Hit;
import com.studiofive.recipeapp.models.Recipe;
import com.studiofive.recipeapp.models.Recipes;
import com.studiofive.recipeapp.ui.RecipeDetailsActivity;
import com.studiofive.recipeapp.ui.RecipeListActivity;

import org.parceler.Parcels;

public class RecipeListAdapter  extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private List<Recipes> mRecipes;
    private ArrayList<Hit> mHit;
    private Context mContext;

    public RecipeListAdapter(Context context, List<Recipes> recipes, ArrayList<Hit> hit){
        mContext = context;
        mRecipes = recipes;
        mHit = hit;
    }





    public RecipeListAdapter(RecipeListActivity recipeListActivity, List<Hit> recipes) {
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
        holder.bindRecipe(mRecipes.get(position));
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
        @BindView(R.id.preparationTextView)
        TextView mPreparationTextView;
        private Context mContext;

        public RecipeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener((View.OnClickListener) this);
        }

        public void bindRecipe(Recipes recipe){
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
