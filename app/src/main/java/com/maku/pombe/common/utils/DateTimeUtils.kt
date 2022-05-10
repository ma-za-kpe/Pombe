package com.maku.pombe.common.utils

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.Exception

object DateTimeUtils {
  fun parse(dateTimeString: String): LocalDateTime = try {
      LocalDateTime.parse(dateTimeString)
    } catch (e: Exception) {
      val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
      LocalDateTime.parse(dateTimeString, dateFormatter)
    }

//  fun dbStringToLocalDateTime(text: String?): LocalDateTime? {
//    return if (text != null && text.isNotEmpty() && text != "null") {
//      try {
//        LocalDateTime.parse(text, text)
//      } catch (ex: Exception) {
//        throw IllegalArgumentException("Cannot parse date time text: $text", ex)
//      }
//    } else {
//      null
//    }
//  }
}
