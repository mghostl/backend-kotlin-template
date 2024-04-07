package com.mghostl.template.client.model

data class TemplatePage(
    val data: List<TemplateDTO>,
    val pageCount: Int,
    val totalCount: Long
)
