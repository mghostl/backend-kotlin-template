
import com.github.rising3.gradle.semver.plugins.Target
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("com.google.cloud.tools.jib") version "3.4.0"
    id("com.github.rising3.semver") version  "0.8.2"

}

jib {
    from {
        image = "openjdk:17"
    }
    to {
        image = "cr.yandex/crpgc24g506crakf94tc/template-service"
        version = project.version
        credHelper {
            helper = "yc"
        }
        tags = setOf(project.version.toString())

    }
    container {
        ports = listOf("8080")
        jvmFlags = listOf("-Dspring.profiles.active=yc")
        extraDirectories {
            paths {
                path {
                   setFrom("../deploy/certs")
                    into = "/home/certs"
                }
            }
        }
    }
}

semver {
    target = Target.FILE
    versionTagPrefix = "v"
    versionGitMessage = "v%s"
}

group = "com.mghostl.templates.core"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}
extra["testcontainersVersion"] = "1.17.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core:4.23.1")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")

    // Openapi
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    // Logging
    implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")
    implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")

    implementation(project(":client"))

    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

kapt.includeCompileClasspath = false

tasks.withType<Test> {
    useJUnitPlatform()
}