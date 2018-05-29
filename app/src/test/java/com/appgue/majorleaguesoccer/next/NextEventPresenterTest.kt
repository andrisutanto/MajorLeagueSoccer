package com.appgue.majorleaguesoccer.next

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

class NextEventPresenterTest {
    @Mock
    private
    lateinit var view: NextEventView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: NextEventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = NextEventPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetNextEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val leagueId = "4346"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(leagueId)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getNextEventList(leagueId)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showNextEventList(events)
        Mockito.verify(view).hideLoading()
    }
}