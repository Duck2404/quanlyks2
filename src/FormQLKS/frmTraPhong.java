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
import javax.swing.JOptionPane;
import javax.swing.table.*;
/**
 *
 * @author duydat
 */
public class frmTraPhong extends javax.swing.JFrame {
    
    DefaultTableModel tableModel = new DefaultTableModel();
    ArrayList<Phong> arrPhong = new ArrayList<>();
    ArrayList<LoaiPhong> arrLoaiPhong = new ArrayList<>();
    ArrayList<KhachHang> arrKH = new ArrayList<>();
    ArrayList<DichVuu> arrDV = new ArrayList<>();
    int soNgayO = 1;
    double tongTienPhong,tongTienDV,tongTien;
    static String tenPhong = "";
    Control control = new Control();
    
    /**
     * Creates new form frmTraPhong
     */
    public frmTraPhong() {
        initComponents();
        loadData();
        
    }
    
    public void loadData()
    {
        arrLoaiPhong = (ArrayList<LoaiPhong>) control.showLoaiPhong();
        arrPhong = (ArrayList<Phong>) control.showDatPhong();
        arrKH = (ArrayList<KhachHang>) control.showKhachHang();
        arrDV = (ArrayList<DichVuu>) control.showDichVu();
        
        
        loadTableGiaPhong(arrLoaiPhong);
        loadtableDatPhong(arrPhong, arrKH);
        
        txtTongTien.setEditable(false);
        txtNgayDen.setEditable(false);
        txtTienDV.setEditable(false);
        txtTienPhong.setEditable(false);
        txtSoNgayO.setEditable(false);
    }
    
    public void loadTableGiaPhong(ArrayList<LoaiPhong> arr)
    {
        tableModel = (DefaultTableModel) tbGiaPhong.getModel();
        tableModel.setRowCount(0);
        for (LoaiPhong lph : arr) {
            {
                String rows[] = new String[2];
                    rows[0] = lph.getTenLoaiPhong();
                    rows[1] = String.valueOf(lph.getGia());
                    
                tableModel.addRow(rows);
            }
        }
    }

    public void loadtableDatPhong(ArrayList<Phong> arr, ArrayList<KhachHang> arrKH)
    {
        tableModel = (DefaultTableModel) tbPhongDat.getModel();
        tableModel.setRowCount(0);
        for (Phong ph : arrPhong) 
        {
            for (KhachHang kh : arrKH)
            {
                if(kh.getMaKH() == ph.getMaKH())
                {
                    String rows[] = new String[4];
                        rows[0] = ph.getMaPhong();
                        rows[1] = ph.getTenPhong();
                        rows[2] = ph.getTenLoaiPhong();
                        rows[3] = kh.getTenKH() ;

                    tableModel.addRow(rows);
                }
           } 
        }
    }
    
