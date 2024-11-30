import java.util.InputMismatchException;

public class Qris extends Pembayaran {

    @Override
    public void prosesPembayaran(String username, int totalHarga) {
        bersihkanConsole();

        // Display QR code
        System.out.println("Silakan pindai QR ini\n");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print("#");
            }
            System.out.println();
        }
        System.out.println("");

        // Simulate redirection
        for (int i = 0; i <= 100000; i++) {
            if (i / 20000 == 0) {
                continue;
            }
            System.out.print("\rMengalihkan ... " + i / 20000);
        }

        while (true) {
            try{
                // Input QRIS PIN
                System.out.print("\nMasukkan PIN QRIS Anda: ");
                input.nextInt();
                break;
            } catch(InputMismatchException e){
                System.out.println("\n=> PIN tidak valid.");
                input.nextLine();
            }
        }

        bersihkanConsole();

        // Display payment information
        System.out.println("=".repeat(10) + " QRIS " + "=".repeat(10));
        System.out.println("\nInformasi Pembayaran\n");
        System.out.println("Nama Pengguna di Marketplace: " + username);
        System.out.println("Total: " + totalHarga);
        System.out.println("");
        System.out.println("=".repeat(26));
    }
}
