package com.anibalbastias.android.marvelapp.base.api.domain.base

/**
 * Created by anibalbastias on 1/30/18.
 */
/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 *
 * @param <T> the cached model input type
 * @param <T> the remote model input type
 * @param <V> the model return type
 */
interface Mapper<E, D> {

    fun executeMapping(type: D): E

}