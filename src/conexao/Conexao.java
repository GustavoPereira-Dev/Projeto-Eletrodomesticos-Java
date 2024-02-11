package conexao;

import javax.swing.JOptionPane;
import java.sql.*;



public class Conexao {
	
	final private String driver = "com.mysql.jdbc.Driver";
	final private String url = "jdbc:mysql://127.0.0.1/clientes";
	final private String usuario = "root";
	final private String senha = "";
	private Connection conexao;
	public Statement statement;
	public ResultSet resultset;
	
	public boolean conecta() {
		boolean result = true;
		
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, usuario, senha);
			JOptionPane.showMessageDialog(null, "Conex�o estabelecida", "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
			
		} catch(ClassNotFoundException Driver) {
			JOptionPane.showMessageDialog(null, "Driver n�o localizado " + Driver, "Mensagem do Programa", JOptionPane.ERROR_MESSAGE);
			result = false;
		} catch(SQLException Fonte) {
			JOptionPane.showMessageDialog(null, "Fonte de dados n�o localizado " + Fonte, "Mensagem do Programa", JOptionPane.ERROR_MESSAGE);
			result = false;
		}
		
		return result;
	}
	
	public void desconecta() {
		try {
			conexao.close();
			JOptionPane.showMessageDialog(null, "Conex�o estabelecida", "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
		} catch(SQLException fecha ) {
			JOptionPane.showMessageDialog(null, "Erro ao desconectar " + fecha, "Mensagem do Programa", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void executaSQL(String sql) {
		try {
			statement = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultset = statement.executeQuery(sql);
		} catch(SQLException excecao) {
			JOptionPane.showMessageDialog(null, "Erro no comando SQL! \n Erro: " + excecao, "Mensagem do Programa", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}

	
	
}
