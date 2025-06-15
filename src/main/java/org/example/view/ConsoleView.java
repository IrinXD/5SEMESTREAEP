package org.example.view;

import org.example.model.Agendamento;
import org.example.model.PontoDeColeta;
import org.example.model.Usuario;
import org.example.service.AgendamentoService;
import org.example.service.PontoDeColetaService;
import org.example.service.UsuarioService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private Scanner scanner;
    private UsuarioService usuarioService;
    private PontoDeColetaService pontoDeColetaService;
    private AgendamentoService agendamentoService;
    private Usuario usuarioLogado;

    public ConsoleView() {
        scanner = new Scanner(System.in);
        usuarioService = new UsuarioService();
        pontoDeColetaService = new PontoDeColetaService();
        agendamentoService = new AgendamentoService();
    }

    public void iniciarAplicacao() {
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    menuCadastroLogin();
                    break;
                case 2:
                    if (usuarioLogado != null) {
                        menuUsuarioLogado();
                    } else {
                        System.out.println("Por favor, faça login para acessar esta opção.");
                    }
                    break;
                case 0:
                    System.out.println("Saindo da aplicação. Obrigado!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println();
        } while (opcao != 0);
        scanner.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("--- BEM-VINDO AO SOFTWARE DE CONTROLE DE LIXO ---");
        System.out.println("1. Cadastro/Login");
        System.out.println("2. Acessar como Usuário Logado");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void menuCadastroLogin() {
        int opcao;
        do {
            System.out.println("\n--- CADASTRO / LOGIN ---");
            System.out.println("1. Cadastrar Novo Usuário");
            System.out.println("2. Fazer Login");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarNovoUsuario();
                    break;
                case 2:
                    fazerLogin();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0 && usuarioLogado == null);
    }

    private void cadastrarNovoUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Endereço (para coletas): ");
        String endereco = scanner.nextLine();

        if (usuarioService.realizarCadastro(nome, email, senha, endereco)) {
            System.out.println("Cadastro realizado com sucesso!");
        } else {
            System.out.println("Falha no cadastro. Tente outro e-mail.");
        }
    }

    private void fazerLogin() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        usuarioLogado = usuarioService.realizarLogin(email, senha);
        if (usuarioLogado != null) {
            System.out.println("Login bem-sucedido! Bem-vindo, " + usuarioLogado.getNome() + "!");
        } else {
            System.out.println("Erro no login.");
        }
    }

    private void menuUsuarioLogado() {
        int opcao;
        do {
            System.out.println("\n--- MENU DO USUÁRIO LOGADO (" + usuarioLogado.getNome() + ") ---");
            System.out.println("1. Agendar Coleta Domiciliar");
            System.out.println("2. Listar Meus Agendamentos");
            System.out.println("3. Cancelar Agendamento");
            System.out.println("4. Visualizar Pontos de Coleta");
            System.out.println("5. Cadastrar Ponto de Coleta (Admin/Teste)");
            System.out.println("0. Logout");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    agendarColetaDomiciliar();
                    break;
                case 2:
                    listarMeusAgendamentos();
                    break;
                case 3:
                    cancelarAgendamento();
                    break;
                case 4:
                    visualizarPontosDeColeta();
                    break;
                case 5:
                    cadastrarPontoDeColeta();
                    break;
                case 0:
                    usuarioLogado = null; // Logout
                    System.out.println("Logout realizado com sucesso.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void agendarColetaDomiciliar() {
        System.out.println("\n--- AGENDAR COLETA DOMICILIAR ---");
        System.out.print("Tipo de lixo (Pilhas, Medicamentos, Baterias, Recicláveis): ");
        String tipoLixo = scanner.nextLine();
        System.out.print("Volume do lixo (Ex: 'Pequeno', 'Médio', 'Grande' ou '5kg'): ");
        String volumeLixo = scanner.nextLine();

        System.out.println("Horários disponíveis (exemplos):");
        System.out.println("  1. 2025-06-30 13:30 (Exemplo: Ano-Mês-Dia Hora:Minuto)");
        System.out.println("  2. 2025-06-30 14:30");
        System.out.print("Digite a data e hora da coleta (formato YYYY-MM-DD HH:MM): ");
        String dataHoraStr = scanner.nextLine();
        LocalDateTime dataHoraColeta;
        try {
            dataHoraColeta = LocalDateTime.parse(dataHoraStr.replace(" ", "T")); // Converte para formato ISO para parse
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data/hora inválido. Use YYYY-MM-DD HH:MM.");
            return;
        }

        Agendamento agendamento = agendamentoService.agendarNovaColeta(
                usuarioLogado.getId(), tipoLixo, volumeLixo, dataHoraColeta
        );

        if (agendamento != null) {
            System.out.println("Agendamento criado com sucesso!");
            System.out.println(agendamentoService.gerarComprovante(agendamento));
        } else {
            System.out.println("Falha ao agendar coleta.");
        }
    }

    private void listarMeusAgendamentos() {
        System.out.println("\n--- MEUS AGENDAMENTOS ---");
        List<Agendamento> agendamentos = agendamentoService.obterAgendamentosDoUsuario(usuarioLogado.getId());
        if (agendamentos.isEmpty()) {
            System.out.println("Você não possui agendamentos.");
        } else {
            for (Agendamento ag : agendamentos) {
                System.out.println(agendamentoService.gerarComprovante(ag));
            }
        }
    }

    private void cancelarAgendamento() {
        System.out.println("\n--- CANCELAR AGENDAMENTO ---");
        System.out.print("Digite o ID do agendamento que deseja cancelar: ");
        int idAgendamento = lerOpcao();

        if (agendamentoService.cancelarAgendamento(idAgendamento, usuarioLogado.getId())) {
            System.out.println("Agendamento cancelado com sucesso!");
        } else {
            System.out.println("Não foi possível cancelar o agendamento. Verifique o ID ou se você é o dono.");
        }
    }

    private void visualizarPontosDeColeta() {
        System.out.println("\n--- PONTOS DE COLETA DISPONÍVEIS ---");
        List<PontoDeColeta> pontos = pontoDeColetaService.listarTodosPontosDeColeta();
        if (pontos.isEmpty()) {
            System.out.println("Nenhum ponto de coleta cadastrado.");
        } else {
            for (PontoDeColeta ponto : pontos) {
                System.out.println("ID: " + ponto.getId() +
                        ", Nome: " + ponto.getNome() +
                        ", Endereço: " + ponto.getEndereco() +
                        ", Horário: " + ponto.getHorarioFuncionamento() +
                        ", Aceita: " + String.join(", ", ponto.getTiposLixoAceitos()));
            }
        }
    }

    private void cadastrarPontoDeColeta() {
        System.out.println("\n--- CADASTRAR NOVO PONTO DE COLETA (APENAS PARA TESTE/ADMIN) ---");
        System.out.print("Nome do Ponto: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Horário de Funcionamento (Ex: Seg-Sex 09:00-17:00): ");
        String horario = scanner.nextLine();
        System.out.print("Tipos de lixo aceitos (separados por vírgula, ex: Pilhas,Baterias,Recicláveis): ");
        String tiposStr = scanner.nextLine();
        List<String> tiposLixo = Arrays.asList(tiposStr.split(","));

        pontoDeColetaService.cadastrarNovoPonto(nome, endereco, horario, tiposLixo);
        System.out.println("Ponto de coleta cadastrado (ou tentativa de cadastro).");
    }

    private int lerOpcao() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Digite um número.");
            scanner.next();
            System.out.print("Escolha uma opção: ");
        }
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }
}