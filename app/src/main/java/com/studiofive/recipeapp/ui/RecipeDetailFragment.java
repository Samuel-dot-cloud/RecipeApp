package com.studiofive.recipeapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.studiofive.recipeapp.Constants;
import com.studiofive.recipeapp.R;

import com.studiofive.recipeapp.models.Ingredient;
import com.studiofive.recipeapp.models.Recipe;
import com.studiofive.recipeapp.models.Recipes;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailFragment extends Fragment implements View.OnClickListener{
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    @BindView(R.id.recipeImageView)
    ImageView mRecipeImageView;
    @BindView(R.id.fullTextView)
    TextView mFullTextView;
    @BindView(R.id.shareTextView)
    TextView mShareTextView;
    @BindView(R.id.edamamTextView)
    TextView mEdamamTextView;
    @BindView(R.id.recipeTextView)
    TextView mRecipeTextView;
    @BindView(R.id.ingredientTextView)
    TextView mIngredientTextView;
    @BindView(R.id.saveRecipeButton)
    Button mSaveRecipeButton;

    private Recipe mRecipe;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment RecipeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipe", Parcels.wrap(recipe));
        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipe = Parcels.unwrap(getArguments().getParcelable("recipe"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.get()
                .load(mRecipe.getImage())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mRecipeImageView);


        mRecipeTextView.setText(mRecipe.getLabel());
        mIngredientTextView.setText(mRecipe.getIngredientLines().toString());


        mShareTextView.setOnClickListener(this);
        mFullTextView.setOnClickListener(this);
        mEdamamTextView.setOnClickListener(this);
        mSaveRecipeButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mFullTextView){
            Intent fullIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mRecipe.getUrl()));
            startActivity(fullIntent);
        }

        if (v == mEdamamTextView){
            Intent edamamIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( mRecipe.getUri()));
            startActivity(edamamIntent);
        }

        if (v == mShareTextView){
            Intent shareIntent = new Intent(Intent.ACTION_SEND, Uri.parse(mRecipe.getShareAs()));
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
        }

        if (v == mSaveRecipeButton){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference recipeRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_RECIPES)
                    .child(uid);

            DatabaseReference pushRef = recipeRef.push();
            String pushId = pushRef.getKey();
            mRecipe.setPushId(pushId);
            pushRef.setValue(mRecipe);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}