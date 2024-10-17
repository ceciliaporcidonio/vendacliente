import com.seu_projeto.cliente.Cliente;
import com.seu_projeto.cliente.ClienteService;
import com.seu_projeto.cliente.dao.ClienteDAOPostgres;
import com.seu_projeto.produto.Produto;
import com.seu_projeto.produto.ProdutoService;
import com.seu_projeto.produto.dao.ProdutoDAOPostgres;
import com.seu_projeto.venda.VendaService;
import com.seu_projeto.venda.Venda;
import com.seu_projeto.venda.dao.VendaDAOPostgres;

import java.util.*;

public class Main {
    private static int idCliente = 1; // ID para clientes
    private static int idProduto = 1; // ID para produtos

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ClienteService clienteService = new ClienteService(new ClienteDAOPostgres());
        ProdutoService produtoService = new ProdutoService(new ProdutoDAOPostgres());
        VendaService vendaService = new VendaService(new VendaDAOPostgres());

        while (true) {
            exibirMenuPrincipal();
            int opcao = lerOpcao(scanner);

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

    private static void exibirMenuPrincipal() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Gerenciar Cliente");
        System.out.println("2. Gerenciar Produto");
        System.out.println("3. Gerenciar Venda");
        System.out.println("4. Sair");
    }

    private static int lerOpcao(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Informe um número:");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void gerenciarCliente(ClienteService clienteService, Scanner scanner) {
        System.out.println("Cliente: 1. Cadastrar 2. Consultar 3. Alterar 4. Excluir 5. Listar Todos");
        int opcao = lerOpcao(scanner);

        switch (opcao) {
            case 1:
                Cliente cliente = criarCliente(scanner);
                clienteService.cadastrarCliente(cliente);
                System.out.println("Cliente cadastrado com sucesso.");
                idCliente++; // Incrementa o ID do cliente
                break;
            case 2:
                System.out.print("Informe o CPF: ");
                String cpfConsulta = scanner.next();
                Cliente clienteConsultado = clienteService.consultarPorCpf(cpfConsulta);
                System.out.println(clienteConsultado != null ? clienteConsultado : "Cliente não encontrado.");
                break;
            case 3:
                System.out.print("Informe o CPF do cliente a ser alterado: ");
                String cpfAlterar = scanner.next();
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
                String cpfExclusao = scanner.next();
                clienteService.excluirCliente(cpfExclusao);
                System.out.println("Cliente excluído com sucesso.");
                break;
            case 5:
                var clientes = clienteService.listarTodosClientes();
                if (!clientes.isEmpty()) {
                    System.out.println("Clientes cadastrados:");
                    clientes.forEach(System.out::println);
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
        String nome = scanner.next();
        System.out.print("Informe o CPF do cliente: ");
        String cpf = scanner.next();
        System.out.print("Informe a cidade do cliente: ");
        String cidade = scanner.next();
        System.out.print("Informe o endereço do cliente: ");
        String endereco = scanner.next();
        System.out.print("Informe o estado do cliente: ");
        String estado = scanner.next();

        return new Cliente(idCliente, nome, cpf, cidade, endereco, estado);
    }

    private static void gerenciarProduto(ProdutoService produtoService, Scanner scanner) {
        System.out.println("Produto: 1. Cadastrar 2. Consultar 3. Alterar 4. Excluir 5. Listar Todos");
        int opcao = lerOpcao(scanner);

        switch (opcao) {
            case 1:
                Produto produto = criarProduto(scanner);
                produtoService.cadastrarProduto(produto);
                System.out.println("Produto cadastrado com sucesso.");
                idProduto++; // Incrementa o ID do produto
                break;
            case 2:
                System.out.print("Informe a Descrição do Produto: ");
                String descricaoConsulta = scanner.next();
                Produto produtoConsultado = produtoService.buscarPorDescricao(descricaoConsulta);
                System.out.println(produtoConsultado != null ? produtoConsultado : "Produto não encontrado.");
                break;
            case 3:
                System.out.print("Informe a Descrição do Produto a ser alterado: ");
                String descricaoAlterar = scanner.next();
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
                String descricaoExclusao = scanner.next();
                produtoService.excluirProduto(descricaoExclusao);
                System.out.println("Produto excluído com sucesso.");
                break;
            case 5:
                var produtos = produtoService.listarTodosProdutos();
                if (!produtos.isEmpty()) {
                    System.out.println("Produtos cadastrados:");
                    produtos.forEach(System.out::println);
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
        String descricao = scanner.next();
        System.out.print("Informe o valor unitário do produto: ");
        double valorUnitario = scanner.nextDouble();
        System.out.print("Informe o código do produto: ");
        String codigoProduto = scanner.next();

        return new Produto(idProduto, descricao, valorUnitario, codigoProduto);
    }

    private static void gerenciarVenda(VendaService vendaService, ClienteService clienteService, ProdutoService produtoService, Scanner scanner) {
        System.out.println("Venda: 1. Registrar Venda 2. Consultar Venda");
        int opcao = lerOpcao(scanner);

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
        String cpf = scanner.next();
        Cliente cliente = clienteService.consultarPorCpf(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        String numeroNotaFiscal = gerarNumeroNotaFiscal();

        System.out.print("Informe a quantidade de produtos: ");
        int quantidadeProdutos = scanner.nextInt();

        Map<Produto, Integer> produtosVendidos = new HashMap<>();

        for (int i = 0; i < quantidadeProdutos; i++) {
            System.out.print("Informe a descrição do produto " + (i + 1) + ": ");
            String descricaoProduto = scanner.next();
            Produto produto = produtoService.buscarPorDescricao(descricaoProduto);

            if (produto != null) {
                System.out.print("Informe a quantidade do produto " + produto.getDescricao() + ": ");
                int quantidade = scanner.nextInt();
                produtosVendidos.put(produto, quantidade);
            } else {
                System.out.println("Produto " + descricaoProduto + " não encontrado.");
            }
        }

        Venda venda = new Venda(numeroNotaFiscal, cliente);
        vendaService.registrarVenda(venda);
        System.out.println("Venda registrada com sucesso.");
    }

    private static void consultarVenda(VendaService vendaService, Scanner scanner) {
        System.out.print("Informe o número da nota fiscal: ");
        String numeroNotaFiscal = scanner.next();
        Optional<Venda> venda = vendaService.buscarVenda(numeroNotaFiscal);
        System.out.println(venda != null ? venda : "Venda não encontrada.");
    }

    private static String gerarNumeroNotaFiscal() {
        return UUID.randomUUID().toString();
    }
}