import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ListBarang {
    ArrayList<Barang> barang;

    /**
     * Membuat daftar barang kosong.
     */
    public ListBarang() {
        this.barang = new ArrayList<>();
    }

    /**
     * Menambahkan barang baru ke daftar dan menyimpan perubahan ke file.
     */
    public void tambahBarang(Barang newBarang) {
        barang.add(newBarang);  
        simpanKeFile();
    }

    /**
     * Menghapus barang dari daftar dan memperbarui file.
     */
    public boolean hapusBarang(String kodeBarang) {
        Barang barangToDelete = null;
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                barangToDelete = b;
                break;
            }
        }
        
        if (barangToDelete != null) {
            barang.remove(barangToDelete);
            String fileName = "Admin/Barang/ListBarang.txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (Barang b : barang) {
                    writer.println("Kode Barang: " + b.getKodeBarang());
                    writer.println("Nama Barang: " + b.getNamaBarang());
                    writer.println("Harga: " + b.getHarga());
                    writer.println("Stok: " + b.getStok());
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

    /**
     * Mengedit detail barang dalam daftar dan memperbarui file.
     */
    public boolean editBarang(String kodeBarang, String namaBaru, int hargaBaru, int stokBaru) {
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                b.setNamaBarang(namaBaru);
                b.setHarga(hargaBaru);
                b.setStok(stokBaru);
                String fileName = "Admin/Barang/ListBarang.txt";
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    for (Barang bar : barang) {
                        writer.println("Kode Barang: " + bar.getKodeBarang());
                        writer.println("Nama Barang: " + bar.getNamaBarang());
                        writer.println("Harga: " + bar.getHarga());
                        writer.println("Stok: " + bar.getStok());
                        writer.println();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        System.out.println("\nKode Barang " + kodeBarang + " Tidak ditemukan.\n");
        return false;
    }

    /**
     * Menyimpan daftar barang ke file.
     */
    private void simpanKeFile() {
        String fileName = "Admin/Barang/ListBarang.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for(int i = 0; i < barang.size(); i++){
                writer.println("Kode Barang: " + barang.get(i).getKodeBarang());
                writer.println("Nama Barang: " + barang.get(i).getNamaBarang());
                writer.println("Harga: " + barang.get(i).getHarga());
                writer.println("Stok: " + barang.get(i).getStok());
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Membaca data barang dari file dan mengisi daftarnya.
     */
    public void bacaDariFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String kodeBarang = scanner.nextLine().split(": ")[1];
                String namaBarang = scanner.nextLine().split(": ")[1];
                int harga = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                int stok = Integer.parseInt(scanner.nextLine().split(": ")[1]);

                barang.add(new Barang(namaBarang, kodeBarang, harga, stok));
                scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Memeriksa apakah barang dengan kode tertentu ada dalam daftar.
     */
    public boolean idValidator(String kodeBarang) {
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                System.out.println("\nNama Barang: " + b.getNamaBarang());
                System.out.println("Harga: " + b.getHarga());
                System.out.println("Stok: " + b.getStok() + "\n");
                return true;
            }
        }
        System.out.println("\nKode Barang " + kodeBarang + " Tidak ditemukan.\n");
        return false;
    }

}
