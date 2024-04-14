plugins {
    id("io.github.qumn.kotlin-library-conventions")
}


dependencies {
    implementation(project(":framework:web"))
    implementation("com.qiniu:qiniu-java-sdk:7.13.+")
    implementation(libs.logging)
    implementation(project(":framework:test"))
    implementation("commons-io:commons-io:2.16.1")
    implementation(libs.ktorm.core)
}