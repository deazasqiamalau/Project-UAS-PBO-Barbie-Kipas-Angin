/*Barbie kipas Angin*/

public class COD extends Pembayaran {

    @Override
    public void prosesPembayaran(String username, int totalHarga) {
        // Menampilkan pesan permintaan metode pembayaran COD
        System.out.println("\n=> Memproses metode pembayaran Cash On Delivery . . .\n");

        // Simulasi proses redirect
        for (int i = 0; i <= 100000; i++) {
            if (i / 20000 == 0) {
                continue;
            }
            System.out.print("\rRedirecting ... " + i / 20000);
        }
        bersihkanConsole();

        // Menampilkan informasi pembayaran COD
        System.out.println("=".repeat(10) + " Cash On Delivery " + "=".repeat(10));
        System.out.println("\nInformasi Pembayaran\n");
        System.out.println("Nama pengguna pada marketplace: " + username);
        System.out.println("Total pembayaran: " + totalHarga);
        System.out.println("");
        System.out.println("=".repeat(39));
        System.out.println("\n=> Metode pembayaran Cash On Delivery berhasil diminta.");
    }
}
