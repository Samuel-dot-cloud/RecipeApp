package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.studiofive.recipeapp.EdamamService;
import adapters.MyRecipesArrayAdapter;
import com.studiofive.recipeapp.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = RecipeActivity.class.getSimpleName();
    @BindView(R.id.findAboutButton)
    Button mFindAboutButton;
    @BindView(R.id.displaySearch)
    TextView mDisplaySearch;
    //    @BindView(R.id.menuBar)
//    BottomNavigationView mMenuBar;
    @BindView(R.id.listView)
    ListView mListView;

    private String[] foods = new String[]{"Flatbread", "Chips", "Fish", "Pork", "Coffee", "Rice", "Burgers", "Chicken", "Cake", "Hotdog", "Barbeque", "Pizza", "Omelet", "Sausage", "Croissant", "Guacamole"};
    private String[] base = new String[]{"Flour", "Plant", "Meat", "Meat", "Drink", "Plant", "Plant and meat", "Meat", "Flour", "Flour,plant and meat", "Meat", "Flour, plant and meat", "Egg", "Meat", "Flour", "Plant"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);


        MyRecipesArrayAdapter adapter = new MyRecipesArrayAdapter(this, android.R.layout.simple_list_item_1, foods, base);
        mListView.setAdapter(adapter);

        //Setting a toaster to display recipe and base
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String restaurant = ((TextView) view).getText().toString();
                Toast.makeText(RecipeActivity.this, restaurant, Toast.LENGTH_SHORT).show();
            }
        });

//        mFindAboutButton.setOnClickListener(this);


//        //setting recipes selected with nav bar
//        mMenuBar.setSelectedItemId(R.id.recipes);
//
//        //Perform ItemSelectedListener
//        mMenuBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (mMenuBar.getSelectedItemId()){
//                    case R.id.nav_home:
//                        startActivity(new Intent(getApplicationContext()
//                                ,MainActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.recipes:
//                        return true;
//                    case R.id.about:
//                        startActivity(new Intent(getApplicationContext()
//                                ,AboutActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
//                return false;
//            }
//        });

        //setting a click listener
        mFindAboutButton.setOnClickListener(this);

        Intent intent = getIntent();
        String recipe = intent.getStringExtra("recipe");
        mDisplaySearch.setText("Recipes found associated with search item:" + recipe);

        getRecipes(recipe);
    }

    @Override
    public void onClick(View v) {
        if (v == mFindAboutButton) {
            Intent intent = new Intent(RecipeActivity.this, AboutActivity.class);
            startActivity(intent);
        }
    }

    private void getRecipes(String recipe) {
        final EdamamService edamamService = new EdamamService();
        edamamService.findRecipes(recipe, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
