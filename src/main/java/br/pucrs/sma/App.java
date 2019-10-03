package br.pucrs.sma;

import br.pucrs.sma.model.ConfigDto;
import br.pucrs.sma.util.NumberGenerator;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("############# SIMULADOR DE FILAS #############");
        System.out.println("Alunos: Israel Deorce - Larissa Martins - Vinicius Kroth");

        ConfigDto config = new Gson().fromJson(readConfigFile("config.json"), ConfigDto.class);
        Simulator simulator = new Simulator(config);
    }

    public static String readConfigFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
