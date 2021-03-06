package r.stookey.exoplanetexplorer.cache

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.RequestFuture
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import r.stookey.exoplanetexplorer.network.Urls
import r.stookey.exoplanetexplorer.util.PlanetDtoImpl
import timber.log.Timber


@HiltWorker
class CacheUpdateWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private var queue: RequestQueue,
    private var mapper: PlanetDtoImpl,
    private var dao: PlanetDao): CoroutineWorker(appContext, workerParams)
{
    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                Timber.d("checking for new Planets in the background")
                val future: RequestFuture<JSONArray> = RequestFuture.newFuture()
                val request = JsonArrayRequest(Request.Method.GET,
                    Urls.ALL_PLANETS_URL, null, future, future)
                queue.add(request)
                val newPlanetJSONArray = future.get()
                var numberOfPlanetsAdded = 0
                val planetList = mapper.convertJsonToPlanets(newPlanetJSONArray)
                planetList.forEach { planet ->
                    if (!dao.isPlanetCached(planet.planetName)) {
                        Timber.d("adding planet, " + planet.planetName + " to local Planet Database")
                        dao.insert(planet)
                        numberOfPlanetsAdded++
                    }
                }
                val data = workDataOf("NUMBER_ADDED" to numberOfPlanetsAdded)
                Timber.d("added ${data.keyValueMap} to cache")
                Result.success(data)
            }
        } catch (throwable: Throwable){
            Timber.e("${throwable.printStackTrace()}")
            return Result.failure()
        }

    }




}