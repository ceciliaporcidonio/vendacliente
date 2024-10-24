import com.seu_projeto.cliente.Cliente;
import com.seu_projeto.cliente.ClienteService;
import com.seu_projeto.cliente.dao.IClienteDAO;
import com.seu_projeto.produto.Produto;
import com.seu_projeto.produto.ProdutoService;
import com.seu_projeto.produto.dao.IProdutoDAO;
import com.seu_projeto.venda.Venda;
import com.seu_projeto.venda.VendaService;
import com.seu_projeto.venda.dao.IVendaDAO;

import org.junit.jupiter.api.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class IntegracaoTest {

    private static IClienteDAO clienteDAO;
    private static IProdutoDAO produtoDAO;
    private static IVendaDAO vendaDAO;
    private static ClienteService clienteService;
    private static ProdutoService produtoService;
    private static VendaService vendaService;

    @BeforeAll
    public static void setup() {
        clienteDAO = mock(IClienteDAO.class);
        produtoDAO = mock(IProdutoDAO.class);
        vendaDAO = mock(IVendaDAO.class);

        clienteService = new ClienteService(clienteDAO);
        produtoService = new ProdutoService(produtoDAO);
        vendaService = new VendaService(vendaDAO);
    }

    @BeforeEach
    public void init() {
        // Se necessário, inicializar objetos comuns
    }

    @AfterEach
    public void cleanup() {
        // Limpar dados ou estados se necessário
    }

    @Test
    public void testCadastrarCliente() {
        Cliente cliente = new Cliente("Ana", "123.456.789-00", "99999-9999", "São Paulo", "Rua A", "SP");
        clienteService.cadastrarCliente(cliente);
        verify(clienteDAO, times(1)).cadastrar(cliente);
    }

    @Test
    public void testConsultarCliente() {
        String cpf = "123.456.789-00";
        Cliente cliente = new Cliente("Ana", cpf, "99999-9999", "São Paulo", "Rua A", "SP");
        when(clienteDAO.consultarPorCpf(cpf)).thenReturn(cliente);

        Cliente clienteConsultado = clienteService.consultarPorCpf(cpf);
        Assertions.assertEquals(cliente, clienteConsultado);
    }

    @Test
    public void testCadastrarProduto() {
        Produto produto = new Produto("Produto A", 10.0, "001", 50);
        produtoService.cadastrarProduto(produto);
        verify(produtoDAO, times(1)).cadastrar(produto);
    }

    @Test
    public void testConsultarProduto() {
        String descricao = "Produto A";
        Produto produto = new Produto(descricao, 10.0, "001", 50);
        when(produtoDAO.buscarPorDescricao(descricao)).thenReturn(produto);

        Produto produtoConsultado = produtoService.buscarPorDescricao(descricao);
        Assertions.assertEquals(produto, produtoConsultado);
    }

    @Test
    public void testCadastrarVenda() {
        // Testando a criação de uma venda
        Venda venda = new Venda(new Cliente("Ana", "123.456.789-00", "99999-9999", "São Paulo", "Rua A", "SP"));
        vendaService.cadastrarVenda(venda);
        verify(vendaDAO, times(1)).cadastrar(venda);
    }
}
