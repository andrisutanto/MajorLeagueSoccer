package com.appgue.majorleaguesoccer.eventdetails

import com.appgue.majorleaguesoccer.model.Event

interface EventDetailView {
    fun showLoading()
    fun hideLoading()
    fun showEventDetail(data: List<Event>)
}