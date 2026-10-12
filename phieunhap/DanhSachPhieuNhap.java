package phieunhap;

import java.time.LocalDate;

public class DanhSachPhieuNhap implements IQuanLy {
    private PhieuNhap[] dsPN;
    private int soLuongPN;

    public DanhSachPhieuNhap(){
        dsPN=new PhieuNhap[1000];
        soLuongPN=0;
    }

    @Override 
    public void nhap(){
        int n=TienIch.nhapSoNguyenDuong("Nhập số lượng phiếu nhập thêm: ");
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
            String ngay = TienIch.ngaytoString(pn.getNgayNhap());
            System.out.printf("%-15s %-15s %-25s\n", pn.getMa(), ngay, pn.getNhaCungCap());
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

        String ma=TienIch.nhapMa(dsPN, soLuongPN, "Nhập mã phiếu: ");
        String ncc= TienIch.nhapChuoi("Nhập tên nhà cung cấp: ");
        LocalDate ngay = TienIch.nhapNgay("Nhập ngày nhập(dd/MM/yyyy): ");
        PhieuNhap pn = new PhieuNhap(ma, ngay ,ncc);
        dsPN[soLuongPN]=pn;
        soLuongPN++;
        System.out.println("Thêm thành công phiếu: "+ma);
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
        System.out.println("=======MENU SỬA======\n1. Sửa mã phiếu\n2. Sửa nhà cung cấp\n3. Sửa ngày nhập\n4. Thoát");
        int n = TienIch.nhapSoNguyenDuong("Nhập lựa chọn của bạn: ");
        boolean tieptuc=true;
        while(tieptuc){
            switch(n){
                case 1:
                    String maMoi= TienIch.nhapMa(dsPN, soLuongPN, "Nhập mã mới: ");
                    dsPN[vitri].setMaPN(maMoi);
                    break;
                case 2:
                    String nhaCungCapMoi=TienIch.nhapChuoi("Nhập nhà cung cấp mới: ");
                    dsPN[vitri].setNhaCungCap(nhaCungCapMoi);
                    break;
                case 3:
                    LocalDate ngayMoi=TienIch.nhapNgay("Nhập ngày mới: ");
                    dsPN[vitri].setNgayNhap(ngayMoi);
                    break;
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
}
