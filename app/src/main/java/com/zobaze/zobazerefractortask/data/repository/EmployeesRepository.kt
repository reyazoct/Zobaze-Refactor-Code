package com.zobaze.zobazerefractortask.data.repository

import com.zobaze.zobazerefractortask.data.DataRequest
import com.zobaze.zobazerefractortask.domain.model.Employee
import com.zobaze.zobazerefractortask.domain.model.base.BaseResponse

interface EmployeesRepository {
    suspend fun fetchEmployeesData(): DataRequest<BaseResponse<List<Employee>>>
}