package com.example.mycryptoapp.presentation

import android.app.Application
import com.example.mycryptoapp.di.DaggerApplicationComponent

class CoinApp : Application() {

    val component by lazy{
        DaggerApplicationComponent.factory().create(this)
    }
}