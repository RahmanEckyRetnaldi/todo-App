package com.naky.todoapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naky.todoapp.database.model.Priority
import com.naky.todoapp.database.model.ToDoTask
import com.naky.todoapp.database.repository.DataStoreRepository
import com.naky.todoapp.database.repository.ToDoRepository
import com.naky.todoapp.utils.Action
import com.naky.todoapp.utils.Constants.MAX_TITLE_LENGTH
import com.naky.todoapp.utils.RequestState
import com.naky.todoapp.utils.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject


@HiltViewModel
class ToDoNotesViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    val action : MutableState<Action> = mutableStateOf(Action.NO_ACTION)
    val id : MutableState<Int> = mutableStateOf(0)
    val title : MutableState<String> = mutableStateOf("")
    val description : MutableState<String> = mutableStateOf("")
    val priority : MutableState<Priority> = mutableStateOf(Priority.LOW)
    val searchAppBarState : MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _searchTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTask : StateFlow<RequestState<List<ToDoTask>>> = _searchTasks

    fun searchDatabase(searchQuery : String){
        _searchTasks.value = RequestState.Loading
        try{
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%$searchQuery")
                    .collect { searchTask ->
                        _searchTasks.value = RequestState.Succes(searchTask)
                    }
            }
        }catch (e:Exception){
            _searchTasks.value = RequestState.Eror(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    val lowPriorityTasks : StateFlow<List<ToDoTask>> =
        repository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            Collections.emptyList()
        )
    val highPriorityTasks : StateFlow<List<ToDoTask>> =
        repository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            Collections.emptyList()
        )

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState : StateFlow<RequestState<Priority>> = _sortState

    fun readSortState (){
        _sortState.value = RequestState.Loading
        try{
            viewModelScope.launch {
               dataStoreRepository.readDataStore
                    .map {Priority.valueOf(it.toString()) }
                   .collect { _sortState.value = RequestState.Succes(it) }
            }
        }catch (e:Exception){
            _searchTasks.value = RequestState.Eror(e)
        }
    }

    fun persistSortState(priority: Priority){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    private val _allTask = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks : StateFlow<RequestState<List<ToDoTask>>> = _allTask

    fun getAllTasks(){
        _allTask.value = RequestState.Loading
        try{
            viewModelScope.launch {
                repository.getAllTask.collect{
                    _allTask.value = RequestState.Succes(it)
                }
            }
        }catch (e:Exception){
            _allTask.value = RequestState.Eror(e)
        }
    }

    private val _selectedTask : MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask : StateFlow<ToDoTask?> = _selectedTask

    fun getSelectedTask(taskId : Int){
        viewModelScope.launch {
            repository.getSelectedTask(taskId).collect {
                task ->
                _selectedTask.value = task
            }
        }
    }

    private fun addTask(){
        viewModelScope.launch(Dispatchers.IO){
            val toDoTask = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value,
            )
            repository.addTask(toDoTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateTask(){
        viewModelScope.launch(Dispatchers.IO){
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.updateTask(toDoTask)
        }
    }

    private fun deleteTask(){
        viewModelScope.launch(Dispatchers.IO){
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.deleteTask(toDoTask)
        }
    }

    private fun deleteAllTask(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllTask()
        }
    }

    fun handleDatabaseActions(action: Action){
        when(action){
            Action.ADD -> addTask()
            Action.UPDATE -> updateTask()
            Action.DELETE -> deleteTask()
            Action.DELETE_ALL -> deleteAllTask()
            Action.UNDO -> addTask()
            else -> {

            }
        }
        this.action.value = Action.NO_ACTION
    }

    fun updateTaskField(selectedTask : ToDoTask?){
        if (selectedTask != null){
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        }else{
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    fun updateTitle(newTitle : String){
        if(newTitle.length < MAX_TITLE_LENGTH){
            title.value = newTitle
        }
    }

    fun validateFields() :Boolean{
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }
}