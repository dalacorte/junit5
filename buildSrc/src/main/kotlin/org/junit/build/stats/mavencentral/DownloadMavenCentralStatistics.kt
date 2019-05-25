package org.junit.build.stats.mavencentral

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import org.gradle.api.internal.AbstractTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property
import org.gradle.workers.WorkerExecutor
import java.net.Authenticator
import java.net.HttpURLConnection
import java.net.PasswordAuthentication
import java.net.URI
import java.net.URL
import java.nio.charset.StandardCharsets.UTF_8
import javax.inject.Inject

open class DownloadMavenCentralStatistics @Inject constructor(@get:Internal val workerExecutor: WorkerExecutor, extension: MavenCentralStatisticsExtension) : AbstractTask() {

    @Input
    val baseUrl: Property<URI> = project.objects.property<URI>().convention(extension.baseUrl)

    @Input
    val username: Property<String> = project.objects.property<String>().convention(extension.username)

    @Input
    val password: Property<String> = project.objects.property<String>().convention(extension.password)

    @TaskAction
    fun downloadCsvFiles() {
        val authenticator = object : Authenticator() {
            override fun getPasswordAuthentication() = PasswordAuthentication(username.get(), password.get().toCharArray())
        }
        val baseUrl = baseUrl.get()
        with(URL("$baseUrl/projects").openConnection() as HttpURLConnection) {
            setAuthenticator(authenticator)
            addRequestProperty("Accept", "application/json")
            inputStream.reader(UTF_8).use {
                val json = Klaxon().parseJsonObject(it)
                json.array<JsonObject>("data")?.forEach {
                    println("id: ${it.string("id")}, name: ${it.string("name")}")
                }
            }
        }
    }

}