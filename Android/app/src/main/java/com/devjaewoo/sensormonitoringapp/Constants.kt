package com.devjaewoo.sensormonitoringapp

val Any.TAG: String
    get()  = if(javaClass.simpleName.length <= 23) javaClass.simpleName else javaClass.simpleName.substring(0, 23)

const val SERVER_URL: String = "http://192.165.90.32:8080"