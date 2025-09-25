package project.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


// Aqui quis separar para desenvolver apartado o extrado e chamar ele no MiniBanco...
public class Extrato {
    private final List<String> linhas = new ArrayList<>();
    private final Path arquivo;

    private static final Logger logger = Logger.getLogger(Extrato.class.getName());

    public Extrato() {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        arquivo = Path.of("project/arquivos", "extrato-" + today + ".csv");
        
        linhas.add("DataHora;Tipo;ContaOrigem;ContaDestino;Valor");
        logger.info("Criado extrato com arquivo em " + arquivo.toAbsolutePath());
    }

    public void registrar(String tipo, String contaOrigem, String contaDestino, BigDecimal valor) {
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String linha = String.join(";", dataHora, tipo, 
            contaOrigem != null ? contaOrigem : "",
            contaDestino != null ? contaDestino : "",
            valor.toString());
        linhas.add(linha);
        logger.info("Registrado extrato: " + linha);
    }

    public void salvar() throws IOException {
        if (arquivo.getParent() != null) {
            Files.createDirectories(arquivo.getParent());
        }
        Files.write(arquivo, linhas, StandardCharsets.UTF_8);
        logger.info("Extrato salvo em " + arquivo.toAbsolutePath());
    }
}
