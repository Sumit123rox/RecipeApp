package org.sumit.features.common.data.database

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.sumit.RecipeAppCmpAppDb
import org.sumit.dbFactory.DatabaseFactory
import orgsumit.Recipe

class DbHelper(
    private val databaseFactory: DatabaseFactory
) {
    private var db: RecipeAppCmpAppDb? = null
    private val mutex = Mutex()

    suspend fun <Result : Any?> withDatabase(block: suspend (RecipeAppCmpAppDb) -> Result) =
        mutex.withLock {
            if (db == null) {
                db = createDb(databaseFactory)
            }

            return@withLock block(db!!)
        }

    private suspend fun createDb(databaseFactory: DatabaseFactory): RecipeAppCmpAppDb =
        RecipeAppCmpAppDb(
            driver = databaseFactory.createDriver(), RecipeAdapter = Recipe.Adapter(
                ingredientsAdapter = listOfStringsAdapter,
                instructionsAdapter = listOfStringsAdapter
            )
        )

}