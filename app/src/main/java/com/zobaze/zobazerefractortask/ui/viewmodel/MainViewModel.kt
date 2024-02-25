package com.zobaze.zobazerefractortask.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zobaze.zobazerefractortask.data.DataRequest
import com.zobaze.zobazerefractortask.data.repository.EmployeesRepository
import com.zobaze.zobazerefractortask.domain.model.Employee
import com.zobaze.zobazerefractortask.ui.data.UiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.InputStream
import java.util.Scanner
import javax.inject.Inject

/**
 * HiltViewModel is used to inject this view model to Main Activity with only instance for each activity or fragment
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val employeesRepo: EmployeesRepository
) : ViewModel() {

    /**
     * _employeesListUiData is used to store 3 states of employees data
     *
     * 1. At initial state this will store Loading state as UiData.Loading()
     *
     * 2. If data is fetch properly from remote server then data will be replaced as UiData.Success with list of employees
     * on each reference
     *
     * 3. If some how request fails to fetch the data from server then _employeesListUiData will store UiData.Failure and same will
     * be rendered to ui by collecting from data binding in xml.
     *
     * Each of these states logic for ui is coded in main_activity.xml using data binding.
     */
    private val _employeesListUiData = MutableStateFlow<UiData<List<Employee>>>(UiData.Loading())
    val employeesListUiData = _employeesListUiData.asStateFlow()

    /**
     * This is CoroutineExceptionHandler which will catch exceptions if some error appear in coded below coroutine scope
     */
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _employeesListUiData.emit(UiData.Failure(throwable.message ?: "Unknown", ::loadDataFromServer))
        }
    }

    init {
        loadDataFromServer()
    }

    /**
     * loadDataFromServer will fetch data from server and retrieve from repository inside viewModelScope
     * and success or failure either of them will be received as DataRequest
     */
    private fun loadDataFromServer() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _employeesListUiData.emit(UiData.Loading())
            val dataRequest = employeesRepo.fetchEmployeesData()
            if (dataRequest is DataRequest.Success) {
                _employeesListUiData.emit(UiData.Success(dataRequest.data.data ?: emptyList()))
            } else {
                _employeesListUiData.emit(UiData.Failure("Failed", ::loadDataFromServer))
            }
        }
    }
}