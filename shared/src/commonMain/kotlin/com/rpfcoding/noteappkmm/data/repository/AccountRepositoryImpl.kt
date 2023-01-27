package com.rpfcoding.noteappkmm.data.repository

import com.rpfcoding.noteappkmm.data.remote.RocketLaunchDto
import com.rpfcoding.noteappkmm.domain.repository.AccountRepository
import com.rpfcoding.noteappkmm.domain.time.DateTimeUtil
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class AccountRepositoryImpl(
    private val httpClient: HttpClient
) : AccountRepository {

    override suspend fun returnSomeString(): String {
        val rockets: List<RocketLaunchDto> = httpClient.get("https://api.spacexdata.com/v4/launches").body()
        val lastSuccessLaunch = rockets.last { it.launchSuccess == true }
        return "\nThere are only ${DateTimeUtil.daysUntilNewYear()} left until New Year! 🎆" +
                "\nThe last successful launch was ${lastSuccessLaunch.launchDate} 🚀"
    }
}