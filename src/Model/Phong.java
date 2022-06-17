/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author duyda
 */
public class Phong extends LoaiPhong{
    String maPhong,tenPhong,trangThai,ghiChuPhong;
    String ngayDat,ngayTra,gioDat,gioTra;
    int maKH,SoluongDV;
    int maDV,maPhieuDat;

    
    public Phong() {
    }

    public int getMaPhieuDat() {
        return maPhieuDat;
    }

    public void setMaPhieuDat(int maPhieuDat) {
        this.maPhieuDat = maPhieuDat;
    }

    
    
    public int getSoluongDV() {
        return SoluongDV;
    }

    public void setSoluongDV(int SoluongDV) {
        this.SoluongDV = SoluongDV;
    }

    
    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getMaDV() {
        return maDV;
    }

    public void setMaDV(int maDV) {
        this.maDV = maDV;
    }

    

    
    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public String getGhiChuPhong() {
        return ghiChuPhong;
    }

    public void setGhiChuPhong(String ghiChuPhong) {
        this.ghiChuPhong = ghiChuPhong;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getGioDat() {
        return gioDat;
    }

    public void setGioDat(String gioDat) {
        this.gioDat = gioDat;
    }

    public String getGioTra() {
        return gioTra;
    }

    public void setGioTra(String gioTra) {
        this.gioTra = gioTra;
    }
    
    
}
