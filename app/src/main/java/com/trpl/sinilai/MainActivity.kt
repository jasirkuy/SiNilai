package com.trpl.sinilai

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.trpl.sinilai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        siapkanDropdown()
        isiDataTersimpan()
        pasangAksiTombol()
        pasangPembersihError()
    }

    private fun siapkanDropdown() {
        binding.acProdi.setSimpleItems(resources.getStringArray(R.array.daftar_prodi))
    }

    private fun isiDataTersimpan() {
        val sari = Mahasiswa(
            nama = "Sari Dewi",
            nim = "2024002",
            prodi = "PPB",
            mataKuliah = "Pemrograman Perangkat Bergerak",
            nilaiAkhir = 88.40,
            huruf = 88.40.toHuruf(),
            lulus = apakahLulus(88.40)
        )
        val budi = Mahasiswa(
            nama = "Budi Santoso",
            nim = "2024003",
            prodi = "PPB",
            mataKuliah = "Pemrograman Perangkat Bergerak",
            nilaiAkhir = 61.20,
            huruf = 61.20.toHuruf(),
            lulus = apakahLulus(61.20)
        )

        tampilkanItem(binding.itemPertama, sari)
        tampilkanItem(binding.itemKedua, budi)

        binding.tvJumlahData.text = getString(R.string.format_jumlah_data, 2)
    }

    private fun tampilkanItem(item: com.trpl.sinilai.databinding.ItemNilaiBinding, data: Mahasiswa) {
        item.tvInisial.text = data.nama.toInisial()
        item.tvNama.text = data.nama
        item.tvNimMatkul.text = getString(R.string.format_nim_matkul, data.nim, data.prodi)
        item.tvNilaiAkhir.text = String.format("%.2f", data.nilaiAkhir)
        item.tvHurufMutu.text = data.huruf

        if (data.lulus) {
            item.tvStatus.text = getString(R.string.status_lulus)
            item.tvStatus.setBackgroundResource(R.drawable.bg_chip_hijau)
            item.tvStatus.setTextColor(getColor(R.color.hijau_sukses))
        } else {
            item.tvStatus.text = getString(R.string.status_tidak_lulus)
            item.tvStatus.setBackgroundResource(R.drawable.bg_chip_merah)
            item.tvStatus.setTextColor(getColor(R.color.merah_error))
        }
    }

    private fun pasangAksiTombol() {
        binding.btnSimpan.setOnClickListener {
            val (valid, jumlahGagal) = validasiFormulir()
            if (valid) {
                hitungDanTampilkan()
            } else {
                Snackbar.make(binding.root, getString(R.string.format_gagal, jumlahGagal), Snackbar.LENGTH_LONG).show()
            }
        }
        binding.btnBersihkan.setOnClickListener { bersihkanFormulir() }
    }

    private fun pasangPembersihError() {
        binding.etNama.setOnFocusChangeListener { _, f -> if (f) binding.tilNama.error = null }
        binding.etNim.setOnFocusChangeListener { _, f -> if (f) binding.tilNim.error = null }
        binding.etMatkul.setOnFocusChangeListener { _, f -> if (f) binding.tilMatkul.error = null }
        binding.etNilaiTugas.setOnFocusChangeListener { _, f -> if (f) binding.tilNilaiTugas.error = null }
        binding.etNilaiUts.setOnFocusChangeListener { _, f -> if (f) binding.tilNilaiUts.error = null }
        binding.etNilaiUas.setOnFocusChangeListener { _, f -> if (f) binding.tilNilaiUas.error = null }
        binding.etBobotTugas.setOnFocusChangeListener { _, f -> if (f) binding.tilBobotTugas.error = null }
        binding.etBobotUts.setOnFocusChangeListener { _, f -> if (f) binding.tilBobotUts.error = null }
        binding.etBobotUas.setOnFocusChangeListener { _, f -> if (f) binding.tilBobotUas.error = null }
    }

    private fun validasiFormulir(): Pair<Boolean, Int> {
        var gagal = 0

        val nama = binding.etNama.text.toString().trim()
        val nim = binding.etNim.text.toString().trim()
        val prodi = binding.acProdi.text.toString().trim()
        val matkul = binding.etMatkul.text.toString().trim()

        when {
            nama.isEmpty() -> { binding.tilNama.error = getString(R.string.error_nama_kosong); gagal++ }
            nama.length < 3 -> { binding.tilNama.error = getString(R.string.error_nama_pendek); gagal++ }
            else -> binding.tilNama.error = null
        }

        when {
            nim.isEmpty() -> { binding.tilNim.error = getString(R.string.error_nim_kosong); gagal++ }
            nim.length != 7 || !nim.all { it.isDigit() } -> { binding.tilNim.error = getString(R.string.error_nim_format); gagal++ }
            else -> binding.tilNim.error = null
        }

        if (prodi.isEmpty()) {
            binding.tilProdi.error = getString(R.string.error_prodi_kosong)
            gagal++
        } else {
            binding.tilProdi.error = null
        }

        if (matkul.isEmpty()) {
            binding.tilMatkul.error = getString(R.string.error_matkul_kosong)
            gagal++
        } else {
            binding.tilMatkul.error = null
        }

        gagal += validasiNilai(binding.etNilaiTugas.text.toString().trim(), binding.tilNilaiTugas)
        gagal += validasiNilai(binding.etNilaiUts.text.toString().trim(), binding.tilNilaiUts)
        gagal += validasiNilai(binding.etNilaiUas.text.toString().trim(), binding.tilNilaiUas)

        gagal += validasiBobot(binding.etBobotTugas.text.toString().trim(), binding.tilBobotTugas)
        gagal += validasiBobot(binding.etBobotUts.text.toString().trim(), binding.tilBobotUts)
        gagal += validasiBobot(binding.etBobotUas.text.toString().trim(), binding.tilBobotUas)

        val bobotTugas = binding.etBobotTugas.text.toString().trim().toIntOrNull()
        val bobotUts = binding.etBobotUts.text.toString().trim().toIntOrNull()
        val bobotUas = binding.etBobotUas.text.toString().trim().toIntOrNull()
        if (bobotTugas != null && bobotUts != null && bobotUas != null) {
            val total = totalBobot(listOf(
                KomponenNilai("Tugas", 0.0, bobotTugas),
                KomponenNilai("UTS", 0.0, bobotUts),
                KomponenNilai("UAS", 0.0, bobotUas)
            ))
            if (total != 100) {
                binding.tilBobotUas.error = getString(R.string.error_total_bobot)
                gagal++
            }
        }

        return Pair(gagal == 0, gagal)
    }

    private fun validasiNilai(teks: String, til: com.google.android.material.textfield.TextInputLayout): Int {
        val nilai = teks.toDoubleOrNull()
        return when {
            teks.isEmpty() -> { til.error = getString(R.string.error_nilai_kosong); 1 }
            nilai == null || nilai < 0.0 || nilai > 100.0 -> { til.error = getString(R.string.error_nilai_rentang); 1 }
            else -> { til.error = null; 0 }
        }
    }

    private fun validasiBobot(teks: String, til: com.google.android.material.textfield.TextInputLayout): Int {
        val bobot = teks.toIntOrNull()
        return when {
            teks.isEmpty() -> { til.error = getString(R.string.error_bobot_kosong); 1 }
            bobot == null || bobot <= 0 -> { til.error = getString(R.string.error_bobot_nol); 1 }
            else -> { til.error = null; 0 }
        }
    }

    private fun hitungDanTampilkan() {
        val komponen = listOf(
            KomponenNilai("Tugas", binding.etNilaiTugas.text.toString().toDouble(), binding.etBobotTugas.text.toString().toInt()),
            KomponenNilai("UTS", binding.etNilaiUts.text.toString().toDouble(), binding.etBobotUts.text.toString().toInt()),
            KomponenNilai("UAS", binding.etNilaiUas.text.toString().toDouble(), binding.etBobotUas.text.toString().toInt())
        )

        val akhir = hitungNilaiAkhir(komponen)
        val lulus = apakahLulus(akhir)

        binding.tvNilaiAkhir.text = String.format("%.2f", akhir)
        binding.tvHurufMutu.text = akhir.toHuruf()
        binding.tvBobotAngka.text = String.format("%.2f", akhir.toBobotAngka())
        binding.tvPredikat.text = akhir.toPredikat()

        if (lulus) {
            binding.tvStatusLulus.text = getString(R.string.status_lulus)
            binding.tvStatusLulus.setBackgroundResource(R.drawable.bg_chip_hijau)
            binding.tvStatusLulus.setTextColor(getColor(R.color.hijau_sukses))
        } else {
            binding.tvStatusLulus.text = getString(R.string.status_tidak_lulus)
            binding.tvStatusLulus.setBackgroundResource(R.drawable.bg_chip_merah)
            binding.tvStatusLulus.setTextColor(getColor(R.color.merah_error))
        }

        binding.cardPratinjau.visibility = View.VISIBLE
        binding.cardKosong.visibility = View.GONE

        Snackbar.make(binding.root, R.string.pesan_berhasil_dihitung, Snackbar.LENGTH_LONG).show()
    }

    private fun bersihkanFormulir() {
        binding.etNama.text?.clear()
        binding.etNim.text?.clear()
        binding.acProdi.setText("", false)
        binding.etMatkul.text?.clear()
        binding.etNilaiTugas.text?.clear()
        binding.etNilaiUts.text?.clear()
        binding.etNilaiUas.text?.clear()
        binding.etBobotTugas.text?.clear()
        binding.etBobotUts.text?.clear()
        binding.etBobotUas.text?.clear()

        binding.tilNama.error = null
        binding.tilNim.error = null
        binding.tilProdi.error = null
        binding.tilMatkul.error = null
        binding.tilNilaiTugas.error = null
        binding.tilNilaiUts.error = null
        binding.tilNilaiUas.error = null
        binding.tilBobotTugas.error = null
        binding.tilBobotUts.error = null
        binding.tilBobotUas.error = null

        binding.cardPratinjau.visibility = View.GONE
        binding.cardKosong.visibility = View.VISIBLE

        Snackbar.make(binding.root, R.string.pesan_dibersihkan, Snackbar.LENGTH_SHORT).show()
    }
}