package com.example.composecampgroup4.data.repository

import com.example.composecampgroup4.data.mapper.toEntity
import com.example.composecampgroup4.data.network.api.ApiService
import com.example.composecampgroup4.data.network.api.HandlerApiService
import com.example.composecampgroup4.data.network.api.HandlerRequestData
import com.example.composecampgroup4.domain.entity.Jar
import com.example.composecampgroup4.domain.entity.Result
import com.example.composecampgroup4.domain.entity.errors.JarDataError
import com.example.composecampgroup4.domain.repository.JarNetworkRepository
import java.io.IOException

class JarNetworkRepositoryImpl(
    private val handlerApiService: HandlerApiService,
    private val apiService: ApiService
) : JarNetworkRepository {
    override suspend fun loadJarData(jarId: String): Result<Jar, JarDataError> {
        return try {
            val (longJarId, ownerName) = handlerApiService.postData(HandlerRequestData(clientId = jarId))
            val jar = apiService.getJarData(longJarId).toEntity(ownerName, longJarId)
            Result.Success(jar)
        } catch (e: retrofit2.HttpException) {
            when(e.code()) {
                408 -> Result.Error(JarDataError.Network.REQUEST_TIMEOUT)
                429 -> Result.Error(JarDataError.Network.TOO_MANY_REQUESTS)
                in 500..505 -> Result.Error(JarDataError.Network.SERVER_ERROR)
                else -> Result.Error(JarDataError.Network.UNKNOWN)
            }
        } catch (e: IOException){
            Result.Error(JarDataError.Network.NO_INTERNET)
        } catch (e: IllegalArgumentException) {
            Result.Error(JarDataError.WrongId)
        } catch (e: Exception) {
            Result.Error(JarDataError.Unknown(e.message ?: "Empty exception message"))
        }
    }
}