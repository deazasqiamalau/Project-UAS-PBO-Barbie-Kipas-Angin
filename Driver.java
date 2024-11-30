import java.io.IOException;


abstract public class Driver {

    public static void bersihkanConsole() {
        try {
            Process process = new ProcessBuilder("cmd", "/c", "cls", "clear").inheritIO().start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    interface AdminDriver {
        
        void deleteUser();

        void addBarang();

        boolean showBarang();

        void editBarang();

        void deleteBarang();

        void mengaturTransaksi();
    }


    interface CustomerDriver {
       
        void showBarang();

        void masukKeranjang();

        boolean showCart();

        void editCart();

        void deleteCart();

        void checkoutBarang();

        void checkInvoice();
    }

    public abstract void run();
}
