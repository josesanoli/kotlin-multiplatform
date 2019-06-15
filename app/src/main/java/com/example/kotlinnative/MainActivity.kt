package com.example.kotlinnative

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.sharedNative.*
import drawer.example.com.kotlinnative.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), PresenterInterface {

    private val presenter = MainPresenter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //textView.text = getTextLabel()

        val user = CustomClass("name1", 1234)

        //textView.text = getUserName(user)
        presenter.delegate = this
        //presenter.user = user
        presenter.getUserIdWithInterface()
        var view: View? = null

        view?.let {

        }
        val test = SerializableClass("User name", 1234)
        Log.d("splash", test.toJsonString())
        Log.d("splash", MainPresenter().toData("{\"user\":\"jose\",\"number\":1234,\"numbr\":1}").toString())

    }

    override fun userId(userId: Int) {
        textView.text = userId.toString()
    }

    override fun error() {
        textView.text = "Not valid user"
    }
}