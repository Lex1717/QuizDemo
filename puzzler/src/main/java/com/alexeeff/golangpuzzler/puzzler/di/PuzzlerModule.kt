package com.alexeeff.golangpuzzler.puzzler.di

import android.content.Context
import com.alexeeff.golangpuzzler.config.presentation.ConfigurationFacade
import com.alexeeff.golangpuzzler.core.cicerone.LocalCiceroneHolder
import com.alexeeff.golangpuzzler.core.di.DomainModule
import com.alexeeff.golangpuzzler.core.di.FeatureScope
import com.alexeeff.golangpuzzler.core.interfaces.NetworkManager
import com.alexeeff.golangpuzzler.core.interfaces.RealmWrapper
import com.alexeeff.golangpuzzler.puzzler.BuildConfig
import com.alexeeff.golangpuzzler.puzzler.data.CourseRepositoryImpl
import com.alexeeff.golangpuzzler.puzzler.data.api.BASE
import com.alexeeff.golangpuzzler.puzzler.data.api.CourseApiService
import com.alexeeff.golangpuzzler.puzzler.data.api.FakeInterceptor
import com.alexeeff.golangpuzzler.puzzler.data.provider.CourseProvider
import com.alexeeff.golangpuzzler.puzzler.data.provider.CourseProviderImpl
import com.alexeeff.golangpuzzler.puzzler.data.storage.CourseStorage
import com.alexeeff.golangpuzzler.puzzler.data.storage.CourseStorageImpl
import com.alexeeff.golangpuzzler.puzzler.data.storage.PuzzlerRealmWrapper
import com.alexeeff.golangpuzzler.puzzler.domain.CourseRepository
import com.alexeeff.golangpuzzler.puzzler.domain.interactor.CheckCardAnswerInteractor
import com.alexeeff.golangpuzzler.puzzler.domain.interactor.GetCourseInteractor
import com.alexeeff.golangpuzzler.puzzler.domain.interactor.GetCourseListInteractor
import com.alexeeff.golangpuzzler.puzzler.domain.interactor.SaveAnswerInteractor
import com.alexeeff.golangpuzzler.puzzler.presentation.model.CardStateController
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.terrakok.cicerone.Router
import javax.inject.Named

/**
 * @author Yaroslav Alexeev
 */
@Module
class PuzzlerModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    fun provideRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder().setLenient()
        val myGson = gsonBuilder.create()

        return GsonConverterFactory.create(myGson)
    }

    @Provides
    fun provideHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            context: Context
    ): OkHttpClient {

        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpClient
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(FakeInterceptor(context))
        }

        return httpClient.build()
    }

    @Provides
    @FeatureScope
    fun provideCourseApiService(
            callAdapterFactory: RxJava2CallAdapterFactory,
            converterFactory: GsonConverterFactory,
            okHttpClient: OkHttpClient
    ): CourseApiService {
        return Retrofit.Builder()
                .addCallAdapterFactory(callAdapterFactory)
                .baseUrl(BASE)
                .addConverterFactory(converterFactory)
                .client(okHttpClient)
                .build()
                .create(CourseApiService::class.java)
    }

    @Provides
    @FeatureScope
    fun provideCourseProvider(courseApiService: CourseApiService): CourseProvider {
        return CourseProviderImpl(courseApiService)
    }

    @Provides
    fun provideRouter(ciceroneHolder: LocalCiceroneHolder): Router {
        return ciceroneHolder.getCoursesCicerone().router
    }

    @Provides
    @FeatureScope
    fun provideRealmWrapper(): RealmWrapper = PuzzlerRealmWrapper()

    @Provides
    @FeatureScope
    fun provideNewsStorage(realmWrapper: RealmWrapper): CourseStorage =
            CourseStorageImpl(realmWrapper)

    @Provides
    @FeatureScope
    fun provideCourseRepository(
            courseProvider: CourseProvider,
            courseStorage: CourseStorage,
            networkManager: NetworkManager
    ): CourseRepository =
            CourseRepositoryImpl(courseProvider, courseStorage, networkManager)

    @Provides
    fun provideCourseInteractor(
            @Named(DomainModule.JOB) jobScheduler: Scheduler,
            @Named(DomainModule.UI) uiScheduler: Scheduler,
            courseRepository: CourseRepository
    ): GetCourseInteractor =
            GetCourseInteractor(jobScheduler, uiScheduler, courseRepository)

    @Provides
    fun provideCheckAnswerInteractor(): CheckCardAnswerInteractor =
            CheckCardAnswerInteractor()

    @Provides
    fun provideSaveAnswerInteractor(
            @Named(DomainModule.JOB) jobScheduler: Scheduler,
            @Named(DomainModule.UI) uiScheduler: Scheduler,
            configurationFacade: ConfigurationFacade
    ): SaveAnswerInteractor =
            SaveAnswerInteractor(jobScheduler, uiScheduler, configurationFacade)

    @Provides
    fun provideGetCourseListInteractor(
            @Named(DomainModule.JOB) jobScheduler: Scheduler,
            @Named(DomainModule.UI) uiScheduler: Scheduler,
            courseRepository: CourseRepository
    ): GetCourseListInteractor =
            GetCourseListInteractor(jobScheduler, uiScheduler, courseRepository)

    @Provides
    @FeatureScope
    fun provideCardStateController() =
            CardStateController()
}