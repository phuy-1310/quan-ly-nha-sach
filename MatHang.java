package model;

public abstract class MatHang {
    private String ma;
    private String ten;
    private double giaNhap;
    private int soLuong;
    private double loinhuan;
    private double thue;

    // constructor

    public MatHang() {
        this.ma ="";
        this.ten = "";
        this.giaNhap =0;
        this.soLuong =0;
    }

    public MatHang(String ma, String ten, double giaNhap, int soLuong) {
        this.ma = ma;
        this.ten = ten;
        this.giaNhap = giaNhap;
        this.soLuong = soLuong;
    }

    // Get_xx & Set_xx
    public String getMa() {
        return ma;
    }
    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }
    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getGiaNhap() {return giaNhap;}
    public void setGiaNhap(double giaNhap){ this.giaNhap = giaNhap;}

    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    //Tinh gia ban
    public abstract double tinhGiaBan();

    // Nhap
    public void nhap() {

        ma = TienIch.nhapChuoi("Nhập mã: ");

        ten = TienIch.nhapChuoi("Nhập tên: ");

        giaNhap = TienIch.nhapGiaTien("Giá nhập hàng: ");

        soLuong = TienIch.nhapSoNguyenDuong("Nhập số lượng: ");

    }

    // Xuat
    public void xuat() {
        System.out.println("Mã: " + ma);
        System.out.println("Tên: " + ten);
        System.out.println("Số lượng: " + soLuong);
        System.out.println("Giá bán: " + tinhGiaBan());
    }
}
