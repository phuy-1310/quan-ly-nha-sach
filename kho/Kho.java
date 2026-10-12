package kho;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import phieunhap.*;
import mathang.*;
import interfaces.IFileIO;
import tienich.*;


public class Kho implements IFileIO  {
    private static final int sucChuaToiDa = 10000;
    private HangTonKho[] dsMatHang;
    private int soLuongMatHang;
    
//số lượng ở đây là số lượng mặt hàng tồn trong kho, kh tính chi tiết từng mặt hàng có bn cái

    

    public Kho(){
        dsMatHang=new HangTonKho[1000];
        soLuongMatHang=0;
    }

//getter
    public int getSuChuaToiDa() {return sucChuaToiDa;}
    public HangTonKho[] getDanhSachTon(){
        return dsMatHang;
    }

    public int getSoLuongTon() {return soLuongMatHang;}

//lấy tổng số lượng của các mặt hàng vdu: sách toán có 4 quyển, sách văn có 3 quyển, tiểu thuyết x có y quyển -> 3+4+y
    public int getTongSoLuongTon() {
        int sum=0;
        for(int i=0;i<soLuongMatHang;i++)
            sum+=dsMatHang[i].getSoLuong();
        return sum;
    }
//kho trống
    public int getConTrong(){
        return sucChuaToiDa-getTongSoLuongTon();
    }
    
    public void kiemTraSucChua(){
        System.out.println("--- THÔNG TIN KHO ---");
        System.out.println("Tổng sức chứa: " + sucChuaToiDa);
        System.out.println("Đang chứa: " + getTongSoLuongTon());
        System.out.println("Không gian còn trống: " + getConTrong());

    }
//tự động tăng kích thước mảng
    public void tangKichThuocMang(){
        HangTonKho[] mangMoi= new HangTonKho[dsMatHang.length *2];
        for(int i=0;i<soLuongMatHang;i++){
            mangMoi[i]=dsMatHang[i];
        }
        dsMatHang=mangMoi;
    }

//tăng tồn kho: 1/ràng buộc mặt hàng, số lượng 2/tìm & ktra mặt hàng đã có trong dsmh hiện có chưa
    public boolean tangTonKho(MatHang mh, int soLuong){
        if(mh==null || soLuong<0) return false;
        if(soLuong>getConTrong()){
            System.out.println("Kho không đủ chỗ!");
            return false;
        }
        int vtMH = TienIch.timKiemViTri(dsMatHang, soLuongMatHang, mh.getMa());
        if(vtMH==-1){
            if(soLuongMatHang==dsMatHang.length) tangKichThuocMang();
            dsMatHang[soLuongMatHang]=new HangTonKho(mh, soLuong);
            soLuongMatHang++;
        }
        else dsMatHang[vtMH].tangSoLuong(soLuong);
        return true;
    }

//nhập hàng từ phiếu: 1/ràng buộc phiếu nhập 2/tăng tồn kho 3/set trạng thái pn
    public boolean nhapHangTuPhieu(PhieuNhap pn){
        if(pn == null) return false;
        int soLuongCanNhap =0;
        for(int i=0;i<pn.getSoLuongCT();i++){
            ChiTietPhieuNhap ct = pn.getChiTiet()[i];
            soLuongCanNhap+= ct.getSoLuong();
        }
        if(soLuongCanNhap>getConTrong()){
            System.out.println("Kho không đủ chỗ trống!");
            pn.setTrangThai(TrangThaiPN.DA_HUY);
            return false;
        }
        for(int i=0; i<pn.getSoLuongCT();i++){
            ChiTietPhieuNhap ct = pn.getChiTiet()[i];
            if(!tangTonKho(ct.getMatHang(), ct.getSoLuong())){

            }
        }
        pn.setTrangThai(TrangThaiPN.DA_DUYET);
        System.out.println("Đã tăng hàng tồn từ phiếu nhập: "+pn.getMa());
        return true;
    }
    @Override
    public void ghiFile(String duongDan){
        try(PrintWriter pw = new PrintWriter(new FileWriter(duongDan))){
            for(int i = 0; i < soLuongMatHang; i++){
                pw.println(dsMatHang[i].toString());
            }
            System.out.println("Đã ghi " + soLuongMatHang + " dòng tồn kho vào file: " + duongDan);
        } catch(IOException e){
            System.out.println("LỖI ghi file: " + e.getMessage());
        }
    }
 
