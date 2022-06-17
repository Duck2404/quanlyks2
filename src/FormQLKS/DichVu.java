
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormQLKS;

import java.sql.*;
import java.util.logging.*;
import Model.*;
import com.sun.jdi.IntegerValue;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author duyda
 */
public class DichVu extends javax.swing.JFrame {
    
    DefaultListModel listModel = new DefaultListModel();
    DefaultTableModel tableModel = new DefaultTableModel();
    ArrayList<LoaiDichVu> arrLoaiDV= new ArrayList<>();
    ArrayList<DichVuu> arrDV= new ArrayList<>();
    
    boolean checkChon = true;
    
    Control control = new Control();
    
    /**
     * Creates new form DichVu
     */
    public DichVu() {
        initComponents();
        loadData();
        
        plKhachHang.setVisible(true);
        plPhong.setVisible(false);
        btnKH.setEnabled(false);
        btnPhong.setEnabled(true);
    }
    
    public void loadData()
    {
        arrLoaiDV = (ArrayList<LoaiDichVu>) control.showLoaiDichVu();
        arrDV = (ArrayList<DichVuu>) control.showDichVu();
        QuanLyKhachSan.arrKhachHang = (ArrayList<KhachHang>) control.showKHDatPhong();
        QuanLyKhachSan.arrDatPhong = (ArrayList<Phong>) control.showDatPhong();
        
        loadListandComboBox(arrLoaiDV);
        loadTableDichVu(arrDV);
        loadTableKHDatPhong(QuanLyKhachSan.arrKhachHang);
        loadTableDatPhong(QuanLyKhachSan.arrDatPhong);
    }
    
    public void clear()
    {
        loadTableDichVu(arrDV);
        txtGiaTien.setText("");
        txtMaDV.setText("");
        txtTenDV.setText("");
        txtsoLuong.setText("");
        cbxLoaiDV.setSelectedIndex(0);
    }
    
    public void loadListandComboBox(ArrayList<LoaiDichVu> arr)
    {
        for (LoaiDichVu loaiDV : arr) {
            listModel.addElement(loaiDV.getTenLoaiDV());
            cbxLoaiDV.addItem(loaiDV.getTenLoaiDV());
        }
        ListLoaiDV.setModel(listModel);
    }
    
    public void loadTableDichVu(ArrayList<DichVuu> arr)
    {
        tableModel = (DefaultTableModel) tbDichVu.getModel();
        tableModel.setRowCount(0);
        for (DichVuu dv : arr) {
             String rows[] = new String[3];
                rows[0] = String.valueOf(dv.getMaDV());
                rows[1] = dv.getTenDV();
                rows[2] = String.valueOf(dv.getGiaDV());
                
                
                tableModel.addRow(rows);
        }  
    }
    
    public void showDetailsDVu()
    {
        tableModel = (DefaultTableModel) tbDichVu.getModel();
        if(tbDichVu.getSelectedRow()>-1)
        {
            int rowIndex = tbDichVu.getSelectedRow();
            int maDV = Integer.parseInt(tableModel.getValueAt(rowIndex, 0).toString());
            
            for (DichVuu dv : arrDV) 
            {
                if(dv.getMaDV()== maDV)
                {
                    txtMaDV.setText(String.valueOf(dv.getMaDV()) );
                    txtTenDV.setText(dv.getTenDV());
                    txtGiaTien.setText(String.valueOf(dv.getGiaDV()));
                    cbxLoaiDV.setSelectedItem(dv.getTenLoaiDV());
                }
            }
        }
    }

    public void showDVTheoLoai(String m)    
    {   
        tableModel = (DefaultTableModel) tbDichVu.getModel();
        tableModel.setRowCount(0);
        for (DichVuu dv : arrDV) 
        {
            if(dv.getTenLoaiDV().equals(m))
            {
                String rows[] = new String[3];
                    rows[0] = String.valueOf(dv.getMaDV());
                    rows[1] = dv.getTenDV();
                    rows[2] = String.valueOf(dv.getGiaDV());
                    
                    tableModel.addRow(rows);
            }
        }
    }
    
