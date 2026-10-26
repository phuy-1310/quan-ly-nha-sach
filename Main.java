import hoadon.*;
import khachhang.*;
import kho.*;
import mathang.*;
import nhanvien.*;
import phieunhap.*;
import tienich.TienIch;
 

public class Main {
 
    // ====== Dữ liệu dùng chung ======
    static Kho kho = new Kho();
    static DanhSachMatHang dsmh = new DanhSachMatHang();
    static DanhSachKhachHang dskh = new DanhSachKhachHang();
    static DanhSachPhieuNhap dspn = new DanhSachPhieuNhap();
    static DanhSachHoaDon dshd = new DanhSachHoaDon(dsmh, dskh);
 
    // Nhân viên bán hàng mẫu để lập hóa đơn
    static NhanVienBanHang nvBanHang =
            new NhanVienBanHang("NV01", "Nguyen Van A", "0900000000", "123", "Sang", 8000000);
 
    static String hoiFile(String macDinh) {
        String s = TienIch.nhapChuoi("Nhập đường dẫn file (gõ . để dùng mặc định '" + macDinh + "'): ").trim();
        return (s.isEmpty() || s.equals(".")) ? macDinh : s;
    }
 
    // ====== Menu chính ======
    static void menuChinh() {
        System.out.println();
        System.out.println("============ QUẢN LÝ NHÀ SÁCH ============");
        System.out.println("1. Quản lý kho");
        System.out.println("2. Quản lý phiếu nhập");
        System.out.println("3. Quản lý mặt hàng (cửa hàng)");
        System.out.println("4. Quản lý khách hàng");
        System.out.println("5. Quản lý hóa đơn");
        System.out.println("6. Xem thông tin nhân viên mẫu");
        System.out.println("0. Thoát");
        System.out.println("==========================================");
    }
 
