/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author duyda
 */
public class DichVuu extends LoaiDichVu{
    int maDV;
    String TenDV;
    double GiaDV;

    public DichVuu() {
    }

    public int getMaDV() {
        return maDV;
    }

    public void setMaDV(int maDV) {
        this.maDV = maDV;
    }

    public String getTenDV() {
        return TenDV;
    }

    public void setTenDV(String TenDV) {
        this.TenDV = TenDV;
    }

    public double getGiaDV() {
        return GiaDV;
    }

    public void setGiaDV(double GiaDV) {
        this.GiaDV = GiaDV;
    }
    
    
}
