import com.google.protobuf.gradle.id

plugins {
  java
  id("org.springframework.boot") version "3.5.3"
  id("io.spring.dependency-management") version "1.1.7"
  id("com.google.protobuf") version "0.9.4"
}

group = "dev.lunna.panel"
version = "0.0.1-SNAPSHOT"

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

extra["springGrpcVersion"] = "0.8.0"

dependencies {
  implementation(libs.bundles.starter)
  implementation(libs.grpc.services)
  implementation(libs.spring.grpc.server.starter)
  implementation(libs.spring.session.core)
  developmentOnly(libs.spring.boot.devtools)
  runtimeOnly(libs.mariadb.java.client)
  annotationProcessor(libs.spring.boot.configuration.processor)
  testImplementation(libs.spring.boot.starter.test)
  testImplementation(libs.spring.grpc.test)
  testImplementation(libs.spring.kafka.test)
  testImplementation(libs.spring.security.test)
  testRuntimeOnly(libs.junit.platform.launcher)

  implementation(libs.jetbrains.annotations)
  implementation(libs.google.auto.value.annotations)

  implementation(libs.jjwt.api)
  runtimeOnly(libs.bundles.jjwt.runtime)

  annotationProcessor(libs.google.auto.value.processor)
}

tasks.test {
  useJUnitPlatform()
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

tasks {
  withType<JavaCompile> {
    options.encoding = "UTF-8"
  }

  withType<Javadoc> {
    options.encoding = "UTF-8"
  }
}

dependencyManagement {
  imports {
    mavenBom("org.springframework.grpc:spring-grpc-dependencies:${property("springGrpcVersion")}")
  }
}

protobuf {
  protoc {
    artifact = "com.google.protobuf:protoc"
  }
  plugins {
    id("grpc") {
      artifact = "io.grpc:protoc-gen-grpc-java"
    }
  }
  generateProtoTasks {
    all().forEach {
      it.plugins {
        id("grpc") {
          option("jakarta_omit")
          option("@generated=omit")
        }
      }
    }
  }
}
