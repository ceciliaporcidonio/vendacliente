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

    // Alteração para retornar Optional
    public Optional<Venda> buscarVenda(String numeroNotaFiscal) {
        if (numeroNotaFiscal == null || numeroNotaFiscal.isEmpty()) {
            throw new IllegalArgumentException("O número da nota fiscal não pode ser nulo ou vazio.");
        }

        // Usando Optional para buscar a venda
        return vendaDAO.buscarPorNumero(numeroNotaFiscal);
    }
}
