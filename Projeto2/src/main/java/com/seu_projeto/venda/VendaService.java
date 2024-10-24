package com.seu_projeto.venda;

import com.seu_projeto.venda.dao.IVendaDAO;
import com.seu_projeto.produto.Produto;
import com.seu_projeto.cliente.Cliente;

import java.util.List;
import java.util.Optional;

public class VendaService {

    private final IVendaDAO vendaDAO;

    public VendaService(IVendaDAO vendaDAO) {
        this.vendaDAO = vendaDAO;
    }

    public void cadastrarVenda(Cliente cliente, Produto produto, int quantidade) {
        if (produto.getEstoque() >= quantidade) {
            Venda venda = new Venda(cliente);
            venda.adicionarProduto(produto, quantidade);

            vendaDAO.registrar(venda);
            produto.setEstoque(produto.getEstoque() - quantidade);

            System.out.println("Venda realizada com sucesso!");
            venda.gerarNotaFiscal();
        } else {
            System.out.println("Estoque insuficiente para o produto " + produto.getDescricao());
        }
    }

    public Optional<Venda> buscarPorNumero(String numeroNotaFiscal) {
        return vendaDAO.buscarPorNumero(numeroNotaFiscal);
    }

    public List<Venda> listarTodasVendas() {
        return vendaDAO.listarTodas();
    }
}
