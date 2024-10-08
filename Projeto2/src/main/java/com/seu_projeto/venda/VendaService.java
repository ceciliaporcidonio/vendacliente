package com.seu_projeto.venda;

import com.seu_projeto.venda.dao.IVendaDAO;

import java.util.Optional;

public class VendaService {
    private IVendaDAO vendaDAO;

    public VendaService(IVendaDAO vendaDAO) {
        this.vendaDAO = vendaDAO;
    }

    public Venda registrarVenda(Venda venda) {
        if (venda == null) {
            throw new IllegalArgumentException("A venda não pode ser nula.");
        }
        vendaDAO.registrar(venda);
        return venda;
    }

    public Optional<Venda> buscarVenda(String numeroNotaFiscal) {
        return vendaDAO.buscarPorNumero(numeroNotaFiscal);
    }
}
