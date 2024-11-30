import java.util.List;

/**
* @author Kelompok Barbie Kipas Angin
* @since 2024-11-30
*/

abstract public class Akun {

    //Menyimpan informasi akun pengguna ke dalam file teks.
    public abstract void saveToTextFile(String username, String password);
    
    //Membaca dan mengembalikan daftar akun pelanggan dari sumber data.
    public abstract List<String> readCustomerAccounts();
    
    //Memvalidasi kredensial masuk untuk pengguna.
    public abstract boolean validateSignIn(String username, String password);

    //Memvalidasi kredensial pendaftaran untuk pengguna baru.
    public abstract int validateSignUp(String username, String password);
}
