package com.reactnativeleaktest.react

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import com.facebook.react.ReactRootView
import com.facebook.react.bridge.Arguments
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.reactnativeleaktest.MainActivity

class SimpleReactActivity: Activity(), DefaultHardwareBackBtnHandler {
    private lateinit var reactRootView: ReactRootView
    private val reactInstanceManager = MainActivity.instance.reactInstanceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val body = intent.getStringExtra(Body)
        val props = Arguments.createMap()
        props.putString("body", body)

        reactRootView = ReactRootView(this)
        reactRootView.startReactApplication(reactInstanceManager, "SimpleReactScreen", Arguments.toBundle(props))
        setContentView(reactRootView)
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        reactInstanceManager.onHostPause(this)
    }

    override fun onResume() {
        super.onResume()
        reactInstanceManager.onHostResume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        reactInstanceManager.onHostDestroy(this)
        reactRootView.unmountReactApplication()
    }

    override fun onBackPressed() {
        reactInstanceManager.onBackPressed()
        super.onBackPressed()
        finish()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            reactInstanceManager.showDevOptionsDialog()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    companion object {
        const val Body = "body"

        fun getStartIntent(context: Context, body: String): Intent {
            val intent = Intent(context, SimpleReactActivity::class.java).apply {
                putExtra(Body, body)
            }
            return intent
        }
    }
}