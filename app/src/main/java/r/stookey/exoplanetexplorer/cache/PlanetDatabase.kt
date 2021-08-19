package r.stookey.exoplanetexplorer.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import r.stookey.exoplanetexplorer.domain.Planet
import timber.log.Timber

@Database(entities = [Planet::class], version = 2)
abstract class PlanetDatabase: RoomDatabase() {

    abstract fun planetDao(): PlanetDao

    companion object{
        @Volatile private var instance: PlanetDatabase? = null

        fun getInstance(context: Context): PlanetDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

    private fun buildDatabase(context: Context): PlanetDatabase {
        return Room.databaseBuilder(context, PlanetDatabase::class.java, "planets-db")
            .addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //Do work when db is created
                        Timber.i("planets-db created ", db.attachedDbs)
                    }
                }
            ).fallbackToDestructiveMigration()
            .build()
    }
    }

}