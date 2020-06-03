package com.studiofive.recipeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.studiofive.recipeapp.adapters.RecipeListAdapter;
import com.studiofive.recipeapp.adapters.RecipeListAdapter;
import com.studiofive.recipeapp.models.Hit;
import com.studiofive.recipeapp.models.Recipe;
import com.studiofive.recipeapp.models.Recipes;
import com.studiofive.recipeapp.network.EdamamApi;
import com.studiofive.recipeapp.network.EdamamClient;
import com.studiofive.recipeapp.network.EdamamRecipesSearchResponse;
import com.studiofive.recipeapp.network.EdamamService;

import com.studiofive.recipeapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = RecipeListActivity.class.getSimpleName();
    @BindView(R.id.findAboutButton)
    Button mFindAboutButton;
    @BindView(R.id.displaySearch)
    TextView mDisplaySearch;
    //    @BindView(R.id.menuBar)
//    BottomNavigationView mMenuBar;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private RecipeListAdapter mAdapter;

    public List<Hit> hits;
    public List<Hit> recipes;
//    public List Recipes;

//    private String[] foods = new String[]{"Flatbread", "Chips", "Fish", "Pork", "Coffee", "Rice", "Burgers", "Chicken", "Cake", "Hotdog", "Barbeque", "Pizza", "Omelet", "Sausage", "Croissant", "Guacamole"};
//    private String[] base = new String[]{"Flour", "Plant", "Meat", "Meat", "Drink", "Plant", "Plant and meat", "Meat", "Flour", "Flour,plant and meat", "Meat", "Flour, plant and meat", "Egg", "Meat", "Flour", "Plant"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);


//        MyRecipesArrayAdapter adapter = new MyRecipesArrayAdapter(this, android.R.layout.simple_list_item_1, foods, base);
//        mListView.setAdapter(adapter);
//
//        //Setting a toaster to display recipe and base
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String restaurant = ((TextView) view).getText().toString();
//                Toast.makeText(RecipeActivity.this, restaurant, Toast.LENGTH_SHORT).show();
//            }
//        });

//        mFindAboutButton.setOnClickListener(this);


        //setting a click listener
        mFindAboutButton.setOnClickListener(this);

        Intent intent = getIntent();
        final String recipe = intent.getStringExtra("recipe");


//        getRecipes(recipe);

        EdamamService.EdamamInstance().getQ
                (recipe, "cea6bb57", "3e7b470f74e1d48bb3d122a8bfc500ed", "0", "100", "521-577", "alcohol-free")
                .enqueue(new Callback<EdamamRecipesSearchResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<EdamamRecipesSearchResponse> call, Response<EdamamRecipesSearchResponse> response) {
                        hideProgressBar();
//
                if (response.isSuccessful()) {
                    Log.d(TAG, "Response is successful");
                    if (response.body() !=null) {
                        recipes = response.body().getHits();

                    }

                    mAdapter = new RecipeListAdapter(RecipeListActivity.this, recipes);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipeListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showRestaurants();
                } else {
                    showUnsuccessfulMessage();
                }

                    }

                    @Override
                    public void onFailure(Call<EdamamRecipesSearchResponse> call, Throwable t) {
                        hideProgressBar();
                showFailureMessage();
                Log.e(TAG, "failure", t);

                    }
                });
//        call.enqueue(new retrofit2.Callback<EdamamRecipesSearchResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<EdamamRecipesSearchResponse> call, retrofit2.Response<EdamamRecipesSearchResponse> response) {
//                hideProgressBar();
//
//                if (response.isSuccessful()) {
//                    recipes = response.body().getQ();
//                    mAdapter = new RecipeListAdapter(RecipeListActivity.this, recipes);
//                    mRecyclerView.setAdapter(mAdapter);
//                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipeListActivity.this);
//                    mRecyclerView.setLayoutManager(layoutManager);
//                    mRecyclerView.setHasFixedSize(true);
//
//                    showRestaurants();
//                } else {
//                    showUnsuccessfulMessage();
//                }
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<EdamamRecipesSearchResponse> call, Throwable t) {
//                hideProgressBar();
//                showFailureMessage();
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        if (v == mFindAboutButton) {
            Intent intent = new Intent(RecipeListActivity.this, AboutActivity.class);
            startActivity(intent);
        }
    }

//    private void getRecipes(final String recipe) {
//
//        final EdamamService edamamService = new EdamamService();
//        edamamService.findRecipes(recipe, new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                recipes = edamamService.processResults(response);
//
////                try {
////                    String jsonData = response.body().string();
////                    Log.v(TAG, jsonData);
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//
//                RecipeListActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String[] recipeNames = new String[recipes.size()];
//                        for (int i = 0; i < recipeNames.length; i++){
//                            recipeNames[i] = recipes.get(i).getLabel();
//                        }
//                        ArrayAdapter adapter = new ArrayAdapter(RecipeListActivity.this, android.R.layout.simple_list_item_1, recipeNames);
//                        mListView.setAdapter(adapter);
//                        for (Hit recipe : recipes) {
//                            Log.d(TAG, "Label: " + recipe.getLabel());
//                            Log.d(TAG, "source: " + recipe.getSource());
//                            Log.d(TAG, "uri: " + recipe.getUri());
//                            Log.d(TAG, "image: " + recipe.getImage());
//                            Log.d(TAG, "url: " + recipe.getUrl());
//                            Log.d(TAG, "shareas: " +  recipe.getShareAs());
//                            Log.d(TAG, "ingredient: " + recipe.getIngredientLines().toString());
//                            Log.d(TAG, "calories: " + Double.toString(recipe.getCalories()));
//                            Log.d(TAG, "time: " + Double.toString(recipe.getTotalTime()));
//                        }
//                    }
//                });
//
//            }
//        });
//    }
    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showRestaurants() {
        mListView.setVisibility(View.VISIBLE);
        mDisplaySearch.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
