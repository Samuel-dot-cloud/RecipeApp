package com.studiofive.recipeapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    private AwesomeValidation awesomeValidation;

    //    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;

    private DatabaseReference mSearchedRecipeReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchedRecipeReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_RECIPE);
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

//       mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();

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

//            if(!(location).equals("")) {
//                addToSharedPreferences(location);
//            }
            Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        }
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
    }

    public void saveRecipeToFirebase(String recipe) {
        mSearchedRecipeReference.push().setValue(recipe);
    }

//    private void addToSharedPreferences(String recipe) {
//        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, recipe).apply();
//    }


}