    @Override
    public void docFile(String duongDan){
        
        try(BufferedReader br=new BufferedReader(new FileReader(duongDan))){
            String dong;
            while((dong=br.readLine()) != null){
                if(dong.trim().isEmpty()) continue;
                String[] phan= dong.split(";");
                String danhmuc=phan[0].trim();
                String ma = phan[1].trim();
                String ten= phan[2].trim();
                double gianhap= Double.parseDouble(phan[3].trim());

                int soLuong;
                try{
                    soLuong = Integer.parseInt(phan[4].trim());
                } catch(NumberFormatException e){
                    System.out.println("Dòng: " + dong + " lỗi số lượng!");
                    continue;
                }
                
                int vtMH = TienIch.timKiemViTri(dsMatHang, soLuongMatHang, ma);
                if(vtMH!=-1){
                    dsMatHang[vtMH].tangSoLuong(soLuong);
                }
                else{
                    switch(danhmuc){
                        case "Sách":
                            String tacGiaSach=phan[5].trim();
                            String nhaXuatBanSach=phan[6].trim();
                            String theLoaiSach=phan[7].trim();
                            Sach themSach = new Sach(ma, ten, gianhap, soLuong ,tacGiaSach, nhaXuatBanSach, theLoaiSach);
                            if(tangTonKho(themSach, soLuong)){
                                System.out.println("Thêm thành công Sách "+ ten);
                            }     
                            break;                       
                        case "Tiểu thuyết":
                            String tacGiaTieuThuyet=phan[5].trim();
                            String nhaXuatBanTieuThuyet=phan[6].trim();
                            String theLoaiTieuThuyet=phan[7].trim();
                            TieuThuyet themTieuThuyet = new TieuThuyet(ma, ten, gianhap, soLuong ,tacGiaTieuThuyet, nhaXuatBanTieuThuyet, theLoaiTieuThuyet);
                            if(tangTonKho(themTieuThuyet, soLuong)){
                                System.out.println("Thêm thành công Tiểu thuyết "+ ten);
                            }
                            break;
                        case "Sách giáo khoa":
                            String monHoc = phan[5].trim();
                            String nhaXuatBan= phan[6].trim();
                            SachGiaoKhoa themSGK = new SachGiaoKhoa(ma, ten, gianhap, soLuong, monHoc, nhaXuatBan);
                            if(tangTonKho(themSGK, soLuong)){
                                System.out.println("Thêm thành công SGK "+ ten);
                            }
                            break;
                        case "Truyện":
                            String tacGiaTruyen=phan[5].trim();
                            String nhaXuatBanTruyen=phan[6].trim();
                            String theLoaiTruyen=phan[7].trim(); 
                            Truyen themTruyen = new Truyen(ma, ten, gianhap, soLuong ,tacGiaTruyen, nhaXuatBanTruyen, theLoaiTruyen);
                            if(tangTonKho(themTruyen, soLuong)){
                                System.out.println("Thêm thành công Truyện "+ ten);
                            }     
                            break;   
                        case "Trang trí":
                            String nhaSXTrangTri=phan[5].trim();
                            TrangTri themTrangTri = new TrangTri(ma, ten, gianhap, soLuong, nhaSXTrangTri);
                            if(tangTonKho(themTrangTri, soLuong)){
                                System.out.println("Thêm thành công Trang trí " + ten);
                            }                      
                            break;
                        case "Vở":
                            int soTrang=Integer.parseInt(phan[5].trim());
                            String nhaSXVo=phan[6].trim();
                            Vo themVo = new Vo(ma, ten, gianhap, soLuong, soTrang, nhaSXVo);
                            if(tangTonKho(themVo, soLuong)){
                                System.out.println("Thêm thành công Vở "+ ten);
                            }
                            break;
                        case "Đồ chơi":
                            int doTuoi=Integer.parseInt(phan[5].trim());
                            String nhaSXDoChoi=phan[6].trim();
                            DoChoi themDoChoi = new DoChoi(ma, ten, gianhap, soLuong, nhaSXDoChoi, doTuoi);
                            if(tangTonKho(themDoChoi, soLuong)){
                                System.out.println("Thêm thành công Đồ chơi " + ten);
                            }
                            break;
                        case "Dụng cụ học tập":
                            String loaiDungCu=phan[5].trim();
                            String nhaSXDungCu=phan[6].trim();
                            DungCuHocTap themDungCu = new DungCuHocTap(ma, ten, gianhap, soLuong, nhaSXDungCu, loaiDungCu);
                            if(tangTonKho(themDungCu, soLuong)){
                                System.out.println("Thêm thành công Dụng cụ học tập "+ten);
                            }
                            break;
                        default:
                            String nhaSX = phan[5].trim();
                            Khac themKhac = new Khac(ma, ten, gianhap, soLuong, nhaSX);
                            if(tangTonKho(themKhac, soLuong)){
                                System.out.println("Thêm thành công Khác "+ten);
                            }
                            break;
                    }
                }

            }
            System.out.println("ĐỌC XONG");
        } catch(IOException e){
            System.out.println("LỖI đọc file: " + e.getMessage());
        }
    }
}

