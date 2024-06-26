plugins {
    id("io.github.qumn.kotlin-application-conventions")
}

dependencies {
    implementation(project(":domain:comment"))
    implementation(project(":domain:trade"))
    implementation(project(":domain:system"))
    implementation(project(":domain:forum"))
    implementation(project(":domain:lostfound"))
    implementation(project(":framework:storage"))
}