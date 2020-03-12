package com.example.rxjavaworkshop


data class AlertResponse(val data: List<Alert>)

data class Alert(val id: String, val attributes: AlertAttributes) {
    val message: String get() = attributes.header
    val severity: Int get() = attributes.severity
}

data class AlertAttributes(val header: String, val severity: Int)

