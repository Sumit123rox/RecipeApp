package org.sumit.features.feed.data.datasources.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.sumit.features.common.data.api.BASE_URL
import org.sumit.features.common.data.models.RecipeListApiResponse
import org.sumit.features.common.data.models.toRecipeItem
import org.sumit.features.common.domain.entities.RecipeItem

class FeedRemoteDataSourcesImpl(private val httpClient: HttpClient) : FeedRemoteDataSources {
    override suspend fun getRecipes(): List<RecipeItem> {
        val recipeListApiResponse = httpClient.get("${BASE_URL}search.php?f=p").body<RecipeListApiResponse>()
        return recipeListApiResponse.meals.mapNotNull { it.toRecipeItem() }
    }
}