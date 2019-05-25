plugins {
	`kotlin-dsl`
}

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	implementation(kotlin("gradle-plugin"))
	implementation("com.beust:klaxon:5.0.5")
}

gradlePlugin {
	plugins {
		create("mavenCentralStatistics") {
			id = "org.junit.build.stats.mavencentral"
			implementationClass = "org.junit.build.stats.mavencentral.MavenCentralStatisticsPlugin"
		}
	}
}
