package phieunhap;

import interfaces.IQuanLy;
import phieunhap.PhieuNhap.TrangThaiPN;


import interfaces.IFileIO;
import tienich.*;

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

    }
    @Override
    public void docFile(String duongDan){

    }
}