    public void searchTenKH()
    {        
        tableModel = (DefaultTableModel) tbPhongDat.getModel();
        tableModel.setRowCount(0);
        
        ResultSet rs = control.searchTenKhachHangDatPhong(txtTimTenKH.getText());
        try 
        {
            while (rs.next()) 
            {
                String rows[] = new String[4];
                    rows[0] = rs.getString("MaPhong");
                    rows[1] = rs.getString("TenPhong");
                    rows[2] = rs.getString("TenLoaiPhong");
                    rows[3] = rs.getString("TenKhachHang");
                
                tableModel.addRow(rows);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyKhachSan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchTenPhong()
    {
        tableModel = (DefaultTableModel) tbPhongDat.getModel();
        tableModel.setRowCount(0);
        
        ResultSet rs = control.searchPhongDat(txtTimTenPhong.getText());
        try 
        {
            while (rs.next()) 
            {
                String rows[] = new String[4];
                    rows[0] = rs.getString("MaPhong");
                    rows[1] = rs.getString("TenPhong");
                    rows[2] = rs.getString("TenLoaiPhong");
                    rows[3] = rs.getString("TenKhachHang");
                
                tableModel.addRow(rows);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyKhachSan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showDetailsDatPhong()
    {
        tableModel = (DefaultTableModel) tbPhongDat.getModel();
        if(tbPhongDat.getSelectedRow()>-1)
        {
            int rowIndex = tbPhongDat.getSelectedRow();
            String maPhong = tableModel.getValueAt(rowIndex, 0).toString();
            for (Phong ph : arrPhong) 
            {
                for(KhachHang kh : arrKH)
                {
                    for(DichVuu dv : arrDV)
                    {
                        if(ph.getMaPhong().equals(maPhong))
                        {
                            if(ph.getMaKH() == kh.getMaKH() )
                            {
                                if(dv.getMaDV() == ph.getMaDV())
                                {
                                    txtTenPhong.setText(ph.getTenPhong());
                                    txtTenKH.setText(kh.getTenKH());
                                    txtLoaiPhong.setText(ph.getTenLoaiPhong());
                                    txtNgayDen.setText(ph.getNgayDat());
                                    txtNgayTra.setText(ph.getNgayTra());
                                    int SoNgay = TinhSoNgayO(txtNgayDen.getText(), txtNgayTra.getText());
                                    txtSoNgayO.setText(String.valueOf(SoNgay));
                                    tongTienPhong = ph.getGia() * (double) SoNgay;
                                    txtTienPhong.setText(String.valueOf(tongTienPhong) + " VNĐ");
                                    tongTienDV = control.tongTienDV(ph);
                                    txtTienDV.setText(String.valueOf(tongTienDV) + " VNĐ"); 
                                    txtTongTien.setText("");
                                    btnTraPhong.setEnabled(false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public int TinhSoNgayO(String ngayThangDen, String ngayThangTra)
    {
        int soNgay = 0;
        // CẮT NGÀY THÁNG ĐẾN
        int namDen = Integer.valueOf(ngayThangDen.substring(0, 4));
        int thangDen = Integer.valueOf(ngayThangDen.substring(5, 7));
        int ngayDen = Integer.valueOf(ngayThangDen.substring(8));
        // CẮT NGÀY THÁNG TRẢ
        int namTra = Integer.valueOf(ngayThangTra.substring(0, 4));
        int thangTra = Integer.valueOf(ngayThangTra.substring(5, 7));
        int ngayTra = Integer.valueOf(ngayThangTra.substring(8));
        
        if(namDen == namTra)
        {
            if( thangDen == thangTra)
            {
                if(ngayDen < ngayTra)
                {
                    soNgay = ngayTra - ngayDen;
                }
                else if(ngayDen == ngayTra)
                {
                    JOptionPane.showMessageDialog(this, "Ở trong một ngày nên số ngày ở bằng 1");
                    soNgay = 1;
                }
                else 
                {
                    JOptionPane.showMessageDialog(this, "Ngày trả trước ngày đến. Vui lòng kiểm tra lại!!!");
                }
            }
            else if(thangDen < thangTra)
            {
                if (thangDen == 1 || thangDen == 3 || thangDen ==5 || thangDen == 7 || thangDen == 8 || thangDen == 10 || thangDen == 12 )
                {
                    
                    soNgay = (ngayTra - ngayDen) + (31*(thangTra-thangDen)) ;
                }
                else if(thangDen == 2 )
                    soNgay = (ngayTra - ngayDen) + (29*(thangTra-thangDen)) ;
                else
                    soNgay = (ngayTra - ngayDen) + (30*(thangTra-thangDen)) ;
            }
            else 
            {
                JOptionPane.showMessageDialog(this, "Tháng trả trước tháng đến. Vui lòng kiểm tra lại!!!");
            } 
        }
        else if(namDen < namTra)
        {
            if (thangDen == 1 || thangDen == 3 || thangDen ==5 || thangDen == 7 || thangDen == 8 || thangDen == 10 || thangDen == 12 )
                {
                    soNgay = (ngayTra - ngayDen) + (31*(thangTra-thangDen))+ (365*(namTra-namDen)) ;
                }
                else if(thangDen == 2 )
                    soNgay = (ngayTra - ngayDen) + (29*(thangTra-thangDen)) + (365*(namTra-namDen)) ;
                else
                    soNgay = (ngayTra - ngayDen) + (30*(thangTra-thangDen)) + (365*(namTra-namDen)) ;
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Năm trả trước năm đến. Vui lòng kiểm tra lại!!!");
        }
        return soNgay;
        
    }
    
    public void updateNgayTraPhong()
    {
        int rowIndex = tbPhongDat.getSelectedRow();
            String maPhong = tableModel.getValueAt(rowIndex, 0).toString();
        for (Phong ph : arrPhong) 
        {
           if(maPhong.equals(ph.getMaPhong()))
           {
               
               control.updateNgayTraPhong(ph.getMaPhieuDat(), txtNgayTra.getText());
               ph.setNgayTra(txtNgayTra.getText());
           }
        }
    }
    
    public void addTraPhong()
    {
        tableModel = (DefaultTableModel) tbPhongDat.getModel();
        if(tbPhongDat.getSelectedRow()>-1)
        {
            int rowIndex = tbPhongDat.getSelectedRow();
            String maPhong = tableModel.getValueAt(rowIndex, 0).toString();
            for (Phong ph : arrPhong) 
            {
                if(ph.getMaPhong().equals(maPhong))
                {
                    control.insertTraPhong(ph,tongTien );
                    control.deleteDatPhong(ph);
                }
            }
        }        
    }
    
    public void clearText()
    {
        txtLoaiPhong.setText("");
        txtNgayDen.setText("");
        txtNgayTra.setText("");
        txtSoNgayO.setText("");
        txtTenKH.setText("");
        txtTenPhong.setText("");
        txtTienDV.setText("");
        txtTienPhong.setText("");
        txtTongTien.setText("");
        btnTraPhong.setEnabled(false);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTimTenKH = new javax.swing.JTextField();
        txtTimTenPhong = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPhongDat = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbGiaPhong = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtTenPhong = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtLoaiPhong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtNgayDen = new javax.swing.JTextField();
        txtTienPhong = new javax.swing.JTextField();
        txtSoNgayO = new javax.swing.JTextField();
        txtNgayTra = new javax.swing.JTextField();
        txtTienDV = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        btnTraPhong = new javax.swing.JButton();
        btnTinhTien = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        btnMenuKhachHang = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Trả Phòng Khách Sạn");
        setBackground(new java.awt.Color(0, 153, 153));

        jPanel1.setBackground(new java.awt.Color(153, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Kiếm Đặt Phòng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Theo tên KH:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Theo Tên Phòng:");

        txtTimTenKH.setBackground(new java.awt.Color(234, 248, 240));
        txtTimTenKH.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimTenKHCaretUpdate(evt);
            }
        });

        txtTimTenPhong.setBackground(new java.awt.Color(234, 248, 240));
        txtTimTenPhong.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimTenPhongCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimTenPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTimTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTimTenPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(153, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chọn Phòng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        tbPhongDat.setBackground(new java.awt.Color(234, 248, 240));
        tbPhongDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phòng", "Tên Phòng", "Loại phòng", "Tên KH"
            }
        ));
        tbPhongDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPhongDatMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPhongDat);
        if (tbPhongDat.getColumnModel().getColumnCount() > 0) {
            tbPhongDat.getColumnModel().getColumn(2).setPreferredWidth(100);
            tbPhongDat.getColumnModel().getColumn(3).setPreferredWidth(120);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giá Phòng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        tbGiaPhong.setBackground(new java.awt.Color(234, 248, 240));
        tbGiaPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Loại Phòng", "Giá/Ngày"
            }
        ));
        tbGiaPhong.setEnabled(false);
        jScrollPane3.setViewportView(tbGiaPhong);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(153, 0, 0));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin Thanh Toán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tên Phòng:");

        txtTenPhong.setBackground(new java.awt.Color(234, 248, 240));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Loại Phòng:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Ngày Đến: ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ngày Trả:");

        txtLoaiPhong.setBackground(new java.awt.Color(234, 248, 240));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tên KH: ");

        txtTenKH.setBackground(new java.awt.Color(234, 248, 240));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Số Ngày Ở:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tổng Tiền Dịch Vụ:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("TỔNG TIỀN THANH TOÁN: ");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Tổng Tiền Phòng:");

        txtNgayDen.setBackground(new java.awt.Color(234, 248, 240));

        txtTienPhong.setBackground(new java.awt.Color(234, 248, 240));

        txtSoNgayO.setBackground(new java.awt.Color(234, 248, 240));

        txtNgayTra.setBackground(new java.awt.Color(234, 248, 240));

        txtTienDV.setBackground(new java.awt.Color(234, 248, 240));

        txtTongTien.setBackground(new java.awt.Color(234, 248, 240));
        txtTongTien.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 0, 51));

        btnTraPhong.setBackground(new java.awt.Color(222, 242, 241));
        btnTraPhong.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnTraPhong.setForeground(new java.awt.Color(153, 153, 0));
        btnTraPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/logout-icon-16.png"))); // NOI18N
        btnTraPhong.setText("Trả Phòng");
        btnTraPhong.setEnabled(false);
        btnTraPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraPhongActionPerformed(evt);
            }
        });

        btnTinhTien.setBackground(new java.awt.Color(222, 242, 241));
        btnTinhTien.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnTinhTien.setForeground(new java.awt.Color(102, 102, 255));
        btnTinhTien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Actions-help-about-icon-16.png"))); // NOI18N
        btnTinhTien.setText("Tính Tiền");
        btnTinhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTinhTienActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsset2/wrench.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search-icon-32.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayDen, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTienPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(69, 69, 69)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoNgayO, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtTienDV, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnTinhTien)
                        .addGap(43, 43, 43)
                        .addComponent(btnTraPhong)
                        .addGap(171, 171, 171))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtSoNgayO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(txtNgayDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(txtTienPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTienDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTraPhong)
                    .addComponent(btnTinhTien))
                .addContainerGap(13, Short.MAX_VALUE))
        );

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

