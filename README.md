# Mini Banco Java

Um sistema simples de banco construído em Java para fins didáticos e práticos, permitindo operações básicas como criar conta, depósito, saque com limite diário, transferência, listagem, importação de contas e registro de extrato em CSV.


Para funcionar recompile todos os arquivos Java do projeto para garantir que todas as classes estejam compiladas !!

```
  javac -d bin $(find ./project -name "*.java")
```

## Funcionalidades

- Criar contas com titular e saldo inicial.
- Depositar valores em contas.
- Sacar valores com bloqueio para limite diário (ex: R$1000/dia).
- Transferir valores entre contas diferentes.
- Listar contas ordenadas por saldo de forma paginada.
- Pesquisar contas por titular (case-insensitive) com paginação.
- Importar contas de arquivo CSV com validação e relatório de erros.
- Registrar extrato de operações (data/hora, tipo, valores) em arquivo CSV.
- Salvar e carregar dados de contas em arquivo CSV.
- Registro de logs para auditoria e debug.

## Estrutura do Projeto

- `Banco.java`: Lógica principal do banco incluindo controle de contas, limite diário, importação e listagens.
- `Conta.java`: Classe modelo para representação de conta bancária.
- `CrudUtils.java`: Utilitário para operações CRUD (criar, depositar, sacar, transferir, listar).
- `MiniBanco.java`: Interface em linha de comando (CLI) para interação com o usuário.
- `Extrato.java`: Geração e salvamento do extrato de operações.
- `main.java`: Classe principal para iniciar a aplicação e configurar logs.

## Use o menu interativo para realizar operações bancárias.
## Importação de Contas
- Utilize arquivos CSV com formato: `numero;titular;saldo` TEM QUE SER CAMINHO + NOME + EXTENSAO ! 
- O sistema valida formato, saldos negativos, duplicidade e gera relatório de erros no console.

## Logs e Extrato
- Logs são gravados no arquivo `app.log`.
- Extrato das operações financeiras são salvos em `project/arquivos/extrato-<DATA>.csv`.

## Limite Diário de Saque
- Configurado para R$ 1000,00 por dia por conta.
- Saques acima do limite são bloqueados com mensagem de erro.
