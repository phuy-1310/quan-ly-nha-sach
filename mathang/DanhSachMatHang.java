package mathang;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import tienich.*;
import interfaces.IQuanLy;
import kho.*;

public class DanhSachMatHang implements IQuanLy {
    private MatHang[] ds;
    private int soluong;
    private Kho kho;

    //constructor
    public DanhSachMatHang() {
        
        ds = new MatHang[100]; // mảng ban đầu
        soluong = 0;
    }

        //Nhap hang(nhap tu kho)
    @Override
    public void nhap(){
        System.out.println("Danh sách mặt hàng đang nhập hàng từ kho: ");
        String maCanLay = TienIch.nhapChuoi("Nhập mã hàng cần lấy: ");
        int soLuongLay = TienIch.nhapSoNguyenDuong("Nhập số lượng cần lấy: ");
        nhapTuKho(kho, maCanLay, soLuongLay);
    }
    public void nhapTuKho(Kho kho, String ma, int soLuongLay) {
            boolean found = false;
            for (int i = 0; i < kho.getSoLuongTon(); i++) {
                HangTonKho htk = kho.getDanhSachTon()[i];
                if (htk.getMatHang().getMa().equalsIgnoreCase(ma)) {
                    found = true;
                    if (soLuongLay > 0 && soLuongLay <= htk.getSoLuong()) {
                        htk.giamSoLuong(soLuongLay);
                        boolean daCoTrongDS = false;
                        for (int j = 0; j < soluong; j++) {
                            if (ds[j].getMa().equalsIgnoreCase(ma)) {
                                ds[j].setSoLuong(ds[j].getSoLuong() + soLuongLay);
                                daCoTrongDS = true;
                                break;
                            }
                        }

                        if (!daCoTrongDS) {
                            MatHang mh = htk.getMatHang();
                            mh.setSoLuong(soLuongLay);
                            if (soluong == ds.length) tangkichthuoc();
                            ds[soluong++] = mh;
                        }

                        System.out.println("Đã nhập " + soLuongLay +
                                        " sản phẩm mã " + ma);
                    } else {
                        System.out.println("Số lượng trong kho không đủ hoặc không hợp lệ!");
                    }
                    break;
                }
            }
            if (!found) {
                System.out.println("Không tìm thấy mặt hàng có mã " + ma + " trong kho.");
            }
    }


    //Xuat hang
    @Override
    public void xuat(){
        for(int i =0; i < soluong;i++){
            System.out.println("\n--------THÔNG TIN MẶT HÀNG THỨ " +(i+1)+ "--------");
            ds[i].xuat();
        }
    }

        private void tangkichthuoc() {
            MatHang[] temp = new MatHang[ds.length *2];
            for (int i = 0; i < ds.length; i++) {
                temp[i] = ds[i];
            }
            ds = temp;
        }


