package com.appgue.majorleaguesoccer.db

/**
 * Created by ITP on 22-May-18.
 */
data class FavoriteEvent(val eventId: String?,
                         val teamHome: String?,
                         val teamAway: String?,
                         val eventDate: String?,
                         val scoreHome: String?,
                         val scoreAway: String?,
                         val teamHomeId: String?,
                         val teamAwayId: String?){
    companion object {
        const val TABLE_FAVORITE_EVENT: String = "TABLE_FAVORITE_EVENT"
        const val EVENT_ID: String = "EVENT_ID"
        const val TEAM_HOME: String = "TEAM_HOME"
        const val TEAM_AWAY: String = "TEAM_AWAY"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val SCORE_HOME: String = "SCORE_HOME"
        const val SCORE_AWAY: String = "SCORE_AWAY"
        const val TEAM_HOME_ID: String = "TEAM_HOME_ID"
        const val TEAM_AWAY_ID: String = "TEAM_AWAY_ID"
    }
}

