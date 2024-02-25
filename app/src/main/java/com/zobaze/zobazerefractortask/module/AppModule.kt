package com.zobaze.zobazerefractortask.module

import com.zobaze.zobazerefractortask.data.repository.EmployeesRepository
import com.zobaze.zobazerefractortask.domain.network.ApiService
import com.zobaze.zobazerefractortask.domain.repository.EmploysRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesEmployeeRepository(apiService: ApiService): EmployeesRepository {
        return EmploysRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummy.restapiexample.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}