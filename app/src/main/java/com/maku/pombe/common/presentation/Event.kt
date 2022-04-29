package com.maku.pombe.common.presentation

class Event<out T>(private val content: T) {

  private var hasBeenHandled = false

  /**
   * Returns the content and prevents its use again.
   */
  fun getContentIfNotHandled(): T? {
    return if (hasBeenHandled) {
      null
    } else {
      hasBeenHandled = true
      content
    }
  }
}
