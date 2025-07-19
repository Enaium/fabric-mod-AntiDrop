import me.modmuss50.mpp.ModPublishExtension
import me.modmuss50.mpp.PublishModTask

plugins {
    id("java")
}

buildscript {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
        mavenCentral()
    }

    val loomVersion: String by project
    val modPublishVersion: String by project


    dependencies {
        classpath("net.fabricmc:fabric-loom:${loomVersion}")
        classpath("me.modmuss50.mod-publish-plugin:me.modmuss50.mod-publish-plugin.gradle.plugin:${modPublishVersion}")
    }
}

sourceSets {
    main {
        java {
            setSrcDirs(emptySet<File>())
        }
    }
}

allprojects {
    group = "cn.enaium"
    version = "1.1.1"
}

subprojects {
    apply {
        plugin("java")
        plugin("fabric-loom")
        plugin("me.modmuss50.mod-publish-plugin")
    }

    val archivesBaseName: String by project

    base {
        archivesName.set(archivesBaseName)
    }

    version = "${property("minecraftVersion")}-${version}"

    tasks.processResources {
        inputs.property("currentTimeMillis", System.currentTimeMillis())

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version.toString()))
        }
    }

    repositories {
        mavenCentral()
    }

    sourceSets.main {
        resources {
            srcDir(file(rootProject.projectDir).resolve("resources"))
        }
    }

    afterEvaluate {
        configure<ModPublishExtension> {
            file = tasks.named<AbstractArchiveTask>("remapJar").get().archiveFile.get()
            type = STABLE
            displayName = "AntiDrop ${project.version}"
            changelog = rootProject.file("changelog.md").readText(Charsets.UTF_8)
            modLoaders.add("fabric")

            curseforge {
                projectId = "580810"
                accessToken = providers.gradleProperty("curseforge.token")
                minecraftVersions.add(property("minecraftVersion").toString())
                requires("fabric-api")
            }

            modrinth {
                projectId = "CWgsoj4A"
                accessToken = providers.gradleProperty("modrinth.token")
                minecraftVersions.add(property("minecraftVersion").toString())
                requires("fabric-api")
            }

            github {
                repository = "Enaium/fabric-mod-AntiDrop"
                accessToken = providers.gradleProperty("github.token")
                commitish = "master"
            }

            tasks.withType<PublishModTask>().configureEach {
                dependsOn(tasks.named("remapJar"))
            }
        }
    }
}