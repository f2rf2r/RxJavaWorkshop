package com.example.rxjavaworkshop

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName!!
    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textview)
        val restApi = createRestApi()

        compositeDisposable += Observable.just("Getting Alerts")
            .subscribeBy { textView.text = it }

        compositeDisposable += restApi.getAlerts()
            .map { it.data }
            .flatMap { Observable.fromIterable(it) }
            .filter { it.severity > 5 }
            .map { it.message + "\n\n" }
            .reduce("", { prev, current ->
                prev + current
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    textView.text = it
                }, onError = {
                    Log.w(TAG, "Error getting alerts", it)
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
