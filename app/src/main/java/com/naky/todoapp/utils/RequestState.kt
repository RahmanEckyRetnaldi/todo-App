package com.naky.todoapp.utils

sealed class RequestState<out T>{
    object Idle : RequestState<Nothing>()
    object Loading : RequestState<Nothing>()
    data class Succes<T> (val data : T) : RequestState<T>()
    data class Eror(val eror : Throwable) : RequestState<Nothing>()
}