    //Them Hang
    @Override
    public void them() { 
        if (soluong == ds.length) {
            tangkichthuoc();
        }
        int k;
        do {
            k = TienIch.nhapSoNguyenDuong("Nhập số lượng mặt hàng cần thêm (1-100): ");
            if (k <= 0 || k > 100) {
                System.out.println("Giá trị không hợp lệ! Vui lòng thử lại.");
            }
        } while (k <= 0 || k > 100);

        for (int i = 0; i < k; i++) {
            MatHang mh;
            boolean hopLe;
            do {
                System.out.println("\nChọn loại mặt hàng: ");
                System.out.println("1. Sách");
                System.out.println("2. Sách giáo khoa");
                System.out.println("3. Tiểu thuyết");
                System.out.println("4. Vở");
                System.out.println("5. Truyện");
                System.out.println("6. Dụng cụ học tập");
                System.out.println("7. Trang trí");
                System.out.println("8. Đồ chơi");
                System.out.println("9. Khác");
                int loai = TienIch.nhapSoNguyenDuong("Nhập loại mặt hàng cần thêm: ");
                switch (loai) {
                    case 1 : mh = new Sach(); break;
                    case 2 : mh = new SachGiaoKhoa(); break;
                    case 3 : mh = new TieuThuyet(); break;
                    case 4 : mh = new Vo(); break;
                    case 5 : mh = new Truyen(); break;
                    case 6 : mh = new DungCuHocTap(); break;
                    case 7 : mh = new TrangTri(); break;
                    case 8 : mh = new DoChoi(); break;
                    case 9 : mh = new Khac(); break;
                    default : mh = new Khac(); break; // mặc định
                };

                mh.nhap();
                hopLe = true;

                // kiểm tra trùng mã
                for (int j = 0; j < soluong; j++) {
                    if (mh.getMa().equalsIgnoreCase(ds[j].getMa())) {
                        System.out.println("Mã sản phẩm đã tồn tại! Vui lòng nhập lại.");
                        hopLe = false;
                        break;
                    }
                }

                // kiểm tra giá
                if (mh.getGiaNhap() <= 0) {
                    System.out.println("Giá sản phẩm phải là một giá trị dương!");
                    hopLe = false;
                }

                // kiểm tra số lượng
                if (mh.getSoLuong() <= 0) {
                    System.out.println("Số lượng sản phẩm phải là một giá trị nguyên dương!");
                    hopLe = false;
                }

            } while (!hopLe);

            ds[soluong++] = mh; // chỉ thêm khi hợp lệ
        }
    }


        //Xoa Hang
    @Override
    public void xoa(){
        String maCanXoa;
        maCanXoa=TienIch.nhapChuoi("Nhập mã cần xóa: ");
        xoa(maCanXoa);
    }
    public void xoa(String ma){
        boolean found = false;
        for(int i =0; i < soluong;i++){
            if(ds[i].getMa().equalsIgnoreCase(ma)) {
                for(int j = i; j< soluong-1;j++){
                    ds[j] = ds[j+1];
                }
                soluong--;
                found = true;
                System.out.println("Đã xóa mặt hàng có mã "+ ma);
                break;
            }
        }
        if(!found)
            System.out.println("Không tồn tại mặt hàng có mã "+ ma);
    }

    //Sua Hang
    @Override
    public void sua(){
        String ma;
        ma = TienIch.nhapChuoi("Nhập mã cần sửa");
        sua(ma);
    }
    public void sua(String ma) {
        boolean found = false;

        for (int i = 0; i < soluong; i++) {
            if (ds[i].getMa().equalsIgnoreCase(ma)) {
                found = true;
                int choice;
                do {
                    System.out.println("Lựa chọn thông tin cần sửa của mặt hàng: ");
                    System.out.println("1. Mã mặt hàng");
                    System.out.println("2. Tên mặt hàng");
                    System.out.println("3. Giá nhập mặt hàng");
                    System.out.println("4. Số lượng mặt hàng");
                    System.out.println("0. Thoát chức năng");

                    choice = TienIch.nhapSoNguyenDuong("Nhập: ");

                    switch (choice) {
                        case 1:
                            System.out.print("Nhập mã mới: ");
                            String maMoi = TienIch.nhapMa(ds, soluong, ma); //hàm này có ktra kh trùng
                            ds[i].setMa(maMoi);
                            break;
                        case 2:
                            ds[i].setTen(TienIch.nhapChuoi("Nhập tên mới: "));
                            break;
                        case 3:
                            double giaMoi;
                            giaMoi = TienIch.nhapGiaTien("Nhập giá nhập mới: ");
                            ds[i].setGiaNhap(giaMoi);
                            break;
                        case 4:
                            int slMoi;
                            slMoi= TienIch.nhapSoNguyenDuong("Nhập số lượng mới: ")
                            ds[i].setSoLuong(slMoi);
                            break;
                        case 0:
                            System.out.println("Thoát sửa mặt hàng.");
                            break;
                        default:
                            System.out.println("Lựa chọn không hợp lệ.");
                    }
                } while (choice != 0);

                System.out.println("Đã sửa thông tin mặt hàng thành công!");
                break;
            }
        }

        if (!found) {
            System.out.println("Không tồn tại mặt hàng có mã " + ma);
        }
    }

