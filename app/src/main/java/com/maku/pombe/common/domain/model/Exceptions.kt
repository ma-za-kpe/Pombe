package com.maku.pombe.common.domain.model

import java.io.IOException

class NoDrinksException(message: String): Exception(message)
class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)
class NetworkException(message: String): Exception(message)