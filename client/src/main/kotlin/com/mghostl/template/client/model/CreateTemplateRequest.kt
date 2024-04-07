package com.mghostl.template.client.model

import org.springframework.lang.NonNull
import java.time.ZonedDateTime

data class CreateTemplateRequest(
    @NonNull
    override var name: String? = null,
    @NonNull
    override var description: String? = null,
    @NonNull
    override var date: ZonedDateTime? = null,
) : BaseTemplateDTO(name, description, date)