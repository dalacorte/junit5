package org.junit.build.stats.mavencentral

import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property
import java.net.URI

open class MavenCentralStatisticsExtension(project: Project) {
    val baseUrl: Property<URI> = project.objects.property<URI>().convention(project.provider {
        URI.create("https://oss.sonatype.org/service/local/stats")
    })
    val username: Property<String> = project.objects.property<String>().convention(project.provider {
        project.properties["nexusUsername"] as String
    })
    val password: Property<String> = project.objects.property<String>().convention(project.provider {
        project.properties["nexusPassword"] as String
    })
}
