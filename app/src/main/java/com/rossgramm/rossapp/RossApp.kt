package com.rossgramm.rossapp

import android.app.Application

class RossApp : Application() {

    companion object {
        lateinit var app: RossApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}