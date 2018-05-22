package com.appgue.majorleaguesoccer.next

import com.appgue.majorleaguesoccer.model.Event

interface NextEventView {
    fun showLoading()
    fun hideLoading()
    fun showNextEventList(data: List<Event>)
}
