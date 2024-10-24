package com.seu_projeto.venda.dao;

import com.seu_projeto.venda.Venda;

import java.util.List;
import java.util.Optional;

public interface IVendaDAO {
    void registrar(Venda venda);
    Optional<Venda> buscarPorNumero(String numeroNotaFiscal);
    void excluir(String numeroNotaFiscal);
    List<Venda> listarTodas(); // Método para listar todas as vendas
}