    //timkiem

    public void timkiem_hang(){
        String timma = TienIch.nhapChuoi("Nhập mã hàng cần tìm:");
        boolean found = false;
        for(int i = 0 ; i< soluong;i++){
            if(timma.equalsIgnoreCase(ds[i].getMa())){
                ds[i].xuat();
                found = true;
            }
        }
        if(!found){
            System.out.println("Không tồn tại mặt hàng nào có mã " + timma + " !" );
        }
    }

    //bo loc tim kiem nang cao
    public void timkiem_nangcao() {

        System.out.println("\nChọn loại mặt hàng: ");
        System.out.println("1. Sách");
        System.out.println("2. Sách giáo khoa");
        System.out.println("3. Tiểu thuyết");
        System.out.println("4. Vở");
        System.out.println("5. Truyện");
        System.out.println("6. Dụng cụ học tập");
        System.out.println("7. Trang trí");
        System.out.println("8. Đồ chơi");
        System.out.println("9. Khác");
        System.out.println("Lựa chọn khác: thoát");

        int choice = TienIch.nhapSoNguyenDuong("Nhập loại hàng muốn tìm kiếm: ");
        switch (choice) {

            case 1:
                System.out.println("Chọn danh mục tìm kiếm: ");
                System.out.println("1/ Tìm kiếm theo tên tác giả");
                System.out.println("2/ Tìm kiếm theo thể loại");
                System.out.println("3/ Tìm kiếm theo nhà xuất bản");
                System.out.println("Lựa chọn khác: thoát");
                int choice1 = TienIch.nhapSoNguyenDuong("Nhập:");
                switch (choice1) {

                    case 1:
                        boolean tontai11 = false;
                        String boloc11 = TienIch.nhapChuoi("Nhập tên tác giả muốn tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof Sach) { //Kiểm tra xem mặt hàng thứ i có phải sách hay kh
                                Sach s = (Sach) ds[i]; // gán lại dữ liệu
                                if (boloc11.equalsIgnoreCase(s.getTacGia())) {
                                    s.xuat();
                                    tontai11 = true;
                                }
                            }
                        }
                        if (!tontai11) {
                            System.out.println("Không tồn tại bất kì mặt hàng nào có tên tác giả này !");
                        }

                    case 2:
                        boolean tontai12 = false;
                        String boloc12 = TienIch.nhapChuoi("Nhập thể loại cần tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof Sach) {
                                Sach s = (Sach) ds[i];
                                if (boloc12.equalsIgnoreCase(s.getTheloai())) {
                                    s.xuat();
                                    tontai12 = true;
                                }
                            }
                        }
                        if (!tontai12) {
                            System.out.println("Không tồn tại bất kì mặt hàng nào có thể loại này !");
                        }

                    case 3:
                        boolean tontai13 = false;
                        String boloc13 = TienIch.nhapChuoi("Nhập NXB cần tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof Sach) {
                                Sach s = (Sach) ds[i];
                                if (boloc13.equalsIgnoreCase(s.getNhaXuatBan())) {
                                    s.xuat();
                                    tontai13 = true;
                                }
                            }
                        }
                        if (!tontai13) {
                            System.out.println("Không tồn tại bất kì sách nào thuộc NXB này !");
                        }

