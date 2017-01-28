import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;

public class ClientCore extends Thread
{
	private ArrayList<Pizza> pizze;
	private String ip;
	private int port;
	private ArrayList<JComponent> toDisable;
	private ObjectOutputStream oO;
	private ObjectInputStream oI;
	private ClientGUI gui;
	private Cliente client;
	
	public ClientCore(String ip, int port, Cliente client, ClientGUI gui)
	{
		this.ip = ip;
		this.port = port;
		this.gui = gui;
		this.client = client;
		try
		{
			@SuppressWarnings("resource")
			Socket commSock = new Socket(ip, port);
			oO = new ObjectOutputStream(commSock.getOutputStream());
			oI = new ObjectInputStream(commSock.getInputStream());
		}
		catch (Exception e)
		{
		}
		this.start();
	}
	
	public ClientCore()
	{
		
	}
	
	@Override
	public void run()
	{
		try
		{
			oO.writeUnshared(this.client);
			oO.flush();
			String fileTmp = (String)oI.readObject();
			this.pizze = ClientCore.parseData(fileTmp);
			this.loadData(pizze, gui.getModProdotti());
			String buffer;
			while((buffer = (String)oI.readObject()) != null)
			{
				System.out.println(buffer);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
	
	public ArrayList<Pizza> getPizze()
	{
		return pizze;
	}

	public String getIp()
	{
		return ip;
	}

	public int getPort()
	{
		return port;
	}

	public ArrayList<JComponent> getToDisable()
	{
		return toDisable;
	}

	public ObjectOutputStream getoO()
	{
		return oO;
	}

	public ObjectInputStream getoI()
	{
		return oI;
	}

	public void setPizze(ArrayList<Pizza> pizze)
	{
		this.pizze = pizze;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public void setToDisable(ArrayList<JComponent> toDisable)
	{
		this.toDisable = toDisable;
	}

	public void setoO(ObjectOutputStream oO)
	{
		this.oO = oO;
	}

	public void setoI(ObjectInputStream oI)
	{
		this.oI = oI;
	}
	
	public boolean send(ArrayList<Pizza> toSend)
	{
		try
		{
			oO.writeUnshared(toSend);
			oO.flush();
			return true;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public void loadData(ArrayList<Pizza> pizze, DefaultTableModel model)
	{
		String[] tmp = new String[3];
		for (Pizza p : pizze)
		{
			tmp[0] = p.getNome();
			tmp[1] = p.getIngredienti();
			tmp[2] = p.getPrezzo();
			model.addRow(tmp);
		}
	}
	
	public static ArrayList<Pizza> parseData(File file)
	{
		ArrayList<Pizza> products = new ArrayList<Pizza>();
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;
		    while ((line = br.readLine()) != null)
		    {
		    	String[] tmp = line.split(";");
		    	products.add(new Pizza(tmp[0], tmp[1], tmp[2]));
		    }
		    br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String altData = "Margherita;Pomodoro, mozzarella;3,50\nCalzone;Pomodoro, mozzarella, prosciutto, funghi champignon, ricotta, grana in cottuna;4,50\nCapricciosa;Pomodoro, mozzarella, prosciutto, funghi champignon, carciofi, capperi, olive nere, origano;5,00\nClassica;Pomodoro, mozzarella, prosciutto, funghi champignon, salamino piccante;4,00\nDiavola;Pomodoro, mozzarella, salamino piccante;4,00\nFunghi;Pomodoro, mozzarella, funghi champignon;4,00\nNapoletana;Pomodoro, mozzarella, capperi, acciughe, origano, pomodorini cirietto, grana in cottura;5,00\nParma;Pomdoro, mozzarella, crudo di parma;5,00\nParmigiana;Pomodoro, mozzarella, melanzane grigliate,grana in cottura;4,50\nOrtolana;Pomodoro, mozzarella, zucchine e melanzane grigliate, patate e peperoni al forno, spinaci, pomodorini ciliegino, grana in cottura;6,00";
			for (String pizza : altData.split("\n"))
				products.add(new Pizza(pizza.split(";")[0], pizza.split(";")[1], pizza.split(";")[2]));
		}
		return products;
	}
	
	public static ArrayList<Pizza> parseData(String file)
	{
		ArrayList<Pizza> products = new ArrayList<Pizza>();
		try 
		{
		    for (String line : file.split("\n"))
		    {
		    	String[] tmp = line.split(";");
		    	products.add(new Pizza(tmp[0], tmp[1], tmp[2]));
		    }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String altData = "Margherita;Pomodoro, mozzarella;3,50\nCalzone;Pomodoro, mozzarella, prosciutto, funghi champignon, ricotta, grana in cottuna;4,50\nCapricciosa;Pomodoro, mozzarella, prosciutto, funghi champignon, carciofi, capperi, olive nere, origano;5,00\nClassica;Pomodoro, mozzarella, prosciutto, funghi champignon, salamino piccante;4,00\nDiavola;Pomodoro, mozzarella, salamino piccante;4,00\nFunghi;Pomodoro, mozzarella, funghi champignon;4,00\nNapoletana;Pomodoro, mozzarella, capperi, acciughe, origano, pomodorini cirietto, grana in cottura;5,00\nParma;Pomdoro, mozzarella, crudo di parma;5,00\nParmigiana;Pomodoro, mozzarella, melanzane grigliate,grana in cottura;4,50\nOrtolana;Pomodoro, mozzarella, zucchine e melanzane grigliate, patate e peperoni al forno, spinaci, pomodorini ciliegino, grana in cottura;6,00";
			for (String pizza : altData.split("\n"))
				products.add(new Pizza(pizza.split(";")[0], pizza.split(";")[1], pizza.split(";")[2]));
		}
		return products;
	}
	
	
}
