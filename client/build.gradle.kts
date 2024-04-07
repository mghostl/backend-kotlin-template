import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.rising3.gradle.semver.plugins.Target
import java.net.URI

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    `maven-publish`
    id("com.github.rising3.semver") version "0.8.2"
}

val springBootVersion = "3.2.4"
group = "com.mghostl"
semver {
    target = Target.FILE
    versionTagPrefix = "v"
    versionGitMessage = "v%s"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = URI("https://maven.pkg.github.com/mghostl/template-service")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
            artifactId = "template-service-client"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework:spring-core:6.1.5")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.1") {
        exclude(group = "commons-fileupload", module = "commons-fileupload")
        exclude(group = "org.bouncycastle", module = "bcprov-jdk15on")
    }
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")


}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}
