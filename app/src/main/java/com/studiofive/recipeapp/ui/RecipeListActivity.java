package com.studiofive.recipeapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studiofive.recipeapp.Constants;
import com.studiofive.recipeapp.R;
import com.studiofive.recipeapp.adapters.RecipesListAdapter;
import com.studiofive.recipeapp.models.Recipe;
import com.studiofive.recipeapp.network.EdamamService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

    public ArrayList<Recipe> recipes;
        private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        mFindAboutButton.setOnClickListener(this);

        Intent intent = getIntent();
        final String recipe = intent.getStringExtra("recipe");
        getRecipes(recipe);

                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentSearch = mSharedPreferences.getString(Constants.PREFERENCES_SEARCH_KEY, null);
        if (mRecentSearch != null) {
            getRecipes(mRecentSearch);
        }
    }

    private void getRecipes(String recipe){
        final EdamamService edamamService = new EdamamService();
        edamamService.findRecipes(recipe, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                hideProgressBar();
                showFailureMessage();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                recipes = edamamService.processResults(response);
                RecipeListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressBar();
                        mAdapter = new RecipesListAdapter(getApplicationContext(), recipes);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipeListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                        showRecipes();
                    }

                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getRecipes(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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


    private void addToSharedPreferences(String recipe) {
        mEditor.putString(Constants.PREFERENCES_SEARCH_KEY, recipe).apply();
    }


}
