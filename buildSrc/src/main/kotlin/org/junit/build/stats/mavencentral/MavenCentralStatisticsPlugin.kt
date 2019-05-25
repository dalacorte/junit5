package org.junit.build.stats.mavencentral

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

open class MavenCentralStatisticsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create<MavenCentralStatisticsExtension>("mavenCentralStatistics", project)
        project.tasks.register("downloadMavenCentralStatistics", DownloadMavenCentralStatistics::class, extension)
    }
}
