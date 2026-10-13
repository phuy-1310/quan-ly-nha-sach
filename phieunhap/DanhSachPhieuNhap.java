package phieunhap;

import interfaces.IQuanLy;
import mathang.DoChoi;
import mathang.DungCuHocTap;
import mathang.Khac;
import mathang.Sach;
import mathang.SachGiaoKhoa;
import mathang.TieuThuyet;
import mathang.TrangTri;
import mathang.Truyen;
import mathang.Vo;
import phieunhap.PhieuNhap.TrangThaiPN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import interfaces.IFileIO;
import tienich.*;
import kho.Kho;

public class DanhSachPhieuNhap implements IQuanLy, IFileIO{
    private PhieuNhap[] dsPN;
    private int soLuongPN;

    public DanhSachPhieuNhap(){
        dsPN=new PhieuNhap[1000];
        soLuongPN=0;
    }

    @Override 
    public void nhap(){
        int n=TienIch.nhapSoNguyenDuong("Nhập số lượng phiếu nhập: ");
        for(int i=0;i<n;i++){
            System.out.println("======Nhập phiếu nhập thứ"+(i+1)+"======");
            them();
        }
    }
    @Override 
    public void xuat(){
        if(soLuongPN==0){ 
            System.out.println("Danh sách phiếu nhập đang trống!");
            return;
        }    
        System.out.println("\n======DANH SÁCH PHIẾU NHẬP======");
        System.out.printf("%-15s %-15s %-15s\n %", "Mã Phiếu", "Ngày Nhập", "Nhà Cung Cấp");
        System.out.println("--------------------------------------------------------");
        for(int i=0;i<soLuongPN;i++){
            PhieuNhap pn = dsPN[i];
            String ngay = TienIch.ngaytoString(pn.getChiTiet().getNgayNhap());
            System.out.printf("%-15s %-15s %-25s\n", pn.getMa(), ngay, pn.getChiTiet().getNhaCungCap());
            System.out.println("Thông tin hàng nhập: ");
            pn.xuat();
        }
    }
    @Override 
    public void them(){
        if(soLuongPN==dsPN.length){
            PhieuNhap[] mangMoi = new PhieuNhap[dsPN.length*2];
            for(int i=0;i<dsPN.length;i++){
                mangMoi[i]=dsPN[i];
            }
            dsPN=mangMoi;
            System.out.println("Đã tự động tăng sức chứa mảng lên: "+ dsPN.length);
        }

        PhieuNhap pn = new PhieuNhap();
        pn.nhap();
        dsPN[soLuongPN]=pn;
        soLuongPN++;
        System.out.println("Thêm thành công phiếu: "+pn.getMa());
    }
    @Override 
    public void sua(){
        if(soLuongPN==0){
            System.out.println("Danh sách phiếu nhập đang trống!");
            return;
        }
        String maCanSua=TienIch.nhapChuoi("Nhập mã phiếu cần xóa: ");
        int vitri=TienIch.timKiemViTri(dsPN, soLuongPN, maCanSua);
        if(vitri==-1){
            System.out.println("Không tìm thấy phiếu có mã: "+ maCanSua);
            return;
        }
        System.out.println("=======MENU SỬA======");
        System.out.println("1. Sửa thông tin mặt hàng nhập ghi trên phiếu");
        System.out.println("2. Sửa thông tin phiếu nhập(mã phiếu nhập, ngày nhập, ncc,...");
        System.out.println("3. Sửa trạng thái phiếu nhập");
        System.out.println("4. Thoát");
        int n = TienIch.nhapSoNguyenDuong("Nhập lựa chọn của bạn: ");
        boolean tieptuc=true;
        while(tieptuc){
            switch(n){
                case 1:
                    dsPN[vitri].sua();
                    break;
                case 2:
                    dsPN[vitri].setMaPN(TienIch.nhapMa(dsPN, soLuongPN, "Nhập mã phiếu nhập mới: "));
                    dsPN[vitri].getChiTiet().setNgayNhap(TienIch.nhapNgay("Nhập ngày nhập mới: "));
                    dsPN[vitri].getChiTiet().setNhaCC("Nhập nhà cung cấp mới: ");
                    break;
                case 3:
                    System.out.println("Chọn trạng thái mới của phiếu: ");
                    System.out.println("1. Chờ duyệt\n 2. Đã duyệt\n 3.Đã hủy");
                    int chon=TienIch.nhapSoNguyenDuong("Nhập chọn: ");
                    switch(chon){
                        case 1: dsPN[vitri].setTrangThai(TrangThaiPN.CHO_DUYET);
                        case 2: dsPN[vitri].setTrangThai(TrangThaiPN.DA_DUYET);
                        case 3: dsPN[vitri].setTrangThai(TrangThaiPN.DA_HUY);
                        break;
                    }
                case 4:
                    tieptuc=false;
                    break;
                default: System.out.println("Lựa chọn không hợp lệ!");

            }
        }
    }
    @Override 
    public void xoa(){
        if(soLuongPN==0){
            System.out.println("Danh sách phiếu nhập đang trống!");
            return;
        }
        String maCanXoa=TienIch.nhapChuoi("Nhập mã phiếu cần xóa: ");
        int vitri=TienIch.timKiemViTri(dsPN, soLuongPN, maCanXoa);
        if(vitri==-1){
            System.out.println("Không tìm thấy phiếu có mã: "+ maCanXoa);
            return;
        }
        for(int i=vitri;i<soLuongPN-1;i++){
            dsPN[i]=dsPN[i+1];
        }
        dsPN[soLuongPN-1]=null;
        soLuongPN--;
        System.out.println("Xóa thành công phiếu mã "+maCanXoa);
    }

