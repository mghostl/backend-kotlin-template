package com.mghostl.templates.mapper

import com.mghostl.template.client.model.CreateTemplateRequest
import com.mghostl.template.client.model.TemplateDTO
import com.mghostl.templates.model.Template
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants.ComponentModel
import org.mapstruct.MappingTarget
import org.mapstruct.Mappings

@Mapper(componentModel = ComponentModel.SPRING)
interface TemplateMapper {

    fun map(template: Template): TemplateDTO

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "createdAt", ignore = true),
        Mapping(target = "updatedAt", ignore = true)
    )
    fun map(createTemplateRequest: CreateTemplateRequest): Template

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "createdAt", ignore = true),
        Mapping(target = "updatedAt", ignore = true)
    )
    fun update(@MappingTarget template: Template, templateDTO: TemplateDTO): Template

}