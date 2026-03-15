package com.example.dreamscanfix.data.remote

interface VisionProcessor {
    suspend fun identifyObject(imageUrl: String): String?
}