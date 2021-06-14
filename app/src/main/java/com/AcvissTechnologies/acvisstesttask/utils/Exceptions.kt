package com.AcvissTechnologies.acvisstesttask.utils

import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)

/* implementation "androidx.room:room-runtime:2.2.5"
    implementation "androidx.room:room-coroutines:2.2.5"
    kapt "androidx.room:room-compiler:2.2.5"
    implementation "androidx.room:room-ktx:2.2.5"
* */