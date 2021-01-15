package com.gestash.newsfeed.di

import androidx.lifecycle.ViewModelProvider
import com.gestash.newsfeed.domain.NewsFeedService
import com.gestash.newsfeed.domain.NewsRepository
import com.gestash.newsfeed.ui.article.ArticleViewModel
import com.gestash.newsfeed.ui.dashboard.DashboardViewModel
import com.gestash.newsfeed.ui.home.HomeViewModel
import com.gestash.newsfeed.ui.notifications.NotificationsViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Di {

    private const val OPEN_NEWS_API = "https://newsapi.org/v2/"

    private val viewModelModule = module {
        viewModel { DashboardViewModel() }
        viewModel { HomeViewModel(get(), androidContext()) }
        viewModel { NotificationsViewModel() }
//        viewModel { ArticleViewModel(get()) }
    }

    private val repositoriesModule = module {
        single { NewsRepository(get()) }
//        single { LocationProvider(androidContext()) }
//        single { ConverterData(androidContext()) }
//        single { WeatherIconParser(androidContext()) }
    }

    private val servicesModule = module {
        single {
            val retrofit: Retrofit = get()
            retrofit.create(NewsFeedService::class.java)
        }
    }

    private val dataSourceModule = module {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        single {
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(OPEN_NEWS_API)
                .client(client)
                .build()
        }
    }

    val koinModules = arrayListOf(viewModelModule, repositoriesModule, servicesModule, dataSourceModule)

}