import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerDriver extends Driver {
    Customer akun;
    Transaksi transaksi;
    Invoice invoice;
    ListBarang listBarang;
    Keranjang keranjang;
    Scanner input = new Scanner(System.in);
    CustomerImpl customerImpl = new CustomerImpl();


    public CustomerDriver(Customer akun) {
        this.akun = akun;
    }
    
    private class CustomerImpl implements CustomerDriver {
        
        @Override
        public void masukKeranjang() {
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");
            keranjang = new Keranjang();
            String kodeBarang;
            int jumlahBeli = 0;
            // int count = 0;
            
            showBarang();
            while (true) {
                System.out.print("Masukkan kode item yang ingin Anda tambahkan ke keranjang: ");
                kodeBarang = input.next();
                if (listBarang.idValidator(kodeBarang)) {
                    break;
                }
            }
            while (true) {
                while (true) {
                    try {
                        System.out.print("Jumlah: ");
                        jumlahBeli = input.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("\n=> Jenis input jumlah tidak valid!\n");
                        input.nextLine();
                    }
                    if(jumlahBeli > 0){
                        break;
                    }else{
                        System.out.println("\n=> Jumlah harus lebih dari 0\n");
                    }
                }
                if(keranjang.tambahBarang(kodeBarang, "Customer/Cust" + akun.getUsername() + "/Keranjang.txt", jumlahBeli) > 0){
                    System.out.println("\n=> Barang yang ada di keranjang Anda jumlahnya melebihi stok\n");
                }
                else{
                    break;
                }
            }
            
            System.out.println("\n=> Item berhasil ditambahkan ke keranjang\n");
            for (int i = 0; i <= 8000; i++) {
                if (i / 2000 == 0) {
                    continue;
                }
                System.out.print("\rMengalihkan ... " + i / 2000);
            }
            bersihkanConsole();
            
            
        }
        
        @Override
        public void checkoutBarang() {
            transaksi = new Transaksi();

            Path path = Paths.get("Customer/Cust" + akun.getUsername() + "/Transaksi.txt");
            try {
                byte[] fileContent = Files.readAllBytes(path);

                if (fileContent.length == 0) {
                    if(showCart()){
                        transaksi.buatTransaksi(akun.getUsername());
                        // bersihkanConsole(); 
                    }
                    else{
                        System.out.println("\n=> Anda tidak perlu membayar apa pun, keranjang tidak boleh kosong!\n");
                    }
                } else {
                    System.out.println("\n\n=> Anda masih memiliki transaksi dalam antrian");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Terjadi kesalahan saat memeriksa file.");
            }
        }
        
        @Override
        public void checkInvoice() {
            transaksi = new Transaksi();
            
            Path path = Paths.get("Customer/Cust" + akun.getUsername() + "/Invoice.txt");
            try {
                byte[] fileContent = Files.readAllBytes(path);

                if (fileContent.length == 0) {
                    System.out.println("\n=> Anda belum membuat invoice");
                } else {
                    transaksi.cekTransaksi(akun.getUsername());
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Terjadi kesalahan saat memeriksa file.");
            }
        }
        
        @Override
        public void showBarang() {
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");
            ArrayList<Barang> barang = listBarang.barang;
            System.out.println("\n" + "=".repeat(30) + " DAFTAR BARANG " + "=".repeat(30) + "\n");
            if (barang.isEmpty()) {
                System.out.println("=> barang tidak ditemukan\n");
            } else {
                for (Barang b : barang) {
                    if(b.getStok() > 0){
                        System.out.println("Kode barang: " + b.getKodeBarang());
                        System.out.println("Nama barang: " + b.getNamaBarang());
                        System.out.println("Price: " + b.getHarga());
                        System.out.println("Stock: " + b.getStok());
                        System.out.println();
                    }
                }
            }
            System.out.println("=".repeat(72) + "\n");
        }
        
        @Override
        public boolean showCart() {
            keranjang = new Keranjang();
            if(keranjang.lihatKeranjang(akun.getUsername()) == 0){
                return false;
            }
            return true;
        }
        
        @Override
        public void editCart() {
            keranjang = new Keranjang();
            String kodeBarang;
            
            if(!showCart()){
                System.out.println("=> Anda tidak memiliki item di keranjang Anda.");   
            }
            else{
                System.out.println("\n" + "=".repeat(30) + " EDIT BARANG DI KERANJANG " + "=".repeat(30) + "\n");
                while (true) {
                    System.out.print("Masukkan kode barang yang ingin Anda edit: ");
                    kodeBarang = input.next();
                    if (keranjang.idValidator(kodeBarang, akun.getUsername())) {
                        while (true){
                            int quantity;
                            while (true) {   
                                System.out.print("\nJumlah baru: ");
                                quantity = input.nextInt();
                                if(quantity > 0){
                                    break;
                                }
                                else{
                                    System.out.println("\n=> Jumlah harus lebih dari 0");
                                }
                            }
                            int count = keranjang.editBarang(kodeBarang, quantity, akun.getUsername());
                            if(count == 1){
                                System.out.println("\n=> Jumlah melebihi stock");
                            }
                            else{
                                break;
                            }
                        }

                        System.out.println("\n=> Jumlah item berhasil diedit\n");
                        break;
                    }
                }
                for (int i = 0; i <= 8000; i++) {
                    if (i / 2000 == 0) {
                        continue;
                    }
                    System.out.print("\rMengalihkan ... " + i / 2000);
                }
                bersihkanConsole();
            }
        }
        
        @Override
        public void deleteCart() {
            keranjang = new Keranjang();
            String kodeBarang;
            
            if(!showCart()){
                System.out.println("=> Anda tidak memiliki barang di keranjang Anda.");   
            }
            else{

                System.out.println("\n" + "=".repeat(30) + " HAPUS BARANG DI KERANJANG " + "=".repeat(30) + "\n");
                while (true) {
                    System.out.print("Masukkan kode barang yang ingin Anda hapus: ");
                    kodeBarang = input.next();
                    
                    if (keranjang.hapusBarang(kodeBarang, akun.getUsername())) {
                        System.out.println("\n=> Barang berhasil dihapus.\n");
                        break;
                    }
                    System.out.println("\n=> Kode barang tidak ditemukan.\n");
                }
                
                for (int i = 0; i <= 8000; i++) {
                    if (i / 2000 == 0) {
                        continue;
                    }
                    System.out.print("\rMengalihkan ... " + i / 2000);
                }
                bersihkanConsole();
            }
        }
    }
    
    /**
     * Menjalankan antarmuka pelanggan dengan berbagai opsi untuk mengelola item, keranjang, dan transaksi.
     */
    public void run() {
        
        int adminInput = 10;
        
        while (true) {

            System.out.println("\n" + "=".repeat(30) + " DASHBOARD PELANGGAN " + "=".repeat(30) + "\n");
            System.out.println("Pilih Opsi");
            System.out.println("1. Lihat Barang");
            System.out.println("2. Lihat Keranjang");
            System.out.println("3. Tambah Barang ke keranjang");
            System.out.println("4. Edit Barang di keranjang");
            System.out.println("5. Hapus Barang di keranjang");
            System.out.println("6. Minta Pembayaran");
            System.out.println("7. Lihat Invoice");
            System.out.println("0. Keluar");

            while (true) {
                try {
                    System.out.print("\nInput : ");
                    adminInput = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\n=> Jenis masukan harus berupa angka!");
                    input.nextLine();
                }
                if (adminInput < 0 || adminInput > 7) {
                    System.out.println("\n=> Silakan masukkan pilihan nomor yang tersedia");
                } else if (adminInput == 1) {
                    bersihkanConsole();
                    customerImpl.showBarang();
                    System.out.println("Pilih Opsi");
                    System.out.println("1. Lihat Barang");
                    System.out.println("2. Lihat Keranjang");
                    System.out.println("3. Tambah Barang ke keranjang");
                    System.out.println("4. Edit Barang di keranjang");
                    System.out.println("5. Hapus Barang di keranjang");
                    System.out.println("6. Request Checkout");
                    System.out.println("7. Lihat Invoice");
                    System.out.println("0. Keluar");
                } else {
                    break;
                }
            }

            if (adminInput == 2) {
                bersihkanConsole();
                customerImpl.showCart();
            } else if (adminInput == 3) {
                bersihkanConsole();
                customerImpl.masukKeranjang();
            } else if (adminInput == 4) {
                bersihkanConsole();
                customerImpl.editCart();
            } else if (adminInput == 5) {
                bersihkanConsole();
                customerImpl.deleteCart();
            } else if (adminInput == 6) {
                bersihkanConsole();
                customerImpl.checkoutBarang();
            } else if (adminInput == 7) {
                bersihkanConsole();
                customerImpl.checkInvoice();
            } else if (adminInput == 0) {
                bersihkanConsole();
                break;
            }
        }
        System.out.println("\n=> Logout Berhasil, Terima kasih...\n");
    }
}
