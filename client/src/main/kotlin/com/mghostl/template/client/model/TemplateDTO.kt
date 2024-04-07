package com.mghostl.template.client.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.mghostl.template.client.model.BaseTemplateDTO
import java.time.ZonedDateTime

data class TemplateDTO(

    var id: Long? = null,

    override var name: String? = null,

    override var description: String? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
    override var date: ZonedDateTime? = null,
) : BaseTemplateDTO(name, description, date)
