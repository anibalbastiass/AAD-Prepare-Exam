package com.anibalbastias.android.marvelapp.base.api.data.marvel

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MarvelAPIGSONManager private constructor() {

    @Retention(AnnotationRetention.SOURCE)
    annotation class SectionType

    @Retention(AnnotationRetention.SOURCE)
    annotation class ActionType

    private fun createGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    private fun createGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()

//        gsonBuilder.registerTypeAdapter(TypeData::class.java, MarvelPageDeserializer())
//        gsonBuilder.registerTypeAdapter(SectionData::class.java, MarvelSectionDeserializer())

        return gsonBuilder
    }

    companion object {
        private var instance: MarvelAPIGSONManager? = null

        //ETAGS FOR SERIES HARDCODED FROM API, UNKNOWN PROCESS FOR GET THAT
        const val SECTION_ETAG_SERIES_DEFAULT = "7c863cc13618a99490552d620b6d3e88fd139e72"
        const val SECTION_ETAG_SERIES1 = "b0b99d36d74e1e3c383224f799fd68197a87fb44"

        fun create(): Gson {
            if (instance == null) {
                instance = MarvelAPIGSONManager()
            }
            return instance!!.createGson()
        }

        fun createGsonBuilder(): GsonBuilder {
            if (instance == null) {
                instance = MarvelAPIGSONManager()
            }
            return instance!!.createGsonBuilder()
        }
    }
}