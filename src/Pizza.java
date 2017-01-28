import java.io.Serializable;
import java.util.ArrayList;

public class Pizza implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String nome;
	private String ingredienti;
	private String note;
	private String prezzo;
	
	public Pizza(String nome, String ingredienti, String prezzo)
	{
		this.nome = nome;
		this.ingredienti = ingredienti;
		this.prezzo = prezzo;
		this.note = "";
	}
	
	public Pizza(String nome, ArrayList<String> ingredienti, String prezzo)
	{
		this.nome = nome;
		this.ingredienti = "";
		for (String s : ingredienti)
			this.ingredienti += s + ", ";
		this.prezzo = prezzo;
		this.note = "";
	}
	
	public Pizza(String nome, String ingredienti, String prezzo, String note)
	{
		this.nome = nome;
		this.ingredienti = "";
		this.ingredienti = ingredienti;
		this.prezzo = prezzo;
		this.note = note;
	}

	public String getNome()
	{
		return nome;
	}

	public String getIngredienti()
	{
		return ingredienti;
	}

	public String getPrezzo()
	{
		return prezzo;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public void setIngredienti(String ingredienti)
	{
		this.ingredienti = ingredienti;
	}

	public void setPrezzo(String prezzo)
	{
		this.prezzo = prezzo;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}
	
	protected Pizza clone()
	{
		return new Pizza(this.nome, this.ingredienti, this.prezzo, this.note);
	}
}
