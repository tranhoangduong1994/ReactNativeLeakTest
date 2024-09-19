package com.reactnativeleaktest.react

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

class ReactCommunicator: ReactPackage {
    inner class ReactCommunicatorModule(reactContext: ReactApplicationContext): ReactContextBaseJavaModule(reactContext) {
        override fun getName(): String = "ReactCommunicator"

        @ReactMethod
        fun navigateToSimpleReactActivity(body: String) {
            val currentActivity = reactApplicationContext.currentActivity ?: return
            val intent = SimpleReactActivity.getStartIntent(currentActivity, body)
            currentActivity.startActivity(intent)
        }

        @ReactMethod
        fun popScreen() {
            val currentActivity = reactApplicationContext.currentActivity ?: return
            currentActivity.finish()
        }
    }

    override fun createViewManagers(
        reactContext: ReactApplicationContext
    ): MutableList<ViewManager<View, ReactShadowNode<*>>> = mutableListOf()

    override fun createNativeModules(
        reactContext: ReactApplicationContext
    ): MutableList<NativeModule> = listOf(ReactCommunicatorModule(reactContext)).toMutableList()
}