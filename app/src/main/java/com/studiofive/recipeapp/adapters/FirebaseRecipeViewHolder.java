package com.studiofive.recipeapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.studiofive.recipeapp.Constants;
import com.studiofive.recipeapp.R;
import com.studiofive.recipeapp.models.Recipe;
import com.studiofive.recipeapp.ui.RecipeDetailsActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseRecipeViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindRecipe(Recipe recipe){
        ImageView recipeImageView = (ImageView) mView.findViewById(R.id.recipeImageView);
        TextView recipeTextView = (TextView) mView.findViewById(R.id.recipeTextView);
        TextView calorieTextView = (TextView) mView.findViewById(R.id.calorieTextView);
        TextView totalTextView = (TextView) mView.findViewById(R.id.totalTimeTextView);

        Picasso.get().load(recipe.getImage()).into(recipeImageView);

        calorieTextView.setText("Total calories:" + recipe.getCalories() + " kcal");
        totalTextView.setText("Preparation time:" + recipe.getTotalTime() + " minutes");
        recipeTextView.setText(recipe.getLabel());
    }

    @Override
    public void onClick(View v) {
        final ArrayList<Recipe> recipes = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_RECIPES);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    recipes.add(snapshot.getValue(Recipe.class));
                }
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("recipe", Parcels.wrap(recipes));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
