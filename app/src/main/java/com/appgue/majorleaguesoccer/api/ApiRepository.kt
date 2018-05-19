package com.appgue.majorleaguesoccer.api

import java.net.URL

/**
 * Created by Andri on 5/19/2018.
 */
class ApiRepository {

    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}