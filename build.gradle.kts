// TODO bring common part of gradle configuration here

plugins {
    val kotlinPluginVersion = "1.8.22"
    kotlin("jvm") version kotlinPluginVersion apply false
    kotlin("kapt") version kotlinPluginVersion apply false
    kotlin("plugin.spring") version kotlinPluginVersion apply false
    kotlin("plugin.jpa") version kotlinPluginVersion apply false
}