package controle;

//imports/dependências do projeto
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import conexao.Conexao;
import java.sql.*;

//Início da classe
@SuppressWarnings("serial")
public class MenuJFrame extends JFrame {

	//declaração de variáveis
	
	//Font
	private Font fonte;
	private Font fonte1;

	//JPanel (Tela)
	private JPanel contentPane;
	
	//JLabel
	JLabel lLinha;
	JLabel lMarca; 
	JLabel lTipo;
	JLabel lCodigo;
	JLabel lPreco;
	JLabel lFornecedor; 
	JLabel lDescricao; 
	JLabel lGarantia;
	JLabel lPesquisa;
	
	//JButton
	private JButton btnFirst;
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnLast;
	private JButton btnNovo;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JButton btnCadastrar;
	
	//JTextField
	private JTextField tPreco;
	private JTextField tLinha;
	private JTextField tMarca;
	private JTextField tTipo;
	private JTextField tCodigo;
	private JTextField tPesquisa;
	private JTextField tFornecedor;
	private JTextField tDescricao;
	private JTextField tGarantia;
	
	//ImageIcon
	ImageIcon first;
	ImageIcon previous;
	ImageIcon next; 
	ImageIcon last; 
	ImageIcon clean;
	ImageIcon save; 
	ImageIcon edit; 
	ImageIcon delete;
	ImageIcon icone;
	
	//variáveis referente a JTable
	DefaultTableModel modelo;
	private JTable tblClientes;
	private JScrollPane scrollPane;
	private final String colunas[] = {"Código", "tipo", "marca", "linha", "preco", "fornecedor", "descrição", "garantia"};
	private final String linhas[][] = {};
	
	//ConexÃ£o
	private Conexao con_cliente;
	
	//variáveis auxiliares
	private int indiceAtual = 0;
	private int lenghtTable = 0;
	private int linha_selecionada = 0;
	
	//Construtor
	public MenuJFrame() {
		
		//Declaração dos componentes e ActionListeners
		initComponents();
		declararActionListeners();
		
		//Configuração inicial da conexÃ£o
		con_cliente = new Conexao();
		con_cliente.conecta();
		lenghtTable = verificarLarguraTabela();
		con_cliente.executaSQL("select * from tbeletrodomesticos order by cod");
		
		//Configuração da Tabela
		preencherTabela();
		posicionarRegistro("primeiro");
		tblClientes.setAutoCreateColumnsFromModel(true);
		tblClientes.getSelectionModel().setSelectionInterval(indiceAtual , indiceAtual);
	}
	
