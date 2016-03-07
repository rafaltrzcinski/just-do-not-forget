package pl.trzcinskiraf.justdonotforget

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*

object TestUtils {

    fun pressButton(id: Int) {
        onView(withId(id)).perform(click());
    }

    fun isComponentDisplayed(id: Int) {
        onView(withId(id)).check(matches(isDisplayed()))
    }

    fun isInRecyclerView(recyclerViewId: Int, itemId: Int) {
        onView(withId(recyclerViewId)).check(matches(hasDescendant(withId(itemId))))
    }
}