    @Override
    public void ghiFile(String duongDan){
        try(PrintWriter pw = new PrintWriter(new FileWriter(duongDan))){
            for(int i=0;i<soLuongPN;i++){
                PhieuNhap pn = dsPN[i];
                for(int j=0;i<pn.getSoLuongNhap();j++){
                    pw.println(pn.getMa()+";"+pn.getDSMH()[j].toString());
                }
            }
            System.out.println("Đã ghi thành công "+ soLuongPN +"phiếu nhập vào file");
        }catch(IOException e){
            System.out.println("Không ghi được file, lỗi: "+e);
        }
    }
    @Override
    public void docFile(String duongDan){
        try(BufferedReader br = new BufferedReader(new FileReader(duongDan))){
            String dong;
            while((dong=br.readLine())!=null){
                if(dong.trim().isEmpty()) continue;
               
                String phan[]=dong.split(";");

                String maPn=phan[0].trim();
                String danhMuc=phan[1].trim();
                String ma=phan[2].trim();
                String ten=phan[3].trim();

                double gianhap=Double.parseDouble(phan[4].trim());
                int soLuong=Integer.parseInt(phan[5].trim());

                int vtPn = TienIch.timKiemViTri(dsPN, soLuongPN, maPn);
                if(vtPn==-1) vtPn=soLuongPN;

                switch (danhMuc) {
                    case "Sách":
                        String tacGiaSach=phan[6].trim();
                        String nhaXuatBanSach=phan[7].trim();
                        String theLoaiSach=phan[8].trim();
                        Sach themSach = new Sach(ma, ten, gianhap, soLuong ,tacGiaSach, nhaXuatBanSach, theLoaiSach);
                        dsPN[vtPn].them(themSach);
                        break;
                    case "Tiểu thuyết":
                        String tacGiaTieuThuyet=phan[6].trim();
                        String nhaXuatBanTieuThuyet=phan[7].trim();
                        String theLoaiTieuThuyet=phan[8].trim();
                        TieuThuyet themTieuThuyet = new TieuThuyet(ma, ten, gianhap, soLuong ,tacGiaTieuThuyet, nhaXuatBanTieuThuyet, theLoaiTieuThuyet);
                        dsPN[vtPn].them(themTieuThuyet);
                        break;
                    case "Sách giáo khoa":
                        String monHoc = phan[6].trim();
                        String nhaXuatBan= phan[7].trim();
                        SachGiaoKhoa themSGK = new SachGiaoKhoa(ma, ten, gianhap, soLuong, monHoc, nhaXuatBan);
                        dsPN[vtPn].them(themSGK);
                        break;
                    case "Truyện":
                        String tacGiaTruyen=phan[6].trim();
                        String nhaXuatBanTruyen=phan[7].trim();
                        String theLoaiTruyen=phan[8].trim(); 
                        Truyen themTruyen = new Truyen(ma, ten, gianhap, soLuong ,tacGiaTruyen, nhaXuatBanTruyen, theLoaiTruyen);
                        dsPN[vtPn].them(themTruyen);
                        break;   
                    case "Trang trí":
                        String nhaSXTrangTri=phan[6].trim();
                        TrangTri themTrangTri = new TrangTri(ma, ten, gianhap, soLuong, nhaSXTrangTri);                    
                        dsPN[vtPn].them(themTrangTri);
                        break;
                    case "Vở":
                        int soTrang=Integer.parseInt(phan[6].trim());
                        String nhaSXVo=phan[7].trim();
                        Vo themVo = new Vo(ma, ten, gianhap, soLuong, soTrang, nhaSXVo);
                        dsPN[vtPn].them(themVo);
                        break;
                    case "Đồ chơi":
                        int doTuoi=Integer.parseInt(phan[6].trim());
                        String nhaSXDoChoi=phan[7].trim();
                        DoChoi themDoChoi = new DoChoi(ma, ten, gianhap, soLuong, nhaSXDoChoi, doTuoi);                      
                        dsPN[vtPn].them(themDoChoi);
                        break;
                    case "Dụng cụ học tập":
                        String loaiDungCu=phan[6].trim();
                        String nhaSXDungCu=phan[7].trim();
                        DungCuHocTap themDungCu = new DungCuHocTap(ma, ten, gianhap, soLuong, nhaSXDungCu, loaiDungCu);
                        dsPN[vtPn].them(themDungCu);
                        break;
                    default:
                        String nhaSX = phan[6].trim();
                        Khac themKhac = new Khac(ma, ten, gianhap, soLuong, nhaSX);
                        dsPN[vtPn].them(themKhac);
                        break;
                }
            }
        }catch(IOException e){
            System.out.println("Lỗi đọc file "+e);
        }  
    }

    public void nhapKho(Kho kho){
        for(int i=0;i<soLuongPN;i++){
            dsPN[i].xuat();
            String chon = TienIch.nhapChuoi("Xác nhận nhập hàng?(y/n)");
            if(chon.equals("y")){
                kho.nhapHangTuPhieu(dsPN[i]);
                System.out.println("Đã nhập kho thành công!");
            }
            else{
                dsPN[i].setTrangThai(TrangThaiPN.DA_HUY);
                System.out.println("Đã hủy phiếu!");
            }
        }
    }
}
