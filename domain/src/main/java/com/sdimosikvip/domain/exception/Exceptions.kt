package com.sdimosikvip.domain

import java.io.IOException

class NoConnectionException() : IOException()

class ServerException : IOException()

class ClientException : IOException()

class UncheckedException : IOException()

class NoCachedDataException : IOException()