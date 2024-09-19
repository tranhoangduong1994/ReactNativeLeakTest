package com.reactnativeleaktest

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import com.facebook.react.PackageList
import com.facebook.react.ReactInstanceManager
import com.facebook.react.common.LifecycleState
import com.facebook.soloader.SoLoader
import com.reactnativeleaktest.databinding.MainActivityBinding
import com.reactnativeleaktest.react.ReactCommunicator
import com.reactnativeleaktest.react.SimpleReactActivity

class MainActivity: Activity() {
    private lateinit var binding: MainActivityBinding
    lateinit var reactInstanceManager: ReactInstanceManager
        private set

    companion object {
        const val OVERLAY_PERMISSION_REQ_CODE = 1  // Choose any value
        lateinit var instance: MainActivity
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        SoLoader.init(this, false)

        val packages = PackageList(application).packages.apply {
            add(ReactCommunicator())
        }

        reactInstanceManager = ReactInstanceManager.builder()
            .setApplication(application)
            .setCurrentActivity(this)
            .setBundleAssetName("index.android.bundle")
            .setJSMainModulePath("index")
            .addPackages(packages)
            .setUseDeveloperSupport(BuildConfig.DEBUG)
            .setInitialLifecycleState(LifecycleState.RESUMED)
            .build()

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextScreenButton.setOnClickListener {
            val intent = SimpleReactActivity.getStartIntent(this, "First React screen")
            startActivity(intent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package: $packageName"))
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}