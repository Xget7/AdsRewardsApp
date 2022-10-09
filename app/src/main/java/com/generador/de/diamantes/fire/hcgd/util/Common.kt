package com.generador.de.diamantes.fire.hcgd.util

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.regex.Pattern
import android.app.Activity
import android.content.ContextWrapper

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}


class Common {


    companion object {
        val VERIFIED_USER = false


        val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        fun isValidString(str: String): Boolean {
            return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
        }
        fun isValidPassword(str: String): Boolean {
            return str.length >= 6
        }
        fun isValidName(str: String): Boolean {
            return str.length >= 4
        }

        fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
            clear()
            addAll(newList)
        }

        //find the current activity from a composable
        fun Context.findActivity(): Activity? = when(this) {
            is Activity -> this
            is ContextWrapper -> baseContext.findActivity()
            else -> null
        }
    }
}