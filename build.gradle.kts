import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

val repositoryURL: String by project
val kotlinVersion: String by project
val jacksonJavaTime: String by project

plugins {
	application
	kotlin("jvm") version "1.9.22"
	`maven-publish`
	java
}

val compileOptions: (KotlinJvmOptions.() -> Unit) = {
	jvmTarget = "17"
	allWarningsAsErrors = false
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
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
	withSourcesJar()
}

group = "br.dev.schirmer"
version = "6.1.0"

sourceSets.main {
	java {
		srcDirs("src")
	}
}

sourceSets.test {
	java {
		srcDirs("test")
	}
}

/* Avoid overloading the cloud file manager and time machine */
allprojects {
	layout.buildDirectory.set(File("${System.getProperty("user.home")}/GradleBuild/Libs/${rootProject.name}"))
}

repositories {
	mavenCentral()
}

dependencies {
	//Jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonJavaTime")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonJavaTime")
}

publishing {
	publications {
		register("mavenJava", MavenPublication::class) {
			artifact(tasks.jar) {
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
			name = "GitHub"
			url = uri(repositoryURL)
			isAllowInsecureProtocol = true
			credentials {
				username = System.getenv("MAVEN_USERNAME")
				password = System.getenv("MAVEN_PASSWORD")
			}
		}
	}
}