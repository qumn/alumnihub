plugins {
    id("io.github.qumn.kotlin-application-conventions")
}

dependencies {
    implementation(project(":domain:trade"))
    implementation(project(":domain:system"))
}