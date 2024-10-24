package com.seu_projeto.venda.dao;

import com.seu_projeto.venda.Venda;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class VendaDAOMemoria implements IVendaDAO {
    private final Map<Integer, Venda> bancoDeDados = new HashMap<>();

    @Override
    public void registrar(Venda venda) {
        if (bancoDeDados.containsKey(venda.getNumeroNotaFiscal())) {
            throw new IllegalArgumentException("Venda já registrada com o número de nota fiscal: " + venda.getNumeroNotaFiscal());
        }
        bancoDeDados.put(venda.getNumeroNotaFiscal(), venda);
        System.out.println("Venda registrada com sucesso!");
    }

    @Override
    public Optional<Venda> buscarPorNumero(String numeroNotaFiscal) {
        try {
            int numero = Integer.parseInt(numeroNotaFiscal);
            return Optional.ofNullable(bancoDeDados.get(numero));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public void excluir(String numeroNotaFiscal) {
        try {
            int numero = Integer.parseInt(numeroNotaFiscal);
            bancoDeDados.remove(numero);
            System.out.println("Venda excluída com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Número da nota fiscal inválido: " + numeroNotaFiscal);
        }
    }

    @Override
    public List<Venda> listarTodas() {
        return new ArrayList<>(bancoDeDados.values());
    }
}
