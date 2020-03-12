package com.example.rxjavaworkshop

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName!!
    private var call: Call<AlertResponse>? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textview)
        val restApi = createRestApi()

        textView.text = "Getting Alerts"

        call = restApi.getAlerts()
        call?.enqueue(object : Callback<AlertResponse> {
            override fun onResponse(call: Call<AlertResponse>, response: Response<AlertResponse>) {
                val alertResponse = response.body()
                if (response.isSuccessful && alertResponse != null) {
                    var alertText = ""
                    alertResponse.data.forEach { alert ->
                        if (alert.severity > 5) {
                            alertText = alertText + alert.message + "\n\n"
                        }
                    }
                    textView.text = alertText
                }
            }

            override fun onFailure(call: Call<AlertResponse>, error: Throwable) {
                Log.w(TAG, "Error getting alerts", error)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        call?.cancel()
    }
}
