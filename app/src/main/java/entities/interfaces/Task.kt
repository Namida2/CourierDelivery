package entities.interfaces

import entities.ErrorMessage

interface Task<TArg, TResult> {
    fun onSuccess(vararg arg: TArg): TResult
    fun onError(message: ErrorMessage? = null)
}