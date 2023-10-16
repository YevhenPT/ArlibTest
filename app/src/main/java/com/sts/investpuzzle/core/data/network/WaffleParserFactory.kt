package com.sts.investpuzzle.core.data.network

import com.androidnetworking.gsonparserfactory.GsonParserFactory
import com.google.gson.GsonBuilder

class WaffleParserFactory {

    val gsonParserFactory : GsonParserFactory
    init {
        val gson = GsonBuilder()
            .create()
        gsonParserFactory = GsonParserFactory(gson)
    }
}