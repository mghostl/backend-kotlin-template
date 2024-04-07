package com.mghostl.templates.service

import com.mghostl.template.client.model.CreateTemplateRequest
import com.mghostl.template.client.model.TemplateDTO
import com.mghostl.template.client.model.TemplateFilter
import com.mghostl.template.client.model.TemplatePage

interface TemplateService {
    fun save(createTemplateRequest: CreateTemplateRequest): TemplateDTO

    fun find(id: Long): TemplateDTO

    fun findAll(filter: TemplateFilter?, limit: Int, offset: Int): TemplatePage

    fun update(id: Long, templateDTO: TemplateDTO): TemplateDTO
}