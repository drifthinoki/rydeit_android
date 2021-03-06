package com.rydeit.io.helper

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.rydeit.io.R
import com.rydeit.io.databinding.DialogCustomBinding

object DialogHelper {

    enum class DialogType {
        LOGIN_FAIL, VERIFY_FAIL, REGISTER_SUCCESS, RESET_PASSWORD_SUCCESS;

        val title:Int
            get() {
                return when(this) {
                    LOGIN_FAIL -> R.string.dialog_login_fail_title
                    VERIFY_FAIL -> R.string.dialog_verify_fail_title
                    REGISTER_SUCCESS -> R.string.dialog_register_success_title
                    RESET_PASSWORD_SUCCESS -> R.string.dialog_reset_password_title
                }
            }

        val icon:Int
            get() {
                return when(this) {
                    LOGIN_FAIL, VERIFY_FAIL -> R.drawable.ic_account_notice
                    REGISTER_SUCCESS, RESET_PASSWORD_SUCCESS -> R.drawable.ic_account_basic
                }
            }

        val message:Int
            get() {
                return when(this) {
                    LOGIN_FAIL -> R.string.dialog_login_fail_msg
                    VERIFY_FAIL -> R.string.dialog_verify_fail_msg
                    REGISTER_SUCCESS -> R.string.dialog_register_success_msg
                    RESET_PASSWORD_SUCCESS -> R.string.dialog_reset_password_msg
                }
            }

        val mainBtnText:Int
            get() {
                return when(this) {
                    LOGIN_FAIL, VERIFY_FAIL -> R.string.btn_text_close
                    REGISTER_SUCCESS, RESET_PASSWORD_SUCCESS -> R.string.btn_text_login
                }
            }

        val secondBtnText:Int?
            get() {
                return when(this) {
                    LOGIN_FAIL, VERIFY_FAIL, REGISTER_SUCCESS, RESET_PASSWORD_SUCCESS -> null
                }
            }

    }

    fun showDialog(activity: Activity, dialogType: DialogType, mainButtonAction: (()->Unit)? = null) {
        val builder = AlertDialog.Builder(activity)
        val binding = DialogCustomBinding.inflate(activity.layoutInflater, null, false)
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)

        binding.dialogIcon.setImageResource(dialogType.icon)
        binding.dialogTitle.text = activity.getText(dialogType.title)
        binding.dialogMsg.text = activity.getText(dialogType.message)
        binding.dialogMainBtn.text = activity.getText(dialogType.mainBtnText)
        binding.dialogMainBtn.setOnClickListener {
            mainButtonAction?.invoke()
            dialog.dismiss()
        }

        dialog.show()
    }
}