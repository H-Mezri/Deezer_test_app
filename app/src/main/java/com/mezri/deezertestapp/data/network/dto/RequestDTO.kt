package com.mezri.deezertestapp.data.network.dto

data class RequestDTO<T> (val data: List<T>, val checksum: String, val total: Int, val suivant: String)