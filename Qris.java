import java.util.InputMismatchException;

public class Qris extends Pembayaran {

    @Override
    public void prosesPembayaran(String username, int totalHarga) {
        bersihkanConsole();

        // Display QR code
        System.out.println("Kindly scan this QR\n");
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
            System.out.print("\rRedirecting ... " + i / 20000);
        }

        while (true) {
            try{
                // Input QRIS PIN
                System.out.print("\nInput your QRIS PIN: ");
                input.nextInt();
                break;
            } catch(InputMismatchException e){
                System.out.println("\n=> PIN is not valid.");
                input.nextLine();
            }
        }

        bersihkanConsole();

        // Display payment information
        System.out.println("=".repeat(10) + " QRIS " + "=".repeat(10));
        System.out.println("\nPayment Information\n");
        System.out.println("Username on marketplace: " + username);
        System.out.println("Total: " + totalHarga);
        System.out.println("");
        System.out.println("=".repeat(26));
    }
}
