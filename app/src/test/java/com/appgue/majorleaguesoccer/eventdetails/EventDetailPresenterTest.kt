package com.appgue.majorleaguesoccer.eventdetails

import com.appgue.majorleaguesoccer.TestContextProvider
import com.appgue.majorleaguesoccer.api.ApiRepository
import com.appgue.majorleaguesoccer.api.TheSportDBApi
import com.appgue.majorleaguesoccer.model.Event
import com.appgue.majorleaguesoccer.model.EventResponse
import com.google.gson.Gson
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventDetailPresenterTest {
    @Mock
    private
    lateinit var view: EventDetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: EventDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetEventDetail() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val eventId = "526849"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEventDetail(eventId)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getEventDetail(eventId)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showEventDetail(events)
        Mockito.verify(view).hideLoading()
    }
}