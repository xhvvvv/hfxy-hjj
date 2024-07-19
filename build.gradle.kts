
plugins {
//    kotlin("jvm")
//    id("org.jetbrains.compose")
}

group = "com.wxfactory"
version = "1.0-SNAPSHOT"


buildscript{
    repositories{
        maven("https://maven.aliyun.com/repository/central")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        google()
        mavenCentral()
        gradlePluginPortal()

        maven { url = uri("https://jitpack.io") }
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    if (project.hasProperty("xh_group_password")) {
        //host仓库
        val xh_self_local: String by project
        //group仓库
        val xh_public: String by project
        val xh_group_name: String by project
        val xh_group_password: String by project

        repositories {
            maven {
                this.isAllowInsecureProtocol = true
                url = uri(xh_self_local)
                credentials {
                    username = xh_group_name
                    password = xh_group_password
                }
            }
            maven {
                this.isAllowInsecureProtocol = true
                url = uri(xh_public)
                credentials {
                    username = xh_group_name
                    password = xh_group_password
                }
            }
        }
    }
}

if (project.hasProperty("xh_group_password")) {
    //host仓库
    val xh_self_local: String by project
    //group仓库
    val xh_public: String by project
    val xh_group_name: String by project
    val xh_group_password: String by project

    //配置子项目的仓库
    subprojects{
        repositories {
            maven {
                this.isAllowInsecureProtocol = true
                url = uri(xh_self_local)
                credentials {
                    username = xh_group_name
                    password = xh_group_password
                }
            }
            maven {
                this.isAllowInsecureProtocol = true
                url = uri(xh_public)
                credentials {
                    username = xh_group_name
                    password = xh_group_password

                }
            }
        }
    }
}

subprojects{
    repositories {
        google()
        mavenLocal()
        maven(url="https://mirrors.huaweicloud.com/repository/maven/")
        mavenCentral()
    }
}


afterEvaluate{
    if (project.hasProperty("org.gradle.java.installations.paths").not()){
        println("警告！没有配置ToolChain的Jdk本机位置")
    }else{
        val jdk:String = project.findProperty("org.gradle.java.installations.paths") as String
        if (jdk.isEmpty()){
            println("警告！没有配置ToolChain的Jdk本机位置")
        }
    }

    val mavenLocal = project.findProperty("systemProp.maven.repo.local")
    println("本机maven仓库位置=====$mavenLocal=====")
}