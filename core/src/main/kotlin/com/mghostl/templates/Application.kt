package com.mghostl.templates

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["com.mghostl.templates.repository"])
@EntityScan(basePackages = ["com.mghostl.templates.model"]) // TODO move these configuration to the starter
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}