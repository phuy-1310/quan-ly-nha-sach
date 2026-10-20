package phieunhap;

import java.time.LocalDate;

import nhacungcap.NhaCungCap;


public class ChiTietPhieuNhap {
    private LocalDate ngayNhap;
    private NhaCungCap nhacc;

//constructor
    public ChiTietPhieuNhap(){
        ngayNhap =  LocalDate.now();
        nhacc = new NhaCungCap();
    }

    public ChiTietPhieuNhap(LocalDate ngayNhap, NhaCungCap ncc){
        this.ngayNhap = ngayNhap;
        setNhaCC(ncc);
    }
 
//getter
    public LocalDate getNgayNhap(){
        return ngayNhap;
    }
    
    public NhaCungCap getNhaCungCap(){
        return nhacc;
    }
//setter
    public void setNgayNhap(LocalDate ngayNhap){
        this.ngayNhap=ngayNhap;
    }

    public void setNhaCC(NhaCungCap ncc){
        this.nhacc=ncc;
    }
}

