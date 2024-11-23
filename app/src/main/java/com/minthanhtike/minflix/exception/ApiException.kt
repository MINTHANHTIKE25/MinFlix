package com.minthanhtike.minflix.exception

class ApiException(message: String, val code: Int): Exception(message)