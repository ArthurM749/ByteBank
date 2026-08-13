package com.bytebank.views;

import java.awt.BorderLayout;
import java.time.LocalDateTime;

import javax.swing.JButton;
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
  private int id = 0;

  public TransacaoView() {
    setTitle("Sistema de Transações ByteBank");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    JLabel label = new JLabel("Valor:");
    JTextField textField = new JTextField(10);
    JButton button = new JButton("Salvar");

    panel.add(label);
    panel.add(textField);
    panel.add(button);

    add(panel, BorderLayout.NORTH);

    String[] colunas = {"ID", "Valor", "Tipo", "Data"};

    DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

    JTable tabela = new JTable(modeloTabela);
    JScrollPane scrollPane = new JScrollPane(tabela);

    add(scrollPane, BorderLayout.CENTER);

    // Açao para adicionar na table
    button.addActionListener((args) -> {
      double valor = Double.parseDouble(textField.getText());
      Transacao novaTransacao = new Transacao(id++, valor, "PIX", LocalDateTime.now().toString());

      controller.adicionarTransacao(novaTransacao);
      textField.setText("");
    });
  }
  
}