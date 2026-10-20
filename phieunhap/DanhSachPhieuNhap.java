package phieunhap;

import interfaces.IQuanLy;
import mathang.*;
import nhacungcap.NhaCungCap;
import phieunhap.PhieuNhap.TrangThaiPN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        System.out.printf("%-15s %-15s %-15s\n", "Mã Phiếu", "Ngày Nhập", "Nhà Cung Cấp");
        System.out.println("--------------------------------------------------------");
        for(int i=0;i<soLuongPN;i++){
            PhieuNhap pn = dsPN[i];
            String ngay = TienIch.ngaytoString(pn.getChiTiet().getNgayNhap());
            NhaCungCap ncc = pn.getChiTiet().getNhaCungCap();
            String tenNCC = ncc == null ? "" : ncc.getTenNCC();
            System.out.printf("%-15s %-15s %-25s\n", pn.getMa(), ngay, tenNCC);
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
        System.out.println("2. Sửa thông tin phiếu nhập(mã phiếu nhập, ngày nhập)");
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
                for(int j=0;j<pn.getSoLuongNhap();j++){
                    pw.println(pn.getMa()+";"+pn.getChiTiet().getNgayNhap()+";"+pn.getDSMH()[j].toString()+";"+pn.getTrangThai());
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

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM.yyyy");
                LocalDate ngay = LocalDate.parse(phan[1].trim(), formatter);

                String danhMuc=phan[2].trim();
                String ma=phan[3].trim();
                String ten=phan[4].trim();

                double gianhap=Double.parseDouble(phan[5].trim());
                int soLuong=Integer.parseInt(phan[6].trim());

                NhaCungCap ncc = new NhaCungCap();

                int vtPn = TienIch.timKiemViTri(dsPN, soLuongPN, maPn);
                if(vtPn==-1){
                    vtPn=soLuongPN;
                    soLuongPN++;
                    dsPN[vtPn]=new PhieuNhap();
                    ChiTietPhieuNhap ct = new ChiTietPhieuNhap(ngay, ncc);
                    dsPN[vtPn].setChiTiet(ct);
                    dsPN[vtPn].setMaPN(maPn);
                    TrangThaiPN tt = TrangThaiPN.valueOf(phan[phan.length-1].trim());
                    dsPN[vtPn].setTrangThai(tt);
                }
                if(dsPN[vtPn].getTrangThai()==TrangThaiPN.DA_HUY ||
                dsPN[vtPn].getTrangThai()==TrangThaiPN.DA_DUYET){
                    System.out.println("Phiếu này đã duyệt hoặc hủy!");
                    continue;
                }
                switch (danhMuc) {
                    case "Sách":
                        String tacGiaSach=phan[7].trim();
                        String nhaXuatBanSach=phan[8].trim();
                        String theLoaiSach=phan[9].trim();
                        Sach themSach = new Sach(ma, ten, gianhap, soLuong ,tacGiaSach, nhaXuatBanSach, theLoaiSach);
                        dsPN[vtPn].them(themSach);
                        dsPN[vtPn].getChiTiet().setNhaCC(taoNhaCungCap(nhaXuatBanSach));
                        break;
                    case "Tiểu thuyết":
                        String tacGiaTieuThuyet=phan[7].trim();
                        String nhaXuatBanTieuThuyet=phan[8].trim();
                        String theLoaiTieuThuyet=phan[9].trim();
                        TieuThuyet themTieuThuyet = new TieuThuyet(ma, ten, gianhap, soLuong ,tacGiaTieuThuyet, nhaXuatBanTieuThuyet, theLoaiTieuThuyet);
                        dsPN[vtPn].them(themTieuThuyet);
                        dsPN[vtPn].getChiTiet().setNhaCC(taoNhaCungCap(nhaXuatBanTieuThuyet));
                        break;
                    case "Sách giáo khoa":
                        String monHoc = phan[7].trim();
                        String nhaXuatBan= phan[8].trim();
                        SachGiaoKhoa themSGK = new SachGiaoKhoa(ma, ten, gianhap, soLuong, monHoc, nhaXuatBan);
                        dsPN[vtPn].them(themSGK);
                        dsPN[vtPn].getChiTiet().setNhaCC(taoNhaCungCap(nhaXuatBan));
                        break;
                    case "Truyện":
                        String tacGiaTruyen=phan[7].trim();
                        String nhaXuatBanTruyen=phan[8].trim();
                        String theLoaiTruyen=phan[9].trim(); 
                        Truyen themTruyen = new Truyen(ma, ten, gianhap, soLuong ,tacGiaTruyen, nhaXuatBanTruyen, theLoaiTruyen);
                        dsPN[vtPn].them(themTruyen);
                        dsPN[vtPn].getChiTiet().setNhaCC(taoNhaCungCap(nhaXuatBanTruyen));
                        break;   
                    case "Trang trí":
                        String nhaSXTrangTri=phan[7].trim();
                        TrangTri themTrangTri = new TrangTri(ma, ten, gianhap, soLuong, nhaSXTrangTri);                    
                        dsPN[vtPn].them(themTrangTri);
                        dsPN[vtPn].getChiTiet().setNhaCC(taoNhaCungCap(nhaSXTrangTri));
                        break;
                    case "Vở":
                        int soTrang=Integer.parseInt(phan[7].trim());
                        String nhaSXVo=phan[8].trim();
                        Vo themVo = new Vo(ma, ten, gianhap, soLuong, soTrang, nhaSXVo);
                        dsPN[vtPn].them(themVo);
                        dsPN[vtPn].getChiTiet().setNhaCC(taoNhaCungCap(nhaSXVo));
                        break;
                    case "Đồ chơi":
                        int doTuoi=Integer.parseInt(phan[7].trim());
                        String nhaSXDoChoi=phan[8].trim();
                        DoChoi themDoChoi = new DoChoi(ma, ten, gianhap, soLuong, nhaSXDoChoi, doTuoi);                      
                        dsPN[vtPn].them(themDoChoi);
                        dsPN[vtPn].getChiTiet().setNhaCC(taoNhaCungCap(nhaSXDoChoi));
                        break;
                    case "Dụng cụ học tập":
                        String loaiDungCu=phan[7].trim();
                        String nhaSXDungCu=phan[8].trim();
                        DungCuHocTap themDungCu = new DungCuHocTap(ma, ten, gianhap, soLuong, nhaSXDungCu, loaiDungCu);
                        dsPN[vtPn].them(themDungCu);
                        dsPN[vtPn].getChiTiet().setNhaCC(taoNhaCungCap(nhaSXDungCu));
                        break;
                    default:
                        String nhaSX = phan[7].trim();
                        Khac themKhac = new Khac(ma, ten, gianhap, soLuong, nhaSX);
                        dsPN[vtPn].them(themKhac);
                        dsPN[vtPn].getChiTiet().setNhaCC(taoNhaCungCap(nhaSX));
                        break;
                }
                System.out.println("Đã nhập thành công phiếu: "+dsPN[vtPn].getMa());
            }
        }catch(IOException e){
            System.out.println("Lỗi đọc file "+e);
        }  
    }

    private NhaCungCap taoNhaCungCap(String tenNCC) {
        return new NhaCungCap("", tenNCC, "", "", "");
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
