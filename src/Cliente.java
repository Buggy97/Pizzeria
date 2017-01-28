import java.io.Serializable;

public class Cliente implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 386079203788207998L;
	private String nome;
	private String cognome;
	private String indirizzo;
	private int number;
	
	public Cliente(String nome, String cognome, String indirizzo, int number)
	{
		this.nome = nome.replace(";", "");
		this.cognome = cognome.replace(";", "");
		this.indirizzo = indirizzo.replace(";", "");
		this.number = number;
	}
	
	public Cliente(String clientData)
	{
		String[] tmp = clientData.split(";");
		this.nome = tmp[2];
		this.cognome = tmp[0];
		this.indirizzo = tmp[1]; 
		this.number = Integer.parseInt(tmp[3]);
	}
	
	public String getHash()
	{
		return this.getCognome()+";"+this.getIndirizzo()+";"+this.getNome()+";"+this.getNumber();
	}
	
	public String getNome()
	{
		return nome;
	}
	public String getCognome()
	{
		return cognome;
	}
	public String getIndirizzo()
	{
		return indirizzo;
	}
	public int getNumber()
	{
		return number;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public void setCognome(String cognome)
	{
		this.cognome = cognome;
	}
	public void setIndirizzo(String indirizzo)
	{
		this.indirizzo = indirizzo;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}
}
