import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


/**
 * @author Kelompok 4
 * @version 1.0
 * @since 2023-12-01
 */
public class Transaksi {
    public ListBarang listBarangAdmin;
    public ListBarang listBarangKeranjang;
    public ArrayList<Barang> barangAdmin;
    public ArrayList<Barang> barangKeranjang;
    Invoice invoice;
    public Random random = new Random();
    public LocalDate currentDate = LocalDate.now();
    public int lastTwoDigitsOfYear = currentDate.getYear() % 100;
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
    public int randomNumber = random.nextInt(900) + 100;
    private List<String> daftarKodeTransaksiUnik = new ArrayList<>();
    private String kodeTransakasi;
    private int jumlahHarga;
    private String metodePembayaran;
    
    Scanner input = new Scanner(System.in);
    
    public Transaksi() {
        this.barangAdmin = new ArrayList<>();
        this.barangKeranjang = new ArrayList<>();
        this.listBarangAdmin = new ListBarang();
        this.listBarangKeranjang = new ListBarang(); 
    }

    public void setKodeTransakasi(String kodeTransakasi) {
        this.kodeTransakasi = kodeTransakasi;
    }

    public String getKodeTransakasi() {
        return kodeTransakasi;
    }

    public void setJumlahHarga(int jumlahHarga) {
        this.jumlahHarga = jumlahHarga;
    }

