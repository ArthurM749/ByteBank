package com.bytebank.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.bytebank.controllers.TransacaoController;
import com.bytebank.models.Transacao;

public class TransacaoView extends JFrame {
  private TransacaoController controller = new TransacaoController();
  private String[] tipos = {"PIX", "Dinheiro"};

  public TransacaoView() {
    setTitle("Sistema de Transações ByteBank");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Paineis

    JPanel panelTopo = new JPanel(new BorderLayout());
    panelTopo.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    JPanel panelFormulario = new JPanel(new GridLayout(5, 2,5,5));
    JPanel panelBotoes = new JPanel();

    //CAMPOS
    
    JTextField campoId = new JTextField();
    JTextField campoValor = new JTextField();
    JComboBox<String> comboTipo = new JComboBox<>(tipos);

    //MONTANDO FORMULARIO

    panelFormulario.add(new JLabel("ID (Para buscar/deletar): "));
    panelFormulario.add(campoId);
    panelFormulario.add(new JLabel("Valor (R$): "));
    panelFormulario.add(campoValor);
    panelFormulario.add(new JLabel("Tipo: "));
    panelFormulario.add(comboTipo);

    //Botões

    JButton botaoSalvar = new JButton("Salvar");
    JButton botaoBuscar = new JButton("Buscar ID");
    JButton botaoEditar = new JButton("Editar");
    JButton botaoDeletar = new JButton("Deletar");
    
    panelBotoes.add(botaoSalvar);
    panelBotoes.add(botaoBuscar);
    panelBotoes.add(botaoEditar);
    panelBotoes.add(botaoDeletar);
    
    panelTopo.add(panelFormulario, BorderLayout.CENTER);
    panelTopo.add(panelBotoes, BorderLayout.SOUTH);


    add(panelTopo, BorderLayout.NORTH);

    String[] colunas = {"ID", "Valor", "Tipo", "Data"};

    DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

    JTable tabela = new JTable(modeloTabela);
    JScrollPane scrollPane = new JScrollPane(tabela);

    add(scrollPane, BorderLayout.CENTER);

    // Açao para adicionar na table
    botaoSalvar.addActionListener((args) -> {
      int novoId = controller.listarTodos().size() + 1;
      
      double valor = Double.parseDouble(campoValor.getText());
      Transacao novaTransacao = new Transacao(novoId, valor, "PIX", LocalDateTime.now().toString());

      controller.adicionarTransacao(novaTransacao);
      campoValor.setText("");
    });
      
  }
  
}