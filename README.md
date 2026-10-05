# RouterAutoLogin

Aplikasi Android WebView yang langsung membuka `http://192.168.1.1/` dan mencoba mengisi login otomatis:
- Username: `user`
- Password: `masohi86`

## Build online dengan GitHub Actions
1. Buat repository GitHub baru, misalnya `RouterAutoLogin`.
2. Upload seluruh isi folder proyek ini ke branch `main`.
3. Buka tab **Actions**.
4. Pilih **Build Router Auto Login APK**.
5. Klik **Run workflow** jika workflow belum berjalan otomatis.
6. Setelah selesai, buka hasil workflow dan download artifact **RouterAutoLogin-debug-apk**.
7. Extract ZIP artifact, lalu instal `app-debug.apk` di HP.

Tidak perlu Android Studio dan tidak perlu emulator untuk proses build ini.

## Catatan
HP harus terhubung ke Wi-Fi yang sama dengan router `192.168.1.1` agar halaman router dapat dibuka.
