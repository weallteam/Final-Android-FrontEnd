package com.example.galaxyapp;

import android.app.Activity;
import android.content.Intent;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.galaxyapp.Activity.HomepageActivity;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static junit.framework.TestCase.assertNotNull;

;

public class Searchdefs {

    private ActivityTestRule<HomepageActivity> signUpTestRule = new ActivityTestRule<>(HomepageActivity.class);
    private Activity signUpActivity;

    @Before("@search-feature")
    public void setup() {
        signUpTestRule.launchActivity(new Intent());
        signUpActivity = signUpTestRule.getActivity();
    }

    @After("@search-feature")
    public void tearDown() {
        signUpTestRule.finishActivity();
    }

    @cucumber.api.java.en.Given("^I am on the dashboard screen$")
    public void iAmOnTheSignUpScreen() {
        assertNotNull(signUpActivity);
    }

    @cucumber.api.java.en.When("^I enter anything (\\S+)$")
    public void iInputFirstNameFirstName(String username) {
        onView(ViewMatchers.withId(com.example.galaxyapp.R.id.signupUsername)).perform(typeText(username));
        closeSoftKeyboard();

    }

    @cucumber.api.java.en.And("^I click on the search button$")
    public void iClickOnTheGetStartedButton() {
        onView(ViewMatchers.withId(com.example.galaxyapp.R.id.signupbtnnext1)).perform(click());
    }


    @Then("^I should see the login screen$")
    public void iShouldSeeTheLoginScreen() {
        onView(ViewMatchers.withId(com.example.galaxyapp.R.id.loginbtnCreateAccount)).check(matches(ViewMatchers.withText(R.string.hello_blank_fragment)));
    }

    @Then("^I should see an error on the (\\S+)$")
    public void iShouldSeeAnErrorOnTheView() {

    }


}
