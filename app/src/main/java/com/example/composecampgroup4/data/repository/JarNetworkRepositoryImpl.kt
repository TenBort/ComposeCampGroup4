package com.example.composecampgroup4.data.repository

import com.example.composecampgroup4.data.mapper.toEntity
import com.example.composecampgroup4.data.network.api.ApiFactory
import com.example.composecampgroup4.data.network.api.ApiService
import com.example.composecampgroup4.data.network.api.HandlerApiService
import com.example.composecampgroup4.data.network.api.HandlerRequestData
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.domain.entity.Result
import com.example.composecampgroup4.domain.entity.errors.JarDataError
import com.example.composecampgroup4.domain.repository.JarNetworkRepository
import java.io.IOException
import javax.inject.Inject

class JarNetworkRepositoryImpl @Inject constructor(
    private val apiFactory: ApiFactory
) : JarNetworkRepository {

    override suspend fun loadJarData(jarId: String): Result<Jar, JarDataError> {
        return try {
            val (longJarId, ownerName) = apiFactory.handlerApiService.postData(HandlerRequestData(clientId = jarId))
            loadJarData(longJarId, ownerName)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun loadJarData(longJarId: String, ownerName: String): Result<Jar, JarDataError> {
        return try {
            val jar = apiFactory.apiService.getJarData(longJarId).toEntity(ownerName, longJarId)
            Result.Success(jar)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun handleException(exception: Exception): Result<Jar, JarDataError> {
        return when(exception) {
            is retrofit2.HttpException -> {
                when (exception.code()) {
                    408 -> Result.Error(JarDataError.Network.REQUEST_TIMEOUT)
                    429 -> Result.Error(JarDataError.Network.TOO_MANY_REQUESTS)
                    in 500..505 -> Result.Error(JarDataError.Network.SERVER_ERROR)
                    else -> Result.Error(JarDataError.Network.UNKNOWN)
                }
            }
            is IOException -> Result.Error(JarDataError.Network.NO_INTERNET)
            is IllegalArgumentException -> Result.Error(JarDataError.WrongId)
            else -> Result.Error(JarDataError.Unknown(exception.message ?: "Empty exception message"))
        }
    }
}