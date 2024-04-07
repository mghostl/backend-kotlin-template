package com.mghostl.template.client.configuration

import com.mghostl.template.client.feign.TemplateClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(TemplateClient::class)
@EnableFeignClients(basePackages = ["com.mghostl.template.client"])
@ConditionalOnProperty(prefix = "template-client", name = ["enabled"], havingValue = "true")
class TemplateClientAutoConfiguration