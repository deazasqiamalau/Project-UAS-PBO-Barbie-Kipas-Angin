public class Barang {

    private String kodeBarang;
    private String namaBarang;
    private int harga;
    private int stok;

    public Barang(String namaBarang, String kodeBarang, int harga, int stok) {
        this.namaBarang = namaBarang;
        this.kodeBarang = kodeBarang;
        this.harga = harga;
        this.stok = stok;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public int getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}
