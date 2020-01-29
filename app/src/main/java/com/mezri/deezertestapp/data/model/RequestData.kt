package com.mezri.deezertestapp.data.model

data class RequestData<T> (val data: List<T>, val checksum: String, val total: Int, val suivant: String)