    // ====== Kho ======
    static void menuKho() {
        int chon;
        do {
            System.out.println("\n---------- KHO ----------");
            System.out.println("1. Xem hàng tồn");
            System.out.println("2. Thêm 1 mặt hàng vào kho");
            System.out.println("3. Nhập nhiều mặt hàng vào kho");
            System.out.println("4. Sửa số lượng tồn");
            System.out.println("5. Xóa mặt hàng khỏi kho");
            System.out.println("6. Tra cứu theo mã");
            System.out.println("7. Kiểm tra sức chứa");
            System.out.println("8. Đọc kho từ file");
            System.out.println("9. Ghi kho ra file");
            System.out.println("0. Quay lại");
            chon = TienIch.nhapSoNguyenDuong("Chọn: ");
            switch (chon) {
                case 1: kho.xuat(); break;
                case 2: kho.them(); break;
                case 3: kho.nhap(); break;
                case 4: kho.sua(); break;
                case 5: kho.xoa(); break;
                case 6: kho.timHangTon(); break;
                case 7: kho.kiemTraSucChua(); break;
                case 8: kho.docFile(hoiFile("kho/kho_input.txt")); break;
                case 9: kho.ghiFile(hoiFile("kho/kho_output.txt")); break;
                case 0: break;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }
 
    // ====== Phiếu nhập ======
    static void menuPhieuNhap() {
        int chon;
        do {
            System.out.println("\n---------- PHIẾU NHẬP ----------");
            System.out.println("1. Xem danh sách phiếu nhập");
            System.out.println("2. Thêm 1 phiếu nhập");
            System.out.println("3. Thêm nhiều phiếu nhập");
            System.out.println("4. Sửa phiếu nhập");
            System.out.println("5. Xóa phiếu nhập");
            System.out.println("6. Nhập hàng của phiếu vào kho");
            System.out.println("7. Xem hàng tồn trong kho");
            System.out.println("8. Đọc phiếu nhập từ file");
            System.out.println("9. Ghi phiếu nhập ra file");
            System.out.println("10. Xem danh sách nhà cung cấp trong lần nhập này");
            System.out.println("0. Quay lại");
            chon = TienIch.nhapSoNguyenDuong("Chọn: ");
            switch (chon) {
                case 1: dspn.xuat(); break;
                case 2: dspn.them(); break;
                case 3: dspn.nhap(); break;
                case 4: dspn.sua(); break;
                case 5: dspn.xoa(); break;
                case 6: dspn.nhapKho(kho); break;
                case 7: kho.xuat(); break;
                case 8: dspn.docFile(hoiFile("phieunhap/phieunhap_input.txt")); break;
                case 9: dspn.ghiFile(hoiFile("phieunhap/phieunhap_output.txt")); break;
                case 10: dspn.xuatDsNCC();
                case 0: break;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }
 
    // ====== Mặt hàng ======
    static void menuMatHang() {
        int chon;
        do {
            System.out.println("\n---------- MẶT HÀNG (CỬA HÀNG) ----------");
            System.out.println("1. Xem số lượng mặt hàng đang bày bán");
            System.out.println("2. Nhập hàng loạt từ kho lên");
            System.out.println("3. Nhập 1 mặt hàng cụ thể từ kho lên");
            System.out.println("4. Xuất danh sách mặt hàng");
            System.out.println("5. Thêm mặt hàng");
            System.out.println("6. Xóa mặt hàng");
            System.out.println("7. Sửa mặt hàng");
            System.out.println("8. Tìm kiếm theo mã");
            System.out.println("9. Bộ lọc nâng cao");
            System.out.println("0. Quay lại");
            chon = TienIch.nhapSoNguyenDuong("Chọn: ");
            switch (chon) {
                case 1: System.out.println("Số mặt hàng: " + dsmh.getSoMH()); break;
                case 2: dsmh.nhapHangLoat(kho); break;
                case 3: {
                    // dsmh.nhap() dùng field kho chưa được khởi tạo nên gọi nhapTuKho trực tiếp
                    String ma = TienIch.nhapChuoi("Nhập mã hàng cần lấy từ kho: ");
                    int sl = TienIch.nhapSoNguyenDuong("Nhập số lượng cần lấy: ");
                    dsmh.nhapTuKho(kho, ma, sl);
                    break;
                }
                case 4: dsmh.xuat(); break;
                case 5: dsmh.them(); break;
                case 6: dsmh.xoa(); break;
                case 7: dsmh.sua(); break;
                case 8: dsmh.timkiem(); break;
                case 9: dsmh.timkiem_nangcao(); break;
                case 0: break;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }
 
    // ====== Khách hàng ======
    static void menuKhachHang() {
        int chon;
        do {
            System.out.println("\n---------- KHÁCH HÀNG ----------");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm khách hàng");
            System.out.println("3. Nhập nhiều khách hàng");
            System.out.println("4. Sửa khách hàng");
            System.out.println("5. Xóa khách hàng");
            System.out.println("6. Đọc từ file");
            System.out.println("7. Ghi ra file");
            System.out.println("0. Quay lại");
            chon = TienIch.nhapSoNguyenDuong("Chọn: ");
            switch (chon) {
                case 1: dskh.xuat(); break;
                case 2: dskh.them(); break;
                case 3: dskh.nhap(); break;
                case 4: dskh.sua(); break;
                case 5: dskh.xoa(); break;
                case 6: dskh.docFile(hoiFile("khachhang.txt")); break;
                case 7: dskh.ghiFile(hoiFile("khachhang.txt")); break;
                case 0: break;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }
 
    // ====== Hóa đơn ======
    static void menuHoaDon() {
        int chon;
        do {
            System.out.println("\n---------- HÓA ĐƠN ----------");
            System.out.println("1. Xem danh sách hóa đơn");
            System.out.println("2. Lập hóa đơn mới");
            System.out.println("3. Sửa hóa đơn");
            System.out.println("4. Xóa hóa đơn");
            System.out.println("5. Đọc từ file");
            System.out.println("6. Ghi ra file");
            System.out.println("0. Quay lại");
            chon = TienIch.nhapSoNguyenDuong("Chọn: ");
            switch (chon) {
                case 1: dshd.xuat(); break;
                case 2: {
                    String maKH = TienIch.nhapChuoi("Nhập mã khách hàng: ");
                    KhachHang kh = dskh.timkiem_khachhang(maKH);
                    if (kh == null) {
                        System.out.println("Không tìm thấy khách hàng! Hãy thêm khách hàng trước.");
                        break;
                    }
                    String maHD = TienIch.nhapChuoi("Nhập mã hóa đơn: ");
                    dshd.taoHoaDon(maHD, kh, nvBanHang);
                    break;
                }
                case 3: dshd.sua(); break;
                case 4: dshd.xoa(); break;
                case 5: dshd.docFile(hoiFile("hoadon.txt"), dskh, dsmh); break;
                case 6: dshd.ghiFile(hoiFile("hoadon.txt")); break;
                case 0: break;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }
 
    public static void main(String[] args) {
        int chon;
        do {
            menuChinh();
            chon = TienIch.nhapSoNguyenDuong("Chọn chức năng: ");
            switch (chon) {
                case 1: menuKho(); break;
                case 2: menuPhieuNhap(); break;
                case 3: menuMatHang(); break;
                case 4: menuKhachHang(); break;
                case 5: menuHoaDon(); break;
                case 6: nvBanHang.xuatThongTin(); break;
                case 0: System.out.println("Tạm biệt!"); break;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }
}