	//Método que verifica a largura da tabela, com base no valor de consultas no MySQL
	public int verificarLarguraTabela() {
		con_cliente.executaSQL("select count(*) as total from tbeletrodomesticos");
		try {
			while(con_cliente.resultset.next()){
                return con_cliente.resultset.getInt("total");
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			escreverTexto("erro", "erro no verificarLargura: " + e);
		}
		return 0;
	}
		
		
	//Inicialização e configuração prévia dos Componentes
	public void initComponents() {
		//Nome do app
		setTitle("Cadastro de Produtos");
		
		//Definicação das fontes
		fonte = new Font("Sans-Serif", Font.BOLD, 12);
        fonte1 = new Font("Arial", Font.TRUETYPE_FONT, 12);
        
        //Ã�cones dos botões e do app
		first = new ImageIcon("target/first.png");
		previous = new ImageIcon("target/previous.png");
		next = new ImageIcon("target/next.png");
		last = new ImageIcon("target/last.png");
		clean = new ImageIcon("target/clean.png");
		save = new ImageIcon("target/save.png");
		edit = new ImageIcon("target/edit.png");
		delete = new ImageIcon("target/delete.png");
		icone = new ImageIcon("target/client.png");
		
		//ConfiguraÃ§Ã£o da tela
		setIconImage(icone.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 165, 234));
		contentPane.setForeground(new Color(128, 165, 234));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Configuração das JLabels
		lLinha = new JLabel("Linha:");
		lLinha.setForeground(new Color(196, 253, 108));
		lLinha.setBounds(86, 158, 58, 14);
		lLinha.setFont(fonte);
		contentPane.add(lLinha);
		
		lMarca = new JLabel("Marca:");
		lMarca.setForeground(new Color(107, 254, 111));
		lMarca.setBounds(86, 118, 112, 14);
		lMarca.setFont(fonte);
		contentPane.add(lMarca);
		
		lTipo = new JLabel("Tipo:");
		lTipo.setForeground(new Color(128, 253, 224));
		lTipo.setBounds(86, 78, 46, 14);
		lTipo.setFont(fonte);
		contentPane.add(lTipo);
		
		lCodigo = new JLabel("Código:");
		lCodigo.setForeground(new Color(134, 220, 253));
		lCodigo.setBackground(new Color(0, 0, 0));
		lCodigo.setBounds(86, 38, 46, 14);
		lCodigo.setFont(fonte);
		contentPane.add(lCodigo);
		
		lPreco = new JLabel("Preço:");
		lPreco.setForeground(new Color(109, 123, 252));
		lPreco.setBounds(476, 78, 46, 14);
		contentPane.add(lPreco);
		
		lFornecedor = new JLabel("Fornecedor:");
		lFornecedor.setForeground(new Color(196, 253, 108));
		lFornecedor.setFont(new Font("Dialog", Font.BOLD, 12));
		lFornecedor.setBounds(476, 38, 78, 14);
		contentPane.add(lFornecedor);
		
		lDescricao = new JLabel("Descrição:");
		lDescricao.setForeground(new Color(196, 253, 108));
		lDescricao.setFont(new Font("Dialog", Font.BOLD, 12));
		lDescricao.setBounds(476, 118, 78, 14);
		contentPane.add(lDescricao);
		
		lGarantia = new JLabel("Garantia:");
		lGarantia.setForeground(new Color(196, 253, 108));
		lGarantia.setFont(new Font("Dialog", Font.BOLD, 12));
		lGarantia.setBounds(476, 158, 58, 14);
		contentPane.add(lGarantia);
		
		lPesquisa = new JLabel("Pesquisa por linha do Produto:");
		lPesquisa.setBounds(171, 530, 202, 14);
		lPesquisa.setFont(fonte);
		contentPane.add(lPesquisa);
		
		//Configuração das JTextFields
		tPreco = new JTextField();
		tPreco.setBackground(new Color(252, 255, 232));
		tPreco.setBounds(576, 78, 200, 20);
		tPreco.setFont(fonte1);
		contentPane.add(tPreco);
		tPreco.setColumns(10);
		
		tLinha = new JTextField();
		tLinha.setBackground(new Color(252, 255, 232));
		tLinha.setBounds(186, 158, 200, 20);
		tLinha.setFont(fonte1);
		contentPane.add(tLinha);
		tLinha.setColumns(10);
		
		tMarca = new JTextField();
		tMarca.setBackground(new Color(252, 255, 232));
		tMarca.setBounds(186, 118, 200, 20);
		tMarca.setFont(fonte1);
		contentPane.add(tMarca);
		tMarca.setColumns(10);
		
		tCodigo = new JTextField();
		tCodigo.setBackground(new Color(252, 255, 232));
		tCodigo.setBounds(186, 38, 86, 20);
		tCodigo.setFont(fonte1);
		contentPane.add(tCodigo);
		tCodigo.setColumns(10);
		
		tTipo = new JTextField();
		tTipo.setBackground(new Color(252, 255, 232));
		tTipo.setBounds(186, 78, 200, 20);
		tTipo.setFont(fonte1);
		contentPane.add(tTipo);
		tTipo.setColumns(10);
		
		tFornecedor = new JTextField();
		tFornecedor.setText((String) null);
		tFornecedor.setFont(new Font("Arial", Font.PLAIN, 12));
		tFornecedor.setColumns(10);
		tFornecedor.setBackground(new Color(252, 255, 232));
		tFornecedor.setBounds(576, 38, 200, 20);
		contentPane.add(tFornecedor);
		
		tDescricao = new JTextField();
		tDescricao.setText((String) null);
		tDescricao.setFont(new Font("Arial", Font.PLAIN, 12));
		tDescricao.setColumns(10);
		tDescricao.setBackground(new Color(252, 255, 232));
		tDescricao.setBounds(576, 118, 200, 20);
		contentPane.add(tDescricao);
		
		tGarantia = new JTextField();
		tGarantia.setText((String) null);
		tGarantia.setFont(new Font("Arial", Font.PLAIN, 12));
		tGarantia.setColumns(10);
		tGarantia.setBackground(new Color(252, 255, 232));
		tGarantia.setBounds(576, 158, 200, 20);
		contentPane.add(tGarantia);
		
		tPesquisa = new JTextField();
		tPesquisa.setBackground(new Color(249, 249, 213));
		tPesquisa.setFont(fonte1);
		tPesquisa.setBounds(366, 530, 274, 20);
		contentPane.add(tPesquisa);
		tPesquisa.setColumns(10);
		
		//Configuração dos JButtons
		btnFirst = new JButton(first);
		btnFirst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFirst.setBackground(new Color(128, 255, 255));
		btnFirst.setBounds(112, 259, 45, 25);
		contentPane.add(btnFirst);
		
		btnPrevious = new JButton(previous);
		btnPrevious.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPrevious.setBackground(new Color(128, 255, 255));
		btnPrevious.setBounds(180, 259, 45, 25);
		btnFirst.setBorder(BorderFactory.createLineBorder(Color.yellow));
		btnPrevious.setBorder(BorderFactory.createLineBorder(Color.yellow));
		contentPane.add(btnPrevious);
		
		btnNext = new JButton(next);
		btnNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNext.setBackground(new Color(128, 255, 255));
		btnNext.setBounds(248, 259, 45, 25);
		btnNext.setBorder(BorderFactory.createLineBorder(Color.yellow));
		contentPane.add(btnNext);
		
		btnLast = new JButton(last);
		btnLast.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLast.setBorder(UIManager.getBorder("Button.border"));
		btnLast.setBackground(new Color(128, 255, 255));
		btnLast.setBounds(316, 259, 45, 25);
		btnLast.setBorder(BorderFactory.createLineBorder(Color.yellow));
		contentPane.add(btnLast);
		
		btnNovo = new JButton(clean);
		btnNovo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNovo.setBackground(new Color(255, 255, 128));
		btnNovo.setBorder(BorderFactory.createLineBorder(Color.decode("#e6e600")));
		btnNovo.setBounds(506, 259, 45, 25);
		contentPane.add(btnNovo);
		
		btnCadastrar = new JButton(save);
		btnCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCadastrar.setBackground(new Color(0, 255, 64));
		btnCadastrar.setBorder(BorderFactory.createLineBorder(Color.decode("#009900")));
		btnCadastrar.setBounds(574, 259, 45, 25);
		contentPane.add(btnCadastrar);
		
		btnAlterar = new JButton(edit);
		btnAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAlterar.setBackground(new Color(255, 128, 64));
		btnAlterar.setBounds(642, 259, 45, 25);
		contentPane.add(btnAlterar);
		
		btnExcluir = new JButton(delete);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBackground(new Color(255, 0, 0));
		btnExcluir.setBorder(BorderFactory.createLineBorder(Color.decode("#ff9980")));
		btnExcluir.setBounds(710, 259, 45, 25);
		contentPane.add(btnExcluir);
		
		//Configuração da tableModel para a tabela
		TableModel tableModel = new DefaultTableModel(linhas, colunas){
			
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		    
		};
		
		
		//Configuração do JTable e JScrollPane
		scrollPane = new JScrollPane();
		scrollPane.setBounds(58, 356, 741, 128);
		contentPane.add(scrollPane);
		
		tblClientes = new JTable(tableModel);
		tblClientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
		tblClientes.getTableHeader().setOpaque(false);
		tblClientes.getTableHeader().setBackground(new Color(32, 136, 203));
		tblClientes.getTableHeader().setForeground(new Color(255, 255, 255));
		tblClientes.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.blue));
		tblClientes.setGridColor(Color.red);
		tblClientes.setBackground(Color.yellow);
		tblClientes.setForeground(Color.black);
		tblClientes.setSelectionBackground(Color.green);
		tblClientes.setSelectionForeground(Color.blue);
		scrollPane.setViewportView(tblClientes);
	}
	
	//Declaração dos action listeners
	public void declararActionListeners() {
		//ActionListeners
		btnFirst.addActionListener((ActionEvent e) -> {
			indiceAtual = 0;
		    posicionarRegistro("primeiro");
			tblClientes.getSelectionModel().setSelectionInterval(indiceAtual, indiceAtual);
	    });

		btnPrevious.addActionListener((ActionEvent e) -> {
			indiceAtual--;
				
			if(indiceAtual != -1) {
				posicionarRegistro("anterior");
			} else {
				indiceAtual = lenghtTable - 1;
				posicionarRegistro("ultimo");
			}
				
			System.out.println(indiceAtual);
			if(indiceAtual != lenghtTable - 1) {
				tblClientes.getSelectionModel().setSelectionInterval(indiceAtual, indiceAtual );
	    	} else {
	    			
	    			tblClientes.getSelectionModel().setSelectionInterval(indiceAtual , indiceAtual);
	    	}
		});
		
		btnNext.addActionListener((ActionEvent e) -> {
			indiceAtual++;
					
			if(indiceAtual != lenghtTable) {
				posicionarRegistro("proximo");
			} else {
			    indiceAtual = 0;
			    posicionarRegistro("primeiro");
			}
				
	        if(indiceAtual < lenghtTable) {
	    		tblClientes.getSelectionModel().setSelectionInterval(indiceAtual, indiceAtual);
	    	} else {
	    		tblClientes.getSelectionModel().setSelectionInterval(1 + indiceAtual - lenghtTable , 1 + indiceAtual - lenghtTable);
	    	}		
	        
		});	
		
		btnLast.addActionListener((ActionEvent e) -> {
			indiceAtual = lenghtTable - 1;
			posicionarRegistro("ultimo");
			tblClientes.getSelectionModel().setSelectionInterval(indiceAtual, indiceAtual);
        });
		
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tPreco.setText("");
				tLinha.setText("");
				tMarca.setText("");
				tTipo.setText("");
				tCodigo.setText("");
				tFornecedor.setText("");
				tDescricao.setText("");
				tGarantia.setText("");
				tCodigo.requestFocus();
			}
		});
		
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tipo = tTipo.getText();
				String marca = tMarca.getText();
				String linha = tLinha.getText();
				String preco = tPreco.getText();
				String fornecedor = tFornecedor.getText();
				String descricao = tDescricao.getText();
				String garantia = tGarantia.getText();
				
				if(verificarValorNulo(tipo, marca, linha, preco, fornecedor, descricao, garantia)) {
				try {
					String insert_sql="insert into tbeletrodomesticos (tipo, marca, linha, preco, fornecedor, descricao, garantia) values ('" + tipo + "','" + marca + "','" + linha + "','" + preco + "','" + fornecedor + "','" + descricao + "','" + garantia + "')";
					con_cliente.statement.executeUpdate(insert_sql);
					escreverTexto("aviso", "Gravação realizada com sucesso!");
					con_cliente.executaSQL("select * from tbeletrodomesticos order by cod");
					preencherTabela();
					posicionarRegistro("primeiro");
					lenghtTable++;
					indiceAtual = 0;
					tblClientes.getSelectionModel().setSelectionInterval(indiceAtual, indiceAtual);
				} catch(SQLException erro) {
					escreverTexto("erro",   "Erro na gravação : \n  " + erro);
				}
				
				} else {
					escreverTexto("erro",  "Algum dos 4 últimos campos estão nulo, favor adicionar valor");
				}
			}
		});
		
		
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				String sql = "";
				String msg = "";
				String tipo = tTipo.getText();
				String marca = tMarca.getText();
				String linha = tLinha.getText();
				String preco = tPreco.getText();
				String fornecedor = tFornecedor.getText();
				String descricao = tDescricao.getText();
				String garantia = tGarantia.getText();
				int par = 0;
				
				try {
					//tipo, marca, linha, preco, fornecedor, descricao, garantia
					if(tCodigo.getText().equals("") && verificarValorNulo(tipo, marca, linha, preco, fornecedor, descricao, garantia)) {
						sql = "insert into tbeletrodomesticos (tipo, marca, linha, preco, fornecedor, descricao, garantia) values ('" + tipo + "','" + marca + "','" + linha + "','" + preco + "','" + fornecedor + "','" + descricao + "','" + garantia + "')";
						msg = "Gravação de um novo registro";
						lenghtTable++;
						par = 1;
					} else if(verificarValorNulo(tipo, marca, linha, preco, fornecedor, descricao, garantia)) {
						sql = "update tbeletrodomesticos set tipo='" + tipo + "', marca='" + marca + "', linha='" + linha + "', preco='" + preco + "', fornecedor='" + fornecedor + "', descricao='" + descricao + "', garantia='" +  "' where cod = " + tCodigo.getText();
						msg = "Alteração de registro";
						par = 1;
					} 
					
					if(par == 1) {
					con_cliente.statement.executeUpdate(sql);
					escreverTexto("aviso", msg + " realizada com sucesso!");
					
					con_cliente.executaSQL("select * from tbeletrodomesticos order by cod");
					preencherTabela();
					posicionarRegistro("primeiro");
					indiceAtual = 0;
					tblClientes.getSelectionModel().setSelectionInterval(indiceAtual , indiceAtual );
					} else {
						escreverTexto("erro", "Algum dos 4 últimos campos esá¡ nulo, favor adicionar valor");
					}
				} catch(SQLException erro) {
					
					escreverTexto("erro", "Erro na  : " + msg + " \n  " + erro);
				}
				
				
			}
		});
	
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
			if(!tCodigo.getText().equals("")) {
				String sql = "";
				try {
					int resposta = JOptionPane.showConfirmDialog(rootPane, "Deseja excluir o registro: ", "Confirmar Exclusão", JOptionPane.YES_NO_CANCEL_OPTION, 3);
					if(resposta == 0) {
						sql = "delete from tbeletrodomesticos where cod =" + tCodigo.getText();
						int excluir = con_cliente.statement.executeUpdate(sql);
						if(excluir == 1) {
							escreverTexto("aviso",  "Exclusão realizada com sucesso");
							con_cliente.executaSQL("select * from tbeletrodomesticos order by cod");
							preencherTabela();
							posicionarRegistro("primeiro");
							lenghtTable--;
							indiceAtual = 0;
							tblClientes.getSelectionModel().setSelectionInterval(indiceAtual , indiceAtual );
						} else {
							escreverTexto("erro",  "Id inexistente na tabela");
							posicionarRegistro("primeiro");
						}
					} else {
						escreverTexto("aviso",  "Operação cancelada pelo usuário!");
					}
				} catch(SQLException erro) {
					escreverTexto("erro",  "Erro na exclusão \n " + erro);
				}
				
			} else {
				escreverTexto("erro",  "O campo de Id está nulo, favor adicionar valor");
			}
				
			}	
		});
		
		//MouseListeners
		
		btnFirst.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent avt) {
		        btnFirst.setBorder(BorderFactory.createLineBorder(Color.blue));
		    }
		        	
		    public void mouseExited(MouseEvent avt) {
		        btnFirst.setBorder(BorderFactory.createLineBorder(Color.yellow));
		    }
		});

		btnPrevious.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent avt) {
	        	btnPrevious.setBorder(BorderFactory.createLineBorder(Color.blue));
	        }
	        	
	        public void mouseExited(MouseEvent avt) {
	        	btnPrevious.setBorder(BorderFactory.createLineBorder(Color.yellow));
	        }
		});	
			
		btnNext.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent avt) {
        		btnNext.setBorder(BorderFactory.createLineBorder(Color.blue));
        	}
        	
        	public void mouseExited(MouseEvent avt) {
        		btnNext.setBorder(BorderFactory.createLineBorder(Color.yellow));
        	}
		});

		btnLast.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent avt) {
        		btnLast.setBorder(BorderFactory.createLineBorder(Color.blue));
        	}
        	
        	public void mouseExited(MouseEvent avt) {
        		btnLast.setBorder(BorderFactory.createLineBorder(Color.yellow));
        	}
		});
		
		
		//Mouse, Action e KeyListener da Tabela
		tblClientes.addMouseListener(new MouseListener() {
					
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub	
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				linha_selecionada = tblClientes.getSelectedRow();
				
				if(linha_selecionada < indiceAtual) {
					while(linha_selecionada < indiceAtual) {
						indiceAtual--;
						posicionarRegistro("anterior");
					}
				} else if (linha_selecionada > indiceAtual){
					while(linha_selecionada > indiceAtual) {
						indiceAtual++;
						posicionarRegistro("proximo");
					}
				}
				
				indiceAtual = tblClientes.getSelectedRow();
				tCodigo.setText(tblClientes.getValueAt(linha_selecionada, 0).toString());
				tTipo.setText(tblClientes.getValueAt(linha_selecionada, 1).toString());
				tMarca.setText(tblClientes.getValueAt(linha_selecionada, 2).toString());
				tLinha.setText(tblClientes.getValueAt(linha_selecionada, 3).toString());
				tPreco.setText(tblClientes.getValueAt(linha_selecionada, 4).toString());
				tFornecedor.setText(tblClientes.getValueAt(linha_selecionada, 5).toString());
				tDescricao.setText(tblClientes.getValueAt(linha_selecionada, 6).toString());
				tGarantia.setText(tblClientes.getValueAt(linha_selecionada, 7).toString());
			}
		});
			
		tblClientes.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				linha_selecionada = tblClientes.getSelectedRow();
				System.out.println(linha_selecionada);
				if(linha_selecionada < indiceAtual) {
					while(linha_selecionada < indiceAtual) {
						indiceAtual--;
						posicionarRegistro("anterior");
					}
				} else if (linha_selecionada > indiceAtual){
					while(linha_selecionada > indiceAtual) {
						indiceAtual++;
						posicionarRegistro("proximo");
					}
				}
				
				indiceAtual = tblClientes.getSelectedRow();
				tCodigo.setText(tblClientes.getValueAt(linha_selecionada, 0).toString());
				tTipo.setText(tblClientes.getValueAt(linha_selecionada, 1).toString());
				tMarca.setText(tblClientes.getValueAt(linha_selecionada, 2).toString());
				tLinha.setText(tblClientes.getValueAt(linha_selecionada, 3).toString());
				tPreco.setText(tblClientes.getValueAt(linha_selecionada, 4).toString());
				tFornecedor.setText(tblClientes.getValueAt(linha_selecionada, 5).toString());
				tDescricao.setText(tblClientes.getValueAt(linha_selecionada, 6).toString());
				tGarantia.setText(tblClientes.getValueAt(linha_selecionada, 7).toString());	
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
				
		});

		btnNovo.addMouseListener(new MouseAdapter() {
	        public void mouseEntered(MouseEvent avt) {
	        	btnNovo.setBorder(BorderFactory.createLineBorder(Color.decode("#ffff00")));
	        }
	        	
	        public void mouseExited(MouseEvent avt) {
	        	btnNovo.setBorder(BorderFactory.createLineBorder(Color.decode("#e6e600")));
	        }
		});

		btnCadastrar.addMouseListener(new MouseAdapter() {
	        public void mouseEntered(MouseEvent avt) {
	        	btnCadastrar.setBorder(BorderFactory.createLineBorder(Color.decode("#00ff00")));
	        }
	        	
	        public void mouseExited(MouseEvent avt) {
	        	btnCadastrar.setBorder(BorderFactory.createLineBorder(Color.decode("#009900")));
	        }
	    });
		
		btnAlterar.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent avt) {
        		btnAlterar.setBorder(BorderFactory.createLineBorder(Color.decode("#ff9933")));
        	}
        	
        	public void mouseExited(MouseEvent avt) {
        		btnAlterar.setBorder(BorderFactory.createLineBorder(Color.decode("#ffcc99")));
        	}
        });

		btnExcluir.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent avt) {
        		btnExcluir.setBorder(BorderFactory.createLineBorder(Color.decode("#ff471a")));
        	}
        	
        	public void mouseExited(MouseEvent avt) {
        		btnExcluir.setBorder(BorderFactory.createLineBorder(Color.decode("#ff9980")));
        	}
		});

		//KeyListener
		tPesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if(!"".equals(tPesquisa.getText())) {
					String pesquisa = "select * from tbeletrodomesticos where linha like '" + tPesquisa.getText() + "%'";
					con_cliente.executaSQL(pesquisa);
					
					if(con_cliente.resultset.first()) {
						preencherTabela();
					} else {
						escreverTexto("erro",  "\n Não existe dados com este parâmetro!!!");
					}
					
					
					
					
					lenghtTable = verificarLarguraTabela() - 2;
					con_cliente.executaSQL(pesquisa);
					posicionarRegistro("primeiro");
					} else {
						lenghtTable = verificarLarguraTabela();
						con_cliente.executaSQL("select * from tbeletrodomesticos order by cod");
						//Configuração da Tabela
						preencherTabela();
						posicionarRegistro("primeiro");
						
					}
				
					
					indiceAtual = 0;
					tblClientes.getSelectionModel().setSelectionInterval(indiceAtual , indiceAtual);
				
				} catch(SQLException errosql) {
					escreverTexto("erro", "\n Os dados digitados não foram localizados!!  " + errosql);
				}
			}
		});

	}
	
	//Preencimento da Tabela com os dados da conexão
	public void preencherTabela() {
		tblClientes.getColumnModel().getColumn(0).setPreferredWidth(3);
		tblClientes.getColumnModel().getColumn(1).setPreferredWidth(50);
		tblClientes.getColumnModel().getColumn(2).setPreferredWidth(20);
		tblClientes.getColumnModel().getColumn(3).setPreferredWidth(14);
		tblClientes.getColumnModel().getColumn(4).setPreferredWidth(30);
		tblClientes.getColumnModel().getColumn(5).setPreferredWidth(30);
		tblClientes.getColumnModel().getColumn(6).setPreferredWidth(30);
		tblClientes.getColumnModel().getColumn(7).setPreferredWidth(30);
		
		
		modelo = (DefaultTableModel) tblClientes.getModel();
		modelo.setNumRows(0);
		
		try {
			con_cliente.resultset.beforeFirst();
			while(con_cliente.resultset.next()) {
				modelo.addRow(new Object[] {
						con_cliente.resultset.getString("cod"),con_cliente.resultset.getString("tipo"), con_cliente.resultset.getString("marca"),
						con_cliente.resultset.getString("linha"), con_cliente.resultset.getString("preco"), con_cliente.resultset.getString("fornecedor"),
						con_cliente.resultset.getString("descricao"), con_cliente.resultset.getString("garantia")
				});
			}
		} catch(SQLException erro) {
			escreverTexto("erro", "Erro ao listar dados da tabela!! : \n  " + erro);
		}
	}
	
	//Atualização do registro
	public void posicionarRegistro(String valor) {
		String msg = "";
		try {
			switch(valor) {
				case "primeiro":
					con_cliente.resultset.first();
					msg = "primeiro registro";
					break;
				case "ultimo":
					con_cliente.resultset.last();
					msg = "último registro";
					break;
				case "proximo":
					con_cliente.resultset.next();
					msg = "registro anterior";
					break;
				case "anterior":
					con_cliente.resultset.previous();
					msg = "registro seguinte";
					break;
			}
			mostrar_Dados();
		} catch(SQLException erro) {
			escreverTexto("erro",  "Não foi possível posicionar o " + msg + ":" + erro);
		}
	}
	
	//Impressão dos dados de uma linha da tabela para os JTextFields
	public void mostrar_Dados() {
		try {
			tCodigo.setText(con_cliente.resultset.getString("cod"));
			tTipo.setText(con_cliente.resultset.getString("tipo"));
			tMarca.setText(con_cliente.resultset.getString("marca"));
			tLinha.setText(con_cliente.resultset.getString("linha"));
			tPreco.setText(con_cliente.resultset.getString("preco"));
			tFornecedor.setText(con_cliente.resultset.getString("fornecedor"));
			tDescricao.setText(con_cliente.resultset.getString("descricao"));
			tGarantia.setText(con_cliente.resultset.getString("garantia"));
		} catch(SQLException erro) {
			escreverTexto("erro", "Não localizou dados: " + erro);
		}
	}
	
	//Verifica se algum campo do JTextField está nulo
	public boolean verificarValorNulo(String n1, String n2, String n3, String n4, String n5, String n6, String n7) {
    	if(!"".equals(n1) && !"".equals(n2) && !"".equals(n3)  && !"".equals(n4) && !"".equals(n5) && !"".equals(n6) && !"".equals(n7)) {
    		return true;
    	}
    	return false;
    }
	
	public void escreverTexto(String status, String mensagem) {
		
		if(status == "erro") {
			JOptionPane.showMessageDialog(null, mensagem, "Mensagem do Programa", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, mensagem, "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void MenuComponentes() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuJFrame frame = new MenuJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}