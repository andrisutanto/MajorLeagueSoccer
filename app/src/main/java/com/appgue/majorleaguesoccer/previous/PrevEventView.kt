package com.appgue.majorleaguesoccer.previous

import com.appgue.majorleaguesoccer.model.Event

interface PrevEventView {
    fun showLoading()
    fun hideLoading()
    fun showPrevEventList(data: List<Event>)
}
