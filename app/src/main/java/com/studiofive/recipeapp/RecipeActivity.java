package com.studiofive.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {
//    @BindView(R.id.findAboutButton)
//    Button mFindAboutButton;
    @BindView(R.id.displaySearch)
    TextView mDisplaySearch;
    @BindView(R.id.menuBar)
    BottomNavigationView mMenuBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

//        mFindAboutButton.setOnClickListener(this);


        //setting recipes selected with nav bar
        mMenuBar.setSelectedItemId(R.id.recipes);

        //Perform ItemSelectedListener
        mMenuBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (mMenuBar.getSelectedItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.recipes:
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext()
                                ,AboutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        Intent intent = getIntent();
    }

//    @Override
//    public void onClick(View v){
//        if(v == mFindAboutButton){
//            Intent intent = new Intent(RecipeActivity.this, AboutActivity.class);
//            startActivity(intent);
//        }
//    }
}
