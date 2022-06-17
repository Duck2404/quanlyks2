/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormQLKS;

import java.sql.*;
import java.util.logging.*;
import Model.*;
import java.util.ArrayList;
import javax.swing.table.*;

/**
 *
 * @author duyda
 */
public class QuanLyKhachSan extends javax.swing.JFrame {

    /**
     * Creates new form QuanLyKhachSan
     */
    
    DefaultTableModel tableModel = new DefaultTableModel();
    static ArrayList<KhachHang> arrKhachHangCho,arrKhachHang;
    static  ArrayList<Phong> arrPhong,arrDatPhong;
    Control control = new Control();
    
    String gioiTinh =  "";
    
   
    public QuanLyKhachSan() {
        initComponents();
        loadData();
        rdbNam.setSelected(true);
        this.setTitle("Đặt Phòng");
        
    }
    
    public void loadData()
    {
        arrKhachHang = (ArrayList<KhachHang>) control.showKhachHang();
        arrPhong = (ArrayList<Phong>) control.showPhong();
        loadTablePhongTrong(arrPhong);
        arrDatPhong = (ArrayList<Phong>) control.showDatPhong();
        loadTableDatPhong(arrDatPhong, arrKhachHang);
        arrKhachHangCho = (ArrayList<KhachHang>) control.showKhachHangCho();
        loadTableKH(arrKhachHangCho);
    }
    
    public void clearText()
    {
        txtCMND.setText("");
        txtDiaChi.setText("");
        txtQuocTich.setText("");
        txtSDT.setText("");
        txtSoKhach.setText("");
        txtTenKH.setText("");
        rdbNam.setSelected(true);
    }
    
    public void loadTableKH(ArrayList<KhachHang> arr)
    {
        tableModel = (DefaultTableModel) tbKhachHangCho.getModel();
        tableModel.setRowCount(0);
        for (KhachHang kh : arr) {
            {
                String rows[] = new String[5];
                    rows[0] = String.valueOf(kh.getMaKH());
                    rows[1] = kh.getTenKH();
                    rows[2] = kh.getGioiTinh();
                    rows[3] = kh.getSoDienThoai();
                    rows[4] = String.valueOf(kh.getSoKhach());
                
                tableModel.addRow(rows);
            }
        }
    }
    
    public void loadTablePhongTrong(ArrayList<Phong> arr )
    {
        tableModel = (DefaultTableModel) tbPhongTrong.getModel();
        tableModel.setRowCount(0);
        for (Phong ph : arr) {
            if(ph.getTrangThai().equals("Trống"))
            {
                String rows[] = new String[5];
                    rows[0] = ph.getMaPhong();
                    rows[1] = ph.getTenPhong();
                    rows[2] = ph.getTenLoaiPhong();
                    rows[3] = String.valueOf(ph.getGia());
                    rows[4] = ph.getGhiChuPhong();
                
                tableModel.addRow(rows);
            }
        }
    }
    
    public void loadTableDatPhong(ArrayList<Phong> arrPhong, ArrayList<KhachHang> arrKH)
    {
        tableModel = (DefaultTableModel) tbDatPhong.getModel();
        tableModel.setRowCount(0);
        for (Phong ph : arrPhong) 
        {
            for (KhachHang kh : arrKH)
            {
                if(kh.getMaKH() == ph.getMaKH())
                {
                    String rows[] = new String[8];
                        rows[0] = ph.getTenPhong();
                        rows[1] = kh.getTenKH() ;
                        rows[2] = ph.getTenLoaiPhong();
                        rows[3] = String.valueOf(ph.getGia());
                        rows[4] = ph.getNgayDat();
                        rows[5] = ph.getGioDat();
                        rows[6] = ph.getNgayTra();
                        rows[7] = ph.getGioTra();

                    tableModel.addRow(rows);
                }
           } 
        }
    }

