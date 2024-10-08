package com.seu_projeto.venda.dao;

import com.seu_projeto.venda.Venda;
import java.util.Optional;
import java.util.List;

public interface IVendaDAO {
    void registrar(Venda venda);
    Optional<Venda> buscarPorNumero(String numeroNotaFiscal); // Retornando Optional<Venda>
    void excluir(String numeroNotaFiscal);
    List<Venda> listarTodas(); // Novo método para listar todas as vendas
}