    public int getJumlahHarga() {
        return jumlahHarga;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

     /**
     * Writes transaction details to the specified file, including items, payment method, and total amount.
     *
     * @param fileName          The name of the file to write transaction details.
     * @param barangKeranjang   List of items in the shopping cart.
     * @param username          The username of the customer.
     * @param totalHarga        The total amount of the transaction.
     * @param metodePembayaran  The payment method used for the transaction.
     * @param status            The status of the transaction (e.g., WAITING).
     */
    public void tulisInvoice(String fileName, ArrayList<Barang> barangKeranjang, String username, int totalHarga, String metodePembayaran, String status){
        setKodeTransakasi(username + "TRS" + currentDate.format(formatter).toString() + randomNumber + " - " + status);
        LocalDateTime currentDatewithTime = LocalDateTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println("Kode Transaksi: " + getKodeTransakasi());
            for (Barang b : barangKeranjang) {
                writer.println();
                writer.println("Item's Code: " + b.getKodeBarang());
                writer.println("Item's Name: " + b.getNamaBarang());
                writer.println("Quantity: " + b.getHarga());
                writer.println("Price: " + b.getStok());
                writer.println();
            }
            writer.println("Payment method: " + metodePembayaran);
            writer.println("Total Amount: " + totalHarga);
            writer.println("Created at: " + currentDatewithTime.format(formatterTime));
            writer.println();
            writer.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks and manages transactions for a given user.
     *
     * @param username The username of the customer.
     */
    public void cekTransaksi(String username){
        Invoice invoice = new Invoice();
        listBarangAdmin.bacaDariFile("Admin/Barang/ListBarang.txt");
        barangAdmin = listBarangAdmin.barang;

        listBarangKeranjang.bacaDariFile("Customer/Cus" + username + "/Transaksi.txt");
        barangKeranjang = listBarangKeranjang.barang;

        if(invoice.manageInvoice(username) > 0){
            try (PrintWriter writer = new PrintWriter("Customer/Cus" + username + "/Transaksi.txt")) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    
    /**
     * Initiates the checkout process for a user, including choosing payment method and generating invoices.
     *
     * @param username The username of the customer.
     */
    public void buatTransaksi(String username){
        listBarangKeranjang.bacaDariFile("Customer/Cus" + username + "/Keranjang.txt");
        barangKeranjang = listBarangKeranjang.barang;

        listBarangAdmin = new ListBarang();
        listBarangAdmin.bacaDariFile("Admin/Barang/ListBarang.txt");
        barangAdmin = listBarangAdmin.barang;

        String platform;
        int paymentMethod;
        int statusCheckout;
        setJumlahHarga(0);

        for (Barang b : barangKeranjang) {
            for(Barang bar : barangAdmin){
                if(b.getKodeBarang().equals(bar.getKodeBarang())){
                    if(bar.getStok() < b.getHarga()){
                        System.out.println(bar.getStok());
                        System.out.println("Failed processing transaction");
                        System.out.println("Quantity over the stock\n");
                        System.out.println("Item's code: " + b.getKodeBarang());
                        System.out.println("Item's name: " + b.getNamaBarang());
                        System.out.println("Quantity: " + b.getHarga());
                        return;
                    }
                }
            }
            setJumlahHarga(getJumlahHarga() + b.getStok());
        }

        System.out.println("=".repeat(50) + " Checkout Request " + "=".repeat(50));
        System.out.println("\n1. Bank Transfer ");
        System.out.println("2. QRIS ");
        System.out.println("3. Cash On Delivery ");
        System.out.print("\nKindly choose your payment method: ");
        paymentMethod = input.nextInt();

        Pembayaran metodePembayaran;

        if(paymentMethod == 1){
            platform = "Bank Transfer";
            metodePembayaran = new Bank();
        }
        else if(paymentMethod == 2){
            platform = "QRIS";
            metodePembayaran = new Qris();
        }
        else{
            platform = "COD";
            metodePembayaran = new COD();
        }
        metodePembayaran.prosesPembayaran(username, getJumlahHarga());


        System.out.println("\n1. Request checkout");
        System.out.println("2. Cancel");
        System.out.print("\nInput: ");
        statusCheckout = input.nextInt();
        if(statusCheckout == 2){
            System.out.println("\n=> Checkout request cancelled\n");
            for(int i = 0; i <= 8000; i++){
                if(i/2000 == 0){
                    continue;
                }
                System.out.print("\rRedirecting ... " + i/2000);
            }
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("Customer/Cus" + username + "/Transaksi.txt"))) {
            for (Barang b : barangKeranjang) {
                writer.println("Kode Barang: " + b.getKodeBarang());
                writer.println("Nama Barang: " + b.getNamaBarang());
                writer.println("Jumlah: " + b.getHarga());
                writer.println("Harga: " + b.getStok());
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tulisInvoice("Admin/Transaksi/Transaksi.txt", barangKeranjang, username, getJumlahHarga(), platform, "MENUNGGU");
        tulisInvoice("Customer/Cus" + username + "/Invoice.txt", barangKeranjang, username, getJumlahHarga(), platform, "MENUNGGU");

        barangKeranjang.clear();
        try (PrintWriter writer = new PrintWriter(new FileWriter("Customer/Cus" + username + "/Keranjang.txt"))) {
            writer.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n=> Checkout requested successfully\n");
        for(int i = 0; i <= 8000; i++){
                if(i/2000 == 0){
                    continue;
                }
            }
        return;
    }

    /**
     * Reads transaction details from the specified file and populates the unique transaction codes.
     *
     * @param namaFile The name of the file to read transaction details.
     */
    public void bacaDariFile(String namaFile) {
        try (Scanner scanner = new Scanner(new File(namaFile))) {
            while (scanner.hasNextLine()) {
                String baris = scanner.nextLine();
                if (baris.startsWith("Kode Transaksi:")) {
                    String kodeTransaksi = baris.split(": ")[1];

                    if (!daftarKodeTransaksiUnik.contains(kodeTransaksi)) {
                        daftarKodeTransaksiUnik.add(kodeTransaksi);
                    }
                    lewatiBaris(scanner, 4);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Skips a specified number of lines while reading from a file.
     *
     * @param scanner     The Scanner object for reading from the file.
     * @param jumlahBaris The number of lines to skip.
     */
    private void lewatiBaris(Scanner scanner, int jumlahBaris) {
        for (int i = 0; i < jumlahBaris && scanner.hasNextLine(); i++) {
            scanner.nextLine();
        }
    }

     /**
     * Gets the list of unique transaction codes.
     *
     * @return The list of unique transaction codes.
     */
    public List<String> getDaftarKodeTransaksiUnik() {
        return daftarKodeTransaksiUnik;
    }

    /**
     * Validates a transaction code by checking if it exists in the list of unique transaction codes.
     *
     * @param kodeTransaksi The transaction code to validate.
     * @return {@code true} if the transaction code is valid, {@code false} otherwise.
     */
    public boolean KodeValidator(String kodeTransaksi) {
        return daftarKodeTransaksiUnik.contains(kodeTransaksi);
    }

    /**
     * Clears the console screen.
     */
    public static void bersihkanConsole() {
        try {
            Process process = new ProcessBuilder("cmd", "/c", "cls", "clear").inheritIO().start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
