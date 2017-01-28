import java.awt.Font;

import javax.swing.JLabel;

public class PROVA
{
	public static void main (String[] args)
	{
		JLabel lblTotale_1 = new JLabel("0.00$");
		lblTotale_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTotale_1.setBounds(223, 270, 58, 14);
		System.out.println(lblTotale_1.getText().replace("0", ""));
		//lblTotale_1.setText((Float.parseFloat(lblTotale_1.getText()) - 10.00)+"");
		
	}
	
}