    public void addDichVu()
    {
        DichVuu dv = new DichVuu();
        dv.setMaDV(control.searchMaxMaDV()+1);
        dv.setTenDV(txtTenDV.getText());
        dv.setGiaDV(Double.valueOf(txtGiaTien.getText()));
        for (LoaiDichVu ldv : arrLoaiDV) {
            if(ldv.getTenLoaiDV().equals(cbxLoaiDV.getSelectedItem().toString()))
            {
                dv.setMaLoaiDV(ldv.getMaLoaiDV());
                dv.setTenLoaiDV(ldv.getTenLoaiDV());
                break;
            }
        }
        
        arrDV.add(dv);
        control.insertDichVu(dv);
    }
    
    public void deleteDichVu(ArrayList<DichVuu> arrDV)
    {
        tableModel = (DefaultTableModel) tbDichVu.getModel();
        if(tbDichVu.getSelectedRow()>-1)
        {
            for (DichVuu dv : arrDV) 
            {
                if(dv.getMaDV() == Integer.valueOf(txtMaDV.getText()))
                {
                    control.deleteDV(Integer.valueOf(txtMaDV.getText()));
                    arrDV.remove(dv);
                    loadTableDichVu(arrDV);
                    clear();
                }
            }   
        }
        
    }
    
    public void updateDichVu()
    {
        for (DichVuu dv : arrDV) {
            if(dv.getMaDV() == Integer.valueOf(txtMaDV.getText()))
            {
                dv.setMaDV(Integer.valueOf(txtMaDV.getText()));
                dv.setTenDV(txtTenDV.getText());
                dv.setGiaDV(Double.valueOf(txtGiaTien.getText()));
                for (LoaiDichVu ldv : arrLoaiDV) 
                {
                    if(ldv.getTenLoaiDV().equals(cbxLoaiDV.getSelectedItem().toString()))
                    {
                        dv.setMaLoaiDV(ldv.getMaLoaiDV());
                        dv.setTenLoaiDV(ldv.getTenLoaiDV());
                        break;
                    }
                }
                control.updateDV(dv);
            }
        }
    }
    
