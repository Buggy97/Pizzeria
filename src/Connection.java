import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JLabel;

public class Connection extends Thread
{
	private Socket commSock;
	private ServerCore serverInstance;
	private ObjectOutputStream oO;
	private ObjectInputStream oI;
	private Cliente client;
	private JLabel totale;
	
	public Connection(Socket sock, ServerCore serverInstance, JLabel lbl)
	{
		this.commSock = sock;
		this.serverInstance = serverInstance;
		this.totale = lbl;
		try
		{
			oO = new ObjectOutputStream(commSock.getOutputStream());
			oI = new ObjectInputStream(commSock.getInputStream());
			this.start();
		}
		catch (Exception e)
		{
			System.out.println("[CONNECTION] Errore creazione stream");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void run()
	{
		try
		{
			client = (Cliente)oI.readObject();
			serverInstance.getClientTable().addRow(new String[]{client.getNome() + " " + client.getCognome()});
			serverInstance.getClients().add(client.getHash());
			ArrayList<Pizza> buffer;
			BufferedReader br = new BufferedReader(new FileReader(new File("pizze.dat")));
		    String line;
		    String out = "";
		    
		    while ((line = br.readLine()) != null)
		    	out += (line + "\n");
		    br.close();
		    
			oO.writeUnshared(out);
			oO.flush();
			
			while((buffer = (ArrayList<Pizza>)oI.readObject()) != null)
			{
				totale.setText(serverInstance.placeOrder(this.client, buffer));
				oO.writeUnshared("Ordine processato.");
				oO.flush();
			}
		}
		catch (Exception e)
		{
			try
			{
				commSock.close();
			} 
			catch (IOException e1)
			{
			}
			serverInstance.refreshConnectedUsers();
		}
	}

	public Cliente getClient()
	{
		return client;
	}

	public Socket getCommSock()
	{
		return commSock;
	}

	public void setCommSock(Socket commSock)
	{
		this.commSock = commSock;
	}

	public void setClient(Cliente client)
	{
		this.client = client;
	}
}
