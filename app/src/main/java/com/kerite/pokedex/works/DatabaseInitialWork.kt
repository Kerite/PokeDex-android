package com.kerite.pokedex.works

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kerite.pokedex.R
import com.kerite.pokedex.database.PokemonDatabase

class DatabaseInitialWork(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {
    companion object {
        val CHANNEL_ID = "NOTIFICATION_INIT"
    }

    override fun doWork(): Result {
        Log.d("DatabaseInitWork", "Initializing database")
        return try {
            val db = PokemonDatabase.getInstance(applicationContext)
            val pokemonDao = db.pokemonDao()
            Toast.makeText(
                applicationContext,
                R.string.notification_title_init_database,
                Toast.LENGTH_SHORT
            ).show()
            Result.success()
        } catch (e: java.lang.Exception) {
            Result.failure()
        }
//        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
//            .setContentTitle(applicationContext.getString(R.string.notification_title_init_database))
//        NotificationManagerCompat.from(applicationContext).notify(1, notificationBuilder.build())
    }
}