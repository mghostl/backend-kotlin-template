package com.mghostl.templates.controller

import com.mghostl.templates.service.TemplateService
import com.mghostl.template.client.feign.TemplateClient
import com.mghostl.template.client.model.CreateTemplateRequest
import com.mghostl.template.client.model.TemplateDTO
import com.mghostl.template.client.model.TemplateFilter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/templates")
class TemplateController(
    private val templateService: TemplateService
) : TemplateClient {
    // TODO do we need to add some auth ?

    // TODO add validation

    @PostMapping
    override fun save(@RequestBody createTemplateRequest: CreateTemplateRequest) =
        templateService.save(createTemplateRequest).let { ResponseEntity.ok(it) }

    @GetMapping("{id}")
    override fun get(@PathVariable("id") id: Long) =
        templateService.find(id).let { ResponseEntity.ok(it) }

    @PutMapping("{id}")
    override fun update(@PathVariable("id") id: Long, @RequestBody templateDTO: TemplateDTO) =
        templateService.update(id, templateDTO).let { ResponseEntity.ok(it) }

    @GetMapping
    override fun getAll(
        @RequestBody(required = false) filter: TemplateFilter?,
        @RequestParam(required = false, defaultValue = "10") limit: Int,
        @RequestParam(required = false, defaultValue = "0") offset: Int
    ) =
        templateService.findAll(filter, limit, offset).let { ResponseEntity.ok(it) }
}