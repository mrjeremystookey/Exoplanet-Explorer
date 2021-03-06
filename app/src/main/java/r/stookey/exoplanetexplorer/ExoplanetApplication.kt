package r.stookey.exoplanetexplorer

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.github.mikephil.charting.utils.Utils
import dagger.hilt.android.HiltAndroidApp
import r.stookey.exoplanetexplorer.cache.CacheUpdateWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltAndroidApp
class ExoplanetApplication: Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory
    @Inject lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.d("Timber tree planted")
        Utils.init(applicationContext)
        //Run on startup and then every 7 days to check for new planets
        periodicBackgroundWork()
    }



    //Syncs planet list on startup and then every week
    private fun periodicBackgroundWork(){
        Timber.d("startup planet sync")
        val planetSyncWorkRequest = PeriodicWorkRequestBuilder<CacheUpdateWorker>(7, TimeUnit.DAYS)
            .addTag("STARTUP_PLANET_SYNC")
            .setInitialDelay(90, TimeUnit.SECONDS)
            .build()
        workManager.enqueue(planetSyncWorkRequest)
    }


    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .setMinimumLoggingLevel(if (BuildConfig.DEBUG) android.util.Log.DEBUG else android.util.Log.ERROR)
        .build()
}
