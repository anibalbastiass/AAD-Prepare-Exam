package com.anibalbastias.android.marvelapp

import android.content.Context
import com.anibalbastias.android.marvelapp.MarvelApplication.Companion.appContext
import com.anibalbastias.android.marvelapp.util.isTablet
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

@GlideModule
class MarvelGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // LRU Pool size
        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize

        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()

        // set disk cache size & external vs. internal
        val cacheSize100MegaBytes = 104857600

        // or any other path
//        val downloadDirectoryPath = Environment.getDownloadCacheDirectory().path

        // Set builders
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes.toLong()))
        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize.toLong()))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.append(CustomImageSizeModel::class.java, InputStream::class.java, CustomImageSizeUrlLoaderFactory())
    }
}

interface CustomImageSizeModel {
    fun requestCustomSizeUrl(width: Int, height: Int): String
}

class CustomImageSizeUrlLoaderFactory : ModelLoaderFactory<CustomImageSizeModel, InputStream> {

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<CustomImageSizeModel, InputStream> {
        val modelLoader = multiFactory.build<GlideUrl, InputStream>(GlideUrl::class.java, InputStream::class.java)

        val modelCache = if (isTablet(appContext)) {
            ModelCache(600)
        } else {
            ModelCache<CustomImageSizeModel, GlideUrl>(300)
        }
        return CustomImageSizeUrlLoader(modelLoader, modelCache)
    }

    override fun teardown() {

    }
}

class CustomImageSizeUrlLoader(
    concreteLoader: ModelLoader<GlideUrl, InputStream>,
    modelCache: ModelCache<CustomImageSizeModel, GlideUrl>
) :
    BaseGlideUrlLoader<CustomImageSizeModel>(concreteLoader, modelCache) {

    override fun getUrl(model: CustomImageSizeModel, width: Int, height: Int, options: Options): String {
        return model.requestCustomSizeUrl(width, height)
    }

    override fun handles(customImageSizeModel: CustomImageSizeModel): Boolean {
        return true
    }
}