package com.mghostl.template.client.feign

import com.mghostl.template.client.model.CreateTemplateRequest
import com.mghostl.template.client.model.TemplateDTO
import com.mghostl.template.client.model.TemplateFilter
import com.mghostl.template.client.model.TemplatePage
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(url = "\${template-client.url}", path = "api/templates", name = "template")
interface TemplateClient {
    @PostMapping
    fun save(@RequestBody createTemplateRequest: CreateTemplateRequest): ResponseEntity<TemplateDTO>

    @GetMapping("{id}")
    fun get(@PathVariable id: Long): ResponseEntity<TemplateDTO>

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody templateDTO: TemplateDTO): ResponseEntity<TemplateDTO>

    @GetMapping
    fun getAll(@RequestBody filter: TemplateFilter? = null, @RequestParam limit: Int = 10, @RequestParam offset: Int = 0): ResponseEntity<TemplatePage>
}