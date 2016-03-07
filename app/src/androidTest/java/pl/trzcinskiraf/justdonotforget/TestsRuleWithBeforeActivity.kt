package pl.trzcinskiraf.justdonotforget

import android.support.test.rule.ActivityTestRule

inline fun <reified T : JustDoNotForgetActivity> rule(crossinline beforeActivityFunction: () -> Unit): ActivityTestRule<T> {
    return object : ActivityTestRule<T>(T::class.java) {
        override fun beforeActivityLaunched() = beforeActivityFunction()
    }
}

inline fun <reified T : JustDoNotForgetActivity> ruleManuallyStarted(crossinline beforeActivityFunction: () -> Unit): ActivityTestRule<T> {
    return object : ActivityTestRule<T>(T::class.java, false, false) {
        override fun beforeActivityLaunched() = beforeActivityFunction()
    }
}