    public void searchTenDV()
    {
        tableModel = (DefaultTableModel) tbDichVu.getModel();
        tableModel.setRowCount(0);
        
        ResultSet rs = control.searchTenDV(txtTenDV.getText());
        try {
            while (rs.next()) {
                 String rows[] = new String[5];
                    rows[0] = String.valueOf(rs.getInt("MaDichVu"));
                    rows[1] = rs.getString("TenDichVu");
                    rows[2] = String.valueOf(rs.getDouble("GiaTien"));
                
                tableModel.addRow(rows);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyKhachSan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTableKHDatPhong(ArrayList<KhachHang> arr)
    {
        tableModel = (DefaultTableModel) tbKhachHang.getModel();
        tableModel.setRowCount(0);
        for (KhachHang kh : arr) {
            {
                String rows[] = new String[4];
                    rows[0] = String.valueOf(kh.getMaKH());
                    rows[1] = kh.getTenKH();
                    rows[2] = kh.getSoDienThoai();
                    rows[3] = kh.getGioiTinh();
                    
                tableModel.addRow(rows);
            }
        }
    }
    
    public void loadTableDatPhong(ArrayList<Phong> arr)
    {
        tableModel = (DefaultTableModel) tbPhong.getModel();
        tableModel.setRowCount(0);
        for (Phong ph : arr) {
            {
                String rows[] = new String[4];
                    rows[0] = ph.getMaPhong();
                    rows[1] = ph.getTenPhong();
                    rows[2] = ph.getGhiChuPhong();
                    rows[3] = ph.getNgayDat();
                    
                tableModel.addRow(rows);
            }
        }
    }
    
    public void showDetailsKHDatPhong()
    {
        tableModel = (DefaultTableModel) tbKhachHang.getModel();
        if(tbKhachHang.getSelectedRow()>-1)
        {
            int rowIndex = tbKhachHang.getSelectedRow();
            int maKH = Integer.parseInt(tableModel.getValueAt(rowIndex, 0).toString());
            
            for (KhachHang kh : QuanLyKhachSan.arrKhachHang) 
            {
                if(kh.getMaKH() == maKH)
                {
                    txtMaKH.setText(String.valueOf(kh.getMaKH()) );
                    txtTenKH.setText(kh.getTenKH());
                }
            }
        }
    }
    
    public void showDetailsDatPhong()
    {
        tableModel = (DefaultTableModel) tbPhong.getModel();
        if(tbPhong.getSelectedRow()>-1)
        {
            int rowIndex = tbPhong.getSelectedRow();
            String maPH = tableModel.getValueAt(rowIndex, 0).toString();
            
            for (Phong ph : QuanLyKhachSan.arrDatPhong) 
            {
                if(ph.getMaPhong().equals(maPH))
                {
                    txtMaPhong.setText(String.valueOf(ph.getMaPhong()) );
                    txtTenPhong.setText(ph.getTenPhong());
                }
            }
        }
        
    }
    
    public void addDVtheoPHorKH()
    {
        if(checkChon == true)
        {
            for (Phong ph : QuanLyKhachSan.arrDatPhong) 
            {
            
                    if(ph.getMaKH()== Integer.valueOf(txtMaKH.getText()))
                    {
                        control.addDVvaoDatPhong(ph.getMaPhong(),Integer.valueOf(txtMaKH.getText()),Integer.valueOf(txtMaDV.getText()),Integer.valueOf(txtsoLuong.getText()));
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                        clear();
                        txtMaKH.setText("");
                        txtTenKH.setText("");
                        tbKhachHang.setSelectionMode(0); // load lai tb
                    }
                
            } clear();
                        txtMaKH.setText("");
                        txtTenKH.setText("");
                        tbKhachHang.setSelectionMode(0); // load lai tb
        }
        else
        {
            for (Phong ph : QuanLyKhachSan.arrDatPhong) 
            {
                if(ph.getMaPhong().equals(txtMaPhong.getText()))
                {
                    control.addDVvaoDatPhong(ph.getMaPhong(),ph.getMaKH(),Integer.valueOf(txtMaDV.getText()),Integer.valueOf(txtsoLuong.getText()));
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    clear();
                    txtMaPhong.setText("");
                    txtTenPhong.setText("");
                    tbPhong.setSelectionMode(0);// load lai tb
                }
            } 
        }
    }
    
    public void searchTenKH()
    {
        tableModel = (DefaultTableModel) tbKhachHang.getModel();
        tableModel.setRowCount(0);
        
        ResultSet rs = control.searchTenKHDatPhong(txtTenKH.getText());
        try {
            while (rs.next()) {
                String rows[] = new String[4];
                    rows[0] = String.valueOf(rs.getInt("MaKhachHang"));
                    rows[1] = rs.getString("TenKhachHang");
                    rows[2] = rs.getString("SoDienThoai");
                    rows[3] = rs.getString("GioiTinh");
                
                tableModel.addRow(rows);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyKhachSan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchTenPhong()
    {
        tableModel = (DefaultTableModel) tbPhong.getModel();
        tableModel.setRowCount(0);
        
        ResultSet rs = control.searchTenPhongDat(txtTenPhong.getText());
        try {
            while (rs.next()) {
                String rows[] = new String[4];
                    rows[0] = rs.getString("MaPhong");
                    rows[1] = rs.getString("TenPhong");
                    rows[2] = rs.getString("GhiChu");
                    rows[3] = rs.getString("NgayDatPhong");
                
                tableModel.addRow(rows);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyKhachSan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jToolBar2 = new javax.swing.JToolBar();
        btnMenuKhachHang = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListLoaiDV = new javax.swing.JList<>();
        btnThemLoaiDV = new javax.swing.JButton();
        btnChonLoaiDV = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDichVu = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtMaDV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTenDV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtGiaTien = new javax.swing.JTextField();
        btnThemDV = new javax.swing.JButton();
        btnXoaDV = new javax.swing.JButton();
        btnSuaDV = new javax.swing.JButton();
        btnBoChon = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbxLoaiDV = new javax.swing.JComboBox<>();
        txtSoLuong = new javax.swing.JLabel();
        txtsoLuong = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnKH = new javax.swing.JButton();
        btnPhong = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        plKhachHang = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbKhachHang = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        btnClearKH = new javax.swing.JButton();
        btnTimKH = new javax.swing.JButton();
        plPhong = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbPhong = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtMaPhong = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTenPhong = new javax.swing.JTextField();
        btnClearPhong = new javax.swing.JButton();
        btnTimPhog = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
        );

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Order Dịch Vụ");
        setBackground(new java.awt.Color(0, 153, 153));
        setResizable(false);

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

        jButton4.setBackground(new java.awt.Color(204, 204, 0));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(242, 242, 242));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/Person-Male-Light-icon-16.png"))); // NOI18N
        jButton4.setText("Khách Hàng");
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

        jButton5.setBackground(new java.awt.Color(204, 204, 0));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(242, 242, 242));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/Home-icon-16.png"))); // NOI18N
        jButton5.setText("Phòng");
        jButton5.setBorderPainted(false);
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton5.setMargin(new java.awt.Insets(1, 10, 1, 20));
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton5);

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

        jPanel2.setBackground(new java.awt.Color(153, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dịch Vụ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Loại Dịch Vụ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        ListLoaiDV.setBackground(new java.awt.Color(234, 248, 240));
        ListLoaiDV.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ListLoaiDVValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(ListLoaiDV);

        btnThemLoaiDV.setBackground(new java.awt.Color(222, 242, 241));
        btnThemLoaiDV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnThemLoaiDV.setForeground(new java.awt.Color(0, 0, 255));
        btnThemLoaiDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Login-icon-16.png"))); // NOI18N
        btnThemLoaiDV.setText("Thêm Loại Dịch Vụ");
        btnThemLoaiDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLoaiDVActionPerformed(evt);
            }
        });

        btnChonLoaiDV.setBackground(new java.awt.Color(222, 242, 241));
        btnChonLoaiDV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnChonLoaiDV.setForeground(new java.awt.Color(51, 51, 51));
        btnChonLoaiDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/loading.png"))); // NOI18N
        btnChonLoaiDV.setText("Chọn");
        btnChonLoaiDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonLoaiDVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThemLoaiDV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnChonLoaiDV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemLoaiDV)
                    .addComponent(btnChonLoaiDV))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(153, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Dịch Vụ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        jScrollPane2.setBackground(new java.awt.Color(153, 0, 0));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Dịch Vụ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        tbDichVu.setBackground(new java.awt.Color(234, 248, 240));
        tbDichVu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã DV", "Tên Dịch Vụ", "Giá Tiền"
            }
        ));
        tbDichVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDichVuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbDichVu);
        if (tbDichVu.getColumnModel().getColumnCount() > 0) {
            tbDichVu.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbDichVu.getColumnModel().getColumn(1).setPreferredWidth(80);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mã DV:");

        txtMaDV.setBackground(new java.awt.Color(234, 248, 240));
        txtMaDV.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tên DV:");

        txtTenDV.setBackground(new java.awt.Color(234, 248, 240));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Giá: ");

        txtGiaTien.setBackground(new java.awt.Color(234, 248, 240));

        btnThemDV.setBackground(new java.awt.Color(222, 242, 241));
        btnThemDV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnThemDV.setForeground(new java.awt.Color(51, 153, 0));
        btnThemDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/btn_add.png"))); // NOI18N
        btnThemDV.setText("Thêm");
        btnThemDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDVActionPerformed(evt);
            }
        });

        btnXoaDV.setBackground(new java.awt.Color(222, 242, 241));
        btnXoaDV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnXoaDV.setForeground(new java.awt.Color(255, 204, 51));
        btnXoaDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/delete (1).png"))); // NOI18N
        btnXoaDV.setText("Xóa");
        btnXoaDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDVActionPerformed(evt);
            }
        });

        btnSuaDV.setBackground(new java.awt.Color(222, 242, 241));
        btnSuaDV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSuaDV.setForeground(new java.awt.Color(255, 51, 51));
        btnSuaDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/config.png"))); // NOI18N
        btnSuaDV.setText("Sửa");
        btnSuaDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaDVActionPerformed(evt);
            }
        });

        btnBoChon.setBackground(new java.awt.Color(222, 242, 241));
        btnBoChon.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnBoChon.setForeground(new java.awt.Color(255, 51, 0));
        btnBoChon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/cancel.png"))); // NOI18N
        btnBoChon.setText("Bỏ chọn");
        btnBoChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoChonActionPerformed(evt);
            }
        });

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/search-icon-16.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Loại DV:");

        cbxLoaiDV.setBackground(new java.awt.Color(234, 248, 240));
        cbxLoaiDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxLoaiDVActionPerformed(evt);
            }
        });

        txtSoLuong.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtSoLuong.setForeground(new java.awt.Color(255, 255, 255));
        txtSoLuong.setText("Số lượng");

        txtsoLuong.setBackground(new java.awt.Color(234, 248, 240));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaDV, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnThemDV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSuaDV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnXoaDV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBoChon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtGiaTien)
                                    .addComponent(txtTenDV, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cbxLoaiDV, javax.swing.GroupLayout.Alignment.TRAILING, 0, 102, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtsoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbxLoaiDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuong)
                            .addComponent(txtsoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoaDV)
                            .addComponent(btnThemDV))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSuaDV)
                            .addComponent(btnBoChon))))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(101, 204, 183));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Đặt dịch vụ"));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnKH.setBackground(new java.awt.Color(204, 255, 255));
        btnKH.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnKH.setForeground(new java.awt.Color(51, 102, 255));
        btnKH.setText("Khách Hàng");
        btnKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKHActionPerformed(evt);
            }
        });
        jPanel4.add(btnKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 37, 100, 20));

        btnPhong.setBackground(new java.awt.Color(204, 255, 255));
        btnPhong.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnPhong.setForeground(new java.awt.Color(0, 51, 255));
        btnPhong.setText("Phòng");
        btnPhong.setVerifyInputWhenFocusTarget(false);
        btnPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhongActionPerformed(evt);
            }
        });
        jPanel4.add(btnPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 37, 100, 20));

        jPanel5.setLayout(new java.awt.CardLayout());

        plKhachHang.setBackground(new java.awt.Color(153, 0, 0));
        plKhachHang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        plKhachHang.setAlignmentX(5.0F);
        plKhachHang.setPreferredSize(new java.awt.Dimension(682, 190));
        plKhachHang.setRequestFocusEnabled(false);

        tbKhachHang.setBackground(new java.awt.Color(234, 248, 240));
        tbKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã KH", "Tên KH", "Số Điện Thoại", "Giới Tính"
            }
        ));
        tbKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKhachHangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbKhachHang);
        if (tbKhachHang.getColumnModel().getColumnCount() > 0) {
            tbKhachHang.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbKhachHang.getColumnModel().getColumn(1).setPreferredWidth(120);
        }

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Mã KH:");

        txtMaKH.setBackground(new java.awt.Color(234, 248, 240));
        txtMaKH.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tên KH:");

        txtTenKH.setBackground(new java.awt.Color(234, 248, 240));
        txtTenKH.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTenKHCaretUpdate(evt);
            }
        });

        btnClearKH.setBackground(new java.awt.Color(222, 242, 241));
        btnClearKH.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnClearKH.setForeground(new java.awt.Color(0, 0, 255));
        btnClearKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/Button-Close-icon-16.png"))); // NOI18N
        btnClearKH.setText("Bỏ Chọn");
        btnClearKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearKHActionPerformed(evt);
            }
        });

        btnTimKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/btn_search.png"))); // NOI18N
        btnTimKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout plKhachHangLayout = new javax.swing.GroupLayout(plKhachHang);
        plKhachHang.setLayout(plKhachHangLayout);
        plKhachHangLayout.setHorizontalGroup(
            plKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(plKhachHangLayout.createSequentialGroup()
                        .addGroup(plKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(plKhachHangLayout.createSequentialGroup()
                                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnTimKH, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10))
                    .addGroup(plKhachHangLayout.createSequentialGroup()
                        .addComponent(btnClearKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addContainerGap())
        );
        plKhachHangLayout.setVerticalGroup(
            plKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plKhachHangLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(plKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(plKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTimKH, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnClearKH)
                .addContainerGap(46, Short.MAX_VALUE))
            .addGroup(plKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.add(plKhachHang, "card2");

        plPhong.setBackground(new java.awt.Color(153, 0, 0));
        plPhong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        plPhong.setPreferredSize(new java.awt.Dimension(682, 197));

        tbPhong.setBackground(new java.awt.Color(234, 248, 240));
        tbPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phòng", "Tên Phòng", "Ghi chú", "Ngày Đặt"
            }
        ));
        tbPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPhongMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbPhong);
        if (tbPhong.getColumnModel().getColumnCount() > 0) {
            tbPhong.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbPhong.getColumnModel().getColumn(1).setPreferredWidth(80);
            tbPhong.getColumnModel().getColumn(2).setPreferredWidth(120);
            tbPhong.getColumnModel().getColumn(3).setPreferredWidth(120);
        }

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Mã Phòng:");

        txtMaPhong.setBackground(new java.awt.Color(234, 248, 240));
        txtMaPhong.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tên Phòng:");

        txtTenPhong.setBackground(new java.awt.Color(234, 248, 240));
        txtTenPhong.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTenPhongCaretUpdate(evt);
            }
        });

        btnClearPhong.setBackground(new java.awt.Color(222, 242, 241));
        btnClearPhong.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnClearPhong.setForeground(new java.awt.Color(0, 51, 255));
        btnClearPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/Button-Close-icon-16.png"))); // NOI18N
        btnClearPhong.setText("Bỏ Chọn");
        btnClearPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearPhongActionPerformed(evt);
            }
        });

        btnTimPhog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/btn_search.png"))); // NOI18N
        btnTimPhog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimPhogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout plPhongLayout = new javax.swing.GroupLayout(plPhong);
        plPhong.setLayout(plPhongLayout);
        plPhongLayout.setHorizontalGroup(
            plPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plPhongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(plPhongLayout.createSequentialGroup()
                        .addGroup(plPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(plPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTimPhog, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plPhongLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnClearPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        plPhongLayout.setVerticalGroup(
            plPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plPhongLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(plPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(plPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(plPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtTenPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTimPhog, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnClearPhong)
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(plPhongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.add(plPhong, "card2");

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 56, -1, -1));

        jButton1.setBackground(new java.awt.Color(222, 242, 241));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 153, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/btn_add.png"))); // NOI18N
        jButton1.setText("Thêm Dịch Vụ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, 150, 35));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenuBar1.setBackground(new java.awt.Color(153, 153, 0));
        jMenuBar1.setForeground(new java.awt.Color(242, 242, 242));
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnChonLoaiDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonLoaiDVActionPerformed
        // TODO add your handling code here:
        showDVTheoLoai(ListLoaiDV.getSelectedValue());
    }//GEN-LAST:event_btnChonLoaiDVActionPerformed

    private void ListLoaiDVValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ListLoaiDVValueChanged
        // TODO add your handling code here:
        cbxLoaiDV.setSelectedItem(ListLoaiDV.getSelectedValue());        
    }//GEN-LAST:event_ListLoaiDVValueChanged

    private void tbDichVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDichVuMouseClicked
        // TODO add your handling code here:
        showDetailsDVu();
    }//GEN-LAST:event_tbDichVuMouseClicked

    private void btnBoChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoChonActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnBoChonActionPerformed

    private void cbxLoaiDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxLoaiDVActionPerformed
        // TODO add your handling code here:
        ListLoaiDV.setSelectedIndex(cbxLoaiDV.getSelectedIndex());
    }//GEN-LAST:event_cbxLoaiDVActionPerformed

    private void btnThemDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDVActionPerformed
        // TODO add your handling code here:
        addDichVu();
        loadTableDichVu(arrDV);
        arrDV = (ArrayList<DichVuu>) control.showDichVu();
        loadTableDichVu(arrDV);
    }//GEN-LAST:event_btnThemDVActionPerformed

    private void btnXoaDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDVActionPerformed
        // TODO add your handling code here:
        System.out.println(txtTenDV.getText());
            if(Integer.valueOf(txtMaDV.getText()) != control.showMaDV(Integer.valueOf(txtMaDV.getText())))
            {
                deleteDichVu(arrDV);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Dịch vụ đang được sử dụng"); 
            }
        
    }//GEN-LAST:event_btnXoaDVActionPerformed

    private void btnSuaDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaDVActionPerformed
        // TODO add your handling code here:
        updateDichVu();
        loadTableDichVu(arrDV);
    }//GEN-LAST:event_btnSuaDVActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        searchTenDV();
        
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKHActionPerformed
        // TODO add your handling code here:
        plKhachHang.setVisible(true);
        plPhong.setVisible(false);
        btnKH.setEnabled(false);
        btnPhong.setEnabled(true);
        checkChon = true;
    }//GEN-LAST:event_btnKHActionPerformed

    private void btnPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhongActionPerformed
        // TODO add your handling code herep
        plPhong.setVisible(true);
        plKhachHang.setVisible(false);
        btnKH.setEnabled(true);
        btnPhong.setEnabled(false);
        checkChon = false;
    }//GEN-LAST:event_btnPhongActionPerformed

    private void tbKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKhachHangMouseClicked
        // TODO add your handling code here:
        showDetailsKHDatPhong();
    }//GEN-LAST:event_tbKhachHangMouseClicked

    private void tbPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhongMouseClicked
        // TODO add your handling code here:
        showDetailsDatPhong();
    }//GEN-LAST:event_tbPhongMouseClicked

    private void btnClearKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearKHActionPerformed
        // TODO add your handling code here:
        txtMaKH.setText("");
        txtTenKH.setText("");
        loadTableKHDatPhong(QuanLyKhachSan.arrKhachHang);
        
    }//GEN-LAST:event_btnClearKHActionPerformed

    private void btnClearPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearPhongActionPerformed
        // TODO add your handling code here:
        txtMaPhong.setText("");
        txtTenPhong.setText("");
        loadTableDatPhong(QuanLyKhachSan.arrDatPhong);
    }//GEN-LAST:event_btnClearPhongActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        addDVtheoPHorKH();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtTenPhongCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTenPhongCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenPhongCaretUpdate

    private void txtTenKHCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTenKHCaretUpdate
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtTenKHCaretUpdate

    private void btnMenuKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuKhachHangActionPerformed
        // TODO add your handling code here:
        QuanLyKhachSan x = new QuanLyKhachSan();
        x.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMenuKhachHangActionPerformed

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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        frmKhachHang frm = new frmKhachHang();
        frm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        frmPhong frm = new frmPhong();
        frm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnTimPhogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimPhogActionPerformed
        // TODO add your handling code here:
        searchTenPhong();
    }//GEN-LAST:event_btnTimPhogActionPerformed

    private void btnTimKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKHActionPerformed
        // TODO add your handling code here:
        searchTenKH();
    }//GEN-LAST:event_btnTimKHActionPerformed

    private void btnThemLoaiDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLoaiDVActionPerformed
        // TODO add your handling code here:
        LDichVu frm = new LDichVu();
        frm.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_btnThemLoaiDVActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        frmDoiMatKhau frm = new frmDoiMatKhau();
        frm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(DichVu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DichVu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DichVu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DichVu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DichVu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ListLoaiDV;
    private javax.swing.JButton btnBoChon;
    private javax.swing.JButton btnChonLoaiDV;
    private javax.swing.JButton btnClearKH;
    private javax.swing.JButton btnClearPhong;
    private javax.swing.JButton btnKH;
    private javax.swing.JButton btnMenuKhachHang;
    private javax.swing.JButton btnPhong;
    private javax.swing.JButton btnSuaDV;
    private javax.swing.JButton btnThemDV;
    private javax.swing.JButton btnThemLoaiDV;
    private javax.swing.JButton btnTimKH;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTimPhog;
    private javax.swing.JButton btnXoaDV;
    private javax.swing.JComboBox<String> cbxLoaiDV;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel plKhachHang;
    private javax.swing.JPanel plPhong;
    private javax.swing.JTable tbDichVu;
    private javax.swing.JTable tbKhachHang;
    private javax.swing.JTable tbPhong;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtMaDV;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaPhong;
    private javax.swing.JLabel txtSoLuong;
    private javax.swing.JTextField txtTenDV;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenPhong;
    private javax.swing.JTextField txtsoLuong;
    // End of variables declaration//GEN-END:variables

    
}
