package com.studiofive.recipeapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studiofive.recipeapp.Constants;
import com.studiofive.recipeapp.R;
import com.studiofive.recipeapp.network.EdamamClient;
import com.studiofive.recipeapp.network.EdamamRecipesSearchResponse;
import com.studiofive.recipeapp.network.EdamamService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//implementing a changing view interface
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   // binding all views in main activity
    @BindView(R.id.findRecipesButton)
    Button mFindRecipesButton;
    @BindView(R.id.appTitle)
    TextView mAppTitle;
    @BindView(R.id.search_bar)
    EditText mSearchBar;
    @BindView(R.id.videoBackground)
    VideoView mVideoBackground;
    @BindView(R.id.savedRecipesButton)
    Button mSavedRecipesButton;


    private AwesomeValidation awesomeValidation;


    private DatabaseReference mSearchedRecipeReference;
    private ValueEventListener mSearchedRecipeReferenceListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchedRecipeReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_RECIPE);//pinpoints recipe node

        mSearchedRecipeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    String recipe = recipeSnapshot.getValue().toString();
                    Log.d("Recipes updated", "recipe: " + recipe); //log
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding views
        ButterKnife.bind(this);



        //Setting a video background
        String path ="android.resource://com.studiofive.recipeapp/"+ R.raw.cookie;
        Uri u = Uri.parse(path);
        mVideoBackground.setVideoURI(u);
        mVideoBackground.start();

        //Setting a media player for the video
        mVideoBackground.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        //setting a click listener
        mFindRecipesButton.setOnClickListener(this);

        //initialize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //add validation for search item
        awesomeValidation.addValidation(this, R.id.search_bar, RegexTemplate.NOT_EMPTY, R.string.invalid_search);


        mSavedRecipesButton.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        //check validation
        if (awesomeValidation.validate()){
            //on success
            Toast.makeText(MainActivity.this, "Submission successful!!!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(MainActivity.this, "Seems you're feeling lucky today!!", Toast.LENGTH_LONG).show();
        }
        if (v == mFindRecipesButton) {
            //switching to recipes when button is pressed
            String recipe = mSearchBar.getText().toString();

            saveRecipeToFirebase(recipe);


            Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        }

        if (v == mSavedRecipesButton) {
            Intent intent = new Intent(MainActivity.this, SavedRecipeListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    //Override functions to enable optimum video play
    @Override
    protected void onResume(){
        mVideoBackground.resume();
        super.onResume();
    }

    @Override
    protected void onPause(){
        mVideoBackground.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        mVideoBackground.stopPlayback();
        super.onDestroy();
        mSearchedRecipeReference.removeEventListener(mSearchedRecipeReferenceListener);
    }

    public void saveRecipeToFirebase(String recipe) {
        mSearchedRecipeReference.push().setValue(recipe);
    }



}
