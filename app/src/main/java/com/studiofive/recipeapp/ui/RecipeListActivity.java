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

//import com.studiofive.recipeapp.adapters.RecipeListAdapter;
import com.studiofive.recipeapp.adapters.RecipesListAdapter;
import com.studiofive.recipeapp.models.Hit;
import com.studiofive.recipeapp.models.Recipes;
import com.studiofive.recipeapp.network.EdamamApi;
import com.studiofive.recipeapp.network.EdamamClient;
import com.studiofive.recipeapp.network.EdamamRecipesSearchResponse;
import com.studiofive.recipeapp.network.EdamamService;

import com.studiofive.recipeapp.R;

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
//        @BindView(R.id.menuBar)
//    BottomNavigationView mMenuBar;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private RecipesListAdapter mAdapter;

    public String recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        mFindAboutButton.setOnClickListener(this);

        Intent intent = getIntent();
        final String recipe = intent.getStringExtra("recipe");

        EdamamApi edamamApi = EdamamClient.getClient();
Call<EdamamRecipesSearchResponse> call = edamamApi.getRecipe(recipe,"cea6bb57", "3e7b470f74e1d48bb3d122a8bfc500ed", "0", "100", "521-577", "alcohol-free");


                call.enqueue(new Callback<EdamamRecipesSearchResponse>() {
                                 @Override
                                 public void onResponse(Call<EdamamRecipesSearchResponse> call, Response<EdamamRecipesSearchResponse> response) {
                                     hideProgressBar();
                                     if (response.isSuccessful()){
                                         recipes = response.body().getQ();
                                         mAdapter = new RecipesListAdapter(RecipeListActivity.this, recipes);
                                   mRecyclerView.setAdapter(mAdapter);
                                 RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipeListActivity.this);
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
        Toast.makeText(RecipeListActivity.this, "Function works", Toast.LENGTH_SHORT).show();
        mRecyclerView.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.VISIBLE);
        mDisplaySearch.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }


}
