import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Keranjang {
    ArrayList<Barang> barang;
    ListBarang listBarang;

    /**
     * Membuat Keranjang Belanja Kosong
     */
    public Keranjang() {
        this.barang = new ArrayList<>();
    }

    /**
     * Menambahkan Jumlah Item tertentu ke Keranjang Belanja.
     */
    public int tambahBarang(String kodeBarang, String fileName, int jumlahBeli) {
        listBarang = new ListBarang();
        listBarang.bacaDariFile(fileName);
        barang = listBarang.barang;

        ListBarang admin = new ListBarang();
        admin.bacaDariFile("Admin/Barang/ListBarang.txt");
        ArrayList<Barang> barangAdmin = new ArrayList<>();
        barangAdmin = admin.barang;

        int jumlahLama = 0;
        int mark = 0;

        boolean exists = false;
        for (Barang b : barang) {
            for(Barang brg : barangAdmin){
                if (b.getKodeBarang().equals(brg.getKodeBarang()) && b.getKodeBarang().equals(kodeBarang)) {
                    jumlahLama = b.getHarga();
                    
                    if((jumlahLama + jumlahBeli) > brg.getStok()){
                        mark = 1;
                        break;
                    }
                    exists = true;
                    b.setHarga(b.getHarga() + jumlahBeli);
                    break;
                }
            }
        }

        if(mark == 1){
            return 1;
        }

        if (exists) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (int i = 0; i < barang.size(); i++) {
                    Barang bar = barang.get(i);
                    writer.println("Kode Barang: " + bar.getKodeBarang());
                    writer.println("Nama Barang: " + bar.getNamaBarang());
                    writer.println("Jumlah: " + bar.getHarga());
                    if (bar.getKodeBarang().equals(kodeBarang)) {
                        writer.println("Harga: " + (bar.getStok() / jumlahLama) * bar.getHarga());
                    } else {
                        writer.println("Harga: " + bar.getStok());
                    }
                    writer.println();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                
                listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");
                barang = listBarang.barang;
                for (Barang b : barang) {
                    if (b.getKodeBarang().equals(kodeBarang)) {
                        barang.clear();
                        Barang newBarang = new Barang(b.getNamaBarang(), kodeBarang, b.getHarga(), jumlahBeli);
                        barang.add(newBarang);
                        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
                            for (Barang bar : barang) {
                                if(jumlahBeli > b.getStok()){
                                    mark = 2;
                                    break;
                                }
                                else{
                                    writer.println("Kode Barang: " + bar.getKodeBarang());
                                    writer.println("Nama Barang: " + bar.getNamaBarang());
                                    writer.println("Jumlah: " + bar.getStok());
                                    writer.println("Harga: " + (bar.getHarga() * jumlahBeli));
                                    writer.println();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(mark == 2){
                        break;
                    }
                }
            } catch (Exception e) {

            }
        }

        return mark;
    }

    /**
     * Menampilkan item dalam keranjang belanja
     */
    public int lihatKeranjang(String username) {
        int count = 0;

        ListBarang listBarangAdmin = new ListBarang();
        listBarangAdmin.bacaDariFile("Admin/Barang/ListBarang.txt");
        List<Barang> barangAdmin = listBarangAdmin.barang;

        ListBarang listBarangKeranjang = new ListBarang();
        listBarangKeranjang.bacaDariFile("Customer/Cust" + username + "/Keranjang.txt");
        List<Barang> barangKeranjang = listBarangKeranjang.barang;

        System.out.println("\n" + "=".repeat(30) + " Barang keranjang " + "=".repeat(30) + "\n");

        try (PrintWriter writer = new PrintWriter(new FileWriter("Customer/Cust" + username + "/Keranjang.txt"))) {
            for (Barang b : barangKeranjang) {
                boolean existsInAdmin = false;
                for (int i = 0; i < barangAdmin.size(); i++) {
                    Barang ba = barangAdmin.get(i);
                    if (b.getKodeBarang().equals(ba.getKodeBarang())) {
                        count++;
                        existsInAdmin = true;
                        break;
                    }
                }

                if (existsInAdmin) {
                    writer.println("Kode Barang: " + b.getKodeBarang());
                    writer.println("Nama Barang: " + b.getNamaBarang());
                    writer.println("Jumlah: " + b.getHarga());
                    writer.println("Harga: " + b.getStok());
                    writer.println();

                    System.out.println("Kode Barang: " + b.getKodeBarang());
                    System.out.println("Nama Barang: " + b.getNamaBarang());
                    System.out.println("Jumlah: " + b.getHarga());
                    System.out.println("Harga " + b.getStok() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(count == 0){
            System.out.println("=> Keranjang Kosong.\n");
        }
        
        System.out.println("=".repeat(72) + "\n");
        return count;
    }

    /**
     * Mengedit jumlah item di keranjang belanja.
     */
    public int editBarang(String kodeBarang, int quantity, String username) {
        ListBarang admin = new ListBarang();
        admin.bacaDariFile("Admin/Barang/ListBarang.txt");
        ArrayList<Barang> barangAdmin = new ArrayList<>();
        barangAdmin = admin.barang;

        listBarang.bacaDariFile("Customer/Cust" + username + "/Keranjang.txt");
        barang = listBarang.barang;
        int jumlahLama = 0;
        int mark = 0;

        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                jumlahLama = b.getHarga();
                for(Barang brg : barangAdmin){
                    if(brg.getKodeBarang().equals(kodeBarang)){
                        if(brg.getStok() < quantity){
                            mark = 1;
                            return mark;
                        }
                    }
                }
                String fileName = "Customer/Cust" + username + "/Keranjang.txt";
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    for (int i = 0; i < barang.size() / 2; i++) {
                        Barang bar = barang.get(i);
                        writer.println("Kode Barang: " + bar.getKodeBarang());
                        writer.println("Nama Barang: " + bar.getNamaBarang());
                        if (bar.getKodeBarang().equals(kodeBarang)) {
                            writer.println("Jumlah: " + quantity);
                            writer.println("Harga: " + (bar.getStok() / jumlahLama) * quantity);
                        } else {
                            writer.println("Jumlah: " + bar.getHarga());
                            writer.println("Harga: " + bar.getStok());
                        }
                        writer.println();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return 2;
            }
        }

        System.out.println("\nKode Barang " + kodeBarang + " Tidak ditemukan.\n");
        return mark;
    }

    /**
     * Memeriksa apakah item dengan kode yang ditentukan ada di keranjang.
     */
    public boolean idValidator(String kodeBarang, String username) {
        listBarang = new ListBarang();
        listBarang.bacaDariFile("Customer/Cust" + username + "/Keranjang.txt");
        barang = listBarang.barang;
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                System.out.println("\nNama Barang: " + b.getNamaBarang());
                System.out.println("Jumlah: " + b.getHarga());
                System.out.println("Harga " + b.getStok());
                return true;
            }
        }
        System.out.println("\nKode Barang " + kodeBarang + " Tidak ditemukan.\n");
        return false;
    }

    /**
     * Menghapus item dari keranjang belanja.
     */
    public boolean hapusBarang(String kodeBarang, String username) {
        listBarang = new ListBarang();
        listBarang.bacaDariFile("Customer/Cust" + username + "/Keranjang.txt");
        barang = listBarang.barang;
        Barang barangToDelete = null;
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                barangToDelete = b;
                break;
            }
        }
        if (barangToDelete != null) {
            barang.remove(barangToDelete);
            String fileName = "Customer/Cust" + username + "/Keranjang.txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (Barang b : barang) {
                    writer.println("Kode Barang: " + b.getKodeBarang());
                    writer.println("Nama Barang: " + b.getNamaBarang());
                    writer.println("Jumlah: " + b.getHarga());
                    writer.println("Harga: " + b.getStok());
                    writer.println();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

}
