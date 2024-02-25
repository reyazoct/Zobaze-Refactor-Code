package com.zobaze.zobazerefractortask.ui.screens

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zobaze.zobazerefractortask.R
import com.zobaze.zobazerefractortask.databinding.MainActivityBinding
import com.zobaze.zobazerefractortask.ui.data.UiData
import com.zobaze.zobazerefractortask.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Main Activity is the main entry point of the Zobaze Application
 * This is the only screen where this is showing list of employees
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * binding is used to store the binding data after the inflation of main_activity.xml
     */
    private lateinit var binding: MainActivityBinding

    /**
     * MainViewModel is used to fetch, store and retrieve data until this Main Activity is alive.
     *
     * View model is used here to write business logic to fetch data from remote server and keep it intact
     * even when configuration is changed
     */
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        setupUi()
    }

    /**
     * setupUi method is used start initialization of required components
     */
    private fun setupUi() {
        initUi()
        initDataCollections()
    }

    /**
     * initUi is used to initialize EmployeeAdapter and set view model to binding class
     * and can also be perform one time actions in for future development and enhancements
     */
    private fun initUi() {
        binding.employeesRecyclerView.adapter = EmployeeAdapter()
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this
    }

    /**
     * initDataCollections is used to collect all flows or observe live data to set data to adapter after
     * data is received from the remote server
     */
    private fun initDataCollections() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.employeesListUiData.collectLatest { uiData ->
                    if (uiData.isSuccess) {
                        val adapter = binding.employeesRecyclerView.adapter as? EmployeeAdapter ?: return@collectLatest
                        val data = uiData as UiData.Success
                        adapter.setEmployeesList(data.data)
                    }
                }
            }
        }
    }
}

