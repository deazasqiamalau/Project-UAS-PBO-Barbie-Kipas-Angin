import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends Akun {
    public Keranjang keranjang;
    public ArrayList<Invoice> invoiceSelesai;
    private String fileName = "Customer/Credentials/AkunCustomer.txt";
    private int id_Customer = 0;
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public void saveToTextFile(String username, String password) {
        // Menentukan nama file untuk menyimpan data pengguna
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            int id = id_Customer += 1;
            
            writer.println("id: " + id);
            writer.println("Username: " + username);
            writer.println("Password: " + password);
            writer.println(); // Tambahkan baris kosong antara setiap entri pengguna
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String folderName = "Cust" + username;

        File directory = null;
        File keranjangFile = null;
        File invoiceFile = null;
        File transaksiFile = null;

        try {
            // Create the Customer folder if it doesn't exist
            directory = new File("Customer", folderName);
            directory.mkdirs();
                
            // Create Keranjang.txt file
            keranjangFile = new File(directory, "Keranjang.txt");
            keranjangFile.createNewFile();
                
            // Create Invoice.txt file
            invoiceFile = new File(directory, "Invoice.txt");
            invoiceFile.createNewFile();
                
            // Create Transaksi.txt file
            transaksiFile = new File(directory, "Transaksi.txt");
            transaksiFile.createNewFile();
                
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat mencoba membuat file.");
            e.printStackTrace();
        }
        
    }
    
    @Override
    public List<String> readCustomerAccounts() {
        List<String> accounts = new ArrayList<>();
        
        
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String id = scanner.nextLine().split(":")[1].trim();
                String username = scanner.nextLine().split(":")[1].trim();
                String password = scanner.nextLine().split(":")[1].trim();
                
                id_Customer = Integer.parseInt(id);
                accounts.add(username);
                accounts.add(password);
                
                scanner.nextLine();
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan");;
        }
        
        return accounts;
    }
    
    @Override
    public boolean validateSignIn(String username, String password) {
        
        List<String> accounts = readCustomerAccounts();
        
        for (int i = 0; i < accounts.size(); i += 2) {
            String uname = accounts.get(i);
            String pword = accounts.get(i + 1);
            
            if (username.equals(uname) && password.equals(pword)) {
                this.setUsername(username);
                return true;
            }
        }
        return false;
        
    }
    
    public int validateSignUp(String username, String password) {
        List<String> accounts = readCustomerAccounts();
        
        
        for (int i = 0; i < accounts.size(); i += 2) {
            String uname = accounts.get(i);
            String pword = accounts.get(i + 1);
            
            if (username.equals(uname) && password.equals(pword)) {
                System.out.println("\n=> Kamu sudah memiliki akun sebelumnya\n");
                return 0;
            }
            if (username.equals(uname)) {
                return 1;
            }
        }
        return 2;
        
    }
    
    public String getUsername() {
        return this.username;
    }
}
