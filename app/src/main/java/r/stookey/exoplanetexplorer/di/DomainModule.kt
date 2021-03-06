package r.stookey.exoplanetexplorer.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import r.stookey.exoplanetexplorer.util.DataSetUtil
import r.stookey.exoplanetexplorer.util.PlanetDto
import r.stookey.exoplanetexplorer.util.PlanetDtoImpl
import timber.log.Timber
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        Timber.d("Moshi injected")
        return Moshi.Builder().build()
    }

    @Singleton
    @Provides
    fun providesDomainDtoImpl(): PlanetDto {
        Timber.d("PlanetDto injected")
        return PlanetDtoImpl(provideMoshi())
    }

    @Singleton
    @Provides
    fun providesDataSetUtils(): DataSetUtil{
        Timber.d("DataSetUtil injected")
        return DataSetUtil()
    }
}