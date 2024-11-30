import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;


public class Invoice {

    /**
     * Referensi ke objek Transaksi untuk menangani transaksi.
     */
    public Transaksi transaksi;

    /**
     *Referensi ke objek Pembayaran untuk menangani pembayaran.
     */
    public Pembayaran pembayaran;

    /**
     * Menampilkan data faktur untuk pelanggan tertentu.
     */
    public void tampilData(String username) {
        String invoiceFilePath = "Customer/Cus" + username + "/Invoice.txt";

        try (Scanner scanner = new Scanner(new File(invoiceFilePath))) {
            System.out.println("\n" + "=".repeat(30) + " INVOICES " + "=".repeat(30) + "\n");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("Created at")) {
                    System.out.println(line + "\n");
                    System.out.println("-".repeat(50));
                } else {
                    System.out.println(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mengelola faktur untuk pelanggan tertentu, memperbarui file faktur berdasarkan status transaksi.
     */
    public int manageInvoice(String username) {
        int mark = 0;
        transaksi = new Transaksi();
        transaksi.bacaDariFile("Admin/Transaksi/Transaksi.txt");
        List<String> daftarKodeTransaksi = transaksi.getDaftarKodeTransaksiUnik();

        Transaksi invoice = new Transaksi();
        invoice.bacaDariFile("Customer/Cus" + username + "/Invoice.txt");
        List<String> daftarKodeInvoice = invoice.getDaftarKodeTransaksiUnik();

        for (String kodeTransaksi : daftarKodeTransaksi) {
            for (int i = 0; i < daftarKodeInvoice.size(); i++) {
                String kodeInvoice = daftarKodeInvoice.get(i);
                if (kodeInvoice.contains("MENUNGGU")) {
                    if (kodeTransaksi.equals(kodeInvoice.replace("MENUNGGU", "BERHASIL"))) {
                        daftarKodeInvoice.set(i, kodeTransaksi);
                        updateInvoiceFile(username, kodeInvoice, kodeTransaksi);
                        mark = 1;

                    } else if (kodeTransaksi.equals(kodeInvoice.replace("MENUNGGU", "BERHASIL"))) {
                        daftarKodeInvoice.set(i, kodeTransaksi + " - pengembalian dana berhasil diproses!");
                        updateInvoiceFile(username, kodeInvoice, kodeTransaksi + " - pengembalian dana berhasil diproses!");
                        mark = 2;
                    }
                }
            }
        }
        tampilData(username);

        return mark;
    }

    /**
     * Updates the content of the invoice file with the new transaction code.
     */
    private void updateInvoiceFile(String username, String oldKode, String newKode) {
        String invoiceFilePath = "Customer/Cus" + username + "/Invoice.txt";

        try (Scanner scanner = new Scanner(new File(invoiceFilePath));
             PrintWriter writer = new PrintWriter("Customer/Cus" + username + "/Invoice_temp.txt")) {

            while (scanner.hasNextLine()) {
                String baris = scanner.nextLine();

                if (baris.contains(oldKode)) {
                    baris = baris.replace(oldKode, newKode);
                }

                writer.println(baris);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the temporary file to the original file
        File fileLama = new File(invoiceFilePath);
        File fileBaru = new File("Customer/Cus" + username + "/Invoice_temp.txt");
        fileLama.delete();
        fileBaru.renameTo(fileLama);
    }
}
