import com.seu_projeto.cliente.Cliente;
import com.seu_projeto.cliente.ClienteService;
import com.seu_projeto.cliente.dao.ClienteDAOMemoria;
import com.seu_projeto.produto.Produto;
import com.seu_projeto.produto.ProdutoService;
import com.seu_projeto.produto.dao.ProdutoDAOMemoria;
import com.seu_projeto.venda.VendaService;
import com.seu_projeto.venda.Venda;
import com.seu_projeto.venda.dao.VendaDAOMemoria;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ClienteDAOMemoria clienteDAO = new ClienteDAOMemoria();
        ProdutoDAOMemoria produtoDAO = new ProdutoDAOMemoria();
        VendaDAOMemoria vendaDAO = new VendaDAOMemoria();

        ClienteService clienteService = new ClienteService(clienteDAO);
        ProdutoService produtoService = new ProdutoService(produtoDAO);
        VendaService vendaService = new VendaService(vendaDAO);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Gerenciar Cliente");
            System.out.println("2. Gerenciar Produto");
            System.out.println("3. Gerenciar Venda");
            System.out.println("4. Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir o '\n'

            switch (opcao) {
                case 1:
                    gerenciarCliente(clienteService, scanner);
                    break;
                case 2:
                    gerenciarProduto(produtoService, scanner);
                    break;
                case 3:
                    gerenciarVenda(vendaService, clienteService, produtoService, scanner);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void gerenciarCliente(ClienteService clienteService, Scanner scanner) {
        System.out.println("Cliente: 1. Cadastrar 2. Consultar 3. Alterar 4. Excluir 5. Listar Todos");
        int opcao = scanner.nextInt();
        scanner.nextLine();  // Consumir o '\n'

        switch (opcao) {
            case 1:
                Cliente cliente = criarCliente(scanner);
                clienteService.cadastrarCliente(cliente);
                System.out.println("Cliente cadastrado com sucesso.");
                break;
            case 2:
                System.out.print("Informe o CPF: ");
                String cpfConsulta = scanner.nextLine();
                Cliente clienteConsultado = clienteService.consultarPorCpf(cpfConsulta);
                System.out.println(clienteConsultado != null ? clienteConsultado : "Cliente não encontrado.");
                break;
            case 3:
                System.out.print("Informe o CPF do cliente a ser alterado: ");
                String cpfAlterar = scanner.nextLine();
                Cliente clienteAlterar = clienteService.consultarPorCpf(cpfAlterar);
                if (clienteAlterar != null) {
                    System.out.println("Cliente encontrado: " + clienteAlterar);
                    Cliente novoCliente = criarCliente(scanner);
                    novoCliente.setCpf(cpfAlterar); // Mantém o CPF do cliente original
                    clienteService.alterarCliente(novoCliente);
                    System.out.println("Cliente alterado com sucesso.");
                } else {
                    System.out.println("Cliente não encontrado para alteração.");
                }
                break;
            case 4:
                System.out.print("Informe o CPF para exclusão: ");
                String cpfExclusao = scanner.nextLine();
                clienteService.excluirCliente(cpfExclusao);
                System.out.println("Cliente excluído com sucesso.");
                break;
            case 5:
                var clientes = clienteService.listarTodosClientes();
                if (!clientes.isEmpty()) {
                    System.out.println("Clientes cadastrados:");
                    for (Cliente c : clientes) {
                        System.out.println(c);
                    }
                } else {
                    System.out.println("Nenhum cliente cadastrado.");
                }
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static Cliente criarCliente(Scanner scanner) {
        System.out.print("Informe o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Informe o CPF do cliente: ");
        String cpf = scanner.nextLine();
        System.out.print("Informe a cidade do cliente: ");
        String cidade = scanner.nextLine();
        System.out.print("Informe o endereço do cliente: ");
        String endereco = scanner.nextLine();
        System.out.print("Informe o estado do cliente: ");
        String estado = scanner.nextLine();

        return new Cliente(nome, cpf, cidade, endereco, estado);
    }

    private static void gerenciarProduto(ProdutoService produtoService, Scanner scanner) {
        System.out.println("Produto: 1. Cadastrar 2. Consultar 3. Alterar 4. Excluir 5. Listar Todos");
        int opcao = scanner.nextInt();
        scanner.nextLine();  // Consumir o '\n'

        switch (opcao) {
            case 1:
                Produto produto = criarProduto(scanner);
                produtoService.cadastrarProduto(produto);
                System.out.println("Produto cadastrado com sucesso.");
                break;
            case 2:
                System.out.print("Informe a Descrição do Produto: ");
                String descricaoConsulta = scanner.nextLine();
                Produto produtoConsultado = produtoService.buscarPorDescricao(descricaoConsulta);
                System.out.println(produtoConsultado != null ? produtoConsultado : "Produto não encontrado.");
                break;
            case 3:
                System.out.print("Informe a Descrição do Produto a ser alterado: ");
                String descricaoAlterar = scanner.nextLine();
                Produto produtoAlterado = produtoService.buscarPorDescricao(descricaoAlterar);
                if (produtoAlterado != null) {
                    Produto novoProduto = criarProduto(scanner);
                    novoProduto.setCodigoProduto(produtoAlterado.getCodigoProduto()); // Mantém o código do produto original
                    produtoService.alterarProduto(novoProduto);
                    System.out.println("Produto alterado com sucesso.");
                } else {
                    System.out.println("Produto não encontrado para alteração.");
                }
                break;
            case 4:
                System.out.print("Informe a Descrição do Produto para exclusão: ");
                String descricaoExclusao = scanner.nextLine();
                produtoService.excluirProduto(descricaoExclusao);
                System.out.println("Produto excluído com sucesso.");
                break;
            case 5:
                var produtos = produtoService.listarTodosProdutos();
                if (!produtos.isEmpty()) {
                    System.out.println("Produtos cadastrados:");
                    for (Produto p : produtos) {
                        System.out.println(p);
                    }
                } else {
                    System.out.println("Nenhum produto cadastrado.");
                }
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static Produto criarProduto(Scanner scanner) {
        System.out.print("Informe a descrição do produto: ");
        String descricao = scanner.nextLine();
        System.out.print("Informe o valor unitário do produto: ");
        double valorUnitario = scanner.nextDouble();
        scanner.nextLine();  // Consumir o '\n'
        System.out.print("Informe o código do produto: ");
        String codigoProduto = scanner.nextLine();

        return new Produto(descricao, valorUnitario, codigoProduto);
    }

    private static void gerenciarVenda(VendaService vendaService, ClienteService clienteService, ProdutoService produtoService, Scanner scanner) {
        System.out.println("Venda: 1. Registrar Venda 2. Consultar Venda");
        int opcao = scanner.nextInt();
        scanner.nextLine();  // Consumir o '\n'

        switch (opcao) {
            case 1:
                registrarVenda(vendaService, clienteService, produtoService, scanner);
                break;
            case 2:
                consultarVenda(vendaService, scanner);
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static void registrarVenda(VendaService vendaService, ClienteService clienteService, ProdutoService produtoService, Scanner scanner) {
        System.out.print("Informe o CPF do cliente: ");
        String cpf = scanner.nextLine();
        Cliente cliente = clienteService.consultarPorCpf(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        String numeroNotaFiscal = gerarNumeroNotaFiscal(); // Gerar número da nota fiscal automaticamente

        System.out.print("Informe a quantidade de produtos: ");
        int quantidadeProdutos = scanner.nextInt();
        scanner.nextLine();  // Consumir o '\n'

        // Mapa para armazenar produto e sua quantidade
        Map<Produto, Integer> produtosVendidos = new HashMap<>();

        for (int i = 0; i < quantidadeProdutos; i++) {
            System.out.print("Informe a descrição do produto " + (i + 1) + ": ");
            String descricaoProduto = scanner.nextLine();
            Produto produto = produtoService.buscarPorDescricao(descricaoProduto);

            if (produto != null) {
                System.out.print("Informe a quantidade do produto " + produto.getDescricao() + ": ");
                int quantidade = scanner.nextInt();
                scanner.nextLine();  // Consumir o '\n'
                produtosVendidos.put(produto, quantidade);
            } else {
                System.out.println("Produto " + descricaoProduto + " não encontrado.");
            }
        }

        Venda venda = new Venda(numeroNotaFiscal, cliente, produtosVendidos);
        vendaService.registrarVenda(venda);
        System.out.println("Venda registrada com sucesso. Nota Fiscal: " + numeroNotaFiscal);
    }

    private static String gerarNumeroNotaFiscal() {
        // Lógica para gerar um número de nota fiscal (pode ser um UUID ou um número sequencial)
        return String.valueOf(System.currentTimeMillis());
    }

    private static void consultarVenda(VendaService vendaService, Scanner scanner) {
        System.out.print("Informe o número da nota fiscal: ");
        String numeroNotaFiscal = scanner.nextLine();
        var vendaOptional = vendaService.buscarVenda(numeroNotaFiscal);

        if (vendaOptional.isPresent()) {
            Venda venda = vendaOptional.get();
            System.out.println("Venda encontrada: " + venda);
        } else {
            System.out.println("Venda não encontrada.");
        }
    }
}
