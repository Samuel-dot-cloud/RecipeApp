package com.studiofive.recipeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.studiofive.recipeapp.Constants;
import com.studiofive.recipeapp.adapters.RecipesListAdapter;
import com.studiofive.recipeapp.models.Hit;
import com.studiofive.recipeapp.models.Recipe;
import com.studiofive.recipeapp.models.Recipes;
import com.studiofive.recipeapp.network.EdamamApi;
import com.studiofive.recipeapp.network.EdamamClient;
import com.studiofive.recipeapp.network.EdamamRecipesSearchResponse;
import com.studiofive.recipeapp.network.EdamamService;

import com.studiofive.recipeapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipeListActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = RecipeListActivity.class.getSimpleName();
    @BindView(R.id.findAboutButton)
    Button mFindAboutButton;
    @BindView(R.id.displaySearch)
    TextView mDisplaySearch;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private RecipesListAdapter mAdapter;

    public List<Hit> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        mFindAboutButton.setOnClickListener(this);

        Intent intent = getIntent();
        final String recipe = intent.getStringExtra("recipe");

        EdamamApi edamamApi = EdamamClient.getClient();
retrofit2.Call<EdamamRecipesSearchResponse> call = edamamApi.getRecipe(recipe, Constants.EDAMAM_APP_ID, Constants.EDAMAM_APP_KEY, Constants.FROM, Constants.TO, Constants.CALORIES, Constants.HEALTH);


                call.enqueue(new Callback<EdamamRecipesSearchResponse>() {
                                 @Override
                                 public void onResponse(Call<EdamamRecipesSearchResponse> call, Response<EdamamRecipesSearchResponse> response) {
                                     hideProgressBar();
                                     if (response.isSuccessful()){
                                         recipes = response.body().getHits();
                                         mAdapter = new RecipesListAdapter(getApplicationContext(), recipes);
                                   mRecyclerView.setAdapter(mAdapter);
                                 RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                mRecyclerView.setLayoutManager(layoutManager);
                               mRecyclerView.setHasFixedSize(true);
                Log.d(TAG, "show");
                showRecipes();
            } else {
                showUnsuccessfulMessage();
            }
                                     }

                                 @Override
                                 public void onFailure(Call<EdamamRecipesSearchResponse> call, Throwable t) {
                                     hideProgressBar();
                                     showFailureMessage();

                                 }
                             });

    }

    @Override
    public void onClick(View v) {
        if (v == mFindAboutButton) {
            Intent intent = new Intent(RecipeListActivity.this, AboutActivity.class);
            startActivity(intent);
        }
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showRecipes() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.VISIBLE);
        mDisplaySearch.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }


}
