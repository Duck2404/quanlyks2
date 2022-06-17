/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormQLKS;

import Model.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author duyda
 */
public class frmThongKe extends javax.swing.JFrame {

    /**
     * Creates new form frmThongKe
     */
    
    DefaultTableModel tableModel = new DefaultTableModel();
    DefaultTableModel tableModel1 = new DefaultTableModel();
    ArrayList<TraPhong> arrTraPhong = new ArrayList<>();
    ArrayList<Phong> arrPhong = new ArrayList<>();
    ArrayList<LoaiPhong> arrLoaiPhong = new ArrayList<>();
    
    
    String ngayNhap,thangNhap,namNhap,ngayXuat,thangXuat,namXuat;
    double tongTienNgay=0,tongTienLoaiPhong=0,tongTien=0;
    
    Control control = new Control();
    
    public frmThongKe() {
        initComponents();
        arrTraPhong = (ArrayList<TraPhong>) control.showTKTraPhong();
        arrPhong = (ArrayList<Phong>) control.showPhong();
        arrLoaiPhong = (ArrayList<LoaiPhong>) control.showLoaiPhong();
        
        txtshowNgayNhap.setEditable(true);
        txtshowNgayXuat.setEditable(true);
        
        
        txtshowNgayNhap.setText(cbxNamNhap.getSelectedItem().toString() + " / " + cbxThangNhap.getSelectedItem().toString()+ " / " + cbxNgayNhap.getSelectedItem().toString() );
        txtshowNgayXuat.setText(cbxNamXuat.getSelectedItem().toString() + " / " + cbxThangXuat.getSelectedItem().toString()+ " / " + cbxNgayXuat.getSelectedItem().toString() );
        loadTBTK();
        loadCbxLoaiPhong(arrLoaiPhong);
        txtTongTienLoaiPhong.setText(String.valueOf(tongTien));
        txtTongTienNgay.setText(String.valueOf(tongTien));
        
    }
    
    public void loadTBTK()
    {
        tableModel = (DefaultTableModel) tbTKNgay.getModel();
        tableModel1 = (DefaultTableModel)tbLoaiPhong.getModel();
        tableModel.setRowCount(0);
        tableModel1.setRowCount(0);
        tongTien = 0;
        for (TraPhong tp : arrTraPhong) 
        {
            for (Phong ph : arrPhong)
            {
                if(ph.getMaPhong().equals(tp.getMaPhong()))
                {
                String rows[] = new String[4];
                    rows[0] = ph.getTenPhong();
                    rows[1] = ph.getTenLoaiPhong();
                    rows[2] = String.valueOf(tp.getTongTien());
                    rows[3] = tp.getNgayLapPhieu();
                    
                    tongTien+= tp.getTongTien();
                    
                tableModel1.addRow(rows);
                tableModel.addRow(rows);
                }
            }
        }
    }
    
