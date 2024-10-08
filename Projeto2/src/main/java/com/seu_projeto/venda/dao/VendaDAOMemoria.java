package com.seu_projeto.venda.dao;

import com.seu_projeto.venda.Venda;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class VendaDAOMemoria implements IVendaDAO {
    private final Map<String, Venda> bancoDeDados = new HashMap<>();

    public VendaDAOMemoria() {
    }

    // Método para registrar uma venda
    @Override
    public void registrar(Venda venda) {
        bancoDeDados.put(venda.getNumeroNotaFiscal(), venda);
    }

    // Método para buscar uma venda pelo número da nota fiscal
    @Override
    public Optional<Venda> buscarPorNumero(String numeroNotaFiscal) {
        return Optional.ofNullable(bancoDeDados.get(numeroNotaFiscal));
    }

    // Método para excluir uma venda pelo número da nota fiscal
    @Override
    public void excluir(String numeroNotaFiscal) {
        bancoDeDados.remove(numeroNotaFiscal);
    }

    // Método para listar todas as vendas
    @Override
    public List<Venda> listarTodas() {
        return new ArrayList<>(bancoDeDados.values()); // Retorna todas as vendas armazenadas
    }
}
