package com.devjaewoo.sensormonitoringapp.request

import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.devjaewoo.sensormonitoringapp.ApplicationManager
import com.devjaewoo.sensormonitoringapp.SERVER_URL
import com.devjaewoo.sensormonitoringapp.TAG
import org.json.JSONObject


object RequestHandler {
    var accessToken: String = ""
    private val requestQueue = Volley.newRequestQueue(ApplicationManager.applicationContext)

    private val defaultErrorListener = Response.ErrorListener { error ->
        Log.d(TAG, "error: $error")
        val body = JSONObject(String(error.networkResponse.data))
        val errorMessage = body.getString("message")
        Log.e(TAG, "defaultErrorListener: code: ${error.networkResponse.statusCode} message: $errorMessage")
        Toast.makeText(ApplicationManager.applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
    }

    fun request(url: String, jsonObject: JSONObject?, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener?, requestWithToken: Boolean, method: Int) {

        val requestURL = SERVER_URL + url
        Log.d(TAG, "request: $requestURL with data $jsonObject")

        val jsonObjectRequest = object : JsonObjectRequest(
            method,
            requestURL,
            jsonObject,
            responseListener,
            errorListener ?: defaultErrorListener
        ) {

            override fun getHeaders(): MutableMap<String, String> {
                return if(requestWithToken) HashMap<String, String>().apply { put("Authorization", "Bearer $accessToken") } else super.getHeaders()
            }
        }

        requestQueue.add(jsonObjectRequest)
    }
}