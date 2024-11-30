import java.util.InputMismatchException;

/*Barbie Kipas Angin*/ 

public class Bank extends Pembayaran {

    @Override
    public void prosesPembayaran(String username, int totalHarga) {
        String namaBank;
        int bankOption;

        // Menampilkan daftar pilihan bank
        System.out.println("\n1. BSI");
        System.out.println("2. BCA");
        System.out.println("3. BNI");
        System.out.print("\nPilih rekening bank Anda: ");
        bankOption = input.nextInt();

        // Menentukan nama bank berdasarkan pilihan
        namaBank = (bankOption == 1) ? "BSI" : (bankOption == 2) ? "BCA" : "BNI";

        // Meminta PIN bank hingga input valid
        while (true) {
            try {
                System.out.print("\nMasukkan PIN " + namaBank + " Anda: ");
                input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("\n=> PIN tidak valid. Masukkan kembali.");
                input.nextLine(); // Membersihkan buffer input
            }
        }

        bersihkanConsole();

        // Menampilkan detail informasi pembayaran
        System.out.println("=".repeat(10) + " " + namaBank + " " + "=".repeat(10));
        System.out.println("\nInformasi Pembayaran\n");
        System.out.println("Nama pengguna pada marketplace: " + username);
        System.out.println("Total pembayaran: " + totalHarga);
        System.out.println("");
        System.out.println("=".repeat(25));
    }
}
