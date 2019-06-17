package com.alexeeff.golangpuzzler.runner

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import com.alexeeff.golangpuzzler.GolangPuzzlerApp

class CoreAndroidTestRunner: AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, GolangPuzzlerApp::class.java.name, context)
    }
}