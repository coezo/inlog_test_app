package br.com.inlogtest.testapp

import android.app.Application
import br.com.inlogtest.testapp.api.Service
import br.com.inlogtest.testapp.api.TmdbInterceptor
import br.com.inlogtest.testapp.model.Configuration
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class InlogApplication : Application() {

    private lateinit var service: Service

    lateinit var configuration: Configuration

    override fun onCreate() {
        super.onCreate()

        service = getRetrofit().create(Service::class.java)
    }

    private fun getServerUrl(): String = BuildConfig.SERVER_URL

    private fun getRetrofit(): Retrofit {
        val url = getServerUrl()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val cache = Cache(applicationContext.cacheDir, CACHE_SIZE)

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor (TmdbInterceptor())
            .cache(cache)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(url)
            .client(httpClient)
            .build()

    }

    fun getService() = service

    companion object{
        const val CACHE_SIZE = 10L * 1024 * 1024
    }
}