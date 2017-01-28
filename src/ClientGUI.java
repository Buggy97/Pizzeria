import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

public class ClientGUI {

	private JFrame frame;
	private JTable Prodotti;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JTable Carrello;
	private DefaultTableModel modCarrello;
	private DefaultTableModel modProdotti;
	private JTextField textField;
	private ClientCore core;
	private ArrayList<Pizza> toSend;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
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
	public ClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.toSend = new ArrayList<Pizza>();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 754, 353);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 160, 290);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 160, 290);
		panel.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(470, 0, 268, 290);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 268, 290);
		panel_1.add(scrollPane_1);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(170, 7, 46, 14);
		frame.getContentPane().add(lblNome);
		
		JLabel lblNome_1 = new JLabel("");
		lblNome_1.setBounds(211, 7, 128, 14);
		frame.getContentPane().add(lblNome_1);
		
		JLabel lblPrezzo = new JLabel("");
		lblPrezzo.setBounds(408, 7, 65, 14);
		frame.getContentPane().add(lblPrezzo);
		
		JLabel lblPrezzo_1 = new JLabel("Prezzo:");
		lblPrezzo_1.setBounds(360, 7, 46, 14);
		frame.getContentPane().add(lblPrezzo_1);
		
		JLabel lblDescrizione = new JLabel("Ingredienti:");
		lblDescrizione.setBounds(170, 32, 77, 14);
		frame.getContentPane().add(lblDescrizione);
		
		JLabel lblNote = new JLabel("Note:");
		lblNote.setBounds(170, 128, 46, 14);
		frame.getContentPane().add(lblNote);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(170, 57, 293, 60);
		frame.getContentPane().add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(170, 153, 293, 60);
		frame.getContentPane().add(textPane_1);
		
		JLabel lblTotale_1 = new JLabel("0.00$");
		lblTotale_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTotale_1.setBounds(223, 270, 58, 14);
		frame.getContentPane().add(lblTotale_1);
		
		JButton btnRimuovi = new JButton("Rimuovi");
		btnRimuovi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					if (Carrello.getSelectedRow()<0)
						return;
					lblTotale_1.setText((Float.parseFloat(lblTotale_1.getText().substring(0,lblTotale_1.getText().length()-1)) - Float.parseFloat(toSend.get(Carrello.getSelectedRow()).getPrezzo()))+"$");
					int selRow = new Integer(Carrello.getSelectedRow());
					toSend.remove(Carrello.getSelectedRow());
					modCarrello.removeRow(Carrello.getSelectedRow());
					if (selRow > Carrello.getRowCount())
						selRow--;
					if (Carrello.getRowCount()!=0 && selRow < Carrello.getRowCount())
						Carrello.setRowSelectionInterval(selRow, selRow);
					textPane_1.setText("");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
			}
		});
		btnRimuovi.setBounds(269, 224, 89, 23);
		frame.getContentPane().add(btnRimuovi);
		
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Pizza currentPizza = core.getPizze().get(Prodotti.getSelectedRow()).clone();
				currentPizza.setNote(textPane_1.getText());
				try
				{
					int pizzaCount = Integer.parseInt(textField.getText());
					for (int i = 0; i < pizzaCount; i++)
					{
						toSend.add(currentPizza);
						modCarrello.addRow(new String[]{currentPizza.getNome(),currentPizza.getPrezzo()});
					}
				}
				catch (Exception e)
				{
					toSend.add(currentPizza);
					modCarrello.addRow(new String[]{currentPizza.getNome(),currentPizza.getPrezzo()});
				}
				try
				{
					lblTotale_1.setText((Float.parseFloat(lblTotale_1.getText().substring(0,lblTotale_1.getText().length()-1)) + Float.parseFloat(currentPizza.getPrezzo()))+"$");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		btnAggiungi.setBounds(170, 224, 89, 23);
		frame.getContentPane().add(btnAggiungi);
		
		textField = new JTextField();
		textField.setBounds(376, 225, 86, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblTotale = new JLabel("Totale");
		lblTotale.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTotale.setBounds(170, 271, 46, 14);
		frame.getContentPane().add(lblTotale);
		
		JButton btnAcquista = new JButton("Acquista");
		btnAcquista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if (toSend.size()!=0)
				{
					core.send(toSend);
					int tmp = modCarrello.getRowCount();
					for (int i = 0; i < tmp; i++)
						modCarrello.removeRow(0);
					lblTotale_1.setText("0.00");
					toSend.clear();
				}
			}
		});
		btnAcquista.setBounds(373, 267, 89, 23);
		frame.getContentPane().add(btnAcquista);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmConnetti = new JMenuItem("Connetti");
		
		Carrello = new JTable();
		Carrello.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				Carrello.setSelectionBackground(Color.lightGray);
				Prodotti.setSelectionBackground(Color.white);
				btnAggiungi.setEnabled(false);
				btnRimuovi.setEnabled(true);
				Pizza currentPizza = toSend.get(Carrello.getSelectedRow());
				lblNome_1.setText(currentPizza.getNome());
				lblPrezzo.setText(currentPizza.getPrezzo());
				textPane.setText(currentPizza.getIngredienti());
				textPane_1.setText(currentPizza.getNote());
			}
		});
		scrollPane_1.setViewportView(Carrello);
		modCarrello = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Nome", "Prezzo"
				}
			);
		Carrello.setModel(modCarrello);
		
		
		Prodotti = new JTable();
		Prodotti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				Prodotti.setSelectionBackground(Color.lightGray);
				Carrello.setSelectionBackground(Color.white);
				btnAggiungi.setEnabled(true);
				btnRimuovi.setEnabled(false);
				Pizza currentPizza = core.getPizze().get(Prodotti.getSelectedRow());
				lblNome_1.setText(currentPizza.getNome());
				lblPrezzo.setText(currentPizza.getPrezzo());
				textPane.setText(currentPizza.getIngredienti());
				textPane_1.setText(currentPizza.getNote());
			}
		});
		scrollPane.setViewportView(Prodotti);
		modProdotti = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Nome"
				}
			);
		Prodotti.setModel(modProdotti);
		ClientGUI thisClassInstance = this;
		mntmConnetti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				  JTextField textNome;
				  JTextField textCognome;
				  JTextField textTelefono;
				  JTextField txtVia;
				  JTextField textIP;
				  JTextField textPort;
				  
				  JPanel panel = new JPanel();
				  panel.setBounds(10, 11, 336, 187);
				  frame.getContentPane().add(panel);
				  panel.setLayout(null);
				  
				  JLabel lblNome = new JLabel("Nome");
				  lblNome.setBounds(10, 11, 56, 14);
				  panel.add(lblNome);
				  
				  textNome = new JTextField();
				  textNome.setBounds(10, 36, 86, 20);
				  panel.add(textNome);
				  textNome.setColumns(10);
					
				  JLabel lblCognome = new JLabel("Cognome");
				  lblCognome.setBounds(120, 11, 56, 14);
				  panel.add(lblCognome);
					
				  textCognome = new JTextField();
				  textCognome.setBounds(120, 36, 86, 20);
				  panel.add(textCognome);
				  textCognome.setColumns(10);
				  
				  textTelefono = new JTextField();
				  textTelefono.setBounds(231, 36, 86, 20);
				  panel.add(textTelefono);
				  textTelefono.setColumns(10);
					
				  JLabel lblTelefono = new JLabel("Telefono");
				  lblTelefono.setBounds(231, 11, 56, 14);
				  panel.add(lblTelefono);
					
				  JLabel lblIndirizzo = new JLabel("Indirizzo");
				  lblIndirizzo.setBounds(10, 67, 66, 14);
				  panel.add(lblIndirizzo);
					
				  txtVia = new JTextField();
				  txtVia.setBounds(10, 92, 307, 20);
				  panel.add(txtVia);
				  txtVia.setColumns(10);
					
				  JLabel lblIndirizzoIp = new JLabel("Indirizzo IP");
				  lblIndirizzoIp.setBounds(10, 123, 86, 14);
				  panel.add(lblIndirizzoIp);
					
				  JLabel lblPorta = new JLabel("Porta");
				  lblPorta.setBounds(120, 123, 46, 14);
				  panel.add(lblPorta);
					
				  textIP = new JTextField();
				  textIP.setBounds(10, 148, 86, 20);
				  panel.add(textIP);
				  textIP.setColumns(10);
				  
				  textPort = new JTextField();
				  textPort.setBounds(120, 148, 86, 20);
				  panel.add(textPort);
				  textPort.setColumns(10);
					
				  panel.setSize(new Dimension(330, 200));
				  panel.setPreferredSize(new Dimension(330, 200));
	
			      int result = JOptionPane.showConfirmDialog(null, panel, "Inserisci IP e Porta", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) 
			      {
			    	 try
			    	 {
			    		 Cliente new_client = new Cliente(textNome.getText(), textCognome.getText(), txtVia.getText(), Integer.parseInt(textTelefono.getText()));
				    	 core = new ClientCore(textIP.getText(), Integer.parseInt(textPort.getText()), new_client, thisClassInstance);
				    	 JOptionPane.showMessageDialog(new JPanel(), "Connesso!", "Info", JOptionPane.INFORMATION_MESSAGE);
			    		 System.out.println(textIP.getText());
			    	 }
			    	 catch (Exception e)
			    	 {
			    	 }
			      }
			      else
			      {
			      }
				
			}
		});
		mnMenu.add(mntmConnetti);
			
	}

	public DefaultTableModel getModCarrello()
	{
		return modCarrello;
	}

	public DefaultTableModel getModProdotti()
	{
		return modProdotti;
	}

	public JFrame getFrame()
	{
		return frame;
	}

	public ArrayList<Pizza> getToSend()
	{
		return toSend;
	}

	public void setToSend(ArrayList<Pizza> toSend)
	{
		this.toSend = toSend;
	}

	public void setFrame(JFrame frame)
	{
		this.frame = frame;
	}

	public void setModCarrello(DefaultTableModel modCarrello)
	{
		this.modCarrello = modCarrello;
	}

	public void setModProdotti(DefaultTableModel modProdotti)
	{
		this.modProdotti = modProdotti;
	}
}
