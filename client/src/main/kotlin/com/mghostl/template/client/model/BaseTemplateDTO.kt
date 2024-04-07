package com.mghostl.template.client.model

import java.time.ZonedDateTime

abstract class BaseTemplateDTO(
    open var name: String?,
    open var description: String?,
    open var date: ZonedDateTime?,
)
