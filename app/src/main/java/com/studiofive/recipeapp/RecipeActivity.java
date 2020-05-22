package com.studiofive.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.findAboutButton)
    Button mFindAboutButton;
    @BindView(R.id.displaySearch)
    TextView mDisplaySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        mFindAboutButton.setOnClickListener(this);


        Intent intent = getIntent();
    }

    @Override
    public void onClick(View v){
        if(v == mFindAboutButton){
            Intent intent = new Intent(RecipeActivity.this, AboutActivity.class);
            startActivity(intent);
        }
    }
}
