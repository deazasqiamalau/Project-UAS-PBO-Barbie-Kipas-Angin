/**
 * @author Kelompok 4
 * @version 1.0
 * @since 2023-12-01
 */
public class Barang {

    private String kodeBarang;
    private String namaBarang;
    private int harga;
    private int stok;

    /**
     * Constructs a new product with the specified attributes.
     *
     * @param namaBarang The name of the product.
     * @param kodeBarang The code of the product.
     * @param harga      The price of the product.
     * @param stok       The stock quantity of the product.
     */
    public Barang(String namaBarang, String kodeBarang, int harga, int stok) {
        this.namaBarang = namaBarang;
        this.kodeBarang = kodeBarang;
        this.harga = harga;
        this.stok = stok;
    }

    /**
     * Gets the product code.
     *
     * @return The product code.
     */
    public String getKodeBarang() {
        return kodeBarang;
    }

    /**
     * Gets the name of the product.
     *
     * @return The name of the product.
     */
    public String getNamaBarang() {
        return namaBarang;
    }

    /**
     * Gets the price of the product.
     *
     * @return The price of the product.
     */
    public int getHarga() {
        return harga;
    }

    /**
     * Gets the stock quantity of the product.
     *
     * @return The stock quantity of the product.
     */
    public int getStok() {
        return stok;
    }

    /**
     * Sets the name of the product.
     *
     * @param namaBarang The new name of the product.
     */
    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    /**
     * Sets the price of the product.
     *
     * @param harga The new price of the product.
     */
    public void setHarga(int harga) {
        this.harga = harga;
    }

    /**
     * Sets the stock quantity of the product.
     *
     * @param stok The new stock quantity of the product.
     */
    public void setStok(int stok) {
        this.stok = stok;
    }
}
