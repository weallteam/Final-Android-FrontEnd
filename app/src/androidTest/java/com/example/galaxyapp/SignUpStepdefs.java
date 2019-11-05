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

public class SignUpStepdefs {

    private ActivityTestRule<HomepageActivity> signUpTestRule = new ActivityTestRule<>(HomepageActivity.class);
    private Activity signUpActivity;

    @Before("@register-feature")
    public void setup() {
        signUpTestRule.launchActivity(new Intent());
        signUpActivity = signUpTestRule.getActivity();
    }

    @After("@register-feature")
    public void tearDown() {
        signUpTestRule.finishActivity();
    }

    @cucumber.api.java.en.Given("^I am on the sign up screen$")
    public void iAmOnTheSignUpScreen() {
        assertNotNull(signUpActivity);
    }

    @cucumber.api.java.en.When("^I input username (\\S+)$")
    public void iInputFirstNameFirstName(String username) {
        onView(ViewMatchers.withId(com.example.galaxyapp.R.id.signupUsername)).perform(typeText(username));
        closeSoftKeyboard();

    }

    @cucumber.api.java.en.And("^I input password (\\S+)$")
    public void iInputPasswordPassword(String password){
        onView(ViewMatchers.withId(com.example.galaxyapp.R.id.signupPassword)).perform(typeText(password));

    }

    @cucumber.api.java.en.And("^I input rePassword (\\S+)$")
    public void iInputConfirmPasswordConfirmPassword(String repassword) {
        onView(ViewMatchers.withId(com.example.galaxyapp.R.id.signuprePassword)).perform(typeText(repassword));
        closeSoftKeyboard();

    }

    @cucumber.api.java.en.And("^I click on the register button$")
    public void iClickOnTheGetStartedButton() {
        onView(ViewMatchers.withId(com.example.galaxyapp.R.id.signupbtnnext1)).perform(click());
    }

    @Then("^I should see the login screen$")
    public void iShouldSeeTheLoginScreen() {
        onView(ViewMatchers.withId(com.example.galaxyapp.R.id.loginbtnCreateAccount)).check(matches(ViewMatchers.withText(R.string.hello_blank_fragment)));
    }

    @Then("^I should see auth error$")
    public void iShouldSeeAnErrorOnTheView() {

    }
}