    public void showDetailsKH(ArrayList<KhachHang> arrKH)
    {
        tableModel = (DefaultTableModel) tbKhachHangCho.getModel();
        if(tbKhachHangCho.getSelectedRow()>-1)
        {
            int rowIndex = tbKhachHangCho.getSelectedRow();
            int maKH = Integer.parseInt(tableModel.getValueAt(rowIndex, 0).toString());
            
            for (KhachHang kh : arrKH) {
                if(kh.getMaKH()==maKH)
                {
                    txtTenKH.setText(kh.getTenKH());
                    txtSDT.setText(kh.getSoDienThoai());
                    txtCMND.setText(kh.getCMND());
                    txtSoKhach.setText( String.valueOf(kh.getSoKhach()));
                    txtQuocTich.setText(kh.getQuocTich());
                    txtDiaChi.setText(kh.getDiaChi());
                    if(kh.getGioiTinh().equals(rdbNam.getText()))
                        rdbNam.setSelected(true);
                    else if(kh.getGioiTinh().equals(rdbNu.getText()))
                        rdbNu.setSelected(true);
                }
            }
        }
    }
    
    public void addKhachHang()
    {
        KhachHang kh = new KhachHang();
        kh.setMaKH(control.searchMaxMaKH()+1);
        kh.setTenKH(txtTenKH.getText());
        kh.setSoDienThoai(txtSDT.getText());
        kh.setCMND(txtCMND.getText());
        kh.setSoKhach( Integer.valueOf(txtSoKhach.getText()));
        kh.setGioiTinh(gioiTinh);
        kh.setQuocTich(txtQuocTich.getText());
        kh.setDiaChi(txtDiaChi.getText());
        
        arrKhachHang.add(kh);
        arrKhachHangCho.add(kh);
        control.insertKH(kh);
    }
    
    public void deleteKhachHang(ArrayList<KhachHang> arrKH)
    {
        tableModel = (DefaultTableModel) tbKhachHangCho.getModel();
        if(tbKhachHangCho.getSelectedRow()>-1)
        {
            int rowIndex = tbKhachHangCho.getSelectedRow();
            int maKH = Integer.parseInt(tableModel.getValueAt(rowIndex, 0).toString());
            
            for (KhachHang kh : arrKH) {
                if(kh.getMaKH() == maKH)
                {
                    control.deleteKH(kh.getMaKH());
                    arrKH.remove(kh);
                    loadTableKH(arrKH);
                    clearText();
                }
            }
        }
        
    }
    
