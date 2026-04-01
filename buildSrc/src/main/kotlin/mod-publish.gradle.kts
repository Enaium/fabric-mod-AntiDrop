import me.modmuss50.mpp.PublishModTask
import org.gradle.util.internal.VersionNumber

plugins {
    id("me.modmuss50.mod-publish-plugin")
}

afterEvaluate {
    publishMods {
        val disableObfuscation = properties.getOrDefault("fabric.loom.disableObfuscation", false).toString().toBoolean()
        file = tasks.named<AbstractArchiveTask>(if (disableObfuscation) "jar" else "remapJar").get().archiveFile.get()
        type = STABLE
        displayName = "AntiDrop ${project.version}"
        changelog = rootProject.file("changelog.md").readText(Charsets.UTF_8)
        modLoaders.add("fabric")

        val modern = VersionNumber.parse(properties["minecraft.version"].toString()) >= VersionNumber.parse("1.14")

        curseforge {
            projectId = "580810"
            accessToken = providers.gradleProperty("curseforge.token")
            minecraftVersions.add(property("minecraft.version").toString())
            requires("fabric-language-kotlin", "mineconf", if (modern) "fabric-api" else "legacy-fabric-api")
        }

        modrinth {
            projectId = "CWgsoj4A"
            accessToken = providers.gradleProperty("modrinth.token")
            minecraftVersions.add(property("minecraft.version").toString())
            requires("fabric-language-kotlin", "mineconf", if (modern) "fabric-api" else "legacy-fabric-api")
        }

        github {
            repository = "Enaium/fabric-mod-AntiDrop"
            accessToken = providers.gradleProperty("github.token")
            commitish = "master"
        }

        tasks.withType<PublishModTask>().configureEach {
            dependsOn(tasks.named(if (disableObfuscation) "jar" else "remapJar"))
        }
    }
}