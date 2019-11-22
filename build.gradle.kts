import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

version = "0.0.1-SNAPSHOT"
group = "conferencesessionservice"

plugins {
	application
	id("org.springframework.boot") version "2.2.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.50"
	kotlin("plugin.spring") version "1.3.50"
}

val developmentOnly: Configuration by configurations.creating
configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
}

sourceSets {
	main {
		java {
			setSrcDirs(listOf("src/main/kotlin"))
		}
	}

	test {
		java {
			setSrcDirs(listOf("test/main/kotlin"))
		}
	}
}

repositories {
	jcenter()
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.apache.tinkerpop:gremlin-server:3.4.4")
	implementation( "org.apache.tinkerpop:gremlin-core:3.4.4")
	implementation("org.apache.tinkerpop:tinkergraph-gremlin:3.4.4")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "12"
	}
}

