package com.appgue.majorleaguesoccer.model

import com.google.gson.annotations.SerializedName

class TeamBadge {
    @SerializedName("teams")
    var teamresult: List<data>? = null

    class data{
        @SerializedName("strTeamBadge")
        var strTeamBadge : String? = null
    }
}