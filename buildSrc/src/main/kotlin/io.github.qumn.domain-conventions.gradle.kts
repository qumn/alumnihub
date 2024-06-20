plugins {
    id("io.github.qumn.kotlin-library-conventions")
    id("com.google.devtools.ksp")
}

val ktormVersion by project.properties

dependencies {
    implementation(project(":framework:test"))
    implementation(project(":framework:security"))
    api(project(":framework:web"))
    implementation(project(":starter:ktorm-spring-boot-starter"))
    implementation(project(":starter:axon-spring-boot-starter"))
    ksp("org.ktorm:ktorm-ksp-compiler:${ktormVersion}")
}

ksp {
    arg("ktorm.dbNamingStrategy", "lower-snake-case")
}