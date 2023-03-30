package com.dellepiane.ceibatest

inline fun <T> resultOf(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    }
    catch (e: java.lang.Exception){
        Result.failure(e)
    }
}