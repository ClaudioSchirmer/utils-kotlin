import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

val repositoryURL: String by project
val repositoryUser: String by project
val repositoryPassword: String by project
val kotlinVersion: String by project
val jacksonJavaTime: String by project

plugins {
	application
	kotlin("jvm") version "1.7.0"
	`maven-publish`
	java
}

val compileOptions: (KotlinJvmOptions.() -> Unit) = {
	jvmTarget = "11"
	allWarningsAsErrors = true
}

tasks.compileKotlin {
	kotlinOptions {
		compileOptions()
	}
}

tasks.compileTestKotlin {
	kotlinOptions {
		compileOptions()
	}
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
	withSourcesJar()
}

group = "br.dev.schirmer"
version = "1.1.0"

sourceSets.main {
	withConvention(KotlinSourceSet::class) {
		kotlin.srcDirs("src")
	}
}

sourceSets.test {
	withConvention(KotlinSourceSet::class) {
		kotlin.srcDirs("test")
	}
}

repositories {
	mavenCentral()
}

dependencies {
	//Jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonJavaTime")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonJavaTime")
}

tasks.publish {
	dependsOn("jar")
}

publishing {
	publications {
		register("mavenJava", MavenPublication::class) {
			artifact("build/libs/${project.name}-$version.jar") {
				artifactId = project.name
				extension = "jar"
			}
			with(pom) {
				developers {
					developer {
						id.set("CSG")
						name.set("Cláudio Schirmer Guedes")
						email.set("claudioschirmer@icloud.com")
					}
				}
				withXml {
					val dependencies = asNode().appendNode("dependencies")
					configurations.implementation.get().allDependencies.forEach {
						val depNode = dependencies.appendNode("dependency")
						depNode.appendNode("groupId", it.group)
						depNode.appendNode("artifactId", it.name)
						depNode.appendNode("version", it.version)
					}
				}
			}
		}
	}
	repositories {
		maven {
			name = "nexus"
			url = uri("$repositoryURL/repository/maven-releases/")
			isAllowInsecureProtocol = true
			credentials {
				username = repositoryUser
				password = repositoryPassword
			}
		}
	}
}