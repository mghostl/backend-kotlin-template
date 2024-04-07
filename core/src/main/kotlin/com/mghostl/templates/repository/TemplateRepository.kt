package com.mghostl.templates.repository

import com.mghostl.templates.model.Template
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TemplateRepository : JpaRepository<Template, Long> {
    fun findAllByName(administratorId: String, pageable: Pageable): Page<Template>
}