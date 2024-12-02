**SISTEM BELANJA ONLINE WITH JAVA**

disusun oleh "Kelompok Barbie Kipas Angin"
Anggota Tim:
1. Shafa Disya Aulia (2308107010002)
2. Dea Zasqia Pasaribu Malau (2308107010004)
4. Tasya Zahrani (2308107010006)
5. Anisa Ramadhani (2308107010008)
6. Bunga Rasikhah Haya (2308107010010)
7. Khairun Nisa (2308107010074)

**PENDAHULUAN:**
Sistem Belanja Online with Java adalah aplikasi berbasis java yang dirancang untuk menyediakan platform belanja online. Sistem ini mendukung dua tipe pengguna utama: Admin dan Pelanggan, dengan fitur yang disesuaikan untuk masing-masing. Tujuan dari sistem ini adalah memberikan pengalaman belanja yang mudah, aman, dan terorganisasi, sekaligus mempermudah pengelolaan data produk serta transaksi oleh admin.

**FITUR-FITUR UTAMA SISTEM:**

**1. Proses Login Pengguna (Admin/Pelanggan):**
   Login menjadi pintu masuk utama untuk memastikan setiap pengguna dapat mengakses fitur sesuai hak aksesnya.
   - Validasi Kredensial:
     Setiap pengguna harus memasukkan username dan password yang terdaftar. Sistem memvalidasi data ini untuk menentukan apakah pengguna adalah Admin atau Pelanggan.
   - Dashboard Berdasarkan Jenis Akun:
      - Jika login berhasil, pengguna akan diarahkan ke:
        - Dashboard Admin untuk pengelolaan barang dan transaksi.
        - Dashboard Pelanggan untuk menjelajah produk dan melakukan pembelian.
      - Jika login gagal, sistem akan memberikan peringatan dan meminta pengguna untuk mencoba kembali.
   - Keamanan Login:
     Sistem menggunakan metode autentikasi untuk melindungi data pengguna dari akses yang tidak sah.


**2. Fitur Akun Admin:**
   Akun Admin memiliki akses penuh untuk mengelola seluruh sistem, termasuk pengelolaan pengguna dan produk. Berikut adalah beberapa tindakan yang dapat dilakukan oleh Admin melalui dashboard sistem:
   - Hapus Pengguna: Admin dapat menghapus akun pengguna yang terdaftar dalam sistem, memastikan hanya pengguna yang valid yang dapat mengakses platform.
   - Lihat Barang: Admin dapat melihat daftar barang yang terdaftar dalam sistem, memeriksa ketersediaan dan informasi setiap produk.
   - Tambah Barang: Admin memiliki kemampuan untuk menambahkan produk baru ke dalam sistem, memperluas pilihan yang tersedia bagi pengguna.
   - Ubah Barang: Admin dapat mengubah detail produk yang ada, seperti nama, harga, deskripsi, atau gambar, untuk memastikan informasi yang ditampilkan selalu terbaru.
   - Hapus Barang: Admin juga dapat menghapus produk dari sistem jika produk tersebut tidak lagi dijual atau tidak relevan.
   - Kelola Transaksi: Admin dapat melihat dan memproses transaksi yang dibuat oleh pengguna, memastikan setiap pembayaran diterima dan diproses dengan benar.
   - Keluar: Admin dapat keluar dari sistem untuk mengakhiri sesi.

**3. Fitur Akun Pelanggan:**
   Akun Pelanggan memungkinkan pengguna untuk menjelajahi produk, mengelola keranjang belanja, dan melakukan transaksi pembelian. Melalui dashboard pelanggan, berikut adalah beberapa opsi yang tersedia:
   - Lihat Barang: Pelanggan dapat melihat daftar produk yang tersedia di platform, mengeksplorasi berbagai pilihan yang ada.
   - Lihat Keranjang: Pelanggan dapat memeriksa barang-barang yang sudah ditambahkan ke keranjang belanja mereka.
   - Tambah Barang ke Keranjang: Pelanggan dapat memilih produk dan menambahkannya ke dalam keranjang untuk pembelian di masa mendatang.
   - Edit Barang di Keranjang: Pelanggan dapat mengubah jumlah atau jenis produk dalam keranjang sesuai dengan preferensi mereka.
   - Hapus Barang di Keranjang: Jika pelanggan berubah pikiran, mereka dapat menghapus barang yang tidak ingin dibeli dari keranjang belanja.
   - Minta Pembayaran: Setelah selesai memilih produk, pelanggan dapat mengajukan permintaan pembayaran untuk melanjutkan ke proses checkout.
   - Lihat Invoice: Pelanggan dapat melihat invoice atau rincian transaksi setelah pembayaran dilakukan dan transaksi diproses.
   - Keluar: Pelanggan dapat keluar dari akun mereka jika sudah selesai menggunakan aplikasi.
  
**4. Struktur Data Sistem:**
Penyimpanan data dilakukan menggunakan file teks dengan struktur berikut:
   1. Admin/Transaksi/Transaksi.txt, menyimpan data transaksi yang dikelola oleh admin.
   2. Customer/Cust<username>/Invoice.txt, menyimpan faktur setiap pelanggan berdasarkan username.
   3. Admin/Barang/ListBarang.txt, menyimpan daftar produk yang tersedia dalam sistem belanja online.
      Informasi yang disimpan yaitu:
      - Kode barang: Identifikasi unik untuk setiap barang.
      - Nama Barang: Nama produk untuk ditampilkan kepada pelanggan.
      - Harga Barang: Harga produk
      - Stok Barang: Jumlah barang yang tersedia untuk dibeli.
   4. Customer/Credentials/AkunCustomer.txt, menyimpan informasi akun pelanggan. File ini menyimpan data pelanggan seperti :
      - Username: Identifikasi unik untuk setiap pelanggan.
      - Password: Kata sandi yang digunakan untuk keamanan akun pelanggan.

**5. Alur kerja sistem:**
      - Pelanggan masuk ke akun mereka untuk melihat produk yang tersedia dan menambahkannya ke keranjang belanja.
      - Setelah melakukan checkout, admin akan memproses transaksi dan memperbarui statusnya dalam sistem.
      - sistem secara otomatis akan memperbarui dan menghasilkan faktur sesuai dengan transaksi yang berhasil.

