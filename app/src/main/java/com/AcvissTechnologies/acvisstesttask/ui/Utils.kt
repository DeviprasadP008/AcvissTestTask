package com.AcvissTechnologies.acvisstesttask.ui

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.AcvissTechnologies.acvisstesttask.data.network.Resource
import com.google.android.material.snackbar.Snackbar

fun<A: Activity> Activity.startNewActivity(activity: Class<A>){
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.visible(isVisible: Boolean){
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean){
    isEnabled = enabled
    alpha = if(enabled) 1f else 0.5f
}


fun View.snackbar(message: String, action: (() -> Unit)? = null){
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry"){
            it()
        }
    }
    snackbar.show()
}

fun View.snackbarevent(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok"){
            snackbar.dismiss()
        }
    }.show()
}


fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when{
        failure.isNetworkError == true -> requireView().snackbar("Please check your internet connection", retry)
        failure.errorCode == 401 -> {
            //Handle error conditions here
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}

fun basicAlert(view: View, context: Context, alerttitle: String, alertmessage: String){
    val builder = AlertDialog.Builder(context)
    with(builder) {
        setTitle(alerttitle)
        setMessage(alertmessage)
        setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
        show()
    }
}

val positiveButtonClick = { dialog: DialogInterface, which: Int ->
   // Toast.makeText(applicationContext, android.R.string.yes, Toast.LENGTH_SHORT).show()
    dialog.dismiss()
}
val negativeButtonClick = { dialog: DialogInterface, which: Int ->
   // Toast.makeText(applicationContext, android.R.string.no, Toast.LENGTH_SHORT).show()
}
val neutralButtonClick = { dialog: DialogInterface, which: Int ->
   // Toast.makeText(applicationContext, "Maybe", Toast.LENGTH_SHORT).show()
}

fun View.toast(contect: Context, message: String){
    Toast.makeText(contect, message, Toast.LENGTH_SHORT).show()
}

