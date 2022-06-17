/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import Database.*;
import java.util.logging.*;
import java.util.*;

/**
 *
 * @author duyda
 */
public class Control {
    
    DefaultTableModel tableModel = new DefaultTableModel();
    Connect con = new Connect();
    
    // DAT PHONG
    
    public List<KhachHang> showKhachHang()
    {
         ArrayList<KhachHang> arr = new ArrayList<>();
         con.connect();
         ResultSet rs = con.showDB("select * from KhachHang");      
    try {
         while (rs.next())
         {
             KhachHang kh = new KhachHang();
             kh.setMaKH(rs.getInt("MaKhachHang"));
             kh.setTenKH(rs.getString("TenKhachHang"));
             kh.setSoDienThoai(rs.getString("SoDienThoai"));
             kh.setCMND(rs.getString("CMND"));
             kh.setSoKhach(rs.getInt("SoKhach"));
             kh.setGioiTinh(rs.getString("GioiTinh"));
             kh.setQuocTich(rs.getString("QuocTich"));
             kh.setDiaChi(rs.getString("DiaChi")); 
             
             arr.add(kh);
         }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    return  arr;
    }
    
    public List<KhachHang> showKhachHangCho()
    {
         ArrayList<KhachHang> arr = new ArrayList<>();
         con.connect();
         ResultSet rs = con.showDB("select * from KhachHang where MaKhachHang not in (select MaKhachHang from PhieuDatPhong) ");      
    try {
         while (rs.next())
         {
             KhachHang kh = new KhachHang();
             kh.setMaKH(rs.getInt("MaKhachHang"));
             kh.setTenKH(rs.getString("TenKhachHang"));
             kh.setSoDienThoai(rs.getString("SoDienThoai"));
             kh.setCMND(rs.getString("CMND"));
             kh.setSoKhach(rs.getInt("SoKhach"));
             kh.setGioiTinh(rs.getString("GioiTinh"));
             kh.setQuocTich(rs.getString("QuocTich"));
             kh.setDiaChi(rs.getString("DiaChi"));
             
             arr.add(kh);
         }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    return  arr;
    }
    
    
    
    public List<Phong> showPhong()
    {
        ArrayList<Phong> arr = new ArrayList<>();
         con.connect();
         ResultSet rs = con.showDB("select * from Phong,LoaiPhong where Phong.maloaiphong=loaiphong.maloaiphong");      
    try {
         while (rs.next())
         {
             Phong ph = new Phong();
             ph.setMaPhong(rs.getString("MaPhong"));
             ph.setTenPhong(rs.getString("TenPhong"));
             ph.setMaLoaiPhong(rs.getString("MaLoaiPhong"));
             ph.setTrangThai(rs.getString("TrangThai"));
             ph.setGhiChuPhong(rs.getString("GhiChu"));
             ph.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
             ph.setDienTich(rs.getFloat("DienTich"));
             ph.setGia(rs.getDouble("Gia")); 
             
             arr.add(ph);
         }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    return  arr;
    }
    
    public List<Phong> showDatPhong()
    {
        ArrayList<Phong> arrPH = new ArrayList<>();
         con.connect();
         String sql = "select * from PhieuDatPhong,Phong,LoaiPhong,ChiTietPhieuDatPhong\n" +
                "where Phong.MaPhong = PhieuDatPhong.MaPhong \n" +
                "and LoaiPhong.MaLoaiPhong = Phong.MaLoaiPhong\n" +
                "and ChiTietPhieuDatPhong.MaPhieuDat = PhieuDatPhong.MaPhieuDat";
         ResultSet rs = con.showDB(sql);      
    try {
         while (rs.next())
         {
             Phong ph = new Phong();
             ph.setMaPhong(rs.getString("MaPhong"));
             ph.setTenPhong(rs.getString("TenPhong"));
             ph.setMaLoaiPhong(rs.getString("MaLoaiPhong"));
             ph.setTrangThai(rs.getString("TrangThai"));
             ph.setGhiChuPhong(rs.getString("GhiChu"));
             ph.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
             ph.setDienTich(rs.getFloat("DienTich"));
             ph.setGia(rs.getDouble("Gia"));    
             ph.setMaKH(rs.getInt("MaKhachHang"));
             ph.setNgayDat(String.valueOf(rs.getDate("NgayDatPhong")));
             ph.setNgayTra(String.valueOf(rs.getDate("NgayTraPhong")));
             ph.setGioDat(String.valueOf(rs.getTime("NgayDatPhong")));
             ph.setGioTra(String.valueOf(rs.getTime("NgayTraPhong")));
             ph.setMaDV(rs.getInt("MaDichVu"));
             ph.setSoluongDV(rs.getInt("SoLuong"));
             ph.setMaPhieuDat(rs.getInt("MaPhieuDat"));
             arrPH.add(ph);
         }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    return  arrPH;
    }
   
    public void insertKH(KhachHang kh)
    {
        String sql = "insert into KhachHang(TenKhachHang,SoDienThoai,CMND,SoKhach,GioiTinh,QuocTich,DiaChi) "
                  +  "values (N'"+kh.getTenKH()+"','"+kh.getSoDienThoai()+"','"+kh.getCMND()+"','"+kh.getSoKhach()+"',N'"+kh.getGioiTinh()+"',N'"+kh.getQuocTich()+"',N'"+kh.getDiaChi()+"')";
        con.updateDB(sql);
    }
    
    public void deleteKH(int ma)
    {
        String sql = "delete KhachHang where MaKhachHang = " + ma + "";
        con.updateDB(sql);
    }
    
    public ResultSet sreachTenKH(String tenKH){
        con.connect();
        String sql="select * from KhachHang where TenKhachHang like N'%"+ tenKH +"%' and MaKhachHang not in (select MaKhachHang from PhieuDatPhong)";
        return con.showDB(sql);
    }
    
    public int searchMaxMaKH()
    {
        con.connect();
        String sql = "select makhachhang from KhachHang\n" +
                      "where MaKhachHang >= all (select MaKhachHang from KhachHang)";
        int i = 0;
        ResultSet rs = con.showDB(sql);
        try {
            while (rs.next()) {
                
                i = rs.getInt("makhachhang");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return i;
    }
    
    public void insertDatPhong(String maPhongString, int maKH)
    {
        String sql = "insert into PhieuDatPhong(maphong,makhachhang,MaDichVu,SoLuong) values ('"+maPhongString+"','"+maKH+"','1','0')";
        con.updateDB(sql);
    }
    
    public int getMaPhieuDat()
    {
        con.connect();
        String sql = "select MaPhieuDat from PhieuDatPhong where MaPhieuDat >= all (select MaPhieuDat from PhieuDatPhong)";
        int i = 0;
        ResultSet rs = con.showDB(sql);
        try {
            while (rs.next()) {
                
                i = rs.getInt("MaPhieuDat");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return i;
    }
    
    public void insertCTDatPhong(int maPhieuDat,String NgayDat,String NgayTra)
    {
        con.connect();
        String sql = "insert into ChiTietPhieuDatPhong(MaPhieuDat,NgayDatPhong,NgayTraPhong) "
                +    "values ('"+maPhieuDat+"','"+NgayDat+"','"+NgayTra+"')";
        con.updateDB(sql);
    }
    
    // DICH VU
    
    public List<LoaiDichVu> showLoaiDichVu()
    {
        ArrayList<LoaiDichVu> arr = new ArrayList<>();
        con.connect();
        ResultSet rs = con.showDB("select * from LoaiDichVu");
        try {
            while(rs.next() )
            {
                LoaiDichVu ldv = new LoaiDichVu();
                ldv.setMaLoaiDV(rs.getString("MaLoaiDichVu"));
                ldv.setTenLoaiDV(rs.getString("TenLoaiDichVu"));
                arr.add(ldv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
    public List<DichVuu> showDichVu()
    {
        ArrayList<DichVuu> arr = new ArrayList<>();
        con.connect();
        ResultSet rs = con.showDB("select * from DichVu,LoaiDichVu where DichVu.MaLoaiDichVu = LoaiDichVu.MaLoaiDichVu");
        try {
            while(rs.next() )
            {
                DichVuu dv = new DichVuu();
                dv.setMaDV(rs.getInt("MaDichVu"));
                dv.setTenDV(rs.getString("TenDichVu"));
                dv.setGiaDV(rs.getDouble("GiaTien"));
                dv.setMaLoaiDV(rs.getString("MaLoaiDichVu"));
                dv.setTenLoaiDV(rs.getString("TenLoaiDichVu"));
                        
                arr.add(dv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
    public void insertDichVu(DichVuu dv)
    {
        String sql = "insert into DichVu(TenDichVu,GiaTien,MaLoaiDichVu) "
                   + "values (N'"+dv.getTenDV()+"','"+dv.getGiaDV()+"','"+dv.getMaLoaiDV()+"')";
        con.updateDB(sql);
    }
    
    public int searchMaxMaDV()
    {
        con.connect();
        String sql = "select MaDichVu from DichVu \n" +
                     "where MaDichVu >= all (select MaDichVu from DichVu)";
        int i = 0;
        ResultSet rs = con.showDB(sql);
        try {
            while (rs.next()) {
                
                i = rs.getInt("MaDichVu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return i;
    }
    
    public void deleteDV(int ma)
    {
        String sql = "delete DichVu where MaDichVu = " + ma + "";
        con.updateDB(sql);
    }
    
    public void updateDV(DichVuu dv)
    {
        String sql = "update DichVu set TenDichVu= N'"+dv.getTenDV()+"', \n" +
                     "GiaTien= '"+ dv.getGiaDV() +"' , MaLoaiDichVu ='"+dv.getMaLoaiDV()+"' where MaDichVu = "+dv.getMaDV()+"" ;
        con.updateDB(sql);
    }
    
    public ResultSet searchTenDV(String tenDV){
        con.connect();
        String sql="select * from DichVu where TenDichVu like N'%"+ tenDV +"%'";
        return con.showDB(sql);
        
    }
    
    public List<KhachHang> showKHDatPhong()
    {
         ArrayList<KhachHang> arr = new ArrayList<>();
         con.connect();
         ResultSet rs = con.showDB("select * from KhachHang where MaKhachHang in (select MaKhachHang from PhieuDatPhong)");      
    try {
         while (rs.next())
         {
             KhachHang kh = new KhachHang();
             kh.setMaKH(rs.getInt("MaKhachHang"));
             kh.setTenKH(rs.getString("TenKhachHang"));
             kh.setSoDienThoai(rs.getString("SoDienThoai"));
             kh.setCMND(rs.getString("CMND"));
             kh.setSoKhach(rs.getInt("SoKhach"));
             kh.setGioiTinh(rs.getString("GioiTinh"));
             kh.setQuocTich(rs.getString("QuocTich"));
             kh.setDiaChi(rs.getString("DiaChi")); 
             
             arr.add(kh);
         }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    return  arr;
    }
    
    public void addDVvaoDatPhong(String maPhong, int maKH, int maDV, int soLuong)
    {
        String sql = "insert into PhieuDatPhong(MaPhong,MaKhachHang,MaDichVu,SoLuong) values('"+maPhong+"','"+maKH+"','"+maDV+"','"+soLuong+"')" ;
        con.updateDB(sql);
    }
    
//    public void updateDVTheoPhong(int MaDv,int soLuong, String maPH)
//    {
//        String sql = "update PhieuDatPhong set MaDichVu = '"+MaDv+"',SoLuong = '"+soLuong+"' where MaPhong = '"+maPH+"'" ;
//        con.updateDB(sql);
//    }
    
    public ResultSet searchTenKHDatPhong(String tenKH){
        con.connect();
        String sql="select * from KhachHang where TenKhachHang like N'%"+tenKH+"%' \n" +
        "and MaKhachHang in (select MaKhachHang from PhieuDatPhong)";
        return con.showDB(sql);
    }
     
    public ResultSet searchTenPhongDat(String tenPhong)
    {
        con.connect();
        String sql = "select * from Phong,ChiTietPhieuDatPhong,PhieuDatPhong where TenPhong like N'%"+tenPhong+"%' \n" +
                     "and Phong.MaPhong = PhieuDatPhong.MaPhong\n" +
                     "and ChiTietPhieuDatPhong.MaPhieuDat = PhieuDatPhong.MaPhieuDat\n" +
                     "and Phong.MaPhong in (select MaPhong from PhieuDatPhong)";
        return con.showDB(sql);
    }
    
    public int showMaDV(int maDV)
    { 
        String sql = "select * from PhieuDatPhong where MaDichVu ="+maDV+"";  
        ResultSet rs = con.showDB(sql);
        int i = 0;
        try {
            while (rs.next()) {
                
                i = rs.getInt("MaDichVu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    
    // TRA PHONG 
    
    public ArrayList<LoaiPhong>  showLoaiPhong()
    {
        ArrayList<LoaiPhong> arr = new ArrayList<>();
        String sql = "select * from LoaiPhong";
        con.connect();
        ResultSet rs = con.showDB(sql);
        
         try {
         while (rs.next())
         {
             LoaiPhong lph = new LoaiPhong();
             lph.setMaLoaiPhong(rs.getString("MaLoaiPhong"));
             lph.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
             lph.setGia(rs.getDouble("Gia"));
             lph.setDienTich(rs.getFloat("DienTich"));
             
             arr.add(lph);
         }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    return  arr;
        
    }
    
    public ResultSet searchTenKhachHangDatPhong(String tenKhachHang)
    {
        con.connect();
        String sql = "select * from Phong,ChiTietPhieuDatPhong,PhieuDatPhong,KhachHang,LoaiPhong where TenKhachhang like N'%"+ tenKhachHang +"%' \n" +
                     "and Phong.MaPhong = PhieuDatPhong.MaPhong \n" +
                     "and ChiTietPhieuDatPhong.MaPhieuDat = PhieuDatPhong.MaPhieuDat\n" +
                     "and KhachHang.MaKhachHang = PhieuDatPhong.MaKhachHang\n" +
                     "and LoaiPhong.MaLoaiPhong = Phong.MaLoaiPhong\n" +
                     "and Phong.MaPhong in (select MaPhong from PhieuDatPhong)";
        return con.showDB(sql);
    }
    
    public ResultSet searchPhongDat(String tenPhong)
    {
        con.connect();
        String sql = "select * from Phong,ChiTietPhieuDatPhong,PhieuDatPhong,KhachHang,LoaiPhong where TenPhong like N'%"+tenPhong+"%' \n" +
"                     and Phong.MaPhong = PhieuDatPhong.MaPhong \n" +
"                     and ChiTietPhieuDatPhong.MaPhieuDat = PhieuDatPhong.MaPhieuDat\n" +
"                     and KhachHang.MaKhachHang = PhieuDatPhong.MaKhachHang\n" +
"                     and LoaiPhong.MaLoaiPhong = Phong.MaLoaiPhong";
        return con.showDB(sql);
    }
    
    public void updateNgayTraPhong(int maPhieuDat,String ngayTraPhong)
    {
        String sql = "update ChiTietPhieuDatPhong set NgayTraPhong = '"+ngayTraPhong+"' where MaPhieuDat = "+maPhieuDat+"" ;
        con.updateDB(sql);
    }
    
    public void insertTraPhong(Phong ph,double TongTien)
    {
        String sql = "insert into PhieuTraPhong(MaPhong,MaKhachHang,TongTien,NgayLapPhieu)\n" +
                     "values ('"+ph.getMaPhong()+"','"+ph.getMaKH()+"','"+TongTien+"','"+ph.getNgayTra()+"')";
        con.updateDB(sql);
    }
    
    public void deleteDatPhong(Phong ph)
    {
        String sql = "delete ChiTietPhieuDatPhong where MaPhieuDat = "+ph.getMaPhieuDat()+"\n" +
                     "delete PhieuDatPhong where MaPhong = '"+ph.getMaPhong()+"'";
        con.updateDB(sql);
    }
    
    public double tongTienDV(Phong ph)
    {
        String sql = "select sum(GiaTien*SoLuong)as 'TongTienDV' from PhieuDatPhong,DichVu \n" +
                     "where MaPhong = '"+ph.getMaPhong()+"'\n" +
                     "and PhieuDatPhong.MaDichVu = DichVu.MaDichVu";
        double tien = 0;
        ResultSet rs = con.showDB(sql);
        try {
            while (rs.next()) {
                
                tien = rs.getInt("TongTienDV");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return tien;
    }
    
    
    // CHI TIET DICH VU
    
        public ResultSet showCTDV(String maPhong)
        {
            String sql="select * from PhieuDatPhong where MaPhong = '"+maPhong+"'";
            return con.showDB(sql);
        }
    
    // THOGN KE
    
    public List<TraPhong> showTKTraPhong()
    {
        ArrayList<TraPhong> arr = new ArrayList<>();
         con.connect();
         ResultSet rs = con.showDB("select  * from PhieuTraPhong");      
    try {
         while (rs.next())
         {
             TraPhong tph = new TraPhong();
             tph.setMaPhieuTra(rs.getInt("MaPhieuTra"));
             tph.setMaPhong(rs.getString("MaPhong"));
             tph.setMaKH(rs.getInt("MaKhachHang"));
             tph.setTongTien(rs.getDouble("TongTien"));
             tph.setNgayLapPhieu(rs.getString("NgayLapPhieu"));
              
             arr.add(tph);
         }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    return  arr;
    }
    public ResultSet showTKNgayThang(String ngayNhap, String ngayXuat )
    {
        String sql="select * from PhieuTraPhong where NgayLapPhieu between '"+ngayNhap+"' and '"+ngayXuat+"' ";
        return con.showDB(sql);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // DUC
    
   
    // LOAI DICH VU
    
     public void themldv(String maloaiDV, String tenloaiDV){
        con.connect();
        String sql = "insert into LoaiDichVu(MaLoaiDichVu,TenLoaiDichVu) values('"+maloaiDV+"',N'"+ tenloaiDV +"')";
        con.updateDB(sql);
    }
    
    public void sualdv(String maloaiDV, String tenloaiDV){
        con.connect();
        String sql = "update LoaiDichVu set TenLoaiDichVu = '" + tenloaiDV + "'  where MaLoaiDichVu = '" + maloaiDV+ "'";
        con.updateDB(sql);
    }
    public void xoaldv(String maloaiDV){
        con.connect();
        String sql = "delete from LoaiDichVu where MaLoaiDichVu ='"+maloaiDV+"'";
        con.updateDB(sql);
    }
    public ResultSet timkiemldv(String tenloaiDV){
         con.connect();
         String sql = "SELECT * FROM LoaiDichVu WHERE TenLoaiDichVu LIKE N'%"+tenloaiDV+"%'";
         return con.showDB(sql);
        
     }
    
    
    // PHONG
    
    public void insertPhong(String MaPhong, String TenPhong , String MaLoaiPhong, String TrangThai, String GhiChu){
        con.connect();
        String sql = "insert into Phong(MaPhong,TenPhong,MaLoaiPhong,TrangThai,GhiChu) values('"+MaPhong+"',N'"+TenPhong+"','"+MaLoaiPhong+"',N'"+TrangThai+"',N'"+GhiChu+"')";
        con.updateDB(sql);
    }
    
    
    public void updatePhong(String MaPhong, String TenPhong , String MaLoaiPhong, String TrangThai, String GhiChu){
        con.connect();
        String sql = "update Phong set TenPhong = N'" + TenPhong + "',MaLoaiPhong = '"+ MaLoaiPhong +"',TrangThai = N'"+ TrangThai +"',GhiChu = N'"+ GhiChu +"'  where MaPhong = '" + MaPhong+ "' " ;
        con.updateDB(sql);
    }
    
    public void deletePhong(String MaPhong){
        con.connect();
        String sql = "delete from Phong where MaPhong = '" +MaPhong + "' ";
        con.updateDB(sql);
    }
    public ResultSet timphong(String TenPhong){
         con.connect();
         String sql = "SELECT * FROM Phong WHERE TenPhong LIKE N'%"+TenPhong+"%'";
         return con.showDB(sql);
        
    }
    
    // TRA PHONG
    
    
    public void insertLp(String MaLoaiPhongString, String TenLoaiPhong, String DienTich, String Gia){
        con.connect();
        String sql = "insert into LoaiPhong(MaLoaiPhong,TenLoaiPhong,DienTich,Gia) values('"+MaLoaiPhongString+"',N'"+TenLoaiPhong+"','"+DienTich+"','"+Gia+"')";
        con.updateDB(sql);
    }
    
    public void updateLP(String MaLoaiPhongString, String TenLoaiPhong, String DienTich, String Gia){
        con.connect();
        String sql = "update LoaiPhong set TenLoaiPhong = N'"+TenLoaiPhong+"',DienTich ='"+DienTich+"',Gia ='"+Gia+"'where MaLoaiPhong ='"+MaLoaiPhongString+"'";
        con.updateDB(sql);
    }
    
    public void deleteLP(String MaLoaiPhongString){
        con.connect();
        String sql = "delete from LoaiPhong where MaLoaiPhong ='"+MaLoaiPhongString+"'";
        con.updateDB(sql);
    }
    
    public ResultSet timLphong(String TenLoaiPhong){
         con.connect();
         String sql = "SELECT * FROM LoaiPhong WHERE TenLoaiPhong LIKE N'%"+TenLoaiPhong+"%'";
         return con.showDB(sql);
        
     }
    
    //Nguoi Dung
    
    public void themtk(String TenDangNhap, String MatKhau)
    {
        con.connect();
        String sql = "insert into NguoiDung(TenDangNhap,MatKhau) values('"+TenDangNhap+"','"+ MatKhau +"')";
        con.updateDB(sql);
    }
    public void doimatkhau(String MatKhau,String TenDangNhap)
    {
        con.connect();
        String sql = "update NguoiDung set MatKhau = '" + MatKhau + "'  where TenDangNhap = '" + TenDangNhap+ "' " ;
        con.updateDB(sql);
    }
    
    public void xoamk(String MatKhau)
    {
        String sql = "delete from MatKhau where MatKhau'" + MatKhau + "' ";
        con.updateDB(sql);
    }
    
    
    //KH
    
    public ResultSet timkiemkh(String tenKH){
         con.connect();
         String sql = "SELECT * FROM KhachHang WHERE TenKhachHang LIKE N'%"+tenKH+"%'";
         return con.showDB(sql);
        
     }
     
    public void insertKhachHang(String tenKH, String soDienThoai, String CMND,int soKhach, String gioiTinh, String quocTich,String diaChi)
    {
        con.connect();
        String sql = "insert into KhachHang(TenKhachHang,SoDienThoai,CMND,SoKhach,GioiTinh,QuocTich,DiaChi) values(N'"+tenKH+"','"+soDienThoai+"','"+CMND+"',N'"+soKhach+"',N'"+gioiTinh+"',N'"+quocTich+"',N'"+diaChi+"')";
        con.updateDB(sql);
        
    }
    
    
    
    public void updateKhachHang(int maKH,String tenKH, String soDienThoai, String CMND,int soKhach, String gioiTinh, String quocTich,String diaChi)
    {
        con.connect();
        String sql = "update KhachHang set TenKhachHang = N'" + tenKH + "',SoDienThoai = '"+ soDienThoai +"',CMND = '"+ CMND +"',SoKhach = '"+ soKhach + "',GioiTinh = N'"+ gioiTinh+"',QuocTich = N'"+ quocTich +"',DiaChi = N'"+diaChi+"'  where MaKhachHang = '" + maKH+ "' " ;
        con.updateDB(sql);
    }
    
    public void deleteKhachHang(int maKH)
    {
        con.connect();
        String sql = "delete from KhachHang where MaKhachHang = '" + maKH + "' ";
        con.updateDB(sql);
    }
    
}   




