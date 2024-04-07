package com.mghostl.templates.service

import com.mghostl.template.client.model.CreateTemplateRequest
import com.mghostl.template.client.model.TemplateDTO
import com.mghostl.template.client.model.TemplateFilter
import com.mghostl.template.client.model.TemplatePage
import com.mghostl.templates.exceptions.TemplateNotFoundException
import com.mghostl.templates.mapper.TemplateMapper
import com.mghostl.templates.repository.TemplateRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TemplateServiceImpl(
    private val templateRepository: TemplateRepository,
    private val mapper: TemplateMapper
) : TemplateService {
    @Transactional
    override fun save(createTemplateRequest: CreateTemplateRequest) =
        mapper.map(createTemplateRequest)
            .let { templateRepository.save(it) }
            .let { mapper.map(it) }

    @Transactional(readOnly = true)
    override fun find(id: Long) =
        templateRepository.findById(id)
            .orElseThrow { TemplateNotFoundException("There is no template with id $id") }
            .let { mapper.map(it) }

    // TODO use keyset instead of offset
    @Transactional(readOnly = true)
    override fun findAll(filter: TemplateFilter?, limit: Int, offset: Int) =
        (filter?.let {
            templateRepository.findAllByName(
                filter.name,
                PageRequest.of(offset, limit)
            )
        }
            ?: templateRepository.findAll(PageRequest.of(offset, limit)))
            .map { mapper.map(it) }
            .let { TemplatePage(it.content, it.totalPages, it.totalElements) }


    @Transactional
    override fun update(id: Long, templateDTO: TemplateDTO) =
        templateRepository.findById(id)
            .orElseThrow { TemplateNotFoundException("There is no tournament with id $id") }
            .let { mapper.update(it, templateDTO) }
            .apply { templateRepository.save(this) }
            .let { mapper.map(it) }
}