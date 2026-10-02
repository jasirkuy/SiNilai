# SiNilai

Aplikasi Android sederhana untuk mencatat dan menghitung nilai akhir mahasiswa. Dibuat sebagai Tugas Rumah Pertemuan 3 mata kuliah Pemrograman Perangkat Bergerak, Program Studi Teknologi Rekayasa Perangkat Lunak.

## Fitur

- Formulir input data mahasiswa (nama, NIM, program studi, mata kuliah) dan komponen nilai (Tugas, UTS, UAS beserta bobotnya)
- Validasi seluruh kolom sekaligus, dengan pesan kesalahan tepat di bawah kolom yang bermasalah
- Perhitungan nilai akhir, huruf mutu (A–E), bobot angka (0–4), predikat, dan status kelulusan
- Kartu pratinjau hasil yang muncul setelah perhitungan berhasil
- Daftar data tersimpan yang ditampilkan dalam bentuk kartu

## Teknologi

- Kotlin
- Android Views + ViewBinding
- ConstraintLayout
- Material Design 3 (TextInputLayout, MaterialCardView, MaterialButton)

## Aturan Perhitungan Nilai

Nilai akhir = Σ (nilai komponen × bobot ÷ 100)

| Rentang Nilai Akhir | Huruf | Bobot Angka | Predikat |
|---|---|---|---|
| 85.0 – 100.0 | A | 4.0 | Sangat Baik |
| 75.0 – 84.9 | B | 3.0 | Baik |
| 65.0 – 74.9 | C | 2.0 | Cukup |
| 55.0 – 64.9 | D | 1.0 | Kurang |
| < 55.0 | E | 0.0 | Sangat Kurang |

Batas kelulusan: nilai akhir ≥ 65.0.

## Struktur Project

```
app/src/main/
├── java/com/trpl/sinilai/
│   ├── MainActivity.kt    # Menghubungkan antarmuka dengan logika perhitungan
│   └── NilaiUtil.kt       # Data class dan fungsi perhitungan nilai
└── res/
    ├── layout/
    │   ├── activity_main.xml   # Layar utama
    │   └── item_nilai.xml      # Kartu satu data mahasiswa
    ├── values/                 # strings, colors, dimens, themes
    └── drawable/                # shape untuk avatar, chip status, dll
```

## Rencana Pengembangan

Project ini akan dikembangkan lebih lanjut sepanjang semester:

- [ ] Daftar data diubah menjadi `RecyclerView` yang dapat bertambah
- [ ] Kode dirapikan ke arsitektur MVVM dengan `ViewModel`
- [ ] Data disimpan permanen ke SQLite menggunakan Room

## Cara Menjalankan

1. Clone repository ini
2. Buka dengan Android Studio
3. Jalankan di emulator atau perangkat fisik dengan minimum Android 7.0 (API 24)
