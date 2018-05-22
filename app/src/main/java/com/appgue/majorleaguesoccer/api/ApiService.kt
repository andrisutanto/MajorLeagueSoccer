package com.appgue.majorleaguesoccer.api

import com.appgue.majorleaguesoccer.model.Team
import com.appgue.majorleaguesoccer.model.TeamBadge
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("lookupteam.php?")
    fun requestTeam(
            @Query("id") idTeam: Int
    ): Call<TeamBadge>
}