    public void searchTenKH(ArrayList<KhachHang> arrKH)
    {
        tableModel = (DefaultTableModel) tbKhachHangCho.getModel();
        tableModel.setRowCount(0);
        
        ResultSet rs = control.sreachTenKH(txtTenKH.getText());
        try {
            while (rs.next()) {
                String rows[] = new String[5];
                    rows[0] = String.valueOf(rs.getInt("MaKhachHang"));
                    rows[1] = rs.getString("TenKhachHang");
                    rows[2] = rs.getString("GioiTinh");
                    rows[3] = rs.getString("SoDienThoai");
                    rows[4] = String.valueOf(rs.getInt("SoKhach"));
                
                tableModel.addRow(rows);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyKhachSan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addDatPhong(ArrayList<KhachHang> arrKH, ArrayList<Phong> arrPH)
    {
        int maKH=0;
        String maPhong="";
        tableModel = (DefaultTableModel) tbKhachHangCho.getModel();
        if(tbKhachHangCho.getSelectedRow()>-1)
        {
            int rowIndex = tbKhachHangCho.getSelectedRow();
             maKH = Integer.parseInt(tableModel.getValueAt(rowIndex, 0).toString());
        }
        
        tableModel = (DefaultTableModel) tbPhongTrong.getModel();
        if(tbPhongTrong.getSelectedRow()>-1)
        {
            int rowIndex = tbPhongTrong.getSelectedRow();
             maPhong = tableModel.getValueAt(rowIndex, 0).toString();
        }

         Phong ph = new Phong();
         ph.setMaKH(maKH);
         ph.setMaPhong(maPhong);
        
         arrDatPhong.add(ph);
         control.insertDatPhong(maPhong, maKH);
    }
    
    public void addChiTietDatPhong()
    {
        Phong ph = new Phong();
        String NgayDatString,NgayTraString;
        int maPhieuDat = control.getMaPhieuDat();
        
        ph.setNgayDat(NgayGioDatPhong.ngayDat);
        ph.setGioDat(NgayGioDatPhong.gioDat);
        ph.setNgayTra(NgayGioDatPhong.ngayTra);
        ph.setGioTra(NgayGioDatPhong.gioTra);
        
        NgayDatString = ph.getNgayDat() + " " + ph.getGioDat();
        NgayTraString = ph.getNgayTra()+ " " +ph.getGioTra();
        
        arrDatPhong.add(ph);
        control.insertCTDatPhong(maPhieuDat,NgayDatString, NgayTraString);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jToolBar2 = new javax.swing.JToolBar();
        btnMenuKhachHang = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        btnTimKH = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCMND = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        txtQuocTich = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        txtSoKhach = new javax.swing.JTextField();
        btnThemKH = new javax.swing.JButton();
        btnXoaKH = new javax.swing.JButton();
        btnClearKH = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbKhachHangCho = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbPhongTrong = new javax.swing.JTable();
        btnDatPhong = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbDatPhong = new javax.swing.JTable();
        btnCapNhat = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đặt Phòng Khách Sạn");
        setBackground(new java.awt.Color(0, 153, 153));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jToolBar2.setBackground(new java.awt.Color(204, 204, 0));
        jToolBar2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar2.setRollover(true);
        jToolBar2.setBorderPainted(false);

        btnMenuKhachHang.setBackground(new java.awt.Color(204, 204, 0));
        btnMenuKhachHang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMenuKhachHang.setForeground(new java.awt.Color(242, 242, 242));
        btnMenuKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/Login-icon-16.png"))); // NOI18N
        btnMenuKhachHang.setText("Đặt Phòng");
        btnMenuKhachHang.setBorderPainted(false);
        btnMenuKhachHang.setFocusable(false);
        btnMenuKhachHang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnMenuKhachHang.setMargin(new java.awt.Insets(1, 10, 1, 20));
        btnMenuKhachHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMenuKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuKhachHangActionPerformed(evt);
            }
        });
        jToolBar2.add(btnMenuKhachHang);

        jButton2.setBackground(new java.awt.Color(204, 204, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(242, 242, 242));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/Actions-help-about-icon-16.png"))); // NOI18N
        jButton2.setText("Dịch Vụ");
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.setMargin(new java.awt.Insets(1, 10, 1, 20));
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton2);

        jButton3.setBackground(new java.awt.Color(204, 204, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(242, 242, 242));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/logout-icon-16.png"))); // NOI18N
        jButton3.setText("Trả Phòng");
        jButton3.setBorderPainted(false);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton3.setMargin(new java.awt.Insets(1, 10, 1, 20));
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton3);

        jButton7.setBackground(new java.awt.Color(204, 204, 0));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(242, 242, 242));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/Person-Male-Light-icon-16.png"))); // NOI18N
        jButton7.setText("Khách Hàng");
        jButton7.setToolTipText("");
        jButton7.setBorderPainted(false);
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton7.setMargin(new java.awt.Insets(1, 10, 1, 20));
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton7);

        jButton4.setBackground(new java.awt.Color(204, 204, 0));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(242, 242, 242));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/Home-icon-16.png"))); // NOI18N
        jButton4.setText("Phòng");
        jButton4.setBorderPainted(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.setMargin(new java.awt.Insets(1, 10, 1, 20));
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton4);

        jButton6.setBackground(new java.awt.Color(204, 204, 0));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(242, 242, 242));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/Apps-preferences-desktop-user-password-icon-16.png"))); // NOI18N
        jButton6.setText("Người Dùng");
        jButton6.setBorderPainted(false);
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton6.setMargin(new java.awt.Insets(1, 10, 1, 20));
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton6);

        getContentPane().add(jToolBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, 30));

        jPanel1.setBackground(new java.awt.Color(153, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đặt Phòng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(153, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tên Khách:");
        jLabel1.setToolTipText("");

        txtTenKH.setBackground(new java.awt.Color(234, 248, 240));

        btnTimKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/btn_search.png"))); // NOI18N
        btnTimKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKHActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Số Điện Thoại: ");

        txtSDT.setBackground(new java.awt.Color(234, 248, 240));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("CMND: ");

        txtCMND.setBackground(new java.awt.Color(234, 248, 240));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Giới Tính:");

        rdbNam.setBackground(new java.awt.Color(153, 0, 0));
        buttonGroup1.add(rdbNam);
        rdbNam.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdbNam.setForeground(new java.awt.Color(255, 255, 255));
        rdbNam.setText("Nam");
        rdbNam.setToolTipText("");
        rdbNam.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rdbNamStateChanged(evt);
            }
        });

        rdbNu.setBackground(new java.awt.Color(153, 0, 0));
        buttonGroup1.add(rdbNu);
        rdbNu.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdbNu.setForeground(new java.awt.Color(255, 255, 255));
        rdbNu.setText("Nữ");
        rdbNu.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rdbNuStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Quốc Tịch:");
        jLabel5.setToolTipText("");

        txtQuocTich.setBackground(new java.awt.Color(234, 248, 240));

        txtDiaChi.setBackground(new java.awt.Color(234, 248, 240));
        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane2.setViewportView(txtDiaChi);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Số Khách: ");

        txtSoKhach.setBackground(new java.awt.Color(234, 248, 240));

        btnThemKH.setBackground(new java.awt.Color(222, 242, 241));
        btnThemKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemKH.setForeground(new java.awt.Color(153, 153, 0));
        btnThemKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/btn_add.png"))); // NOI18N
        btnThemKH.setText("Thêm KH");
        btnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKHActionPerformed(evt);
            }
        });

        btnXoaKH.setBackground(new java.awt.Color(222, 242, 241));
        btnXoaKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaKH.setForeground(new java.awt.Color(255, 204, 51));
        btnXoaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/delete (1).png"))); // NOI18N
        btnXoaKH.setText("Xóa KH");
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });

        btnClearKH.setBackground(new java.awt.Color(222, 242, 241));
        btnClearKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnClearKH.setForeground(new java.awt.Color(255, 51, 51));
        btnClearKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/cancel.png"))); // NOI18N
        btnClearKH.setText("Bỏ Chọn");
        btnClearKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearKHActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Địa Chỉ: ");
        jLabel8.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(28, 28, 28)
                                    .addComponent(btnXoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(7, 7, 7)
                                    .addComponent(txtCMND, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(btnTimKH, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(txtSoKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(rdbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(rdbNu, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(txtQuocTich, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnClearKH, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1))
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKH, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2))
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3))
                    .addComponent(txtCMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7))
                    .addComponent(txtSoKhach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel4))
                    .addComponent(rdbNam)
                    .addComponent(rdbNu))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel5))
                    .addComponent(txtQuocTich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel8))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoaKH)
                    .addComponent(btnThemKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClearKH)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(153, 0, 0));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Khách Hàng Đặt", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        tbKhachHangCho.setBackground(new java.awt.Color(234, 248, 240));
        tbKhachHangCho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã KH", "Tên KH", "Giới Tính", "SDT", "Số Khách"
            }
        ));
        tbKhachHangCho.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tbKhachHangChoMouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(tbKhachHangCho);
        if (tbKhachHangCho.getColumnModel().getColumnCount() > 0) {
            tbKhachHangCho.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbKhachHangCho.getColumnModel().getColumn(0).setHeaderValue("Mã KH");
            tbKhachHangCho.getColumnModel().getColumn(1).setPreferredWidth(80);
            tbKhachHangCho.getColumnModel().getColumn(2).setPreferredWidth(50);
            tbKhachHangCho.getColumnModel().getColumn(3).setPreferredWidth(60);
            tbKhachHangCho.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Phòng Trống", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        tbPhongTrong.setBackground(new java.awt.Color(234, 248, 240));
        tbPhongTrong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phòng", "Tên Phòng", "Loại Phòng", "Giá", "Ghi Chú"
            }
        ));
        jScrollPane3.setViewportView(tbPhongTrong);
        if (tbPhongTrong.getColumnModel().getColumnCount() > 0) {
            tbPhongTrong.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbPhongTrong.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnDatPhong.setBackground(new java.awt.Color(222, 242, 241));
        btnDatPhong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDatPhong.setForeground(new java.awt.Color(255, 204, 0));
        btnDatPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/bell.png"))); // NOI18N
        btnDatPhong.setText("Đặt Phòng");
        btnDatPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDatPhongActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(153, 0, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách phòng đã đặt", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        tbDatPhong.setBackground(new java.awt.Color(234, 248, 240));
        tbDatPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Phòng", "Tên Khách", "Loại Phòng", "Giá Phòng", "Ngày Đặt", "Giời Đặt", "Ngày Trả", "Giờ Trả"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbDatPhong.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane4.setViewportView(tbDatPhong);
        if (tbDatPhong.getColumnModel().getColumnCount() > 0) {
            tbDatPhong.getColumnModel().getColumn(0).setPreferredWidth(60);
            tbDatPhong.getColumnModel().getColumn(1).setPreferredWidth(120);
            tbDatPhong.getColumnModel().getColumn(3).setPreferredWidth(60);
            tbDatPhong.getColumnModel().getColumn(5).setPreferredWidth(40);
            tbDatPhong.getColumnModel().getColumn(7).setPreferredWidth(40);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCapNhat.setBackground(new java.awt.Color(222, 242, 241));
        btnCapNhat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(51, 0, 51));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/loop.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(btnDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1160, 700));

        jMenuBar1.setBackground(new java.awt.Color(153, 153, 0));
        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jMenu1.setBackground(new java.awt.Color(153, 153, 0));
        jMenu1.setForeground(new java.awt.Color(242, 242, 242));
        jMenu1.setText("Hệ thống");
        jMenuBar1.add(jMenu1);

        jMenu2.setForeground(new java.awt.Color(242, 242, 242));
        jMenu2.setText("Thống Kê");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        jMenu3.setForeground(new java.awt.Color(242, 242, 242));
        jMenu3.setText("Chức năng khác");

        jMenuItem1.setText("Đăng Xuất");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
        });
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem2.setText("Nhân Sự");
        jMenu3.add(jMenuItem2);

        jMenuBar1.add(jMenu3);

        jMenu4.setForeground(new java.awt.Color(242, 242, 242));
        jMenu4.setText("Trợ Giúp");
        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        DichVu dichVu = new DichVu();
        dichVu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        frmTraPhong frm = new frmTraPhong();
        frm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        // TODO add your handling code here:
        addKhachHang();
        clearText();
        arrKhachHangCho = (ArrayList<KhachHang>) control.showKhachHangCho();
        loadTableKH(arrKhachHangCho);
    }//GEN-LAST:event_btnThemKHActionPerformed

    private void rdbNamStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdbNamStateChanged
        // TODO add your handling code here:
       gioiTinh = "Nam";
    }//GEN-LAST:event_rdbNamStateChanged

    private void rdbNuStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdbNuStateChanged
        // TODO add your handling code here:
        gioiTinh = "Nữ";
    }//GEN-LAST:event_rdbNuStateChanged

    private void tbKhachHangChoMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKhachHangChoMouseMoved
        // TODO add your handling code here:
        showDetailsKH(arrKhachHangCho);
    }//GEN-LAST:event_tbKhachHangChoMouseMoved

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        // TODO add your handling code here:
        deleteKhachHang(arrKhachHangCho);
    }//GEN-LAST:event_btnXoaKHActionPerformed

    private void btnClearKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearKHActionPerformed
        // TODO add your handling code here:
        clearText();
        loadTableKH(arrKhachHangCho);
    }//GEN-LAST:event_btnClearKHActionPerformed

    private void btnTimKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKHActionPerformed
        // TODO add your handling code here:
        searchTenKH(arrKhachHangCho);
        clearText();
    }//GEN-LAST:event_btnTimKHActionPerformed

    private void btnDatPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDatPhongActionPerformed
        // TODO add your handling code here:
        NgayGioDatPhong x = new NgayGioDatPhong();
        x.setVisible(true);
        addDatPhong(arrKhachHangCho, arrPhong);
    }//GEN-LAST:event_btnDatPhongActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        addChiTietDatPhong();
        clearText();
        loadData();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnMenuKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuKhachHangActionPerformed
        // TODO add your handling code here:
        QuanLyKhachSan x = new QuanLyKhachSan();
        x.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMenuKhachHangActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        frmDoiMatKhau frm = new frmDoiMatKhau();
        frm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        frmKhachHang frm = new frmKhachHang();
        frm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        frmPhong frm = new frmPhong();
        frm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        // TODO add your handling code here:
        frmThongKe frm = new frmThongKe();
        frm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked
        // TODO add your handling code here:
        frmDangNhap frm = new frmDangNhap();
        frm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        frmDangNhap frm = new frmDangNhap();
        frm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLyKhachSan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyKhachSan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyKhachSan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyKhachSan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyKhachSan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnClearKH;
    private javax.swing.JButton btnDatPhong;
    private javax.swing.JButton btnMenuKhachHang;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnTimKH;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JTable tbDatPhong;
    private javax.swing.JTable tbKhachHangCho;
    private javax.swing.JTable tbPhongTrong;
    private javax.swing.JTextField txtCMND;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtQuocTich;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoKhach;
    private javax.swing.JTextField txtTenKH;
    // End of variables declaration//GEN-END:variables
}
