package org.sumit.dbFactory

import app.cash.sqldelight.db.SqlDriver

const val DB_FILE_NAME = "RecipeAppCmpAppDb.db"

expect class DatabaseFactory {
    suspend fun createDriver(): SqlDriver
}