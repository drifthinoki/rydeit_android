package com.rydeit.io.ui.customview

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rydeit.io.R

open class BaseBottomSheet (private val isDefaultFullScreen: Boolean): BottomSheetDialogFragment(){

    private val TAG = this::class.java.simpleName
    /**
     * BottomSheet 會自動依照內容高度顯示,若內容高度不足,但又需要全版顯示時
     * 1.覆寫 frameLayout's height
     * 2.設定 pickHeight
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            if (!isDefaultFullScreen) return@apply
            this.setOnShowListener {
                val bottomSheetDialog = it as BottomSheetDialog
                val frameLayout = bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
                frameLayout.layoutParams.height = (resources.displayMetrics.heightPixels * 0.97).toInt()

                val behavior = BottomSheetBehavior.from(frameLayout)
                behavior.peekHeight = (resources.displayMetrics.heightPixels * 0.97).toInt()
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme;
    }
}