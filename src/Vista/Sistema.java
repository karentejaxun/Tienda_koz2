package Vista;

import Modelo.Cliente;
import Modelo.ClienteINs;
import Modelo.Detalle_Venta;
import Modelo.EmpresaDatos;
import Modelo.Eventos;
import Modelo.Prendas;
import Modelo.PrendasIns;
import Modelo.Proveedor;
import Modelo.ProveedorDao;
import Modelo.Venta;
import Modelo.VentaN;
import Modelo.login;
import Report.Graficas;
import Report.Reps;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author usuario
 */
public class Sistema extends javax.swing.JFrame {

    Date fechaven = new Date();
    String fechactualsis = new SimpleDateFormat("dd/MM/yyyy").format(fechaven);
    Cliente cli = new Cliente();
    Cliente Cli = new Cliente();
    ClienteINs clI = new ClienteINs();
    Proveedor pr = new Proveedor();
    ProveedorDao PrDao = new ProveedorDao();
    Prendas pre = new Prendas();
    PrendasIns preI = new PrendasIns();
    Venta v = new Venta();
    VentaN vn = new VentaN();
    EmpresaDatos dat = new EmpresaDatos();
    EmpresaDatos datos = new EmpresaDatos();
    Eventos events = new Eventos();
    Detalle_Venta dv = new Detalle_Venta();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel newmodelo = new DefaultTableModel();
    int item;
    float TotalPagar = (float) 0.0;
    double Totalpagar = 0.00;

    public Sistema() {
        //this.setContentPane(new Fondo());
        initComponents();
    }

    public Sistema(login adm) {
        initComponents();
        this.setLocationRelativeTo(null);
        txtidCliente.setVisible(false);
        txtidventa.setVisible(false);
        txtidproveedor.setVisible(false);
        txtidpre.setVisible(false);
        txtidDatos.setVisible(false);
        txttelefonoclienteventa.setVisible(false);
        txtdireccionclienteventa.setVisible(false);
        txtcorreoclienteventa.setVisible(false);

        AutoCompleteDecorator.decorate(cbxproveedorpre);
        preI.ConsultarProve(cbxproveedorpre);
        ListarDatatos();
        if (adm.getRol().equals("Auxiliar")) {
            jButton3.setEnabled(false);
            btnPrendas.setEnabled(false);
            jButton6.setEnabled(false);
            addUser.setEnabled(false);

            LVendedor.setText(adm.getUsuario());
        } else {
            LVendedor.setText(adm.getUsuario());
        }
    }

    public void ListarProveedor() {
        List<Proveedor> ListarPr = PrDao.ListarProveedor();
        modelo = (DefaultTableModel) tableproveedor.getModel();
        Object[] ob = new Object[6];

        for (int i = 0; i < ListarPr.size(); i++) {
            ob[0] = ListarPr.get(i).getIdProveedor();
            ob[1] = ListarPr.get(i).getCodProveedor();
            ob[2] = ListarPr.get(i).getProveedor();
            ob[3] = ListarPr.get(i).getCorreo();
            ob[4] = ListarPr.get(i).getTelefono();

            modelo.addRow(ob);
        }

        tableproveedor.setModel(modelo);
    }

    public void ListarCliente() {

        List<Cliente> ListarCl = clI.ListarCliente();
        modelo = (DefaultTableModel) tablecliente.getModel();
        Object[] ob = new Object[6];

        for (int i = 0; i < ListarCl.size(); i++) {
            ob[0] = ListarCl.get(i).getIdCliente();
            ob[1] = ListarCl.get(i).getNit();
            ob[2] = ListarCl.get(i).getNombre();
            ob[3] = ListarCl.get(i).getTelefono();
            ob[4] = ListarCl.get(i).getDireccion();
            ob[5] = ListarCl.get(i).getCorreo();
            modelo.addRow(ob);
        }

        tablecliente.setModel(modelo);

    }

    public void Limpiartb() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void ListarPrendas() {

        List<Prendas> ListarPre = preI.ListarPrendas();
        modelo = (DefaultTableModel) tablePrendas.getModel();
        Object[] ob = new Object[7];

        for (int i = 0; i < ListarPre.size(); i++) {
            ob[0] = ListarPre.get(i).getIdPrenda();
            ob[1] = ListarPre.get(i).getCodigo();
            ob[2] = ListarPre.get(i).getMarca();
            ob[3] = ListarPre.get(i).getProveedor();
            ob[4] = ListarPre.get(i).getCantidad();
            ob[5] = ListarPre.get(i).getPrecio();
            ob[6] = ListarPre.get(i).getDescripcion();
            modelo.addRow(ob);
        }

        tablePrendas.setModel(modelo);

    }

    public void ListarDatatos() {
        dat = preI.BuscarDatos();
        txtidDatos.setText("" + dat.getIdDatos());
        txtnombreempresa.setText("" + dat.getEmpresa());
        txttelefonoempresa.setText("" + dat.getTelefono());
        txtdireccionempresa.setText("" + dat.getDireccion());
        txtcorreoempresa.setText("" + dat.getCorreo());

    }

    public void ListarVentas() {

        List<Venta> ListarVenta = vn.Listarventas();
        modelo = (DefaultTableModel) tableventas.getModel();
        Object[] ob = new Object[4];

        for (int i = 0; i < ListarVenta.size(); i++) {
            ob[0] = ListarVenta.get(i).getIdVenta();
            ob[1] = ListarVenta.get(i).getCliente();
            ob[2] = ListarVenta.get(i).getVendedor();
            ob[3] = ListarVenta.get(i).getTotal();

            modelo.addRow(ob);
        }

        tableventas.setModel(modelo);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        LVendedor = new javax.swing.JLabel();
        addUser = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnPrendas = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtcodigoventa = new javax.swing.JTextField();
        txtcantidadventa = new javax.swing.JTextField();
        txtdescripcionventa = new javax.swing.JTextField();
        txtprecioventa = new javax.swing.JTextField();
        txtcantidad = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaventa = new javax.swing.JTable();
        btneliminarventa = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtnitventa = new javax.swing.JTextField();
        txtnombreclienteventa = new javax.swing.JTextField();
        btngenerarventa = new javax.swing.JButton();
        Label10 = new javax.swing.JLabel();
        LabelTotal = new javax.swing.JTextField();
        txttelefonoclienteventa = new javax.swing.JTextField();
        txtdireccionclienteventa = new javax.swing.JTextField();
        txtcorreoclienteventa = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtnitcliente = new javax.swing.JTextField();
        txtnombrecliente = new javax.swing.JTextField();
        txttelefonocliente = new javax.swing.JTextField();
        txtdireccioncliente = new javax.swing.JTextField();
        txtcorreocliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablecliente = new javax.swing.JTable();
        btnguardarcliente = new javax.swing.JButton();
        btneditarcliente = new javax.swing.JButton();
        btneliminarcliente = new javax.swing.JButton();
        btnnuevocliente = new javax.swing.JButton();
        txtidCliente = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtcodProveedor = new javax.swing.JTextField();
        txtnombreproveedor = new javax.swing.JTextField();
        txtcorreoproveedor = new javax.swing.JTextField();
        txttelefonoproveedor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableproveedor = new javax.swing.JTable();
        btnguardarproveedor = new javax.swing.JButton();
        txteditarproveedor = new javax.swing.JButton();
        btneliminarproveedor = new javax.swing.JButton();
        btnnuevoproveedor = new javax.swing.JButton();
        txtidproveedor = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtcodigopre = new javax.swing.JTextField();
        txtmarcpre = new javax.swing.JTextField();
        txtdescpre = new javax.swing.JTextField();
        txtcantpre = new javax.swing.JTextField();
        cbxproveedorpre = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablePrendas = new javax.swing.JTable();
        btnguardarpro = new javax.swing.JButton();
        btneliminarpro = new javax.swing.JButton();
        btneditarpro = new javax.swing.JButton();
        btnnuevopro = new javax.swing.JButton();
        btnexcelpro = new javax.swing.JButton();
        txtidpre = new javax.swing.JTextField();
        txtpreciopre = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableventas = new javax.swing.JTable();
        btnpdfventas = new javax.swing.JButton();
        txtidventa = new javax.swing.JTextField();
        botonGrafica = new javax.swing.JButton();
        CalDate = new com.toedter.calendar.JDateChooser();
        jPanel8 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtnombreempresa = new javax.swing.JTextField();
        txttelefonoempresa = new javax.swing.JTextField();
        txtdireccionempresa = new javax.swing.JTextField();
        txtcorreoempresa = new javax.swing.JTextField();
        btnactualizardatosempresa = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtidDatos = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(35, 159, 152));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/kozverde.png"))); // NOI18N

