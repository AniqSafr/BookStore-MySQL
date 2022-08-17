import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BookShop {

	private JFrame frame;
	private JTextField txtBookName;
	private JTextField txtBookEdition;
	private JTextField txtBookPrice;
	private JTable table;
	private JTextField textBookID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookShop window = new BookShop();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookShop() {
		initialize();
		connect();
		table_load();
	}

	Connection on;
	PreparedStatement pst;
	ResultSet rs;
	
	
	public void connect () {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			on = DriverManager.getConnection("jdbc:mysql://localhost/book store","root","");
		}
		catch(ClassNotFoundException ex) {
			
		}
		
		catch (SQLException ex) {
			
		}
	}
	
	public void table_load() {
		
		try {
			pst = on.prepareStatement("select * from book");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException ex) {
			
		}
	}
	
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 660, 447);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(229, 11, 172, 66);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 73, 299, 196);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(25, 40, 101, 17);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(25, 90, 101, 17);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(25, 140, 60, 17);
		panel.add(lblNewLabel_1_2);
		
		txtBookName = new JTextField();
		txtBookName.setBounds(130, 40, 148, 20);
		panel.add(txtBookName);
		txtBookName.setColumns(10);
		
		txtBookEdition = new JTextField();
		txtBookEdition.setColumns(10);
		txtBookEdition.setBounds(130, 90, 148, 20);
		panel.add(txtBookEdition);
		
		txtBookPrice = new JTextField();
		txtBookPrice.setColumns(10);
		txtBookPrice.setBounds(130, 140, 148, 20);
		panel.add(txtBookPrice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bName, edition, price;
				
				bName = txtBookName.getText();
				edition = txtBookEdition.getText();
				price = txtBookPrice.getText();
				
				try {
					pst = on.prepareStatement("insert into book (name,edition,price)values(?,?,?)");
					pst.setString(1, bName);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record added!!");
					
					table_load(); 
					txtBookName.setText("");
					txtBookEdition.setText("");
					txtBookPrice.setText("");
					txtBookName.requestFocus();
					
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				} 
					
			}
		});
		btnNewButton.setBounds(18, 280, 86, 40);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		btnExit.setBounds(114, 280, 86, 40);
		frame.getContentPane().add(btnExit);
		
		JButton btnE = new JButton("Clear");
		btnE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtBookName.setText("");
				txtBookEdition.setText("");
				txtBookPrice.setText("");
				txtBookName.requestFocus();
				
			}
		});
		btnE.setBounds(210, 280, 86, 40);
		frame.getContentPane().add(btnE);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(314, 76, 320, 243);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 332, 299, 65);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Book ID");
		lblNewLabel_1_1_1.setBounds(10, 21, 70, 17);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblNewLabel_1_1_1);
		
		textBookID = new JTextField();
		textBookID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					
					String id = textBookID.getText();
					pst = on.prepareStatement("select name, edition, price from book where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next() == true) {
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						
						txtBookName.setText(name);
						txtBookEdition.setText(edition);
						txtBookPrice.setText(price);
					}
					else {
						txtBookName.setText("");
						txtBookEdition.setText("");
						txtBookPrice.setText("");
					}
				}
				catch(SQLException e2) {
					
				}
				
				
				
			}
		});
		textBookID.setBounds(120, 21, 144, 20);
		textBookID.setColumns(10);
		panel_1.add(textBookID);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bName, edition, price, bookID;
				
				bName = txtBookName.getText();
				edition = txtBookEdition.getText();
				price = txtBookPrice.getText();
				bookID = textBookID.getText();
				
				try {
					pst = on.prepareStatement("update book set name =?, edition =?, price =? where id =?");
					pst.setString(1, bName);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, bookID);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record Update!!");
					
					table_load(); 
					txtBookName.setText("");
					txtBookEdition.setText("");
					txtBookPrice.setText("");
					txtBookName.requestFocus();
					
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				} 
				
			}
		});
		btnUpdate.setBounds(376, 342, 86, 40);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelate = new JButton("Delate");
		btnDelate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bookID;

				bookID = textBookID.getText();
				
				try {
					pst = on.prepareStatement("delete from book where id =?");
					pst.setString(1, bookID);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record Delete!!");
					
					table_load(); 
					txtBookName.setText("");
					txtBookEdition.setText("");
					txtBookPrice.setText("");
					txtBookName.requestFocus();
					
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				} 
			}
		});
		btnDelate.setBounds(493, 342, 86, 40);
		frame.getContentPane().add(btnDelate);
	}
}
