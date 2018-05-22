package com.appgue.majorleaguesoccer.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by ITP on 22-May-18.
 */
class MyDBOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteEvent.db", null, 1) {
    companion object {
        private var instance: MyDBOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDBOpenHelper {
            if (instance == null) {
                instance = MyDBOpenHelper(ctx.applicationContext)
            }
            return instance as MyDBOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavoriteEvent.TABLE_FAVORITE_EVENT, true,
                Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorite.TEAM_ID to TEXT + UNIQUE,
                Favorite.TEAM_NAME to TEXT,
                Favorite.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}

// Access property for Context
val Context.databaseEvent: MyDBOpenHelper
get() = MyDBOpenHelper.getInstance(applicationContext)