package com.zobaze.zobazerefractortask.domain.network

import com.zobaze.zobazerefractortask.domain.model.Employee
import com.zobaze.zobazerefractortask.domain.model.base.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/employees")
    suspend fun fetchEmployeesList(): Response<BaseResponse<List<Employee>>>
}