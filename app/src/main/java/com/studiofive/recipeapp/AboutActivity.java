package com.studiofive.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {
//@BindView(R.id.menuBar)
//BottomNavigationView mMenuBar;
@BindView(R.id.aboutUs)
    TextView mAboutUs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);


//        //setting about selected with nav bar
//        mMenuBar.setSelectedItemId(R.id.about);
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
//                        startActivity(new Intent(getApplicationContext()
//                                ,RecipeActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.about:
//                        return true;
//                }
//                return false;
//            }
//        });
        Intent intent = getIntent();
    }
}
