package org.example.main;

import org.example.model.PontoDeColeta;
import org.example.repository.DatabaseConnection;
import org.example.service.PontoDeColetaService;
import org.example.view.LoginCadastroGUI;

import javax.swing.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();

        // INSERINDO PONTOS DE COLETA
        PontoDeColetaService pontoService = new PontoDeColetaService();

        if (pontoService.listarTodosPontosDeColeta().isEmpty()) {
            System.out.println("Cadastrando pontos de coleta de exemplo...");

            pontoService.cadastrarNovoPonto(
                    "Ecoponto Central",
                    "Rua das Flores, 123 - Centro",
                    "Seg-Sex 08:00-17:00",
                    Arrays.asList("Pilhas", "Baterias", "Recicláveis")
            );

            pontoService.cadastrarNovoPonto(
                    "Farmácia Saúde Viva",
                    "Av. Principal, 456 - Bairro Novo",
                    "Seg-Sab 09:00-20:00",
                    Arrays.asList("Medicamentos")
            );

            pontoService.cadastrarNovoPonto(
                    "Centro de Reciclagem Verde",
                    "Travessa da Paz, 789 - Industrial",
                    "Seg-Sex 07:30-16:30",
                    Arrays.asList("Recicláveis", "Baterias")
            );

            System.out.println("Pontos de coleta de exemplo cadastrados!");
        } else {
            System.out.println("Pontos de coleta já existem no banco de dados. Pulando cadastro de exemplos.");
        }


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginCadastroGUI();
            }
        });
    }
}