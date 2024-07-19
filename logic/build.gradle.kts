
plugins {
    kotlin("jvm")  version "1.9.21"
}
 repositories{
     maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
 }

dependencies{
    api("org.apache.poi:poi-ooxml:5.3.0")
    testImplementation(platform("org.junit:junit-bom:5.10.2-SNAPSHOT"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test{
    useJUnitPlatform()

    this.testLogging {
        this.showStandardStreams = true
        this.debug
    }
}