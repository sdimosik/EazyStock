package com.sdimosikvip.domain

import java.io.IOException

class NoConnectionException() : IOException()

class ServiceUnavailableException : IOException()

class NoCachedDataException : IOException()