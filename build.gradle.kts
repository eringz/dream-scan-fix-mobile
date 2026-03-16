// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.20" apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.legacy.kapt) apply false
}