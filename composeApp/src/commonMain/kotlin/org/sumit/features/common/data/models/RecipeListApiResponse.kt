package org.sumit.features.common.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RecipeListApiResponse(
    val meals: List<RecipeApiItem>
)
