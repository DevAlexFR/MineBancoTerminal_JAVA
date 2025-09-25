package project;

import project.data.MiniBanco;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.*;



// Aqui quis fazer equivalente ao main do python com if __name__ == '__main__': e instanciando a classe para utilizar
class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        Logger rootLogger = Logger.getLogger("");
        for (Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }
        FileHandler fh = new FileHandler("app.log", true);
        fh.setFormatter(new SimpleFormatter());
        rootLogger.addHandler(fh);

        logger.info("Iniciando aplicação");

        MiniBanco mb = new MiniBanco();
        mb.executar();
    }
}