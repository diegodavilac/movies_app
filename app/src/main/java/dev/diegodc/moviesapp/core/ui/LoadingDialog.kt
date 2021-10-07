package dev.diegodc.moviesapp.core.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dev.diegodc.moviesapp.R

class LoadingDialog() : DialogFragment() {
    companion object {

        private const val LOADING_DIALOG_TAG = "loadingDialogTag"

        fun showLoadingDialogFragment(fragmentManager: FragmentManager): LoadingDialog {
            val loadingDialogFragment = LoadingDialog()
            loadingDialogFragment.isCancelable = false
            synchronized(this) {
                val currentDialog = fragmentManager.findFragmentByTag(LOADING_DIALOG_TAG)
                if (currentDialog == null) {
                    loadingDialogFragment.show(fragmentManager, LOADING_DIALOG_TAG)
                }
            }
            return loadingDialogFragment
        }

        fun hideLoadingDialogFragment(fragmentManager: FragmentManager) {
            val dialogFragment = fragmentManager.findFragmentByTag(LOADING_DIALOG_TAG)
            if (dialogFragment is DialogFragment) {
                dialogFragment.dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loading_dialog, container)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }
}

fun AppCompatActivity.showLoadingDialog(): LoadingDialog =
    LoadingDialog.showLoadingDialogFragment(this.supportFragmentManager)

fun AppCompatActivity.hideLoadingDialog() =
    LoadingDialog.hideLoadingDialogFragment(this.supportFragmentManager)

fun Fragment.showLoadingDialog(): LoadingDialog =
    LoadingDialog.showLoadingDialogFragment(this.childFragmentManager)

fun Fragment.hideLoadingDialog() =
    LoadingDialog.hideLoadingDialogFragment(this.childFragmentManager)