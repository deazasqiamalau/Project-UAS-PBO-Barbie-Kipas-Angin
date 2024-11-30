import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class AdminDriver extends Driver {
    Admin akun;
    ListBarang listBarang;
    Keranjang keranjang;
    Transaksi transaksi;
    ArrayList<Transaksi> listTransaksi;

    AdminImpl adminImpl = new AdminImpl();

    Scanner input = new Scanner(System.in);

    private static boolean deleteFolder(File folder) {
        if (!folder.exists()) {
            System.out.println("Folder tidak ditemukan.");
            return false;
        }

        if (!folder.isDirectory()) {
            System.out.println("Objek bukan direktori.");
            return false;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }

        return folder.delete();
    }


    private class AdminImpl implements AdminDriver {

        /**
         * Menghapus akun pengguna pelanggan berdasarkan ID pelanggan yang diberikan.
         */
        @Override
        public void deleteUser() {

            Path path = Paths.get("Customer/Credentials/AkunCustomer.txt");
            try {
                byte[] fileContent = Files.readAllBytes(path);

                if (fileContent.length == 0) {
                    System.out.println("\n=> Tidak ada pelanggan.");
                } else {

                    try (Scanner scanner = new Scanner(new File("Customer/Credentials/AkunCustomer.txt"))) {
                        System.out.println("\n" + "=".repeat(30) + " Pelanggan " + "=".repeat(30) + "\n");
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            System.out.println(line);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    int idCustomer;
                    System.out.println("\n" + "=".repeat(30) + " HAPUS PELANGGAN " + "=".repeat(30) + "\n");
                    System.out.print("ID pelanggan : ");
                    idCustomer = input.nextInt();

                    try {
                        File inputFile = new File("Customer/Credentials/AkunCustomer.txt");
                        File tempFile = new File("Customer/Credentials/AkunCustomer_temp.txt");

                        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                        PrintWriter writer = new PrintWriter(new FileWriter(tempFile));

                        String line;
                        boolean userFound = false;
                        String deletedUsername = null;

                        while ((line = reader.readLine()) != null) {
                            if (line.trim().startsWith("id:")) {
                                int idFromFile = Integer.parseInt(line.split(":")[1].trim());

                                if (idFromFile == idCustomer) {
                                    userFound = true;
                                    deletedUsername = reader.readLine().split(":")[1].trim();
                                    for (int i = 0; i < 2; i++) {
                                        reader.readLine();
                                    }
                                } else {
                                    writer.println(line);
                                }
                            } else {
                                writer.println(line);
                            }
                        }

                        reader.close();
                        writer.close();

                        File folder = new File("Customer/Cus" + deletedUsername);
                        if (!userFound) {
                            System.out.println("ID pelanggan tidak ditemukan.");
                        } else {
                            if (deleteFolder(folder)) {
                                System.out.println("\n=> Pengguna " + deletedUsername + " Berhasil Dihapus.\n");
                            } else {
                                System.out.println("Gagal menghapus folder.");
                            }
                            inputFile.delete();
                            tempFile.renameTo(inputFile);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i <= 8000; i++) {
                        if (i / 2000 == 0) {
                            continue;
                        }
                        System.out.print("\rRedirecting ... " + i / 2000);
                    }
                    bersihkanConsole();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Terjadi kesalahan saat memeriksa file.");
            }
        }

        /**
         * Menambahkan item baru ke daftar item yang tersedia.
         */
        @Override
        public void addBarang() {
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");
            Random random = new Random();
            Scanner input = new Scanner(System.in);
            LocalDate currentDate = LocalDate.now();
            int lastTwoDigitsOfYear = currentDate.getYear() % 100;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
            int randomNumber = random.nextInt(900) + 100;

            System.out.println("\n" + "=".repeat(30) + " Tambahkan/Buat Barang " + "=".repeat(30) + "\n");

            String kodeBarang = "CMK" + currentDate.format(formatter).toString() + randomNumber;

            System.out.print("Nama Barang: ");
            String namaBarang = input.next();

            int harga;
            while (true) {
                try {
                    System.out.print("Harga: ");
                    harga = input.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("\n=> Harga harus berupa angka\n");
                    input.nextLine();
                }
            }

            int stok;
            while (true) {
                try {
                    System.out.print("Stok: ");
                    stok = input.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("\n=> Stok harus berupa angka\n");
                    input.nextLine();
                }
            }

            Barang newBarang = new Barang(namaBarang, kodeBarang, harga, stok);

            listBarang.tambahBarang(newBarang);

            System.out.println("\n=> Item berhasil ditambahkan.\n");
            for (int i = 0; i <= 8000; i++) {
                if (i / 2000 == 0) {
                    continue;
                }
                System.out.print("\rRedirecting ... " + i / 2000);
            }
            bersihkanConsole();
        }

        /**
         * Menampilkan daftar barangyang tersedia.
         */
        @Override
        public boolean showBarang() {
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");
            ArrayList<Barang> barang = listBarang.barang;
            System.out.println("\n" + "=".repeat(30) + " DAFTAR BARANG " + "=".repeat(30) + "\n");
            if (barang.isEmpty()) {
                System.out.println("=> Barang tidak ditemukan\n");
                return false;
            } else {
                for (Barang b : barang) {
                    System.out.println("Kode Barang: " + b.getKodeBarang());
                    System.out.println("Nama Barang: " + b.getNamaBarang());
                    System.out.println("Harga: " + b.getHarga());
                    System.out.println("Stok: " + b.getStok());
                    System.out.println();
                }
            }
            System.out.println("=".repeat(72) + "\n");
            return true;
        }

        /**
         * Edits the details of an existing item.
         */
        @Override
        public void editBarang() {
            String kodeBarang;
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");

            if (showBarang()) {
                System.out.println("\n" + "=".repeat(30) + " EDIT BARANG" + "=".repeat(31) + "\n");
                while (true) {
                    System.out.print("Masukkan kode Barang yang ingin Anda edit: ");
                    kodeBarang = input.next();
                    if (listBarang.idValidator(kodeBarang)) {
                        break;
                    }
                    System.out.println("\n=> Kode barang tidak ditemukan.");
                }

                System.out.print("Nama Barang Baru: ");
                String namaBaru = input.next();

                int hargaBaru;
                while (true) {
                    try {
                        System.out.print("Harga Baru: ");
                        hargaBaru = input.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("\n=> Harga baru harus berupa angka\n");
                        input.nextLine();
                    }
                }

                int stokBaru;
                while (true) {
                    try {
                        System.out.print("Stok Baru: ");
                        stokBaru = input.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("\n=>Stok baru harus berupa angka\n");
                        input.nextLine();
                    }
                }

                listBarang.editBarang(kodeBarang, namaBaru, hargaBaru, stokBaru);
                System.out.println("\n=> Barang berhasil diedit.\n");
                for (int i = 0; i <= 8000; i++) {
                    if (i / 2000 == 0) {
                        continue;
                    }
                    System.out.print("\rRedirecting ... " + i / 2000);
                }
                bersihkanConsole();
            }
        }

        /**
         *Menghapus item dari daftar barang yang tersedia.
         */
        @Override
        public void deleteBarang() {
            String kodeBarang;
            int validation = 0;
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");

            if (showBarang()) {
                System.out.println("\n" + "=".repeat(30) + " HAPUS BARANG" + "=".repeat(30) + "\n");

                while (true) {
                    System.out.print("Masukkan kode barang yang ingin Anda hapus: ");
                    kodeBarang = input.next();
                    if (listBarang.idValidator(kodeBarang)) {
                        while (true) {
                            try {
                                System.out.println("1. Hapus");
                                System.out.println("2. Batal");
                                System.out.print("\nApakah Anda yakin ingin menghapus item ini? : ");
                                validation = input.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("\n=> Masukkan opsi yang tersedia");
                                input.nextLine();
                            }
                            if (validation == 1) {
                                break;
                            } else if (validation == 2) {
                                System.out.println("\n=> Dibatalkan.\n");
                                for (int i = 0; i <= 8000; i++) {
                                    if (i / 2000 == 0) {
                                        continue;
                                    }
                                    System.out.print("\rRedirecting ... " + i / 2000);
                                }
                                return;
                            }
                        }
                    }
                    listBarang.hapusBarang(kodeBarang);
                    break;
                }

                System.out.println("\n=> Item berhasil dihapus.\n");
                for (int i = 0; i <= 8000; i++) {
                    if (i / 2000 == 0) {
                        continue;
                    }
                    System.out.print("\rRedirecting ... " + i / 2000);
                }
                bersihkanConsole();
            }
        }

        /**
         * Mengelola transaksi dengan menerima atau menolaknya berdasarkan masukan admin.
         */
        @Override
        public void mengaturTransaksi() {
            String kodeTransaksi;
            String kodeBaru = null;
            String alert = null;
            int manageOption = 0;
            String updatedStatus = null;
            transaksi = new Transaksi();
            File transaksiFile = new File("Admin/Transaksi/Transaksi.txt");
            if (!transaksiFile.exists() || transaksiFile.length() == 0) {
                System.out.println("\n=> Tidak ada transaksi yang tersedia.");
                return;
            }
        
            transaksi.bacaDariFile("Admin/Transaksi/Transaksi.txt");
            List<String> daftarKodeTransaksi = transaksi.getDaftarKodeTransaksiUnik();
        
            if (daftarKodeTransaksi.isEmpty()) {
                System.out.println("\n=> Tidak ada transaksi yang perlu dikelola.");
                return;
            }

            System.out.println("\n" + "=".repeat(30) + " INVOICES MANAGER " + "=".repeat(30) + "\n");
            System.out.println("");
            try (Scanner scanner = new Scanner(new File("Admin/Transaksi/Transaksi.txt"))) {
                while (scanner.hasNextLine()) {
                    String baris = scanner.nextLine();
                    if (baris.contains("Dibuat di")) {
                        System.out.println(baris + "\n");
                        System.out.println("-".repeat(50));
                    } else {
                        System.out.println(baris);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while (true) {
                System.out.print("\nMasukkan kode transaksi yang ingin Anda kelola: ");
                kodeTransaksi = input.next();
                if (daftarKodeTransaksi.contains(kodeTransaksi + " - MENUNGGU")) {
                    break;
                }
                System.out.println("\n=> Kode transaksi tidak ditemukan");
            }

            while (true) {
                System.out.println("\n1. Menerima");
                System.out.println("2. Menolak");
                System.out.print("\nPilih opsinya: ");
                manageOption = input.nextInt();
                if (manageOption < 1 || manageOption > 2) {
                    System.out.println("\n=>Opsi tidak tersedia, silakan masukkan opsi yang tersedia!");
                } else {
                    break;
                }
            }

            for (int i = 0; i < daftarKodeTransaksi.size(); i++) {
                if ((kodeTransaksi + " - MENUNGGU").equals(daftarKodeTransaksi.get(i))) {
                    if (manageOption == 1) {
                        alert = "\n=> Transaksi berhasil diterima";
                        updatedStatus = " - BERHASIL";
                    } else {
                        alert = "\n=> Transaksi berhasil ditolak";
                        updatedStatus = " - MENOLAK";
                    }
                    kodeBaru = kodeTransaksi + updatedStatus;
                }
            }

            String username = getPrefixBeforeTRS(kodeTransaksi);

            if (manageOption == 1) {
                ListBarang listBarangAdmin = new ListBarang();
                listBarangAdmin.bacaDariFile("Admin/Barang/ListBarang.txt");
                ArrayList<Barang> barangAdmin = listBarangAdmin.barang;

                ListBarang listBarangKeranjang = new ListBarang();
                listBarangKeranjang.bacaDariFile("Customer/Cus" + username + "/Transaksi.txt");
                ArrayList<Barang> barangKeranjang = listBarangKeranjang.barang;

                for (Barang b : barangAdmin) {
                    for (Barang bar : barangKeranjang) {
                        if (b.getKodeBarang().equals(bar.getKodeBarang())) {
                            b.setStok(b.getStok() - bar.getHarga());
                        }

                        try (PrintWriter writer = new PrintWriter(new FileWriter("Admin/Barang/ListBarang.txt"))) {
                            for (Barang brg : barangAdmin) {
                                writer.println("Kode Barang: " + brg.getKodeBarang());
                                writer.println("Nama Barang: " + brg.getNamaBarang());
                                writer.println("Harga: " + brg.getHarga());
                                writer.println("Stok: " + brg.getStok());
                                writer.println();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            try (Scanner scanner = new Scanner(new File("Admin/Transaksi/Transaksi.txt"));
                    PrintWriter writer = new PrintWriter("Admin/Transaksi/Transaksi_temp.txt")) {

                while (scanner.hasNextLine()) {
                    String baris = scanner.nextLine();

                    if (baris.startsWith("Kode Transaksi: ")
                            && baris.equals("Kode Transaksi: " + kodeTransaksi + " - MENUNGGU")) {
                        writer.println("Kode Transaksi: " + kodeBaru);
                    } else {
                        writer.println(baris);
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Ganti file asli dengan file sementara
            File fileLama = new File("Admin/Transaksi/Transaksi.txt");
            File fileBaru = new File("Admin/Transaksi/Transaksi_temp.txt");
            fileLama.delete(); // Menghapus file lama
            fileBaru.renameTo(fileLama);
            System.out.println(alert + "\n");
            for (int i = 0; i <= 8000; i++) {
                if (i / 2000 == 0) {
                    continue;
                }
                System.out.print("\rRedirecting ... " + i / 2000);
            }
            bersihkanConsole();
        }

    }

    /**
     * Mengambil awalan kode transaksi sebelum pengidentifikasi "TRS".
     */
    private static String getPrefixBeforeTRS(String code) {
        int indexTRS = code.indexOf("TRS");
        if (indexTRS != -1) {
            return code.substring(0, indexTRS);
        } else {
            return code;
        }
    }

    /**
     * Menjalankan dasbor admin, memungkinkan admin memilih berbagai opsi
     * mengelola sistem.
     */

    public void run() {

        int adminInput = 10;

        while (true) {

            System.out.println("\n" + "=".repeat(30) + " ADMIN DASHBOARD " + "=".repeat(30) + "\n");
            System.out.println("Memilih Opsi");
            System.out.println("1. Hapus Pengguna");
            System.out.println("2. Lihat Barang");
            System.out.println("3. Tambah Barang");
            System.out.println("4. Ubah Barang");
            System.out.println("5. Hapus Barang");
            System.out.println("6. Kelola Transaksi");
            System.out.println("0. Keluar");

            while (true) {
                try {
                    System.out.print("\nInput : ");
                    adminInput = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\n=> Input type must be a number!");
                    input.nextLine();
                }
                if (adminInput < 0 || adminInput > 6) {
                    System.out.println("\n=> Silakan masukkan pilihan nomor yang tersedia");
                } else if (adminInput == 2) {
                    bersihkanConsole();
                    adminImpl.showBarang();
                    System.out.println("Memilih Opsi");
                    System.out.println("1. Hapus Pengguna");
                    System.out.println("2. Lihat Barang");
                    System.out.println("3. Tambah Barang");
                    System.out.println("4. Ubah Barang");
                    System.out.println("5. Hapus Barang");
                    System.out.println("6. Kelola Transaksi");
                    System.out.println("0. Keluar");
                } else {
                    break;
                }
            }

            if (adminInput == 1) {
                bersihkanConsole();
                adminImpl.deleteUser();
            } else if (adminInput == 3) {
                bersihkanConsole();
                adminImpl.addBarang();
            } else if (adminInput == 4) {
                bersihkanConsole();
                adminImpl.editBarang();
            } else if (adminInput == 5) {
                bersihkanConsole();
                adminImpl.deleteBarang();
            } else if (adminInput == 6) {
                bersihkanConsole();
                adminImpl.mengaturTransaksi();
            } else if (adminInput == 0) {
                bersihkanConsole();
                break;
            }
        }
        System.out.println("\n=> Logout Berhasil, Terima kasih...\n");

    }
}