                    default: break;
                }
            case 2:
                System.out.println("Chọn danh mục tìm kiếm: ");
                System.out.println("1/ Tìm kiếm theo môn học");
                System.out.println("2/ Tìm kiếm theo NXB");
                System.out.println("Lựa chọn khác: thoát");
                int choice2 = TienIch.nhapSoNguyenDuong("Nhập: ");
                switch (choice2){
                    case 1:
                        boolean tontai21 = false;
                        String boloc21 = TienIch.nhapChuoi("Nhập môn học cần tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof SachGiaoKhoa) {
                                SachGiaoKhoa sgk = (SachGiaoKhoa) ds[i];
                                if (boloc21.equalsIgnoreCase(sgk.getMonhoc())) {
                                    sgk.xuat();
                                    tontai21 = true;
                                }
                            }
                        }
                        if (!tontai21) {
                            System.out.println("Không tồn tại bất kì SGK mặt hàng nào thuộc NXB này !");
                        }

                    case 2:
                        boolean tontai22 = false;
                        String boloc22 = TienIch.nhapChuoi("Nhập NXB cần tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof SachGiaoKhoa) {
                                SachGiaoKhoa sgk = (SachGiaoKhoa) ds[i];
                                if (boloc22.equalsIgnoreCase(sgk.getNhaXuatBan())) {
                                    sgk.xuat();
                                    tontai21 = true;
                                }
                            }
                        }
                        if (!tontai22) {
                            System.out.println("Không tồn tại bất kì mặt hàng nào về môn học này !");
                        }

                    default: break;
                }

            case 3:
                System.out.println("Chọn danh mục tìm kiếm: ");
                System.out.println("1/ Tìm kiếm theo tên tác giả");
                System.out.println("2/ Tìm kiếm theo thể loại");
                System.out.println("3/ Tìm kiếm theo nhà xuất bản");
                System.out.println("Lựa chọn khác: thoát");
                int choice3 = TienIch.nhapSoNguyenDuong("Nhập: ");
                switch (choice3) {

                    case 1:
                        boolean tontai31 = false;
                        System.out.println("Nhập tên tác giả muốn tìm: ");
                        String boloc31 = TienIch.nhapChuoi("Nhập tên tác giả muốn tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof TieuThuyet) { //Kiểm tra xem mặt hàng thứ i có phải sách hay kh
                                TieuThuyet tt = (TieuThuyet) ds[i]; // gán lại dữ liệu
                                if (boloc31.equalsIgnoreCase(tt.getTacGia())) {
                                    tt.xuat();
                                    tontai31 = true;
                                }
                            }
                        }
                        if (!tontai31) {
                            System.out.println("Không tồn tại bất kì mặt hàng nào có tên tác giả này !");
                        }

                    case 2:
                        boolean tontai32 = false;
                        String boloc32 = TienIch.nhapChuoi("Nhập thể loại cần tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof Sach) {
                                TieuThuyet tt = (TieuThuyet) ds[i];
                                if (boloc32.equalsIgnoreCase(tt.getTheloai())) {
                                    tt.xuat();
                                    tontai32 = true;
                                }
                            }
                        }
                        if (!tontai32) {
                            System.out.println("Không tồn tại bất kì mặt hàng nào có thể loại này !");
                        }

                    case 3:
                        boolean tontai33 = false;
                        String boloc33 = TienIch.nhapChuoi("Nhập NXB cần tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof Sach) {
                                Sach s = (Sach) ds[i];
                                if (boloc33.equalsIgnoreCase(s.getNhaXuatBan())) {
                                    s.xuat();
                                    tontai33 = true;
                                }
                            }
                        }
                        if (!tontai33) {
                            System.out.println("Không tồn tại bất kì sách nào thuộc NXB này");
                        }

                    default:
                        break;
                }

            case 4:
                System.out.println("Chọn danh mục tìm kiếm: ");
                System.out.println("1/ Tìm kiếm theo số trang");
                System.out.println("2/ Tìm kiếm theo nhà sản xuất");
                System.out.println("Lựa chọn khác: thoát");
                int choice4 = TienIch.nhapSoNguyenDuong("Nhập: ");
                switch (choice4) {
                    case 1:
                        boolean tontai41 = false;
                        int boloc41 = TienIch.nhapSoNguyenDuong("Nhập số trang tập: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof Vo) {
                                Vo v = (Vo) ds[i];
                                if (boloc41 == v.getSotrang()) {
                                    v.xuat();
                                    tontai41 = true;
                                }
                                if (!tontai41) {
                                    System.out.println("Không tồn tại bất kì vở nào có số trang tương tự");
                                }
                            }
                        }
                    case 2:
                        boolean tontai42 = false;
                        String boloc42 = TienIch.nhapChuoi("Nhập nhà sản xuất: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof Vo) {
                                Vo v = (Vo) ds[i];
                                if (boloc42.equalsIgnoreCase(v.getnhaSX()))
                                {
                                    v.xuat();
                                    tontai42 = true;
                                }
                                if (!tontai42) {
                                    System.out.println("Không tồn tại bất kì vở nào có số trang tương tự");
                                }
                            }
                        }
                    default:
                        break;
                }

            case 5:
                System.out.println("Chọn danh mục tìm kiếm: ");
                System.out.println("1/ Tìm kiếm theo tên tác giả");
                System.out.println("2/ Tìm kiếm theo thể loại");
                System.out.println("3/ Tìm kiếm theo nhà xuất bản");
                System.out.println("Lựa chọn khác: thoát");
                int choice5 = TienIch.nhapSoNguyenDuong("Nhập: ");
                switch (choice5){
                    case 1:
                        boolean tontai51 =false;
                        String boloc51 = TienIch.nhapChuoi("Nhập tên tác giả cần tìm: ");
                        for(int i = 0 ; i < soluong;i++){
                            if(ds[i] instanceof Truyen){
                                Truyen t = (Truyen) ds[i];
                                if(boloc51.equalsIgnoreCase(t.getTacGia())){
                                    t.xuat();
                                    tontai51 = true;
                                }
                            }
                        }
                        if(!tontai51){
                            System.out.println("Không tồn tại bất kì mặt hàng nào có tên tác giả này !");
                        }
                    case 2:
                        boolean tontai52 =false;
                        String boloc52 = TienIch.nhapChuoi("Nhập thể loại cần tìm: ");
                        for(int i = 0 ; i < soluong;i++){
                            if(ds[i] instanceof Truyen){
                                Truyen t = (Truyen) ds[i];
                                if(boloc52.equalsIgnoreCase(t.getTheloai())){
                                    t.xuat();
                                    tontai52 = true;
                                }
                            }
                        }
                        if(!tontai52){
                            System.out.println("Không tồn tại bất kì mặt hàng nào có thể loại này !");
                        }
                    case 3:
                        boolean tontai53 =false;
                        String boloc53 = TienIch.nhapChuoi("Nhập NXB cần tìm: ");
                        for(int i = 0 ; i < soluong;i++){
                            if(ds[i] instanceof Truyen){
                                Truyen t = (Truyen) ds[i];
                                if(boloc53.equalsIgnoreCase(t.getNhaXuatBan())){
                                    t.xuat();
                                    tontai53 = true;
                                }
                            }
                        }
                        if(!tontai53){
                            System.out.println("Không tồn tại bất kì mặt hàng nào thuộc NXB này !");
                        }
                }

            case 6:
                System.out.println("Chọn danh mục tìm kiếm: ");
                System.out.println("1/ Tìm kiếm theo loại dụng cụ");
                System.out.println("2/ Tìm kiếm theo nhà sản xuất");
                System.out.println("Lựa chọn khác: thoát");
                int choice6 =TienIch.nhapSoNguyenDuong("Nhập: ");
                switch (choice6) {
                    case 1:
                        boolean tontai61 = false;
                        String boloc61 = TienIch.nhapChuoi("Nhập loại dụng cụ cần tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof DungCuHocTap) {
                                DungCuHocTap dcht = (DungCuHocTap) ds[i];
                                if (boloc61.equalsIgnoreCase(dcht.getLoaidungcu())) {
                                    dcht.xuat();
                                    tontai61 = true;
                                }
                            }
                        }
                        if (!tontai61) {
                            System.out.println("Không tồn tại bất kì mặt hàng nào thuộc loại dụng cụ này !");
                        }
                    case 2:
                        boolean tontai62 = false;
                        String boloc62 = TienIch.nhapChuoi("Nhập nhà sản xuất cần tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof DungCuHocTap) {
                                DungCuHocTap dcht = (DungCuHocTap) ds[i];
                                if (boloc62.equalsIgnoreCase(dcht.getnhaSX())) {
                                    dcht.xuat();
                                    tontai62 = true;
                                }
                            }
                        }
                        if (!tontai62) {
                            System.out.println("Không tồn tại bất kì mặt hàng nào thuộc nhà sản xuất này !");
                        }
                    default: break;
                }

            case 7:
                System.out.println("1/ Tìm kiếm theo nhà sản xuất");
                System.out.println("Lựa chọn khác: thoát");
                int choice7 =TienIch.nhapSoNguyenDuong("Nhập: ");
                switch (choice7){
                    case 1:
                        boolean tontai71 = false;
                        String boloc71 = TienIch.nhapChuoi("Nhập nhà sản xuất cần tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof TrangTri) {
                                TrangTri ttr = (TrangTri) ds[i];
                                if (boloc71.equalsIgnoreCase(ttr.getnhaSX())) {
                                    ttr.xuat();
                                    tontai71 = true;
                                }
                            }
                        }
                        if (!tontai71) {
                            System.out.println("Không tồn tại bất kì mặt hàng nào thuộc nhà sản xuất này !");
                        }
                    default: break;
                }

            case 8:
                System.out.println("1/ Tìm kiếm theo nhà sản xuất");
                System.out.println("2/ Tìm kiếm theo độ tuổi (ví dụ: 0->5 chọn 5, 5->10 chọn 10) ");
                System.out.println("Lựa chọn khác: thoát");
                int choice8 =TienIch.nhapSoNguyenDuong("Nhập: ");
                switch (choice8) {
                    case 1:
                        boolean tontai81 = false;
                        String boloc81 = TienIch.nhapChuoi("Nhập nhà sản xuất cần tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof DoChoi) {
                                DoChoi dc = (DoChoi) ds[i];
                                if (boloc81.equalsIgnoreCase(dc.getnhaSX())) {
                                    dc.xuat();
                                    tontai81 = true;
                                }
                            }
                        }
                        if (!tontai81) {
                            System.out.println("Không tồn tại bất kì mặt hàng nào thuộc nhà sản xuất này !");
                        }
                    case 2:
                        boolean tontai82 = false;
                        int boloc82 = TienIch.nhapSoNguyenDuong("Nhập độ tuổi đồ chơi cần tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof DoChoi) {
                                DoChoi dc = (DoChoi) ds[i];
                                if (boloc82 == dc.getdotuoi()) {
                                    dc.xuat();
                                    tontai82 = true;
                                }
                            }
                        }
                        if (!tontai82) {
                            System.out.println("Không tồn tại bất kì mặt hàng nào thuộc độ tuổi này !");
                        }
                    default:
                        break;
                }

            case 9:
                System.out.println("1/ Tìm kiếm theo nhà sản xuất");
                System.out.println("Lựa chọn khác: thoát");
                int choice9 =TienIch.nhapSoNguyenDuong("Nhập: ");
                switch (choice9){
                    case 1:
                        boolean tontai91 = false;
                        String boloc91 = TienIch.nhapChuoi("Nhập nhà sản xuất cần tìm: ");
                        for (int i = 0; i < soluong; i++) {
                            if (ds[i] instanceof Khac) {
                                Khac k = (Khac) ds[i];
                                if (boloc91.equalsIgnoreCase(k.getnhaSX())) {
                                    k.xuat();
                                    tontai91 = true;
                                }
                            }
                        }
                        if (!tontai91) {
                            System.out.println("Không tồn tại bất kì mặt hàng nào thuộc nhà sản xuất này !");
                        }
                    default: break;
                }

            default:
                break;
        }
    }
}
