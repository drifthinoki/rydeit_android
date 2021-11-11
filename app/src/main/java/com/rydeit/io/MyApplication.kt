package com.rydeit.io

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.rydeit.io.config.Constants.DEBUG
import com.rydeit.io.helper.PinHelper
import com.rydeit.io.helper.PinInputType
import com.rydeit.io.helper.UserHelper
import com.rydeit.io.utils.StorageUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class MyApplication: Application() {

    private val TAG = this::class.java.simpleName

    override fun onCreate() {
        super.onCreate()

        // init StorageUtil
        StorageUtil.with(this)

        setOnAppEnterForegroundEvent()
    }

    private fun setOnAppEnterForegroundEvent() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            private fun onAppEnterForeGround() {
                if (DEBUG) Log.e(TAG,"ProcessLifecycleOwner event: ON_RESUME")
                if (!UserHelper.isLogin()) return
                PinHelper.showPinInputView(applicationContext, PinInputType.ENTER_APP)
            }
        })
    }
}