import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"
}

// apply(from = "dependencies.gradle.kts", to = versions)
group = "com.mghostl"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

object Version {
    const val springAdmin = "2.6.2"
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.spring.io/milestone")
        name = "spring-milestone"
    }
    maven{
        url = uri("https://repo.spring.io/snapshot")
        name = "spring-snapshots"
    }
    maven {
        name = "Sonatype Nexus Snapshots"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}



dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("de.codecentric:spring-boot-admin-starter-server:${Version.springAdmin}")
    implementation("de.codecentric:spring-boot-admin-starter-client:${Version.springAdmin}")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // DB
    implementation("org.liquibase:liquibase-core")
    implementation("org.postgresql:postgresql:42.3.2")

    // Dev tools
    implementation("org.springframework.boot:spring-boot-devtools")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
