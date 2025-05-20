package org.sumit.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.sumit.features.common.data.api.httpClient

fun networkModule() = module {
    single<HttpClient> {
        httpClient
    }
}