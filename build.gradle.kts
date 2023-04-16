import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
  id("org.springframework.boot") version "2.6.2"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("jvm") version "1.6.10"
  kotlin("plugin.spring") version "1.6.10"
  id("io.gitlab.arturbosch.detekt").version("1.20.0")
}

group = "in.coinome"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.postgresql:postgresql")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  testImplementation("io.mockk:mockk:1.10.4")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  detekt("io.gitlab.arturbosch.detekt:detekt-cli:1.19.0-RC1")
  detekt("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.6.0-RC")
  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.19.0-RC1")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

detekt {
  toolVersion = "1.20.0"
  config = files("config/detekt/detekt.yml")
  buildUponDefaultConfig = true
}

fun Project.getKtlintConfiguration(): Configuration {
  return configurations.findByName("ktlint") ?: configurations.create("ktlint") {
    val dependency = project.dependencies.create("com.pinterest:ktlint:0.44.0")
    dependencies.add(dependency)
  }
}

tasks.register("ktlint", JavaExec::class.java) {
  description = "Check Kotlin code style."
  group = "Verification"
  classpath = getKtlintConfiguration()
  mainClass.set("com.pinterest.ktlint.Main")
  args = listOf("src/**/*.kt", "build.gradle.kts")
}

tasks.named("check") {
  dependsOn("ktlint")
}

tasks.register("ktlintFormat", JavaExec::class.java) {
  description = "Fix Kotlin code style deviations."
  group = "formatting"
  classpath = getKtlintConfiguration()
  mainClass.set("com.pinterest.ktlint.Main")
  args = listOf("-F", "src/**/*.kt", "build.gradle.kts")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
  reports {
    xml.required.set(true)
    html.required.set(true)
    html.outputLocation.set(file("build/reports/detekt.html"))
    txt.required.set(true)
    txt.outputLocation.set(file("build/reports/detekt.txt"))
    sarif.required.set(true)
  }
}
