package com.example.android.bakingapp;

/**
 * Created by SG on 7/13/2018.
 */


import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)

public class MainActivityScreenTest {



    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void clickRecyclerViewItem_OpensRecipeActivity() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // gridview item and clicks it.
     //   onData(anything()).inAdapterView(withId(R.id.recylerView)).atPosition(0).perform(click());


        onView(ViewMatchers.withId(R.id.recylerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));



        onView(withId(R.id.RecipeNameDetail)).check(matches(withText("Nutella Pie")));





    }


    @Test
    public void clickRecyclerViewItem_OpensRecipeActivity2() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific


        onView(ViewMatchers.withId(R.id.recylerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));


        onView(withId(R.id.RecipeNameDetail)).check(matches(withText("Brownies")));

    }



    @Test
    public void clickRecyclerViewItem_OpensRecipeActivity3() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific

        onView(ViewMatchers.withId(R.id.recylerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


        onView(withId(R.id.RecipeNameDetail)).check(matches(withText("Yellow Cake")));

    }





    @Test
    public void clickRecyclerViewItem_OpensRecipeActivity4() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific

        onView(ViewMatchers.withId(R.id.recylerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));


        onView(withId(R.id.RecipeNameDetail)).check(matches(withText("Cheesecake")));

    }





}

