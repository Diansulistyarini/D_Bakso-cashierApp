/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaksi;

import connect.konek;
import crud.formbrg;
import java.awt.event.KeyEvent;
import transaksi.formjualan;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import transaksi.formjualan;
import login.dashbord;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class formjualan extends javax.swing.JFrame {

    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel model1 = new DefaultTableModel();
    private int harga;
    private int jmlh;
    private int total;
    private int kembalian;

    public formjualan() {
        initComponents();
        konek con = new konek();
        con.getKoneksi();

        jumlahBeli.hide();
        jumlahMenu.hide();
        id.disable();
        idTransaksi();

        tablemenu.setModel(model);
        model.addColumn("Kode Menu");
        model.addColumn("Nama Menu");
        model.addColumn("Harga");
        showData();

        tablecart.setModel(model1);
//        model1.addColumn("Id");
        model1.addColumn("Tanggal Transaksi");
        model1.addColumn("Nama Menu");
        model1.addColumn("Harga Jual");
        model1.addColumn("Jumlah Beli");
        model1.addColumn("Total Harga");
        showCart();

        Date tanggal = new Date();
        date.setDate(tanggal);
    }

    private void clear() {
//        id.setText(null);
        date.setDate(null);
        namamenu.setText(null);
        hrg.setText(null);
        jml.setText(null);
        ttl.setText(null);
        byr.setText(null);
        kmbl.setText(null);
        idTransaksi();
    }

    private void showData() {
        int row = tablemenu.getRowCount();
        for (int a = 0; a < row; a++) {
            model.removeRow(0);
        }

        String query = "SELECT * FROM `menu`";

        try {
            Connection conn = konek.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                String kode = rs.getString("kd_menu");
                String nama = rs.getString("nama_menu");
                String harga = rs.getString("harga");

                String[] data = {kode, nama, harga};
                model.addRow(data);

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void showCart() {
        int row = tablecart.getRowCount();
        for (int a = 0; a < row; a++) {
            model1.removeRow(0);
        }

        String query = "SELECT * FROM `cart`";

        try {
            Connection conn = konek.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                String Id = rs.getString("id");
                String date = rs.getString("tgl_transaksi");
                String nama = rs.getString("nama_menu");
                String harga = rs.getString("harga_jual");
                String jml = rs.getString("jumlah_beli");
                String ttl = rs.getString("total_harga");

                String[] data = {Id, date, nama, harga, jml, ttl};
                model1.addRow(data);

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void idTransaksi() {
        try {
            java.util.Date tanggal = new java.util.Date();
            java.text.SimpleDateFormat tgl = new java.text.SimpleDateFormat("yyMMdd");

            String kuery = "SELECT MAX(id) FROM data WHERE tgl_transaksi = '" + tgl.format(tanggal) + "'";
            Statement st = konek.getKoneksi().createStatement();
            ResultSet rs = st.executeQuery(kuery);
            while (rs.next()) {
                Long a = rs.getLong(1);
                if (a == 0) {
                    this.id.setText(tgl.format(tanggal) + "00" + (a + 1));
                } else {
                    this.id.setText("" + (a + 1));
                }
            }
            rs.close();
            st.close();
        } catch (Exception a) {
            System.out.println(a);
        }
    }

    private void gabungan() {
        int a = tablecart.getSelectedRow();
        String Id = model1.getValueAt(a, 0).toString();
        String tgl = model1.getValueAt(a, 1).toString();
        String jmlMenu = jumlahMenu.getText();
        String jmlh = jumlahBeli.getText();
        int hrg = Integer.parseInt(byr.getText());
        int total2 = Integer.parseInt(ttl.getText());
        int kembalian = hrg - total2;
        String kembali = Integer.toString(kembalian);
        int kmbli = Integer.parseInt(kmbl.getText());
        kmbl.setText(kembali);
        kmbl.getText();

        String sql = "INSERT INTO `struk` (`id`, `tgl_transaksi`, `Jumlah_menu`, `jumlah_beli`, `sub_total`, `uang`, `kembalian`) "
                + "VALUES ('" + Id + "', '" + tgl + "', '" + jmlMenu + "', '" + jmlh + "', '" + total2 + "', '" + hrg + "', '" + kmbli + "');";

        Connection conn = konek.getKoneksi();
        try {

            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Transaksi Berhasil, Silahkan cetak Struk");

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal Menambahkan");
        } finally {
            idTransaksi();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablemenu = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        hrg = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablecart = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        namamenu = new javax.swing.JTextField();
        date = new com.toedter.calendar.JDateChooser();
        keranjang = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jml = new javax.swing.JTextField();
        ButtonClear = new javax.swing.JButton();
        kmb = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        byr = new javax.swing.JTextField();
        kmbl = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        ttl = new javax.swing.JTextField();
        cancel = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jumlahBeli = new javax.swing.JTextField();
        jumlahMenu = new javax.swing.JTextField();
        struk = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 255));

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel1.setText("List Menu");

        tablemenu.setModel(new javax.swing.table.DefaultTableModel(
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
        tablemenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablemenuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablemenu);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Nama Menu");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Harga Jual");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Tanggal");

        tablecart.setModel(new javax.swing.table.DefaultTableModel(
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
        tablecart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablecartMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablecart);

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel8.setText("List Pemesanan");

        keranjang.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\icondian\\krnjg.png")); // NOI18N
        keranjang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keranjangMouseClicked(evt);
            }
        });

        back.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\ini.png")); // NOI18N
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });
        back.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                backKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Jumlah Beli");

        ButtonClear.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        ButtonClear.setText("Clear");
        ButtonClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonClearMouseClicked(evt);
            }
        });
        ButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonClearActionPerformed(evt);
            }
        });

        kmb.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        kmb.setText("Kembalian");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setText("Total");

        byr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                byrKeyPressed(evt);
            }
        });

        kmbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kmblActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel12.setText("Bayar");

        cancel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cancel.setText("Cancel");
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("ID");

        jumlahBeli.setText("tampung jumlah beli");

        jumlahMenu.setText("tampung jumlah menu");

        struk.setIcon(new javax.swing.ImageIcon("C:\\Users\\ASUS\\Downloads\\struk3.png")); // NOI18N
        struk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                strukMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(191, 191, 191))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jml, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(45, 45, 45)
                            .addComponent(keranjang, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(104, 104, 104)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(date, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                        .addComponent(namamenu, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                        .addComponent(id)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(hrg, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addGap(40, 40, 40))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kmb)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(ttl, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jumlahBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jumlahMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(byr, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ButtonClear)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(kmbl, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(struk)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 48, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cancel)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(back, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cancel)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jumlahBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jumlahMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(ttl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(byr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(namamenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(kmbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(kmb)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(hrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(jml, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ButtonClear)
                                        .addComponent(keranjang, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(struk)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(back)
                .addGap(240, 240, 240))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablemenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablemenuMouseClicked
        // TODO add your handling code here:
        int x = tablemenu.getSelectedRow();

        String nama = model.getValueAt(x, 1).toString();
        String harga = model.getValueAt(x, 2).toString();

        namamenu.setText(nama);
        hrg.setText(harga);


    }//GEN-LAST:event_tablemenuMouseClicked

    private void backKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_backKeyPressed
        // TODO add your handling code here:
        new dashbord().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_backKeyPressed

    private void ButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonClearActionPerformed
        // TODO add your handling code here:
        clear();

    }//GEN-LAST:event_ButtonClearActionPerformed

    private void keranjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keranjangMouseClicked
        // TODO add your handling code here:
        String Id = id.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tgl = sdf.format(date.getDate());
        String nama = namamenu.getText();
        String harga = hrg.getText();
        String jmlh = jml.getText();

        int harga1 = Integer.parseInt(harga);
        int jmlh1 = Integer.parseInt(jmlh);
        int total = harga1 * jmlh1;
        String total1 = Integer.toString(total);

        Connection conn = konek.getKoneksi();
        String query = "INSERT INTO `cart` (`id`,`tgl_transaksi`, `nama_menu`, `harga_jual`, `jumlah_beli`, `total_harga`) VALUES "
                + "('" + Id + "', '" + tgl + "', '" + nama + "', '" + harga + "', '" + jmlh + "', '" + total1 + "');";
        try {

            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Data Berhasil Ditambah ke Keranjang");

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal Menambahkan");
        } finally {
            showCart();
        }


    }//GEN-LAST:event_keranjangMouseClicked

    private void tablecartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablecartMouseClicked
//         TODO add your handling code here:
//        idTransaksi();
        date.setDate(null);
        namamenu.setText(null);
        hrg.setText(null);
        jml.setText(null);
        showCart();
        String sql = "SELECT SUM(total_harga) AS TotalSemua FROM cart";

        try {
            Connection conn = konek.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                ttl.setText(rs.getString("TotalSemua"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

//        ngitung jumlah beli
        try {
            String kuery = "SELECT SUM(jumlah_beli) AS beli FROM cart";
            Connection conn = konek.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(kuery);

            while (rs.next()) {
                jumlahBeli.setText(rs.getString("beli"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

//        Hitung jumlah menu
        try {
            String kuery = "SELECT COUNT(nama_menu) AS jumlah FROM cart";
            Connection conn = konek.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(kuery);

            while (rs.next()) {
                jumlahMenu.setText(rs.getString("jumlah"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }//GEN-LAST:event_tablecartMouseClicked

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
        // TODO add your handling code here:
        new dashbord().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_backMouseClicked

    private void kmblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kmblActionPerformed
        // TODO add your handling code here:
        gabungan();
        idTransaksi();

    }//GEN-LAST:event_kmblActionPerformed

    private void byrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_byrKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int hrg = Integer.parseInt(byr.getText());
            int total = Integer.parseInt(ttl.getText());
            int kembalian = hrg - total;
            String kembali = Integer.toString(kembalian);
            kmbl.setText(kembali);
        }
    }//GEN-LAST:event_byrKeyPressed

    private void ButtonClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonClearMouseClicked
        // TODO add your handling code here:
//        gabungan();
//        idTransaksi();
        Connection conn = konek.getKoneksi();
        String query = "DELETE FROM `cart`";
        try {

            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.executeUpdate(query);
//            JOptionPane.showMessageDialog(null, "Transaksi Berhasil");

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data tdk ke hapus");
        } finally {
            date.setDate(null);
            namamenu.setText(null);
            hrg.setText(null);
            jml.setText(null);
            ttl.setText(null);
            kmbl.setText(null);
            byr.setText(null);
            idTransaksi();
        }


    }//GEN-LAST:event_ButtonClearMouseClicked

    private void cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseClicked
        // TODO add your handling code here:
//        idTransaksi();
        Connection conn = konek.getKoneksi();
        String query = "DELETE FROM `cart`";
        try {

            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Berhasil di Cancel");

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data gagal dicancel");
        } finally {
            date.setDate(null);
            namamenu.setText(null);
            hrg.setText(null);
            jml.setText(null);
            ttl.setText(null);
        }
        showCart();
    }//GEN-LAST:event_cancelMouseClicked

    private void strukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_strukMouseClicked
        // TODO add your handling code here:
        try {
            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("reportstruk.jasper"), null, konek.getKoneksi());
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            Logger.getLogger(formjualan.class.getName()).log(Level.SEVERE, null, e);
        }

    }//GEN-LAST:event_strukMouseClicked

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
            java.util.logging.Logger.getLogger(formjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonClear;
    private javax.swing.JLabel back;
    private javax.swing.JTextField byr;
    private javax.swing.JButton cancel;
    private com.toedter.calendar.JDateChooser date;
    private javax.swing.JTextField hrg;
    private javax.swing.JTextField id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jml;
    private javax.swing.JTextField jumlahBeli;
    private javax.swing.JTextField jumlahMenu;
    private javax.swing.JLabel keranjang;
    private javax.swing.JLabel kmb;
    private javax.swing.JTextField kmbl;
    private javax.swing.JTextField namamenu;
    private javax.swing.JLabel struk;
    private javax.swing.JTable tablecart;
    private javax.swing.JTable tablemenu;
    private javax.swing.JTextField ttl;
    // End of variables declaration//GEN-END:variables

    private void executeQuery(String sql) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
