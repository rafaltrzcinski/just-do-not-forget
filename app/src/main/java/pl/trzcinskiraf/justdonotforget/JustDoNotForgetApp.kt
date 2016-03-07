package pl.trzcinskiraf.justdonotforget

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class JustDoNotForgetApp : Application() {

    companion object {
        lateinit var applicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        Companion.applicationContext = applicationContext
        val config = RealmConfiguration.Builder(this).build()
        Realm.setDefaultConfiguration(config)
    }
}