package com.maku.pombe.common.utils

import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.maku.logging.Logger
import com.maku.pombe.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ImageView.setImage(url: String) {
  Glide.with(this.context)
      .load(url.ifEmpty { null })
      .error(R.drawable.ic_error_placeholder)
      .centerCrop()
      .transition(DrawableTransitionOptions.withCrossFade())
      .into(this)
}

inline fun CoroutineScope.createExceptionHandler(
    message: String,
    crossinline action: (throwable: Throwable) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
  Logger.e(throwable, message)
  throwable.printStackTrace()

  /**
   * A [CoroutineExceptionHandler] can be called from any thread. So, if [action] is supposed to
   * run in the main thread, you need to be careful and call this function on the a scope that
   * runs in the main thread, such as a [viewModelScope].
  */
  launch {
    action(throwable)
  }
}