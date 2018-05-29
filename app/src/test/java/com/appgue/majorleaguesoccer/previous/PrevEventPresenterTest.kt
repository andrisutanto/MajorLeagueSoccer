package com.appgue.majorleaguesoccer.previous

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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PrevEventPresenterTest {
    @Mock
    private
    lateinit var view: PrevEventView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PrevEventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PrevEventPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetPrevEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val leagueId = "4346"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPrevMatch(leagueId)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getPrevEventList(leagueId)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPrevEventList(events)
        Mockito.verify(view).hideLoading()
    }
}