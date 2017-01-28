import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ServerCore extends Thread
{
	private ServerSocket commSock;
	private Map<String, ArrayList<Pizza>> orders;
	private ArrayList<Connection> connections;
	private DefaultTableModel orderTable;
	private DefaultTableModel clientTable;
	private ArrayList<String> clients;
	private JLabel totale;
	private JTable jtClients;
	public ServerCore(String port, DefaultTableModel table, JTable jtClients, DefaultTableModel table2, JLabel lbl)
	{
		try
		{
			commSock = new ServerSocket(Integer.parseInt(port));
			orders = new HashMap<String, ArrayList<Pizza>>();
			connections = new ArrayList<Connection>();
			setClients(new ArrayList<String>());
			new ArrayList<String>();
			this.orderTable = table;
			this.totale = lbl;
			this.setClientTable(table2);
			this.setJtClients(jtClients);
			this.start();
		}
		catch (Exception e)
		{
			System.out.println("[SERVERCORE] Errore nella creazione del server!");
		}
	}
	public ServerCore(int port, DefaultTableModel table, JTable jtClients, DefaultTableModel table2, JLabel lbl)
	{
		try
		{
			commSock = new ServerSocket(port);
			orders = new HashMap<String, ArrayList<Pizza>>();
			connections = new ArrayList<Connection>();
			this.orderTable = table;
			this.setClientTable(table2);
			this.setJtClients(jtClients);
			this.totale = lbl;
			setClients(new ArrayList<String>());
			this.start();
		} 
		catch (Exception e)
		{
			System.out.println("[SERVERCORE] Errore nella creazione del server!");
		}
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				connections.add(new Connection(commSock.accept(), this, this.totale));
			}
			catch (Exception e)
			{
				System.out.println("[SERVERCORE] Errore nella creazione della connessione!");
			}
		}
	}
	
	public String placeOrder(Cliente client, ArrayList<Pizza> pizze)
	{
		synchronized(pizze)
		{
			String id = client.getHash();
			if (orders.containsKey(id))
			{
				for (Pizza p : pizze)
					orders.get(id).add(p);
			}
			else
			{
				orders.put(id, pizze);
			}
			/*if (id.equals(clients.get(jtClients.getSelectedRow())))
			{
				while (orderTable.getRowCount()!=0)
					orderTable.removeRow(0);			
				for (Pizza p : orders.get(id))
					orderTable.addRow(new String[]{p.getNome(), p.getPrezzo()});
			}*/
			float total = 0;
			for (Pizza p : orders.get(id))
				total += Float.parseFloat(p.getPrezzo());
			return total+"$";
		}
	}
	
	
	
	public void loadData(ArrayList<Pizza> pizze)
	{
		String[] tmp = new String[3];
		for (Pizza p : pizze)
		{
			tmp[0] = p.getNome();
			tmp[1] = p.getIngredienti();
			tmp[2] = p.getPrezzo();
			orderTable.addRow(tmp);
		}
	}
	
	public void refreshConnectedUsers()
	{
		for (int i = 0; i < connections.size(); i++)
			if (connections.get(i).getCommSock().isClosed())
				connections.remove(i);
	}
	public ArrayList<String> getClients()
	{
		return clients;
	}
	public void setClients(ArrayList<String> clients)
	{
		this.clients = clients;
	}
	public DefaultTableModel getClientTable()
	{
		return clientTable;
	}
	public void setClientTable(DefaultTableModel clientTable)
	{
		this.clientTable = clientTable;
	}
	public ArrayList<Pizza> getOrder(String clientID)
	{
		synchronized(orders)
		{
			return orders.get(clientID);
		}
	}
	public JTable getJtClients()
	{
		return jtClients;
	}
	public void setJtClients(JTable jtClients)
	{
		this.jtClients = jtClients;
	}
}