        LVendedor.setFont(new java.awt.Font("Cascadia Mono", 1, 24)); // NOI18N
        LVendedor.setForeground(new java.awt.Color(255, 255, 255));

        addUser.setBackground(java.awt.Color.pink);
        addUser.setFont(new java.awt.Font("Cascadia Mono", 0, 12)); // NOI18N
        addUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/4.png"))); // NOI18N
        addUser.setText("AÑADIR USUARIO");
        addUser.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(153, 255, 204));

        jButton1.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nueva venta.png"))); // NOI18N
        jButton1.setText("Nueva Venta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cliente.png"))); // NOI18N
        jButton2.setText("Clientes");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/proveedor.png"))); // NOI18N
        jButton3.setText("Proveedor");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnPrendas.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        btnPrendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/productos.png"))); // NOI18N
        btnPrendas.setText("Prendas");
        btnPrendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrendasMouseClicked(evt);
            }
        });
        btnPrendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrendasActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/6.png"))); // NOI18N
        jButton5.setText("Ventas");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ajustes.png"))); // NOI18N
        jButton6.setText("Ajustes");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jButton1)
                .addGap(47, 47, 47)
                .addComponent(jButton2)
                .addGap(56, 56, 56)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(btnPrendas)
                .addGap(48, 48, 48)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jButton6)
                .addGap(44, 44, 44))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrendas, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jLabel3.setFont(new java.awt.Font("Cascadia Code", 1, 12)); // NOI18N
        jLabel3.setText("CODIGO:");

        jLabel4.setFont(new java.awt.Font("Cascadia Code", 1, 12)); // NOI18N
        jLabel4.setText("DESCRIPCION:");

        jLabel5.setFont(new java.awt.Font("Cascadia Code", 1, 12)); // NOI18N
        jLabel5.setText("CANTIDAD:");

        jLabel6.setFont(new java.awt.Font("Cascadia Code", 1, 12)); // NOI18N
        jLabel6.setText("PRECIO:");

        jLabel8.setFont(new java.awt.Font("Cascadia Code", 1, 12)); // NOI18N
        jLabel8.setText("STOCK DISPONIBLE:");

        txtcodigoventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcodigoventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcodigoventaKeyTyped(evt);
            }
        });

        txtcantidadventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcantidadventaActionPerformed(evt);
            }
        });
        txtcantidadventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidadventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidadventaKeyTyped(evt);
            }
        });

        txtdescripcionventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdescripcionventaKeyTyped(evt);
            }
        });

        txtprecioventa.setEditable(false);
        txtprecioventa.setBackground(new java.awt.Color(255, 255, 255));
        txtprecioventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtprecioventaActionPerformed(evt);
            }
        });

        tablaventa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        jScrollPane1.setViewportView(tablaventa);
        if (tablaventa.getColumnModel().getColumnCount() > 0) {
            tablaventa.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablaventa.getColumnModel().getColumn(1).setPreferredWidth(100);
            tablaventa.getColumnModel().getColumn(2).setPreferredWidth(30);
            tablaventa.getColumnModel().getColumn(3).setPreferredWidth(30);
            tablaventa.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        btneliminarventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btneliminarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarventaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Cascadia Code", 1, 12)); // NOI18N
        jLabel7.setText("NIT:");

        jLabel9.setFont(new java.awt.Font("Cascadia Code", 1, 12)); // NOI18N
        jLabel9.setText("NOMBRE");

        txtnitventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnitventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnitventaKeyTyped(evt);
            }
        });

        txtnombreclienteventa.setEditable(false);
        txtnombreclienteventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnombreclienteventaKeyTyped(evt);
            }
        });

        btngenerarventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/correo.png"))); // NOI18N
        btngenerarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngenerarventaActionPerformed(evt);
            }
        });

        Label10.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        Label10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/pago.png"))); // NOI18N
        Label10.setText("Total a Pagar:");

        LabelTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LabelTotalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtnitventa, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(28, 28, 28)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(txtnombreclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txttelefonoclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtdireccionclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtcorreoclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(47, 47, 47))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(txtcodigoventa)
                                                .addGap(25, 25, 25)))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtdescripcionventa, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4))
                                        .addGap(31, 31, 31)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtcantidadventa, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5))
                                        .addGap(53, 53, 53)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtprecioventa, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(39, 39, 39)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                                .addComponent(btneliminarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(129, 129, 129))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btngenerarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(122, 122, 122)
                        .addComponent(Label10)
                        .addGap(18, 18, 18)
                        .addComponent(LabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtnombreclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnitventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttelefonoclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdireccionclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcorreoclienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btneliminarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcantidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtcodigoventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtdescripcionventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtcantidadventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtprecioventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Label10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(LabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btngenerarventa))
                .addGap(11, 11, 11))
        );

        jTabbedPane1.addTab("", jPanel3);

        jLabel11.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel11.setText("NIT:");

        jLabel12.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel12.setText("NOMBRE:");

        jLabel13.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel13.setText("TELEFONO:");

        jLabel14.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel14.setText("DIRECCION:");

        jLabel15.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel15.setText("CORREO:");

        txtnitcliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnitclienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnitclienteKeyTyped(evt);
            }
        });

        txtnombrecliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreclienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnombreclienteKeyTyped(evt);
            }
        });

        txttelefonocliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttelefonoclienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttelefonoclienteKeyTyped(evt);
            }
        });

        txtdireccioncliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdireccionclienteKeyTyped(evt);
            }
        });

        txtcorreocliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcorreoclienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcorreoclienteKeyTyped(evt);
            }
        });

        tablecliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NIT", "NOMBRE", "TELEFONO", "DIRECCION", "CORREO"
            }
        ));
        tablecliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableclienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablecliente);
        if (tablecliente.getColumnModel().getColumnCount() > 0) {
            tablecliente.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablecliente.getColumnModel().getColumn(1).setPreferredWidth(100);
            tablecliente.getColumnModel().getColumn(2).setPreferredWidth(50);
            tablecliente.getColumnModel().getColumn(3).setPreferredWidth(80);
            tablecliente.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        btnguardarcliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        btnguardarcliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnguardarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarclienteActionPerformed(evt);
            }
        });

        btneditarcliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/actualizar.png"))); // NOI18N
        btneditarcliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btneditarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarclienteActionPerformed(evt);
            }
        });

        btneliminarcliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btneliminarcliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btneliminarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarclienteActionPerformed(evt);
            }
        });

        btnnuevocliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/excel.png"))); // NOI18N
        btnnuevocliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnnuevocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoclienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcorreocliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnitcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtdireccioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                        .addComponent(btnguardarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btneliminarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnnuevocliente, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btneditarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtnombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txttelefonocliente, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1047, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 37, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtnombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(txttelefonocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnitcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnguardarcliente)
                            .addComponent(btneliminarcliente)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(txtcorreocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14)
                                .addComponent(txtdireccioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtidCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(btneditarcliente)
                    .addComponent(btnnuevocliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );

        jTabbedPane1.addTab("", jPanel4);

        jLabel16.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel16.setText("CODIGO:");

        jLabel17.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel17.setText("PROVEEDOR:");

        jLabel18.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel18.setText("CORREO:");

        jLabel19.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel19.setText("TELEFONO:");

        txtcodProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcodProveedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcodProveedorKeyTyped(evt);
            }
        });

        txtnombreproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreproveedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnombreproveedorKeyTyped(evt);
            }
        });

        txtcorreoproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcorreoproveedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcorreoproveedorKeyTyped(evt);
            }
        });

        txttelefonoproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttelefonoproveedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txttelefonoproveedorKeyTyped(evt);
            }
        });

        tableproveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "PROVEEDOR", "CORREO", "TELÉFONO"
            }
        ));
        tableproveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableproveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableproveedor);
        if (tableproveedor.getColumnModel().getColumnCount() > 0) {
            tableproveedor.getColumnModel().getColumn(0).setPreferredWidth(5);
            tableproveedor.getColumnModel().getColumn(1).setPreferredWidth(40);
            tableproveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableproveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            tableproveedor.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        btnguardarproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        btnguardarproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarproveedorActionPerformed(evt);
            }
        });

        txteditarproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/actualizar.png"))); // NOI18N
        txteditarproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txteditarproveedorActionPerformed(evt);
            }
        });

        btneliminarproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btneliminarproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarproveedorActionPerformed(evt);
            }
        });

        btnnuevoproveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/excel.png"))); // NOI18N
        btnnuevoproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoproveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtcodProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcorreoproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtnombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtidproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txttelefonoproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(353, 353, 353))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnguardarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnnuevoproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btneliminarproveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txteditarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtcodProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtidproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txttelefonoproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(txtcorreoproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(40, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnguardarproveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btneliminarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnnuevoproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txteditarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(164, 164, 164))))
        );

        jTabbedPane1.addTab("", jPanel5);

        jLabel21.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel21.setText("CODIGO:");

        jLabel22.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel22.setText("MARCA:");

        jLabel23.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel23.setText("PROVEEDOR:");

        jLabel24.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel24.setText("CANTIDAD:");

        jLabel25.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel25.setText("DESCRIPCION:");

        txtcodigopre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcodigopreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcodigopreKeyTyped(evt);
            }
        });

        txtmarcpre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmarcpreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmarcpreKeyTyped(evt);
            }
        });

        txtdescpre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdescpreKeyTyped(evt);
            }
        });

        txtcantpre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcantpreActionPerformed(evt);
            }
        });
        txtcantpre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantpreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantpreKeyTyped(evt);
            }
        });

        cbxproveedorpre.setEditable(true);
        cbxproveedorpre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbxproveedorpreKeyPressed(evt);
            }
        });

        tablePrendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO ", "MARCA", "PROVEEDOR", "CANTIDAD", "PRECIO", "DESCRIPCIÓN"
            }
        ));
        tablePrendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePrendasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablePrendas);
        if (tablePrendas.getColumnModel().getColumnCount() > 0) {
            tablePrendas.getColumnModel().getColumn(0).setPreferredWidth(5);
            tablePrendas.getColumnModel().getColumn(1).setPreferredWidth(50);
            tablePrendas.getColumnModel().getColumn(2).setPreferredWidth(50);
            tablePrendas.getColumnModel().getColumn(3).setPreferredWidth(50);
            tablePrendas.getColumnModel().getColumn(4).setPreferredWidth(30);
            tablePrendas.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        btnguardarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        btnguardarpro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarproActionPerformed(evt);
            }
        });

        btneliminarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btneliminarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarproActionPerformed(evt);
            }
        });

        btneditarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/actualizar.png"))); // NOI18N
        btneditarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarproActionPerformed(evt);
            }
        });

        btnnuevopro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/excel.png"))); // NOI18N
        btnnuevopro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoproActionPerformed(evt);
            }
        });

        btnexcelpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/excel2.png"))); // NOI18N
        btnexcelpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexcelproActionPerformed(evt);
            }
        });

        txtpreciopre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpreciopreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpreciopreKeyTyped(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel32.setText("PRECIO: Q");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(btnguardarpro, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btneditarpro, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnexcelpro, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(btneliminarpro, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnnuevopro, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcantpre, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcodigopre, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(13, 13, 13)
                        .addComponent(txtpreciopre, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(txtmarcpre, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(txtidpre, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtdescpre, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxproveedorpre, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21)
                                .addComponent(txtcodigopre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22)
                                .addComponent(txtmarcpre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtidpre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbxproveedorpre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txtcantpre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32)
                            .addComponent(txtpreciopre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdescpre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)))
                    .addComponent(jLabel23))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnexcelpro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btneditarpro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnguardarpro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btneliminarpro, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                            .addComponent(btnnuevopro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(157, 157, 157))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("", jPanel6);

        tableventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        tableventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableventasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableventas);
        if (tableventas.getColumnModel().getColumnCount() > 0) {
            tableventas.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableventas.getColumnModel().getColumn(1).setPreferredWidth(60);
            tableventas.getColumnModel().getColumn(2).setPreferredWidth(60);
            tableventas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        btnpdfventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/factura.png"))); // NOI18N
        btnpdfventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpdfventasActionPerformed(evt);
            }
        });

        txtidventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidventaActionPerformed(evt);
            }
        });

        botonGrafica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/diagrama.png"))); // NOI18N
        botonGrafica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGraficaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CalDate, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(404, 404, 404))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(505, 505, 505)
                        .addComponent(btnpdfventas, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 991, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtidventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnpdfventas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CalDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35))
        );

        jTabbedPane1.addTab("", jPanel7);

        jLabel27.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel27.setText("NOMBRE:");

        jLabel28.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel28.setText("TELEFONO:");

        jLabel29.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel29.setText("DIRECCION:");

        jLabel30.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
        jLabel30.setText("CORREO:");

        txtnombreempresa.setText("K'os del Vestuario");
        txtnombreempresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreempresaActionPerformed(evt);
            }
        });

        txttelefonoempresa.setText("4511-0999");

        txtdireccionempresa.setText("Antigua Guatemala");

        txtcorreoempresa.setText("InfoPrendas@K'OS.com.gt");
        txtcorreoempresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcorreoempresaActionPerformed(evt);
            }
        });
        txtcorreoempresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcorreoempresaKeyTyped(evt);
            }
        });

        btnactualizardatosempresa.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
        btnactualizardatosempresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/actualizar.png"))); // NOI18N
        btnactualizardatosempresa.setText("Actualizar");
        btnactualizardatosempresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizardatosempresaActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/empresa.png"))); // NOI18N

        jLabel31.setFont(new java.awt.Font("Cascadia Mono", 1, 36)); // NOI18N
        jLabel31.setText("DETALLES DE LA EMPRESA");

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/qr.png"))); // NOI18N
        jLabel20.setText("jLabel20");

        jLabel10.setFont(new java.awt.Font("Cascadia Mono", 1, 13)); // NOI18N
        jLabel10.setText("Manual de Usuario:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(121, 121, 121))
                            .addComponent(txtnombreempresa, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtcorreoempresa, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txttelefonoempresa, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtdireccionempresa, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(txtidDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel10)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(457, 457, 457)
                        .addComponent(btnactualizardatosempresa))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jLabel1)
                        .addGap(84, 84, 84)
                        .addComponent(jLabel31)))
                .addGap(47, 47, 47))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel31)
                        .addGap(59, 59, 59)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtnombreempresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttelefonoempresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtdireccionempresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcorreoempresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtidDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(btnactualizardatosempresa)
                        .addGap(78, 78, 78))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(43, 43, 43))))
        );

        jTabbedPane1.addTab("", jPanel8);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(LVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(jLabel2)
                        .addGap(413, 413, 413)
                        .addComponent(addUser)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addUser, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, 800));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnactualizardatosempresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizardatosempresaActionPerformed
        // TODO add your handling code here:

        if (!"".equals(txtidDatos.getText()) || !"".equals(txtnombreempresa.getText()) || !"".equals(txttelefonoempresa.getText()) || !"".equals(txtdireccionempresa.getText()) || !"".equals(txtcorreoempresa.getText())) {

            datos.setIdDatos(Integer.parseInt(txtidDatos.getText()));
            datos.setEmpresa(txtnombreempresa.getText());
            datos.setTelefono(Integer.parseInt(txttelefonoempresa.getText()));
            datos.setDireccion(txtdireccionempresa.getText());
            datos.setCorreo(txtcorreoempresa.getText());
            preI.updDatos(datos);
            ListarDatatos();
        }
    }//GEN-LAST:event_btnactualizardatosempresaActionPerformed

    private void txtcorreoempresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcorreoempresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcorreoempresaActionPerformed

    private void txtnombreempresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreempresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreempresaActionPerformed

    private void botonGraficaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGraficaActionPerformed
        // TODO add your handling code here:
        String frep = new SimpleDateFormat("dd/MM/yyyy").format(CalDate.getDate());
        Graficas.Grafs(frep);
    }//GEN-LAST:event_botonGraficaActionPerformed

    private void btnpdfventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpdfventasActionPerformed
        try {
            // TODO add your handling code here:
            int id = Integer.parseInt(txtidventa.getText());
            File file = new File("src/pdf/VentaN" + id + ".pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnpdfventasActionPerformed

    private void tableventasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableventasMouseClicked
        // TODO add your handling code here:
        int fila = tableventas.rowAtPoint(evt.getPoint());
        txtidventa.setText(tableventas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_tableventasMouseClicked

    private void txtpreciopreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpreciopreKeyTyped
        // TODO add your handling code here:
        events.numberKeyPress(evt);
    }//GEN-LAST:event_txtpreciopreKeyTyped

    private void btnexcelproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexcelproActionPerformed
        Reps.reporte();
    }//GEN-LAST:event_btnexcelproActionPerformed

    private void btnnuevoproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoproActionPerformed

        LimpiarPrendas();
    }//GEN-LAST:event_btnnuevoproActionPerformed

    private void btneditarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarproActionPerformed
        if ("".equals(txtidpre.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una Fila");

        } else {
            if (!"".equals(txtcodigopre) || !"".equals(txtmarcpre) || !"".equals(cbxproveedorpre) || !"".equals(txtcantpre) || !"".equals(txtpreciopre) || !"".equals(txtdescpre)) {

                pre.setCodigo(txtcodigopre.getText());
                pre.setMarca(txtmarcpre.getText());
                pre.setProveedor(cbxproveedorpre.getSelectedItem().toString());
                pre.setCantidad(Integer.parseInt(txtcantpre.getText()));
                pre.setPrecio(Integer.parseInt(txtpreciopre.getText()));
                pre.setDescripcion(txtdescpre.getText());
                pre.setIdPrenda(Integer.parseInt(txtidpre.getText()));
                preI.updPrendas(pre);
                JOptionPane.showMessageDialog(null, "Producto Modificado");
                Limpiartb();
                ListarPrendas();
                LimpiarPrendas();
            }
        }

    }//GEN-LAST:event_btneditarproActionPerformed

    private void btneliminarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarproActionPerformed
        if (!"".equals(txtidpre.getText())) {

            int pre = JOptionPane.showConfirmDialog(null, "¿Elimiar El Dato Seleccionado?");
            if (pre == 0) {

                int idCliente = Integer.parseInt(txtidpre.getText());
                preI.DeletePrendas(idCliente);
                Limpiartb();
                LimpiarPrendas();
                ListarPrendas();

            }
        }
    }//GEN-LAST:event_btneliminarproActionPerformed

    private void btnguardarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarproActionPerformed

        if (!"".equals(txtcodigopre.getText()) || !"".equals(txtmarcpre.getText()) || !"".equals(cbxproveedorpre.getSelectedItem()) || !"".equals(txtcantpre.getText())
                || !"".equals(txtpreciopre.getText()) || !"".equals(txtdescpre.getText())) {
            pre.setCodigo(txtcodigopre.getText());
            pre.setMarca(txtmarcpre.getText());
            pre.setProveedor(cbxproveedorpre.getSelectedItem().toString());
            pre.setCantidad(Integer.parseInt(txtcantpre.getText()));
            pre.setPrecio(Integer.parseInt(txtpreciopre.getText()));
            pre.setDescripcion(txtdescpre.getText());

            preI.RegistrarProducto(pre);
            JOptionPane.showMessageDialog(null, "Producto Agregado");

        } else {
            JOptionPane.showMessageDialog(null, "Campos Vacios");
        }
    }//GEN-LAST:event_btnguardarproActionPerformed

    private void tablePrendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePrendasMouseClicked
        int fila = tablePrendas.rowAtPoint(evt.getPoint());
        txtidpre.setText(tablePrendas.getValueAt(fila, 0).toString());
        txtcodigopre.setText(tablePrendas.getValueAt(fila, 1).toString());
        txtmarcpre.setText(tablePrendas.getValueAt(fila, 2).toString());
        cbxproveedorpre.setSelectedItem(tablePrendas.getValueAt(fila, 3).toString());
        txtcantpre.setText(tablePrendas.getValueAt(fila, 4).toString());
        txtpreciopre.setText(tablePrendas.getValueAt(fila, 5).toString());
        txtdescpre.setText(tablePrendas.getValueAt(fila, 6).toString());
    }//GEN-LAST:event_tablePrendasMouseClicked

    private void txtcantpreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantpreKeyTyped
        // TODO add your handling code here:
        events.numberKeyPress(evt);
    }//GEN-LAST:event_txtcantpreKeyTyped

    private void txtdescpreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescpreKeyTyped
        // TODO add your handling code here:
        events.textKeyPress(evt);
    }//GEN-LAST:event_txtdescpreKeyTyped

    private void txtmarcpreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmarcpreKeyTyped
        // TODO add your handling code here:
        events.textKeyPress(evt);
    }//GEN-LAST:event_txtmarcpreKeyTyped

    private void txtcodigopreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigopreKeyTyped
        // TODO add your handling code here:
        events.numberKeyPress(evt);
    }//GEN-LAST:event_txtcodigopreKeyTyped

    private void btnnuevoproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoproveedorActionPerformed
        // TODO add your handling code here:
        LimpiarProveedor();
    }//GEN-LAST:event_btnnuevoproveedorActionPerformed

    private void btneliminarproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarproveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtidproveedor.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "eliminar datos de proveedor");
            if (pregunta == 0) {
                int idProveedor = Integer.parseInt(txtidproveedor.getText());
                PrDao.EliminarProveedor(idProveedor);
                Limpiartb();
                ListarProveedor();
                LimpiarProveedor();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btneliminarproveedorActionPerformed

    private void txteditarproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txteditarproveedorActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtidproveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una Fila");

        } else {
            if (!"".equals(txtcodProveedor) || !"".equals(txtnombreproveedor) || !"".equals(txtcorreoproveedor) || !"".equals(txttelefonoproveedor)) {

                pr.setCodProveedor(Integer.parseInt(txtcodProveedor.getText()));
                pr.setProveedor(txtnombreproveedor.getText());
                pr.setCorreo(txtcorreoproveedor.getText());
                pr.setTelefono(txttelefonoproveedor.getText());
                pr.setIdProveedor(Integer.parseInt(txtidproveedor.getText()));
                PrDao.ModificarProveedor(pr);
                Limpiartb();
                ListarProveedor();
                LimpiarProveedor();
            }
        }
    }//GEN-LAST:event_txteditarproveedorActionPerformed

    private void btnguardarproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarproveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtcodProveedor.getText()) || !"".equals(txtnombreproveedor.getText()) || !"".equals(txtcorreoproveedor.getText()) || !"".equals(txttelefonoproveedor.getText())) {
            pr.setCodProveedor(Integer.parseInt(txtcodProveedor.getText()));
            pr.setProveedor(txtnombreproveedor.getText());
            pr.setCorreo(txtcorreoproveedor.getText());
            pr.setTelefono(txttelefonoproveedor.getText());
            PrDao.RegistrarProveedor(pr);
            Limpiartb();
            ListarProveedor();
            LimpiarProveedor();
            JOptionPane.showMessageDialog(null, "Datos agregados");

        } else {

            JOptionPane.showMessageDialog(null, "No se agrego");
        }
    }//GEN-LAST:event_btnguardarproveedorActionPerformed

    private void tableproveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableproveedorMouseClicked
        int fila = tableproveedor.rowAtPoint(evt.getPoint());
        txtidproveedor.setText(tableproveedor.getValueAt(fila, 0).toString());
        txtcodProveedor.setText(tableproveedor.getValueAt(fila, 1).toString());
        txtnombreproveedor.setText(tableproveedor.getValueAt(fila, 2).toString());
        txtcorreoproveedor.setText(tableproveedor.getValueAt(fila, 3).toString());
        txttelefonoproveedor.setText(tableproveedor.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_tableproveedorMouseClicked

    private void txttelefonoproveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoproveedorKeyTyped
        // TODO add your handling code here:
        events.numberKeyPress(evt);
    }//GEN-LAST:event_txttelefonoproveedorKeyTyped

    private void txtcorreoproveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcorreoproveedorKeyTyped
        // TODO add your handling code here:
        //events.textKeyPress(evt);
    }//GEN-LAST:event_txtcorreoproveedorKeyTyped

    private void txtnombreproveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreproveedorKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtnombreproveedorKeyTyped

    private void txtcodProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodProveedorKeyTyped
        // TODO add your handling code here:
        events.numberKeyPress(evt);
    }//GEN-LAST:event_txtcodProveedorKeyTyped

    private void btnnuevoclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoclienteActionPerformed

        LimpiarCliente();
    }//GEN-LAST:event_btnnuevoclienteActionPerformed

    private void btneliminarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarclienteActionPerformed

        if (!"".equals(txtidCliente.getText())) {

            int pre = JOptionPane.showConfirmDialog(null, "¿Elimiar El Dato Seleccionado?");
            if (pre == 0) {

                int idCliente = Integer.parseInt(txtidCliente.getText());
                clI.DeleteCliente(idCliente);
                Limpiartb();
                LimpiarCliente();
                ListarCliente();

            }
        }

    }//GEN-LAST:event_btneliminarclienteActionPerformed

    private void btneditarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarclienteActionPerformed

        if ("".equals(txtidCliente.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccionar Una Fila");

        } else {

            if (!"".equals(txtnitcliente.getText()) || !"".equals(txtnombrecliente.getText()) || !"".equals(txttelefonocliente.getText()) || !"".equals(txtdireccioncliente.getText()) || !"".equals(txtcorreocliente.getText())) {

                cli.setNit(txtnitcliente.getText());
                cli.setNombre(txtnombrecliente.getText());
                cli.setTelefono(txttelefonocliente.getText());
                cli.setDireccion(txtdireccioncliente.getText());
                cli.setCorreo(txtcorreocliente.getText());
                cli.setIdCliente(Integer.parseInt(txtidCliente.getText()));

                clI.updCliente(cli);
                JOptionPane.showMessageDialog(null, "Cliente Actualizado");
                Limpiartb();
                LimpiarCliente();
                ListarCliente();

            } else {
                JOptionPane.showMessageDialog(null, "Campos Vacios O Erroneos");
            }
        }
    }//GEN-LAST:event_btneditarclienteActionPerformed

    private void btnguardarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarclienteActionPerformed

        if (!"".equals(txtnitcliente.getText())) {

            cli.setNit(txtnitcliente.getText());
            cli.setNombre(txtnombrecliente.getText());
            cli.setTelefono(txttelefonocliente.getText());
            cli.setDireccion(txtdireccioncliente.getText());
            cli.setCorreo(txtcorreocliente.getText());

            clI.insertCliente(cli);
            JOptionPane.showMessageDialog(null, "Cliente Agregado");
            Limpiartb();
            LimpiarCliente();
            ListarCliente();

            JOptionPane.showMessageDialog(null, "Nuevo Cliente Agregado");
        } else {
            JOptionPane.showMessageDialog(null, "Datos Erroneos o Vacios");
        }
    }//GEN-LAST:event_btnguardarclienteActionPerformed

    private void tableclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableclienteMouseClicked

        int fila = tablecliente.rowAtPoint(evt.getPoint());
        txtidCliente.setText(tablecliente.getValueAt(fila, 0).toString());
        txtnitcliente.setText(tablecliente.getValueAt(fila, 1).toString());
        txtnombrecliente.setText(tablecliente.getValueAt(fila, 2).toString());
        txttelefonocliente.setText(tablecliente.getValueAt(fila, 3).toString());
        txtdireccioncliente.setText(tablecliente.getValueAt(fila, 4).toString());
        txtcorreocliente.setText(tablecliente.getValueAt(fila, 5).toString());

    }//GEN-LAST:event_tableclienteMouseClicked

    private void txtcorreoclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcorreoclienteKeyTyped
        // TODO add your handling code here:
        //events.textKeyPress(evt);
    }//GEN-LAST:event_txtcorreoclienteKeyTyped

    private void txtdireccionclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdireccionclienteKeyTyped
        // TODO add your handling code here:
        events.textKeyPress(evt);
    }//GEN-LAST:event_txtdireccionclienteKeyTyped

    private void txttelefonoclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoclienteKeyTyped
        // TODO add your handling code here:
        //events.numberKeyPress(evt);
    }//GEN-LAST:event_txttelefonoclienteKeyTyped

    private void txtnombreclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreclienteKeyTyped
        // TODO add your handling code here:
        events.textKeyPress(evt);
    }//GEN-LAST:event_txtnombreclienteKeyTyped

    private void txtnitclienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnitclienteKeyTyped
        // TODO add your handling code here:
        //.numberKeyPress(evt);
    }//GEN-LAST:event_txtnitclienteKeyTyped

    private void LabelTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LabelTotalActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_LabelTotalActionPerformed

    private void btngenerarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngenerarventaActionPerformed
        if (tablaventa.getRowCount() > 0) {
            if (!"".equals(txtnombreclienteventa.getText())) {
                RegVenta();
                RegistrarDetalle();
                ActuStock();
                pdf();
                LimpTablaVenta();
                LimpiarClVenta();

            } else {
                JOptionPane.showMessageDialog(null, "Error!! Se debe buscar un cliente");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Error!! no se han seleccionado productos en la venta");
        }
    }//GEN-LAST:event_btngenerarventaActionPerformed

    private void txtnombreclienteventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreclienteventaKeyTyped
        // TODO add your handling code here:
        events.textKeyPress(evt);
    }//GEN-LAST:event_txtnombreclienteventaKeyTyped

    private void txtnitventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnitventaKeyTyped
        // TODO add your handling code here:
        //events.numberKeyPress(evt);
    }//GEN-LAST:event_txtnitventaKeyTyped

    private void txtnitventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnitventaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtnitventa.getText())) {
                int Nit = Integer.parseInt(txtnitventa.getText());
                Cli = clI.Buscarcliente(Nit);
                if (Cli.getNombre() != null) {
                    txtnombreclienteventa.setText("" + Cli.getNombre());
                    txttelefonoclienteventa.setText("" + Cli.getTelefono());
                    txtdireccionclienteventa.setText("" + Cli.getDireccion());
                    txtcorreoclienteventa.setText("" + Cli.getCorreo());
                } else {
                    txtnitventa.setText("");
                    JOptionPane.showMessageDialog(null, "El Cliente no existe");

                }

            }
        }
    }//GEN-LAST:event_txtnitventaKeyPressed

    private void btneliminarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarventaActionPerformed
        // TODO add your handling code here:
        modelo = (DefaultTableModel) tablaventa.getModel();
        modelo.removeRow(tablaventa.getSelectedRow());
        TotalPagar();
        txtcodigoventa.requestFocus();
    }//GEN-LAST:event_btneliminarventaActionPerformed

    private void txtprecioventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtprecioventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtprecioventaActionPerformed

    private void txtdescripcionventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdescripcionventaKeyTyped
        // TODO add your handling code here:
        events.textKeyPress(evt);
    }//GEN-LAST:event_txtdescripcionventaKeyTyped

    private void txtcantidadventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidadventaKeyTyped
        // TODO add your handling code here:
        events.numberKeyPress(evt);
    }//GEN-LAST:event_txtcantidadventaKeyTyped

    private void txtcantidadventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidadventaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtcantidadventa.getText())) {
                String codigo = txtcodigoventa.getText();
                String descripcion = txtdescripcionventa.getText();
                int cant = Integer.parseInt(txtcantidadventa.getText());
                int precio = Integer.parseInt(txtprecioventa.getText());
                int total = cant * precio;
                int cantidad = Integer.parseInt(txtcantidad.getText());
                if (cantidad >= cant) {
                    item = item + 1;
                    newmodelo = (DefaultTableModel) tablaventa.getModel();
                    for (int i = 0; i < tablaventa.getRowCount(); i++) {
                        if (tablaventa.getValueAt(i, 1).equals(txtdescripcionventa.getText())) {
                            JOptionPane.showMessageDialog(null, "El Producto  ya esta registrado");
                            return;
                        }
                    }
                    ArrayList Lista = new ArrayList();
                    Lista.add(item);
                    Lista.add(codigo);
                    Lista.add(descripcion);
                    Lista.add(cant);
                    Lista.add(precio);
                    Lista.add(total);
                    Object[] O = new Object[5];
                    O[0] = Lista.get(1);
                    O[1] = Lista.get(2);
                    O[2] = Lista.get(3);
                    O[3] = Lista.get(4);
                    O[4] = Lista.get(5);
                    newmodelo.addRow(O);
                    tablaventa.setModel(newmodelo);
                    TotalPagar();
                    LimpiarVenta();
                    txtcodigoventa.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Cantidad no disponible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese cantidad");

            }
        }
    }//GEN-LAST:event_txtcantidadventaKeyPressed

    private void txtcantidadventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcantidadventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcantidadventaActionPerformed

    private void txtcodigoventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigoventaKeyTyped
        // TODO add your handling code here:
        events.numberKeyPress(evt);
    }//GEN-LAST:event_txtcodigoventaKeyTyped

    private void txtcodigoventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigoventaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtcodigoventa.getText())) {
                String cod = txtcodigoventa.getText();
                pre = preI.BuscarPre(cod);
                if (pre.getMarca() != null) {
                    txtdescripcionventa.setText("" + pre.getMarca());
                    txtprecioventa.setText("" + pre.getPrecio());
                    txtcantidad.setText("" + pre.getCantidad());
                    txtcantidadventa.requestFocus();
                } else {
                    LimpiarVenta();
                    txtcodigoventa.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto");
                txtcodigoventa.requestFocus();
            }
        }
    }//GEN-LAST:event_txtcodigoventaKeyPressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
        LimpTablaVenta();
        ListarVentas();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnPrendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrendasActionPerformed

        Limpiartb();
        ListarPrendas();
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_btnPrendasActionPerformed

    private void btnPrendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrendasMouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrendasMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        Limpiartb();
        ListarProveedor();
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Limpiartb();
        ListarCliente();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void addUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserActionPerformed

        Usuarios user = new Usuarios();
        user.setVisible(true);
    }//GEN-LAST:event_addUserActionPerformed

    private void txtidventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidventaActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtcorreoempresaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcorreoempresaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcorreoempresaKeyTyped

    private void txtnitclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnitclienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtnombrecliente.requestFocusInWindow(); // Cambiar el enfoque al siguiente campo de texto
        }
    }//GEN-LAST:event_txtnitclienteKeyPressed

    private void txtnombreclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreclienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txttelefonocliente.requestFocusInWindow(); // Cambiar el enfoque al siguiente campo de texto
        }
    }//GEN-LAST:event_txtnombreclienteKeyPressed

    private void txtcorreoclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcorreoclienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtdireccioncliente.requestFocusInWindow(); // Cambiar el enfoque al siguiente campo de texto
        }
    }//GEN-LAST:event_txtcorreoclienteKeyPressed

    private void txtcodProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodProveedorKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtnombreproveedor.requestFocusInWindow(); // Cambiar el enfoque al siguiente campo de texto
        }

    }//GEN-LAST:event_txtcodProveedorKeyPressed

    private void txtnombreproveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreproveedorKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtcorreoproveedor.requestFocusInWindow(); // Cambiar el enfoque al siguiente campo de texto
        }
    }//GEN-LAST:event_txtnombreproveedorKeyPressed

    private void txtcodigopreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigopreKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtmarcpre.requestFocusInWindow(); // Cambiar el enfoque al siguiente campo de texto
        }
    }//GEN-LAST:event_txtcodigopreKeyPressed

    private void txtmarcpreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmarcpreKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cbxproveedorpre.requestFocusInWindow(); // Cambiar el enfoque al siguiente campo de texto
        }
    }//GEN-LAST:event_txtmarcpreKeyPressed

    private void txtcantpreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantpreKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtpreciopre.requestFocusInWindow(); // Cambiar el enfoque al siguiente campo de texto
        }
    }//GEN-LAST:event_txtcantpreKeyPressed

    private void txtpreciopreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpreciopreKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtdescpre.requestFocusInWindow(); // Cambiar el enfoque al siguiente campo de texto
        }
    }//GEN-LAST:event_txtpreciopreKeyPressed

    private void txtcantpreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcantpreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcantpreActionPerformed

    private void txttelefonoclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoclienteKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtcorreocliente.requestFocusInWindow(); // Cambiar el enfoque al siguiente campo de texto
        }
        
        
    }//GEN-LAST:event_txttelefonoclienteKeyPressed

    private void txtcorreoproveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcorreoproveedorKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txttelefonoproveedor.requestFocusInWindow(); // Cambiar el enfoque al siguiente campo de texto
        }
        
    }//GEN-LAST:event_txtcorreoproveedorKeyPressed

    private void txttelefonoproveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelefonoproveedorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttelefonoproveedorKeyPressed

    private void cbxproveedorpreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxproveedorpreKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtcantpre.requestFocusInWindow(); // Cambiar el enfoque al siguiente campo de texto
        }
    }//GEN-LAST:event_cbxproveedorpreKeyPressed

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
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser CalDate;
    private javax.swing.JLabel LVendedor;
    private javax.swing.JLabel Label10;
    private javax.swing.JTextField LabelTotal;
    private javax.swing.JButton addUser;
    private javax.swing.JButton botonGrafica;
    private javax.swing.JButton btnPrendas;
    private javax.swing.JButton btnactualizardatosempresa;
    private javax.swing.JButton btneditarcliente;
    private javax.swing.JButton btneditarpro;
    private javax.swing.JButton btneliminarcliente;
    private javax.swing.JButton btneliminarpro;
    private javax.swing.JButton btneliminarproveedor;
    private javax.swing.JButton btneliminarventa;
    private javax.swing.JButton btnexcelpro;
    private javax.swing.JButton btngenerarventa;
    private javax.swing.JButton btnguardarcliente;
    private javax.swing.JButton btnguardarpro;
    private javax.swing.JButton btnguardarproveedor;
    private javax.swing.JButton btnnuevocliente;
    private javax.swing.JButton btnnuevopro;
    private javax.swing.JButton btnnuevoproveedor;
    private javax.swing.JButton btnpdfventas;
    private javax.swing.JComboBox<String> cbxproveedorpre;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tablaventa;
    private javax.swing.JTable tablePrendas;
    private javax.swing.JTable tablecliente;
    private javax.swing.JTable tableproveedor;
    private javax.swing.JTable tableventas;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtcantidadventa;
    private javax.swing.JTextField txtcantpre;
    private javax.swing.JTextField txtcodProveedor;
    private javax.swing.JTextField txtcodigopre;
    private javax.swing.JTextField txtcodigoventa;
    private javax.swing.JTextField txtcorreocliente;
    private javax.swing.JTextField txtcorreoclienteventa;
    private javax.swing.JTextField txtcorreoempresa;
    private javax.swing.JTextField txtcorreoproveedor;
    private javax.swing.JTextField txtdescpre;
    private javax.swing.JTextField txtdescripcionventa;
    private javax.swing.JTextField txtdireccioncliente;
    private javax.swing.JTextField txtdireccionclienteventa;
    private javax.swing.JTextField txtdireccionempresa;
    private javax.swing.JButton txteditarproveedor;
    private javax.swing.JTextField txtidCliente;
    private javax.swing.JTextField txtidDatos;
    private javax.swing.JTextField txtidpre;
    private javax.swing.JTextField txtidproveedor;
    private javax.swing.JTextField txtidventa;
    private javax.swing.JTextField txtmarcpre;
    private javax.swing.JTextField txtnitcliente;
    private javax.swing.JTextField txtnitventa;
    private javax.swing.JTextField txtnombrecliente;
    private javax.swing.JTextField txtnombreclienteventa;
    private javax.swing.JTextField txtnombreempresa;
    private javax.swing.JTextField txtnombreproveedor;
    private javax.swing.JTextField txtpreciopre;
    private javax.swing.JTextField txtprecioventa;
    private javax.swing.JTextField txttelefonocliente;
    private javax.swing.JTextField txttelefonoclienteventa;
    private javax.swing.JTextField txttelefonoempresa;
    private javax.swing.JTextField txttelefonoproveedor;
    // End of variables declaration//GEN-END:variables

    private void LimpiarCliente() {
        txtidCliente.setText("");
        txtnitcliente.setText("");
        txtnombrecliente.setText("");
        txttelefonocliente.setText("");
        txtdireccioncliente.setText("");
        txtcorreocliente.setText("");
    }

    private void LimpiarProveedor() {
        txtidproveedor.setText("");
        txtcodProveedor.setText("");
        txtnombreproveedor.setText("");
        txtcorreoproveedor.setText("");
        txttelefonoproveedor.setText("");
    }

    private void LimpiarPrendas() {
        txtidpre.setText("");
        txtcodigopre.setText("");
        txtmarcpre.setText("");
        cbxproveedorpre.setSelectedItem(null);
        txtcantpre.setText("");
        txtpreciopre.setText("");
        txtdescpre.setText("");
    }

    private void TotalPagar() {
        Totalpagar = 0.00;
        int numfila = tablaventa.getRowCount();
        for (int i = 0; i < numfila; i++) {
            double cal = Double.parseDouble(String.valueOf(tablaventa.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar + cal;

        }
        LabelTotal.setText(String.format("%.2f", Totalpagar));
    }

    private void LimpiarVenta() {
        txtcodigoventa.setText("");
        txtdescripcionventa.setText("");
        txtcantidadventa.setText("");
        txtcantidad.setText("");
        txtprecioventa.setText("");
        txtidventa.setText("");
    }

    private void RegVenta() {
        String cliente = txtnombreclienteventa.getText();
        String vendedor = LVendedor.getText();
        double total = Totalpagar;
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setTotal(total);
        v.setFecha(fechactualsis);
        vn.RegVenta(v);
    }

    private void RegistrarDetalle() {
        int idV = vn.IdDeVenta();

        for (int i = 0; i < tablaventa.getRowCount(); i++) {
            int cod = Integer.parseInt(tablaventa.getValueAt(i, 0).toString());
            int can = Integer.parseInt(tablaventa.getValueAt(i, 2).toString());
            double pre = Double.parseDouble(tablaventa.getValueAt(i, 3).toString());

            dv.setCodPrenda(cod);
            dv.setCantidad(can);
            dv.setPrecio(pre);
            dv.setIdDetalle_venta(idV);
            vn.RegDetalleVenta(dv);

        }

    }

    private void ActuStock() {

        for (int i = 0; i < tablaventa.getRowCount(); i++) {
            String cod = tablaventa.getValueAt(i, 0).toString();
            int can = Integer.parseInt(tablaventa.getValueAt(i, 2).toString());

            pre = preI.BuscarPre(cod);
            int ActualStock = pre.getCantidad() - can;
            vn.updateStock(ActualStock, cod);
        }
    }

    private void LimpTablaVenta() {
        newmodelo = (DefaultTableModel) tablaventa.getModel();
        int fila = tablaventa.getRowCount();

        for (int i = 0; i < fila; i++) {
            newmodelo.removeRow(0);
        }
    }

    private void LimpiarClVenta() {
        txtnitventa.setText("");
        txtnombreclienteventa.setText("");
        txttelefonoclienteventa.setText("");
        txtdireccionclienteventa.setText("");
        txtcorreoclienteventa.setText("");
    }

    private void pdf() {
        try {

            int id = vn.IdDeVenta();

            FileOutputStream archivo;
            File file = new File("src/pdf/VentaN" + id + ".pdf");

            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img = Image.getInstance("src/iconos/kozgrande.png");

            Paragraph fech = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
            fech.add(Chunk.NEWLINE);
            Date date = new Date();
            fech.add("Factura:" + id + " \n" + "FECHA Y HORA: \n " + new SimpleDateFormat("dd-MM-yyyy - hh-mm-ss").format(date) + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(img);

            String nom = txtnombreempresa.getText();
            String telef = txttelefonoempresa.getText();
            String dir = txtdireccionempresa.getText();
            String corr = txtcorreoempresa.getText();

            Encabezado.addCell("");
            Encabezado.addCell("nombre: " + nom + "\ntelefono: " + telef + "\ndireccion: " + dir + "\ncorreo: " + corr);
            Encabezado.addCell(fech);
            doc.add(Encabezado);

            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("** - - - - - - - - - - - - - - - - - - - - - - - - - -DATOS DEL CLIENTE- - - - - - - - - - - - - - - - - - - - - - - - - - **" + "\n\n");
            doc.add(cli);

            PdfPTable tablacli = new PdfPTable(5);
            tablacli.setWidthPercentage(100);
            tablacli.getDefaultCell().setBorder(0);
            float[] Columnacli = new float[]{20f, 40f, 35f, 50f, 40f};
            tablacli.setWidths(Columnacli);
            tablacli.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cl1 = new PdfPCell(new Phrase("NIT", negrita));
            PdfPCell cl2 = new PdfPCell(new Phrase("NOMBRE", negrita));
            PdfPCell cl3 = new PdfPCell(new Phrase("TELÉFONO", negrita));
            PdfPCell cl4 = new PdfPCell(new Phrase("DIRECCIÓN", negrita));
            PdfPCell cl5 = new PdfPCell(new Phrase("CORREO", negrita));

            cl1.setBorder(0);
            cl2.setBorder(0);
            cl3.setBorder(0);
            cl4.setBorder(0);
            cl5.setBorder(0);

            tablacli.addCell(cl1);
            tablacli.addCell(cl2);
            tablacli.addCell(cl3);
            tablacli.addCell(cl4);
            tablacli.addCell(cl5);
            tablacli.addCell(txtnitventa.getText());
            tablacli.addCell(txtnombreclienteventa.getText());
            tablacli.addCell(txttelefonoclienteventa.getText());
            tablacli.addCell(txtdireccionclienteventa.getText());
            tablacli.addCell(txtcorreoclienteventa.getText());
            doc.add(tablacli);

            //TABLA PRENDAS
            PdfPTable tablapre = new PdfPTable(4);
            tablapre.setWidthPercentage(100);
            tablapre.getDefaultCell().setBorder(0);
            float[] Columnapre = new float[]{25f, 50f, 15f, 20f};
            tablapre.setWidths(Columnapre);
            tablapre.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell pre1 = new PdfPCell(new Phrase("CANTIDAD", negrita));
            PdfPCell pre2 = new PdfPCell(new Phrase("DESCRIPCION", negrita));
            PdfPCell pre3 = new PdfPCell(new Phrase("PRECIO", negrita));
            PdfPCell pre4 = new PdfPCell(new Phrase("TOTAL", negrita));

            pre1.setBorder(0);
            pre2.setBorder(0);
            pre3.setBorder(0);
            pre4.setBorder(0);
            pre1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            pre2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            pre3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            pre4.setBackgroundColor(BaseColor.LIGHT_GRAY);

            tablapre.addCell(pre1);
            tablapre.addCell(pre2);
            tablapre.addCell(pre3);
            tablapre.addCell(pre4);

            Paragraph espacio = new Paragraph();
            espacio.add(Chunk.NEWLINE);
            espacio.add("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
            espacio.add(" * * * DETALLE DE COMPRA * * * ");
            espacio.setAlignment(Element.ALIGN_CENTER);
            doc.add(espacio);

            for (int i = 0; i < tablaventa.getRowCount(); i++) {
                String Descripcion = tablaventa.getValueAt(i, 1).toString();
                String Cantidad = tablaventa.getValueAt(i, 2).toString();
                String Precio = tablaventa.getValueAt(i, 3).toString();
                String Total = tablaventa.getValueAt(i, 4).toString();
                System.out.println("Descripción: " + Descripcion + ", Cantidad: " + Cantidad + ", Precio: " + Precio + ", Total: " + Total);

                tablapre.addCell(Cantidad);
                tablapre.addCell(Descripcion);
                tablapre.addCell(Precio);
                tablapre.addCell(Total);

            }
            doc.add(tablapre);

            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total A Pagar: Q" + LabelTotal.getText());
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("---------------------------\n");
            firma.add(" * * * ULTIMA LINEA * * * ");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            Paragraph frase = new Paragraph();
            frase.add(Chunk.NEWLINE);
            frase.add("GRACIAS POR SU COMPRA, VUELVA PRONTO :D\n\n");
            frase.setAlignment(Element.ALIGN_CENTER);
            doc.add(frase);
            doc.close();
            archivo.close();
            //ABRIR ARCHIVO
            Desktop.getDesktop().open(file);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /*public class Fondo extends JPanel {
    
        @Override
        public void paint(Graphics g){
        ImageIcon imagen = new ImageIcon(getClass().getResource("iconos/6445.jpg"));
        g.drawImage(imagen.getImage(),0,0, getWidth(), getHeight(), this);
        
        setOpaque(false);
        super.paint(g);
        }
    }*/
}
