package com.example.repogithublist.ui.repositoryList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repogithublist.data.helper.Response
import com.example.repogithublist.domain.GetRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val getRepositoriesUseCase: GetRepositoriesUseCase
): ViewModel() {
    private val repositoryValues = MutableStateFlow(RepositoryListState())
    var _repositoryValues : StateFlow<RepositoryListState> = repositoryValues

    fun getAllRepositoriesData() {
        viewModelScope.launch(Dispatchers.IO) {
            getRepositoriesUseCase.invoke().collect {
                when (it) {
                    is Response.Success -> {
                        repositoryValues.value =
                            RepositoryListState(repositoryList = it.data ?: emptyList())
                    }
                    is Response.Loading -> {
                        repositoryValues.value = RepositoryListState(isLoading = true)
                    }
                    is Response.Error -> {
                        repositoryValues.value =
                            RepositoryListState(error = it.errorMessage ?: "Erro Inesperado")
                    }
                }
            }
        }
    }
}