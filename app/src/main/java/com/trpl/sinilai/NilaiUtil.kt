package com.trpl.sinilai

data class Mahasiswa(
    val nama: String,
    val nim: String,
    val prodi: String,
    val mataKuliah: String,
    val nilaiAkhir: Double,
    val huruf: String,
    val lulus: Boolean
)

data class KomponenNilai(
    val nama: String,
    val nilai: Double,
    val bobot: Int
)

fun Double.toHuruf(): String = when {
    this >= 85.0 -> "A"
    this >= 75.0 -> "B"
    this >= 65.0 -> "C"
    this >= 55.0 -> "D"
    else -> "E"
}

fun Double.toBobotAngka(): Double = when {
    this >= 85.0 -> 4.0
    this >= 75.0 -> 3.0
    this >= 65.0 -> 2.0
    this >= 55.0 -> 1.0
    else -> 0.0
}

fun Double.toPredikat(): String = when {
    this >= 85.0 -> "Sangat Baik"
    this >= 75.0 -> "Baik"
    this >= 65.0 -> "Cukup"
    this >= 55.0 -> "Kurang"
    else -> "Sangat Kurang"
}

fun String.toInisial(): String {
    return this.split(" ")
        .take(2)
        .map { it.first() }
        .joinToString("")
        .uppercase()
}

fun hitungNilaiAkhir(komponen: List<KomponenNilai>): Double {
    return komponen.sumOf { it.nilai * it.bobot / 100.0 }
}

fun apakahLulus(nilaiAkhir: Double): Boolean {
    return nilaiAkhir >= 65.0
}

fun totalBobot(komponen: List<KomponenNilai>): Int {
    return komponen.sumOf { it.bobot }
}