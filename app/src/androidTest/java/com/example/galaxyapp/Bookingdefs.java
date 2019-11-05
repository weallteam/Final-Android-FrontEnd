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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;

;


public class Bookingdefs {

    private ActivityTestRule<HomepageActivity> signUpTestRule = new ActivityTestRule<>(HomepageActivity.class);
    private Activity signUpActivity;

    @Before("@booking-feature")
    public void setup() {
        signUpTestRule.launchActivity(new Intent());
        signUpActivity = signUpTestRule.getActivity();
    }

    @After("@booking-feature")
    public void tearDown() {
        signUpTestRule.finishActivity();
    }

    @cucumber.api.java.en.Given("^I am on the dashboard screen$")
    public void iAmOnTheSignUpScreen() {
        assertNotNull(signUpActivity);
    }

    @cucumber.api.java.en.When("^I enter book service title (\\S+)$")
    public void iInputservicetitle(String servicetitle) {
        onView(ViewMatchers.withId(R.id.bookserviceTitle)).perform(typeText(servicetitle));
        closeSoftKeyboard();

    }

    @cucumber.api.java.en.When("^I select service image <image>$")
    public void iaddserviceimage(String image) {
        onView(ViewMatchers.withId(R.id.bookserviceimage)).perform(typeText(image));
        closeSoftKeyboard();

    }

    @cucumber.api.java.en.And("^I click on the book button$")
    public void iClickOnTheGetStartedButton() {
        onView(ViewMatchers.withId(R.id.btnbooktheService)).perform(click());
    }

    @Then("^The service is booked$")
    public void iShouldSeeTheLoginScreen() {
        onView(ViewMatchers.withId(com.example.galaxyapp.R.id.loginbtnCreateAccount)).check(matches(ViewMatchers.withText(R.string.hello_blank_fragment)));
    }

    @Then("^I should see an error on the (\\S+)$")
    public void iShouldSeeAnErrorOnTheView() {

    }

    @Then("^I receive an invalid login message$")
    public void IReceiveAnInvalidLoginMessage() {
        onView(withText("Invalid booking. Try again!")).check(matches(isDisplayed()));
    }
}
