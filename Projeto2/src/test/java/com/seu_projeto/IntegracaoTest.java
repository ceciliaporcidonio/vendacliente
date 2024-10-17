import com.seu_projeto.cliente.Cliente;
import com.seu_projeto.cliente.ClienteService;
import com.seu_projeto.cliente.dao.IClienteDAO;
import com.seu_projeto.produto.Produto;
import com.seu_projeto.produto.ProdutoService;
import com.seu_projeto.produto.dao.ProdutoDAOMemoria;
import com.seu_projeto.venda.Venda;
import com.seu_projeto.venda.VendaService;
import com.seu_projeto.venda.dao.VendaDAOMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IntegracaoTest {

    private ClienteService clienteService;
    private ProdutoService produtoService;
    private VendaService vendaService;

    @BeforeEach
    public void setUp() {
        clienteService = new ClienteService(new ClienteDAOMock());
        produtoService = new ProdutoService(new ProdutoDAOMemoria());
        vendaService = new VendaService(new VendaDAOMemoria());
    }

    @Test
    public void testCadastroERegistroDeVenda() {
        // Cadastrar um cliente
        Cliente cliente = new Cliente(1, "Maria", "12345678900", "São Paulo", "Rua A", "SP");
        clienteService.cadastrarCliente(cliente);

        // Verificar se o cliente foi cadastrado corretamente
        assertNotNull(clienteService.consultarPorCpf("12345678900"), "Cliente não foi cadastrado corretamente.");

        // Cadastrar um produto
        Produto produto = new Produto(1, "Produto X", 150.0, "003"); // Adiciona um ID
        produtoService.cadastrarProduto(produto);

        // Verificar se o produto foi cadastrado corretamente
        assertNotNull(produtoService.buscarPorDescricao("Produto X"), "Produto não foi cadastrado corretamente.");

        // Criar um mapa de produtos e suas quantidades
        Map<Produto, Integer> produtos = new HashMap<>();
        produtos.put(produto, 3); // 3 unidades do Produto X

        // Criar a venda
        String numeroNotaFiscal = "NFE456"; // Exemplo de número de nota fiscal
        Venda venda = new Venda(numeroNotaFiscal, cliente);

        // Adicionar produtos à venda
        venda.getProdutos().putAll(produtos); // Adiciona os produtos à venda

        // Registrar a venda
        vendaService.registrarVenda(venda);

        // Verificar se a venda foi registrada
        assertNotNull(vendaService.buscarVenda(numeroNotaFiscal), "Venda não foi registrada corretamente.");
    }

    // Mock de IClienteDAO para os testes integrados
    private static class ClienteDAOMock implements IClienteDAO {
        private final Map<String, Cliente> clientes = new HashMap<>();

        @Override
        public void cadastrar(Cliente cliente) {
            clientes.put(cliente.getCpf(), cliente);
        }

        @Override
        public Cliente consultar(String cpf) {
            return clientes.get(cpf);
        }

        @Override
        public void alterar(Cliente cliente) {
            clientes.put(cliente.getCpf(), cliente);
        }

        @Override
        public void excluir(String cpf) {
            clientes.remove(cpf);
        }

        @Override
        public List<Cliente> listarTodos() {
            return new ArrayList<>(clientes.values());
        }
    }
}
