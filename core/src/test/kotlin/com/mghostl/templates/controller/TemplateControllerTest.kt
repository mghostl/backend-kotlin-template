package com.mghostl.templates.controller

import com.mghostl.templates.BaseControllerTest
import com.mghostl.template.client.model.*
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.stream.Stream

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TemplateControllerTest : BaseControllerTest("/api/templates") {

    @MethodSource("createTemplatesRequests")
    @ParameterizedTest
    fun createTemplateTest(createTemplatesRequest: CreateTemplateRequest?) {
        createTemplate(createTemplatesRequest)
    }

    @Test
    fun getTemplate() {
        val template = createTemplate()
        mvc.perform(get("$path/${template.id}"))
            .andExpectJson(template)
    }

    @MethodSource("getTemplateByIdSource")
    @ParameterizedTest
    fun getTemplateById(getTemplateData: GetTemplateData) {
        val template = createTemplate()
        getTemplateData.let {
            it.resultMatcher(mvc.perform(get("$path/${it.id(template)}")))
        }
    }

    @MethodSource("findAllTemplatesSource")
    @ParameterizedTest
    fun findAll(data: FindAllTemplatesData) {
        val allTemplates = mutableListOf<TemplateDTO>()
        repeat(100) { i ->
            allTemplates.add(createTemplate(CreateTemplateRequest(name = if (i % 2 == 0) "name" else "otherName")))
        }
        mvc.perform(
            get(path)
                .queryParam("limit", data.limit.toString())
                .queryParam("offset", data.offset.toString())
                .json(data.filter)
        ).andExpectJson(
            TemplatePage(
                data = data.result(allTemplates),
                pageCount = data.totalPage,
                totalCount = data.totalCount,
            )
        )
    }

    @Test
    fun updateTemplate() {
        val template = createTemplate()
        val templateUpdate = template
            .apply {
                name = "new Name"
                description = "new description"
                date = date?.plusDays(3)
            }
        mvc.perform(put("$path/${template.id}").json(templateUpdate))
            .andExpectTemplate(templateUpdate)

        mvc.perform(get("$path/${template.id}"))
            .andExpectTemplate(templateUpdate)
    }

    @Test
    fun updateNonExistingTemplate() {
        val template = TemplateDTO(
            name = "new Name",
            description = "new description",
            date = ZonedDateTime.now(),
        )

        mvc.perform(put("$path/123").json(template))
            .andExpect(status().isNotFound)

    }

    @Test
    fun getTemplatePage() {
        val count = 100
        val templates = mutableListOf<TemplateDTO>()
        repeat(count) {
            templates.add(createTemplate())
        }
        var offset = 0
        val limit = 10
        while (offset * (limit + 1) <= count) {
            val expectedPage = TemplatePage(
                templates.subList(offset * limit, offset * limit + limit),
                count / limit,
                count.toLong()
            )
            mvc.perform(get(path).param("limit", limit.toString()).param("offset", offset.toString()))
                .andExpectJson(expectedPage)
            offset += limit
        }
    }

    private fun createTemplate(createTemplateRequest: CreateTemplateRequest? = null): TemplateDTO {
        val request = createTemplateRequest ?: CreateTemplateRequest(
            "Some template",
            "Small description",
            ZonedDateTime.now().minusDays(3),
        )
        val expectedResult = TemplateDTO(
            1,
            request.name,
            request.description,
            request.date,
        )

        return mvc.perform(post(path).json(request))
            .andExpectTemplate(expectedResult)
            .andReturn(TemplateDTO::class.java)
    }

    private fun ResultActions.andExpectTemplate(template: BaseTemplateDTO) =
        andExpect(status().isOk)
            .andExpect(jsonPath("$.name", `is`(template.name)))
            .andExpect(jsonPath("$.description", `is`(template.description)))
            .andExpect(jsonPath("$.date", `is`(template.date?.toJsonString())))

    private fun ZonedDateTime.toJsonString() =
        format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("UTC")))

    companion object {
        @JvmStatic
        fun createTemplatesRequests(): Stream<CreateTemplateRequest?> =
            Stream.of(null, CreateTemplateRequest())

        @JvmStatic
        fun getTemplateByIdSource(): Stream<GetTemplateData> = Stream.of(
            GetTemplateData(
                "WrongId",
                { "wrongId" },
                { resultActions -> resultActions.andExpect(status().isBadRequest) }),
            GetTemplateData(
                "Not existingId",
                { "33" },
                { resultActions -> resultActions.andExpect(status().isNotFound) }),
            GetTemplateData(
                "Successful getting template",
                { template -> template.id.toString() },
                { resultActions -> resultActions.andExpect(status().isOk) }
            )
        )

        @JvmStatic
        fun findAllTemplatesSource(): Stream<FindAllTemplatesData?> = Stream.of(
            FindAllTemplatesData(TemplateFilter("name"), totalPage = 5, totalCount = 50) { templates ->
                templates.filter { it.name == "name" }.subList(0, 10)
            },
            FindAllTemplatesData(null) { templates -> templates.subList(0, 10) },
            FindAllTemplatesData(null, 100, 0, 1, 100) { templates -> templates },
            FindAllTemplatesData(null, offset = 1) { templates -> templates.subList(10, 20) },
            FindAllTemplatesData(null, offset = 11) { _ -> emptyList() }
        )

        data class FindAllTemplatesData(
            val filter: TemplateFilter?,
            val limit: Int = 10,
            val offset: Int = 0,
            val totalPage: Int = 10,
            val totalCount: Long = 100,
            val result: (List<TemplateDTO>) -> List<TemplateDTO>
        )

        data class GetTemplateData(
            val description: String,
            val id: (TemplateDTO) -> String,
            val resultMatcher: (ResultActions) -> ResultActions
        )
    }
}