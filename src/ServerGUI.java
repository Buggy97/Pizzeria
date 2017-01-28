import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
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
import java.awt.Font;

public class ServerGUI {

	private JFrame frame;
	private JTable Carrello;
	private ServerCore core;
	private JTable Clienti;
	private DefaultTableModel modClients;
	private DefaultTableModel modOrders;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI window = new ServerGUI();
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
	public ServerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 754, 353);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblDescrizione = new JLabel("Ingredienti");
		lblDescrizione.setBounds(186, 47, 77, 14);
		frame.getContentPane().add(lblDescrizione);
		
		JLabel lblNote = new JLabel("Note");
		lblNote.setBounds(186, 148, 46, 14);
		frame.getContentPane().add(lblNote);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(186, 72, 293, 61);
		
		frame.getContentPane().add(textPane);
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(186, 173, 293, 75);
		frame.getContentPane().add(textPane_1);
		JLabel label_1 = new JLabel("0.00$");
		
		JButton btnRimuovi = new JButton("Rimuovi");
		btnRimuovi.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				synchronized(core.getOrder(core.getClients().get(Clienti.getSelectedRow())))
				{
					if (Carrello.getSelectedRow()<0)
						return;
					ArrayList<Pizza> clientOrder = core.getOrder(core.getClients().get(Clienti.getSelectedRow()));
					label_1.setText((Float.parseFloat(label_1.getText().substring(0,label_1.getText().length()-1)) - Float.parseFloat(clientOrder.get(Carrello.getSelectedRow()).getPrezzo()))+"$");
					clientOrder.remove(Carrello.getSelectedRow());
					modOrders.removeRow(Carrello.getSelectedRow());
					while (modOrders.getRowCount()!=0)
						modOrders.removeRow(0);
					for (Pizza p : clientOrder)
					{
						modOrders.addRow(new String[]{p.getNome(), p.getPrezzo()});
					}
					if (modOrders.getRowCount()!=0)
					{
						Carrello.setRowSelectionInterval(0, 0);
					}
					textPane.setText("");
					textPane_1.setText("");
				}
			}
		});
		btnRimuovi.setBounds(390, 259, 89, 23);
		frame.getContentPane().add(btnRimuovi);
		
		JPanel panel = new JPanel();
		panel.setBounds(489, 0, 245, 293);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 245, 293);
		panel.add(scrollPane_1);
		
		modOrders = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Nome", "Prezzo"
				}
				);
		
		Carrello = new JTable();
		scrollPane_1.setViewportView(Carrello);
		Carrello.setModel(modOrders);
		
		JLabel lblTelefonoCliente = new JLabel("");
		lblTelefonoCliente.setBounds(252, 22, 118, 14);
		frame.getContentPane().add(lblTelefonoCliente);
		
		JLabel lblIndirizzo2 = new JLabel("Indirizzo");
		lblIndirizzo2.setBounds(186, 8, 64, 14);
		frame.getContentPane().add(lblIndirizzo2);
		
		JLabel lblIndirizzo = new JLabel("");
		lblIndirizzo.setBounds(252, 8, 227, 14);
		frame.getContentPane().add(lblIndirizzo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 176, 293);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(Clienti);
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				btnRimuovi.setEnabled(false);
			}
		});
		scrollPane.setBounds(0, 0, 176, 293);
		panel_1.add(scrollPane);
		
		modClients = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Cliente"
				}
			);
		
		Clienti = new JTable();
		scrollPane.setViewportView(Clienti);
		Clienti.setModel(modClients);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(186, 22, 64, 14);
		frame.getContentPane().add(lblTelefono);
		
		JLabel label = new JLabel("Totale");
		label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label.setBounds(186, 265, 46, 14);
		frame.getContentPane().add(label);
		
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_1.setBounds(239, 265, 58, 14);
		frame.getContentPane().add(label_1);
		Clienti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				synchronized (core.getOrder(core.getClients().get(Clienti.getSelectedRow())))
				{
					Clienti.setSelectionBackground(Color.lightGray);
					ArrayList<Pizza> toDisplay = core.getOrder(core.getClients().get(Clienti.getSelectedRow()));
					Cliente tmp = new Cliente(core.getClients().get(Clienti.getSelectedRow()));
					lblTelefonoCliente.setText(tmp.getNumber()+"");
					lblIndirizzo.setText(tmp.getIndirizzo());
					if (toDisplay != null && toDisplay.size()!=0)
					{
						Pizza firstPizza = toDisplay.get(0);
						textPane.setText(firstPizza.getIngredienti());
						textPane_1.setText(firstPizza.getNote());
						while (modOrders.getRowCount()!=0)
							modOrders.removeRow(0);
						for (Pizza p : toDisplay)
						{
							modOrders.addRow(new String[]{p.getNome(), p.getPrezzo()});
						}
					}
				}
			}
		});
		
		Carrello.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				btnRimuovi.setEnabled(true);
				Pizza toDisplay = core.getOrder(core.getClients().get(Clienti.getSelectedRow())).get(Carrello.getSelectedRow());
				System.out.println(Carrello.getSelectedRow());
				textPane.setText(toDisplay.getIngredienti());
				textPane_1.setText(toDisplay.getNote());
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmConnetti = new JMenuItem("Avvia");
		mntmConnetti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				  JTextField addressField = new JTextField(5);
			      JTextField portField = new JTextField(5);
	
			      JPanel myPanel = new JPanel();
			      myPanel.add(new JLabel("IP:"));
			      myPanel.add(addressField);
			      myPanel.add(Box.createHorizontalStrut(15));
			      myPanel.add(new JLabel("Porta:"));
			      myPanel.add(portField);
	
			      int result = JOptionPane.showConfirmDialog(null, myPanel, "Inserisci IP e Porta", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) 
			      {
			    	 try
			    	 {
				    	 setCore(new ServerCore(portField.getText(), modOrders, Clienti, modClients, label_1));
				    	 for (Component c : frame.getContentPane().getComponents())
				 			c.setEnabled(true);
				    	 JOptionPane.showMessageDialog(new JPanel(), "Connesso!", "Info", JOptionPane.INFORMATION_MESSAGE);
				    	 System.out.println("address value: " + addressField.getText());
				    	 System.out.println("port value: " + portField.getText());
			    	 }
			    	 catch (Exception e)
			    	 {
			 	    	JOptionPane.showMessageDialog(new JPanel(),"Impossibile connettersi al server!", "Errore", JOptionPane.ERROR);
			    	 }
			      }
			      else
			      {
			      }
				
			}
		});
		mnMenu.add(mntmConnetti);
		for (Component c : frame.getContentPane().getComponents())
			c.setEnabled(false);
	}

	public ServerCore getCore()
	{
		return core;
	}

	public void setCore(ServerCore core)
	{
		this.core = core;
	}
}