        jMenuBar1.setBackground(new java.awt.Color(153, 153, 0));
        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimTenKHCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimTenKHCaretUpdate
        // TODO add your handling code here:
        searchTenKH();
        txtTimTenPhong.setText("");
    }//GEN-LAST:event_txtTimTenKHCaretUpdate

    private void txtTimTenPhongCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimTenPhongCaretUpdate
        // TODO add your handling code here:
        searchTenPhong();
        txtTimTenKH.setText("");
    }//GEN-LAST:event_txtTimTenPhongCaretUpdate

    private void tbPhongDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhongDatMouseClicked
        // TODO add your handling code here:
        showDetailsDatPhong();
    }//GEN-LAST:event_tbPhongDatMouseClicked

    private void btnTinhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTinhTienActionPerformed
        // TODO add your handling code here:
         tongTien = tongTienDV + tongTienPhong;
        txtTongTien.setText(String.valueOf(tongTien) + " VNĐ");
        btnTraPhong.setEnabled(true);
    }//GEN-LAST:event_btnTinhTienActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        updateNgayTraPhong();
        JOptionPane.showMessageDialog(this, "Cập nhật thành công!!");
        showDetailsDatPhong();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnTraPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraPhongActionPerformed
        // TODO add your handling code here:
        addTraPhong();
        JOptionPane.showMessageDialog(this, "Trả phòng thành công");
        loadData();
        clearText();
    }//GEN-LAST:event_btnTraPhongActionPerformed

    private void btnMenuKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuKhachHangActionPerformed
        // TODO add your handling code here:
        QuanLyKhachSan frm = new QuanLyKhachSan();
        frm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMenuKhachHangActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        DichVu frm = new DichVu();
        frm.setVisible(true);
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

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        tenPhong = txtTenPhong.getText();
        frmShowDVtheoPhong frm = new frmShowDVtheoPhong();
        frm.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(frmTraPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTraPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTraPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTraPhong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTraPhong().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMenuKhachHang;
    private javax.swing.JButton btnTinhTien;
    private javax.swing.JButton btnTraPhong;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTable tbGiaPhong;
    private javax.swing.JTable tbPhongDat;
    private javax.swing.JTextField txtLoaiPhong;
    private javax.swing.JTextField txtNgayDen;
    private javax.swing.JTextField txtNgayTra;
    private javax.swing.JTextField txtSoNgayO;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenPhong;
    private javax.swing.JTextField txtTienDV;
    private javax.swing.JTextField txtTienPhong;
    private javax.swing.JTextField txtTimTenKH;
    private javax.swing.JTextField txtTimTenPhong;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
