package com.kunalfarmah.apps.weatherforcastcompose.data

/**
 * Wrapper class to get metaData about the response
 */
class DataOrException<T, Boolean, E: Exception> (
    var data: T? = null,
    var loading: kotlin.Boolean?= null,
    var e: E? = null
)
