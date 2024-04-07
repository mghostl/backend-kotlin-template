package com.mghostl.templates

import com.mghostl.templates.repository.TemplateRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.transaction.annotation.Transactional

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseTest  {
    companion object {
        val postgresContainer = PostgresContainer

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            postgresContainer.start()
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            postgresContainer.stop()
        }

        @DynamicPropertySource
        @JvmStatic
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.apply {
                add("spring.datasource.url", PostgresContainer::getJdbcUrl)
                add("spring.datasource.username", PostgresContainer::getUsername)
                add("spring.datasource.password", PostgresContainer::getPassword)
            }
        }

    }

    @Autowired
    private lateinit var templateRepository: TemplateRepository

    @Transactional
    @AfterEach
    fun clean() {
        templateRepository.deleteAll()
    }

}