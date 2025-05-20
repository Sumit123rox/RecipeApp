package org.sumit.features.details.data.datasources.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.sumit.features.common.data.api.BASE_URL
import org.sumit.features.common.data.models.RecipeListApiResponse
import org.sumit.features.common.data.models.toRecipeItem
import org.sumit.features.common.domain.entities.RecipeItem

class RecipeDetailsRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : RecipeDetailsRemoteDataSource {
    override suspend fun getRecipeDetails(recipeId: Long): RecipeItem? {
        return httpClient.get("${BASE_URL}lookup.php?i=$recipeId")
            .body<RecipeListApiResponse>().meals.firstOrNull()?.toRecipeItem()
    }
}