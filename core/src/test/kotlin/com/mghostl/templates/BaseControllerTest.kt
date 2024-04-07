package com.mghostl.templates

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.ContentResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
abstract class BaseControllerTest(val path: String) : BaseTest() {

    @Autowired
    protected lateinit var mvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    fun <T> MockHttpServletRequestBuilder.json(content: T) = contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(content))

    fun <T> ResultActions.andExpectJson(content: T) = andExpect(status().isOk)
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(content)))

    fun <T> ResultActions.andReturn(clazz: Class<T>) = andReturn().response.contentAsString
        .let { objectMapper.readValue(it, clazz) }!!

}