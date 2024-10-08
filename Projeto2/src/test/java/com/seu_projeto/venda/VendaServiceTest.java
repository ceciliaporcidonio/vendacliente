import com.seu_projeto.cliente.Cliente;
import com.seu_projeto.produto.Produto;
import com.seu_projeto.venda.Venda;
import com.seu_projeto.venda.VendaService;
import com.seu_projeto.venda.dao.VendaDAOMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VendaServiceTest {

    private VendaService vendaService;

    @BeforeEach
    public void setUp() {
        vendaService = new VendaService(new VendaDAOMemoria());
    }

    @Test
    public void testRegistrarVenda() {
        // Criação do cliente
        Cliente cliente = new Cliente("João", "12345678900", "São Paulo", "Rua A", "SP");

        // Criação do produto
        Produto produto1 = new Produto("Produto A", 50.0, "001");
        Produto produto2 = new Produto("Produto B", 30.0, "002");

        // Mapa para armazenar produtos e suas quantidades
        Map<Produto, Integer> produtos = new HashMap<>();
        produtos.put(produto1, 2); // 2 unidades do Produto A
        produtos.put(produto2, 1); // 1 unidade do Produto B

        // Criação da venda
        String numeroNotaFiscal = "NFE123"; // Exemplo de número de nota fiscal
        Venda venda = new Venda(numeroNotaFiscal, cliente, produtos);

        // Registrar a venda
        vendaService.registrarVenda(venda);

        // Verificar se a venda foi registrada
        assertNotNull(vendaService.buscarVenda(numeroNotaFiscal).orElse(null));
    }
}
