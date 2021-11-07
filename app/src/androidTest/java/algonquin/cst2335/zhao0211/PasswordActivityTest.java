package algonquin.cst2335.zhao0211;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PasswordActivityTest {

    @Rule
    public ActivityTestRule<PasswordActivity> mActivityTestRule = new ActivityTestRule<>(PasswordActivity.class);

    /**
     * this is generate test code
     */
    @Test
    public void passwordActivityTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.edittext));
        appCompatEditText.perform(click());
        ViewInteraction appCompatEditText2 = onView(withId(R.id.edittext));
        appCompatEditText2.perform(replaceText("123456"), closeSoftKeyboard());
        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textview));
        textView.check(matches(withText("You shall not pass!")));
    }


    /**
     * test missing Uppercase char
     */
    @Test
    public void testFindMissingUppercase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.edittext));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(withId(R.id.edittext));
        appCompatEditText2.perform(replaceText("password123#$*"), closeSoftKeyboard());
        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textview));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * test missing LowerCase char
     */
    @Test
    public void testFindMissingLowerCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.edittext));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(withId(R.id.edittext));
        appCompatEditText2.perform(replaceText("PASSWORD123#$*"), closeSoftKeyboard());
        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textview));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * test missing Number char
     */
    @Test
    public void testFindMissingNumber() {
        ViewInteraction appCompatEditText = onView(withId(R.id.edittext));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(withId(R.id.edittext));
        appCompatEditText2.perform(replaceText("Password#$*"), closeSoftKeyboard());
        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textview));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * test missing Special char
     */
    @Test
    public void testFindMissingSpecial() {
        ViewInteraction appCompatEditText = onView(withId(R.id.edittext));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(withId(R.id.edittext));
        appCompatEditText2.perform(replaceText("Password123"), closeSoftKeyboard());
        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textview));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * test password complexity
     */
    @Test
    public void testComplexity() {
        ViewInteraction appCompatEditText = onView(withId(R.id.edittext));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(withId(R.id.edittext));
        appCompatEditText2.perform(replaceText("Password123#$*"), closeSoftKeyboard());
        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textview));
        textView.check(matches(withText("Your password meets the requirements")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}


