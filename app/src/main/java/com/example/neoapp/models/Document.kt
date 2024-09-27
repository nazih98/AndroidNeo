package com.example.neoapp.models

import java.util.Date

data class Document(
    val id: Int? = null,
    val title: String,
    val createdAt: Date,
    val updatedAt: Date
)