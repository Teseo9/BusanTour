package blog.naver.com.hmetal7.busantour.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import blog.naver.com.hmetal7.busantour.Constants.DATASTORE_SETTINGS
import blog.naver.com.hmetal7.busantour.Constants.URL_BASE
import blog.naver.com.hmetal7.busantour.data.api.BusanTourApi
import blog.naver.com.hmetal7.busantour.data.db.BusanInfoDatabase
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()

        return Retrofit.Builder()
            .addConverterFactory(TikXmlConverterFactory.create(parser))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(URL_BASE)
            .build()
    }

    @Singleton
    @Provides
    fun busanTourApi(retrofit: Retrofit): BusanTourApi {
        return retrofit.create(BusanTourApi::class.java)
    }

    @Singleton
    @Provides
    fun busanInfoDatabase(@ApplicationContext context: Context): BusanInfoDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            BusanInfoDatabase::class.java,
            "busanInfo"
        ).build()

    @Singleton
    @Provides
    fun datastoreSettings(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DATASTORE_SETTINGS) }
        )
}