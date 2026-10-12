package khachhang;
import java.util.Scanner;

public class KhachHang {
    //Thuộc tính
    private String maKH;
    private String tenKH;
    private String soDienThoai;
    //constructor
    public KhachHang() {

    }

    public KhachHang(String maKH, String tenKH, String soDienThoai) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.soDienThoai = soDienThoai;
    }
    // get && set
    public String getMaKH() {
        return maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    //Nhập khách hàng
    public void nhap(){

        System.out.println("Nhập mã khách hàng: ");
        maKH = TienIch.nhapChuoi();
        System.out.println("Nhập tên khách hàng: ");
        tenKH = TienIch.nhapChuoi();
        System.out.println("Nhập số điện thoại: ");
        soDienThoai = TienIch.nhapChuoi();
    }
    //Xuất khách hàng
    public void xuat(){

        System.out.println("Mã khách hàng: " + maKH);
        System.out.println("Tên khách hàng: " + tenKH);
        System.out.println("Số điện thoại: " + soDienThoai);
    }
}

