package com.bytebank.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.bytebank.controllers.TransacaoController;
import com.bytebank.models.Transacao;

public class TransacaoView extends JFrame {
  private TransacaoController controller = new TransacaoController();
  private String[] tipos = { "PIX", "Dinheiro" };

  public TransacaoView() {
    setTitle("Sistema de Transações ByteBank");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Paineis

    JPanel panelTopo = new JPanel(new BorderLayout());
    panelTopo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
    JPanel panelBotoes = new JPanel();

    // CAMPOS

    JTextField campoId = new JTextField();
    JTextField campoValor = new JTextField();
    JComboBox<String> comboTipo = new JComboBox<>(tipos);

    // MONTANDO FORMULARIO

    panelFormulario.add(new JLabel("ID (Para buscar/deletar): "));
    panelFormulario.add(campoId);
    panelFormulario.add(new JLabel("Valor (R$): "));
    panelFormulario.add(campoValor);
    panelFormulario.add(new JLabel("Tipo: "));
    panelFormulario.add(comboTipo);

    // Botões

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

    String[] colunas = { "ID", "Valor", "Tipo", "Data" };

    DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

    JTable tabela = new JTable(modeloTabela);
    JScrollPane scrollPane = new JScrollPane(tabela);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

    add(scrollPane, BorderLayout.CENTER);

    botaoBuscar.addActionListener((args) -> {
      try {
        int idBusca = Integer.parseInt(campoId.getText());
        Transacao t = controller.listarPorId(idBusca);

        if (t != null) {
          campoValor.setText(String.valueOf(t.getValor()));
          comboTipo.setSelectedItem(t.getTipo());

        } else {
          JOptionPane.showMessageDialog(this, "Transação não encontrada");
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Digite um ID valido!");
      }
    });

    // Açao para adicionar na table
    botaoSalvar.addActionListener((args) -> {
      if (campoValor.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor preencha o campo VALOR para salvar! ");
        return;
      }

      try {

        int novoId = controller.listarTodos().size() + 1;
        double valor = Double.parseDouble(campoValor.getText());
        String tipo = (String) comboTipo.getSelectedItem();
        Transacao novaTransacao = new Transacao(tipo, novoId, valor, LocalDateTime.now().toString());

        controller.adicionarTransacao(novaTransacao);
        atualizarTabela(modeloTabela);
        limparCampos(campoId, campoValor);

      } catch (Exception e) {

        JOptionPane.showMessageDialog(this, "Não foi possivel salvar a transação");

      }

    });

    botaoEditar.addActionListener((args) -> {
      try {

        int idBusca = Integer.parseInt(campoId.getText());
        double valor = Double.parseDouble(campoValor.getText());
        String tipo = (String) comboTipo.getSelectedItem();

        Transacao transacaoAtualizada = new Transacao( tipo, idBusca, valor, LocalDateTime.now().toString());
        controller.atualizarTransacao(idBusca, transacaoAtualizada);
        atualizarTabela(modeloTabela);
        
        limparCampos(campoId, campoValor);
        JOptionPane.showMessageDialog(this, "Transação atualizada com sucesso!");

      } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao editar. Busque um ID válido primeiro!");
      }
    });

    botaoDeletar.addActionListener((args) -> {
      try {

        int idBusca = Integer.parseInt(campoId.getText());
        Transacao t = controller.listarPorId(idBusca);

        if (t != null) {
          controller.excluirTransacao(idBusca);
        } else {
          JOptionPane.showMessageDialog(this, "ID não existe!");
        }

        atualizarTabela(modeloTabela);
        limparCampos(campoId, campoValor);

      } catch (Exception e) {

        JOptionPane.showMessageDialog(this, "Digite um ID válido para deletar!");

      }
    });

  }

  private void atualizarTabela(DefaultTableModel modeloTabela) {
    modeloTabela.setRowCount(0);

    for (Transacao t : controller.listarTodos()) {
      modeloTabela.addRow(new Object[] {
          t.getId(), t.getValor(), t.getTipo(), t.getData()
      });
    }
  }

  private void limparCampos(JTextField id, JTextField valor) {
    id.setText("");
    valor.setText("");
  }

}