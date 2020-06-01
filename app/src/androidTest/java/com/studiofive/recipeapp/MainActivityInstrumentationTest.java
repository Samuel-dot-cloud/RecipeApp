package com.studiofive.recipeapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.rule.ActivityTestRule;

import ui.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateEditText(){
        onView(withId(R.id.search_bar)).perform(typeText("Flatbread")).check(matches(withText("Flatbread")));
    }

    @Test
    public void SearchIsSentToRecipesActivity(){
        String recipe = "Pizza";
        onView(withId(R.id.search_bar)).perform(typeText(recipe)).perform(closeSoftKeyboard());
        try {
            Thread.sleep(250);
        } catch (InterruptedException e){
            System.out.println("got interrupted");
        }
        onView(withId(R.id.findRecipesButton)).perform(click());
        onView(withId(R.id.displaySearch)).check(matches(withText("Recipes found associated with search item:" + recipe)));
    }
}
