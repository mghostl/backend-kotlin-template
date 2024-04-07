package com.mghostl.templates

import org.testcontainers.containers.PostgreSQLContainer

object PostgresContainer: PostgreSQLContainer<PostgresContainer>("postgres:15-alpine")