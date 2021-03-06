package entities.interfaces

import entities.ErrorMessage

typealias SimpleTask = Task<Unit, Unit>
typealias DestinationTask = Task<String, Unit>

interface Task<TArg, TResult> {
    fun onSuccess(vararg arg: TArg): TResult
    fun onError(message: ErrorMessage? = null)
}