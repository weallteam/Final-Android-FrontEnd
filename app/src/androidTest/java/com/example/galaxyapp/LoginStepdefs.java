package com.example.galaxyapp;

import android.app.Activity;
import android.content.Intent;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.galaxyapp.Activity.HomepageActivity;

import org.junit.Rule;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;

public class LoginStepdefs {

    @Rule
    public ActivityTestRule<HomepageActivity> LoginTestRule = new ActivityTestRule<>(HomepageActivity.class);

    private Activity LoginActivity;

    @Before("@login-feature")
    public void setup() {
        LoginTestRule.launchActivity(new Intent());
        LoginActivity = LoginTestRule.getActivity();
    }

    @After("@login-feature")
    public void tearDown() {

        LoginTestRule.finishActivity();
    }


    @cucumber.api.java.en.Given("^I am on the sign in screen$")
    public void iAmOnTheSignInScreen() {
        assertNotNull(LoginActivity);

    }


    @cucumber.api.java.en.When("^ I enter username <signUser>$")
    public void iEnterEmail(String username) {
        onView(ViewMatchers.withId(R.id.signupUsername)).perform(typeText(username));
        closeSoftKeyboard();

    }

    @cucumber.api.java.en.And("^I click on the sign in button$")
    public void iClickOnTheSignInButton() {
        onView(ViewMatchers.withId(R.id.signupbtnnext1)).perform(click());

    }

    @Then("^I receive a field required message$")
    public void iReceiveAFieldRequiredMessage() {
        onView(withText("Password is required")).check(matches(isDisplayed()));

    }

    @cucumber.api.java.en.And("^I enter password <password>$")
    public void iEnterPasswordPassword(String password) {
        onView(ViewMatchers.withId(R.id.signupPassword)).perform(typeText(password));
        closeSoftKeyboard();

    }

    @Then("^I receive an invalid login message$")
    public void iReceiveAnInvalidLoginMessage() {
        onView(withText("Error!!Invalid Login, Try Again")).check(matches(isDisplayed()));

    }

    @Then("^I am redirected to the dashboard$")
    public void iamOndashboard() {
        onView(withText("Welcome to dashboard")).check(matches(isDisplayed()));

    }


    @Then("^I receive a field required message$")
    public void IReceiveAFieldRequiredMessage() {
        onView(withText("Password is required")).check(matches(isDisplayed()));
    }

    @Then("^I receive an invalid login message$")
    public void IReceiveAnInvalidLoginMessage() {
        onView(withText("Invalid login. Try again!")).check(matches(isDisplayed()));
    }
}