    public void loadTBNgayThang()
    {
        tableModel = (DefaultTableModel) tbTKNgay.getModel();
        tableModel.setRowCount(0);
        tongTienNgay = 0;
        ResultSet rs = control.showTKNgayThang(txtshowNgayNhap.getText(), txtshowNgayXuat.getText());
        try 
        {
            while (rs.next()) {
                for (Phong ph : arrPhong)
            {
                if(ph.getMaPhong().equals(rs.getString("MaPhong")))
                {
                String rows[] = new String[4];
                    rows[0] = ph.getTenPhong();
                    rows[1] = ph.getTenLoaiPhong();
                    rows[2] = String.valueOf(rs.getDouble("TongTien"));
                    rows[3] = rs.getString("NgayLapPhieu");
                    
                tongTienNgay = tongTienNgay + rs.getDouble("TongTien");
                
                tableModel.addRow(rows);
                }
            }
                
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(frmThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadCbxLoaiPhong(ArrayList<LoaiPhong> arr)
    {
        for (LoaiPhong lp : arr) {
            cbxLoaiPhong.addItem(lp.getTenLoaiPhong());
        }
    }
    
    public void loadTBLoaiPhong()
    {
        tableModel = (DefaultTableModel) tbLoaiPhong.getModel();
        tableModel.setRowCount(0);
        tongTienLoaiPhong = 0;
        for (TraPhong tp : arrTraPhong) 
        {
            for (Phong ph : arrPhong)
            {
                if(ph.getMaPhong().equals(tp.getMaPhong()))
                {
                    if(ph.getTenLoaiPhong().equals(cbxLoaiPhong.getSelectedItem().toString()))
                    {
                        String rows[] = new String[4];
                            rows[0] = ph.getTenPhong();
                            rows[1] = ph.getTenLoaiPhong();
                            rows[2] = String.valueOf(tp.getTongTien());
                            rows[3] = tp.getNgayLapPhieu();
                            
                        tongTienLoaiPhong += tp.getTongTien();
                        
                        tableModel.addRow(rows);
                    }
                }
            }
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

        jPanel2 = new javax.swing.JPanel();
        btnNgayThang = new javax.swing.JButton();
        btnLoaiPhong = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        plNgayThang = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbxNgayNhap = new javax.swing.JComboBox<>();
        cbxThangNhap = new javax.swing.JComboBox<>();
        cbxNamNhap = new javax.swing.JComboBox<>();
        txtshowNgayNhap = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbxNgayXuat = new javax.swing.JComboBox<>();
        cbxThangXuat = new javax.swing.JComboBox<>();
        cbxNamXuat = new javax.swing.JComboBox<>();
        txtshowNgayXuat = new javax.swing.JTextField();
        btnThongKeNgay = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTKNgay = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtTongTienNgay = new javax.swing.JTextField();
        plLoaiPhong = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cbxLoaiPhong = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbLoaiPhong = new javax.swing.JTable();
        btnTKLoaiPhong = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtTongTienLoaiPhong = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thống Kê Doanh Thu");
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(153, 0, 0));

        jPanel2.setBackground(new java.awt.Color(153, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thống Kê Theo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNgayThang.setBackground(new java.awt.Color(222, 242, 241));
        btnNgayThang.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnNgayThang.setForeground(new java.awt.Color(51, 102, 255));
        btnNgayThang.setText("Ngày Tháng");
        btnNgayThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNgayThangActionPerformed(evt);
            }
        });
        jPanel2.add(btnNgayThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 120, -1));

        btnLoaiPhong.setBackground(new java.awt.Color(222, 242, 241));
        btnLoaiPhong.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLoaiPhong.setForeground(new java.awt.Color(255, 102, 102));
        btnLoaiPhong.setText("Loại Phòng");
        btnLoaiPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoaiPhongActionPerformed(evt);
            }
        });
        jPanel2.add(btnLoaiPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(138, 35, 120, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new java.awt.CardLayout());

        plNgayThang.setBackground(new java.awt.Color(153, 0, 0));

        jLabel2.setForeground(new java.awt.Color(242, 242, 242));
        jLabel2.setText("Từ ngày: ");

        cbxNgayNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", " " }));
        cbxNgayNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxNgayNhapActionPerformed(evt);
            }
        });

        cbxThangNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        cbxThangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxThangNhapActionPerformed(evt);
            }
        });

        cbxNamNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021" }));
        cbxNamNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxNamNhapActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(242, 242, 242));
        jLabel3.setText("Đến ngày:");

        cbxNgayXuat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", " " }));
        cbxNgayXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxNgayXuatActionPerformed(evt);
            }
        });

        cbxThangXuat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        cbxThangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxThangXuatActionPerformed(evt);
            }
        });

        cbxNamXuat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021" }));
        cbxNamXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxNamXuatActionPerformed(evt);
            }
        });

        btnThongKeNgay.setBackground(new java.awt.Color(222, 242, 241));
        btnThongKeNgay.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnThongKeNgay.setForeground(new java.awt.Color(204, 204, 0));
        btnThongKeNgay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Actions-document-edit-icon-16.png"))); // NOI18N
        btnThongKeNgay.setText("Thống kê");
        btnThongKeNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeNgayActionPerformed(evt);
            }
        });

        tbTKNgay.setBackground(new java.awt.Color(234, 248, 240));
        tbTKNgay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên phòng", "Loại phòng", "Tổng tiền", "Ngày tạo"
            }
        ));
        jScrollPane1.setViewportView(tbTKNgay);

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 51));
        jLabel4.setText("Tổng tiền: ");

        txtTongTienNgay.setBackground(new java.awt.Color(234, 248, 240));

        javax.swing.GroupLayout plNgayThangLayout = new javax.swing.GroupLayout(plNgayThang);
        plNgayThang.setLayout(plNgayThangLayout);
        plNgayThangLayout.setHorizontalGroup(
            plNgayThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plNgayThangLayout.createSequentialGroup()
                .addGroup(plNgayThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(plNgayThangLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(plNgayThangLayout.createSequentialGroup()
                        .addGroup(plNgayThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(plNgayThangLayout.createSequentialGroup()
                                .addGap(265, 265, 265)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTongTienNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(plNgayThangLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(plNgayThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnThongKeNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(plNgayThangLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbxThangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxNamNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtshowNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxNgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxThangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxNamXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtshowNgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 36, Short.MAX_VALUE)))
                .addContainerGap())
        );
        plNgayThangLayout.setVerticalGroup(
            plNgayThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plNgayThangLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(plNgayThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxThangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNamNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtshowNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cbxNgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxThangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNamXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtshowNgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThongKeNgay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plNgayThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTongTienNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.add(plNgayThang, "card2");

        plLoaiPhong.setBackground(new java.awt.Color(153, 0, 0));

        jLabel5.setText("Loại Phòng: ");

        cbxLoaiPhong.setBackground(new java.awt.Color(222, 242, 241));

        tbLoaiPhong.setBackground(new java.awt.Color(234, 248, 240));
        tbLoaiPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên phòng", "Loại phòng", "Tổng tiền", "Ngày tạo"
            }
        ));
        jScrollPane2.setViewportView(tbLoaiPhong);

        btnTKLoaiPhong.setBackground(new java.awt.Color(222, 242, 241));
        btnTKLoaiPhong.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnTKLoaiPhong.setForeground(new java.awt.Color(204, 204, 0));
        btnTKLoaiPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Actions-document-edit-icon-16.png"))); // NOI18N
        btnTKLoaiPhong.setText("Thống kê");
        btnTKLoaiPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKLoaiPhongActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 51));
        jLabel6.setText("Tổng tiền: ");

        txtTongTienLoaiPhong.setBackground(new java.awt.Color(234, 248, 240));

        javax.swing.GroupLayout plLoaiPhongLayout = new javax.swing.GroupLayout(plLoaiPhong);
        plLoaiPhong.setLayout(plLoaiPhongLayout);
        plLoaiPhongLayout.setHorizontalGroup(
            plLoaiPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plLoaiPhongLayout.createSequentialGroup()
                .addGroup(plLoaiPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(plLoaiPhongLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(plLoaiPhongLayout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTongTienLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(plLoaiPhongLayout.createSequentialGroup()
                        .addGap(274, 274, 274)
                        .addComponent(btnTKLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(207, Short.MAX_VALUE))
            .addGroup(plLoaiPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(plLoaiPhongLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        plLoaiPhongLayout.setVerticalGroup(
            plLoaiPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plLoaiPhongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plLoaiPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbxLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(btnTKLoaiPhong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 295, Short.MAX_VALUE)
                .addGroup(plLoaiPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTongTienLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(plLoaiPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(plLoaiPhongLayout.createSequentialGroup()
                    .addGap(76, 76, 76)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(39, Short.MAX_VALUE)))
        );

        jPanel4.add(plLoaiPhong, "card3");

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 710, 390));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(242, 242, 242));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thống kê Doanh Thu");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 267, 47));

        jMenuBar1.setBackground(new java.awt.Color(153, 153, 0));
        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jMenu1.setForeground(new java.awt.Color(242, 242, 242));
        jMenu1.setText("Hệ thống");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu2.setForeground(new java.awt.Color(242, 242, 242));
        jMenu2.setText("Thống Kê");
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNgayThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNgayThangActionPerformed
        // TODO add your handling code here:
        plLoaiPhong.setVisible(false);
        plNgayThang.setVisible(true);
    }//GEN-LAST:event_btnNgayThangActionPerformed

    private void btnLoaiPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoaiPhongActionPerformed
        // TODO add your handling code here:
        plNgayThang.setVisible(false);
        plLoaiPhong.setVisible(true);
    }//GEN-LAST:event_btnLoaiPhongActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        // TODO add your handling code here:
        
        QuanLyKhachSan frm = new QuanLyKhachSan();
        frm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenu1MouseClicked

    private void btnThongKeNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeNgayActionPerformed
        // TODO add your handling code here:
        loadTBNgayThang();
        txtTongTienNgay.setText(String.valueOf(tongTienNgay));
    }//GEN-LAST:event_btnThongKeNgayActionPerformed

    private void cbxNgayNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxNgayNhapActionPerformed
        // TODO add your handling code here:
        txtshowNgayNhap.setText(cbxNamNhap.getSelectedItem().toString() + " / " + cbxThangNhap.getSelectedItem().toString()+ " / " + cbxNgayNhap.getSelectedItem().toString() );
    }//GEN-LAST:event_cbxNgayNhapActionPerformed

    private void cbxThangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxThangNhapActionPerformed
        // TODO add your handling code here:
        txtshowNgayNhap.setText(cbxNamNhap.getSelectedItem().toString() + " / " + cbxThangNhap.getSelectedItem().toString()+ " / " + cbxNgayNhap.getSelectedItem().toString() );

    }//GEN-LAST:event_cbxThangNhapActionPerformed

    private void cbxNamNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxNamNhapActionPerformed
        // TODO add your handling code here:
        txtshowNgayNhap.setText(cbxNamNhap.getSelectedItem().toString() + " / " + cbxThangNhap.getSelectedItem().toString()+ " / " + cbxNgayNhap.getSelectedItem().toString() );
    }//GEN-LAST:event_cbxNamNhapActionPerformed

    private void cbxNgayXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxNgayXuatActionPerformed
        // TODO add your handling code here:
        txtshowNgayXuat.setText(cbxNamXuat.getSelectedItem().toString() + " / " + cbxThangXuat.getSelectedItem().toString()+ " / " + cbxNgayXuat.getSelectedItem().toString() );
    }//GEN-LAST:event_cbxNgayXuatActionPerformed

    private void cbxThangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxThangXuatActionPerformed
        // TODO add your handling code here:
        txtshowNgayXuat.setText(cbxNamXuat.getSelectedItem().toString() + " / " + cbxThangXuat.getSelectedItem().toString()+ " / " + cbxNgayXuat.getSelectedItem().toString() );
    }//GEN-LAST:event_cbxThangXuatActionPerformed

    private void cbxNamXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxNamXuatActionPerformed
        // TODO add your handling code here:
        txtshowNgayXuat.setText(cbxNamXuat.getSelectedItem().toString() + " / " + cbxThangXuat.getSelectedItem().toString()+ " / " + cbxNgayXuat.getSelectedItem().toString() );
    }//GEN-LAST:event_cbxNamXuatActionPerformed

    private void btnTKLoaiPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKLoaiPhongActionPerformed
        // TODO add your handling code here:
        loadTBLoaiPhong();
        txtTongTienLoaiPhong.setText(String.valueOf(tongTienLoaiPhong));
    }//GEN-LAST:event_btnTKLoaiPhongActionPerformed

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
            java.util.logging.Logger.getLogger(frmThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmThongKe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoaiPhong;
    private javax.swing.JButton btnNgayThang;
    private javax.swing.JButton btnTKLoaiPhong;
    private javax.swing.JButton btnThongKeNgay;
    private javax.swing.JComboBox<String> cbxLoaiPhong;
    private javax.swing.JComboBox<String> cbxNamNhap;
    private javax.swing.JComboBox<String> cbxNamXuat;
    private javax.swing.JComboBox<String> cbxNgayNhap;
    private javax.swing.JComboBox<String> cbxNgayXuat;
    private javax.swing.JComboBox<String> cbxThangNhap;
    private javax.swing.JComboBox<String> cbxThangXuat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel plLoaiPhong;
    private javax.swing.JPanel plNgayThang;
    private javax.swing.JTable tbLoaiPhong;
    private javax.swing.JTable tbTKNgay;
    private javax.swing.JTextField txtTongTienLoaiPhong;
    private javax.swing.JTextField txtTongTienNgay;
    private javax.swing.JTextField txtshowNgayNhap;
    private javax.swing.JTextField txtshowNgayXuat;
    // End of variables declaration//GEN-END:variables
}
