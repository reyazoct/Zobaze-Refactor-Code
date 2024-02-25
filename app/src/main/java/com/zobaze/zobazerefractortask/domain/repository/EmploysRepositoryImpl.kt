package com.zobaze.zobazerefractortask.domain.repository

import com.zobaze.zobazerefractortask.data.DataRequest
import com.zobaze.zobazerefractortask.data.repository.EmployeesRepository
import com.zobaze.zobazerefractortask.domain.model.Employee
import com.zobaze.zobazerefractortask.domain.model.base.BaseResponse
import com.zobaze.zobazerefractortask.domain.network.ApiService
import com.zobaze.zobazerefractortask.exception.ExceptionEmptyResponse
import com.zobaze.zobazerefractortask.exception.ExceptionServerDown
import com.zobaze.zobazerefractortask.exception.ExceptionUnauthorised
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class EmploysRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : EmployeesRepository {
    override suspend fun fetchEmployeesData(): DataRequest<BaseResponse<List<Employee>>> {
        val response = apiService.fetchEmployeesList()
        return responseToDataRequest(response)
    }

    private fun <T> responseToDataRequest(response: Response<T>): DataRequest<T> {
        return if (response.isSuccessful) {
            return response.body()?.let {
                DataRequest.Success(it)
            } ?: run {
                DataRequest.Failure(ExceptionEmptyResponse())
            }
        } else {
            val ex = when (response.code()) {
                401 -> ExceptionUnauthorised()
                502, 503 -> ExceptionServerDown()
                else -> HttpException(response)
            }
            DataRequest.Failure(ex)
        }
    }
}