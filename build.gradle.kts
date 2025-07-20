@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
    id("fabric-loom") version "1.11-SNAPSHOT"

    id("com.modrinth.minotaur") version "2.8.7"
    id("com.github.breadmoirai.github-release") version "2.5.2"
    `maven-publish`
    signing
}

group = "dev.nyon"
val majorVersion = "1.0.0"
val mcVersion = "1.21.8"
version = "$majorVersion-$mcVersion"
description = "Fabric/Quilt mod, which adds a game like feature to gather all items"
val githubRepo = "btwonion/item-hunt"

repositories {
    mavenCentral()
    maven("https://maven.terraformersmc.com")
    maven("https://maven.quiltmc.org/repository/release/")
    maven("https://maven.isxander.dev/releases")
}

dependencies {
    minecraft("com.mojang:minecraft:$mcVersion")
    mappings(loom.layered {
        mappings("org.quiltmc:quilt-mappings:1.21.6-pre1+build.1:intermediary-v2")
        officialMojangMappings()
    })

    implementation("org.vineflower:vineflower:1.11.1")
    modImplementation("net.fabricmc:fabric-loader:0.16.14")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.129.0+$mcVersion")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.13.4+kotlin.2.2.0")
    modImplementation("dev.isxander:yet-another-config-lib:3.7.0+1.21.6-fabric")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    modApi("com.terraformersmc:modmenu:15.0.0-beta.3")
}

tasks {
    processResources {
        val modId = "item-hunt"
        val modDescription = "Fabric/Quilt mod, which adds a game like feature to gather all items"

        inputs.property("id", modId)
        inputs.property("name", modId)
        inputs.property("description", modDescription)
        inputs.property("version", project.version)
        inputs.property("github", githubRepo)

        filesMatching("fabric.mod.json") {
            expand(
                "id" to modId,
                "name" to modId,
                "description" to modDescription,
                "version" to project.version,
                "github" to githubRepo,
            )
        }
    }

    register("releaseMod") {
        group = "publishing"

        dependsOn("modrinthSyncBody")
        dependsOn("modrinth")
        dependsOn("githubRelease")
        dependsOn("publish")
    }

    withType<JavaCompile> {
        options.release = 21
    }

    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget("21")
        }
    }
}
val changelogText =
    file("changelogs.md").takeIf { it.exists() }?.readText() ?: "No changelog provided."

modrinth {
    token.set(findProperty("modrinth.token")?.toString())
    projectId.set("mExC8RRZ")
    versionNumber.set(project.version.toString())
    versionType.set("release")
    uploadFile.set(tasks["remapJar"])
    gameVersions.set(listOf("1.21.6", "1.21.7", "1.21.8"))
    loaders.set(listOf("fabric", "quilt"))
    dependencies {
        required.project("fabric-api")
        required.project("fabric-language-kotlin")
        required.project("yacl")
        optional.project("modmenu")
    }
    changelog.set(changelogText)
    syncBodyFrom.set(file("README.md").readText())
}

githubRelease {
    token(findProperty("github.token")?.toString())

    val split = githubRepo.split("/")
    owner = split[0]
    repo = split[1]
    tagName = "v${project.version}"
    body = changelogText
    overwrite = true
    releaseAssets = tasks["remapJar"].outputs.files
    targetCommitish = "master"
}

publishing {
    repositories {
        maven {
            name = "nyon"
            url = uri("https://repo.nyon.dev/releases")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "dev.nyon"
            artifactId = "item-hunt"
            version = project.version.toString()
            from(components["java"])
        }
    }
}

java {
    withSourcesJar()

    JavaVersion.VERSION_21.let {
        sourceCompatibility = it
        targetCompatibility = it
    }
}