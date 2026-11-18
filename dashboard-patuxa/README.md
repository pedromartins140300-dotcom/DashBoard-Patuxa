# Dashboard PATUXA — painel de gestão para pet shop

Projeto em **dupla** da disciplina **Projeto Aplicado I** (UniSENAI, Análise e Desenvolvimento de Sistemas).

## Problema
O gestor de um pet shop precisa acompanhar o movimento do negócio (atendimentos, fila de espera, serviços mais procurados, desempenho da equipe) em um só lugar. O objetivo foi criar um painel que reúna essas informações e calcule estatísticas simples sobre os atendimentos do mês.

## O que foi feito
- **Dashboard PATUXA** (HTML, CSS e JavaScript): painel do gestor com visão geral, alertas, atendimentos por dia, horários de pico, serviços mais realizados, fila de espera, produtividade por profissional, avaliações e uma aba de **Estatística**.
- **Módulo estatístico em Java:** calcula **média, mediana e moda** dos atendimentos por dia do mês, mostra o resultado no console e envia os números para o dashboard.

## Como Java e dashboard se conectam
1. `Main.java` define os atendimentos de cada dia do mês e usa a classe `EstatisticaAtendimentos` para calcular média, mediana e moda.
2. `ExportadorHTML.java` grava o resultado no arquivo `estatistica_java.js`, ao lado do HTML.
3. O dashboard carrega esse arquivo e o JavaScript mostra os números na aba **Estatística**.
4. O programa abre o dashboard no navegador.

Para atualizar os dados, altere o vetor `atendimentosDoMes` em `Main.java` e execute de novo.

## Resultado do cálculo (dados de exemplo, 30 dias)
```
Dias analisados: 30
Total de atendimentos: 742
Média:   24.73 atendimentos por dia
Mediana: 24.5 atendimentos por dia
Moda:    [24] atendimentos por dia (aparece 8 vezes)
```

## Estrutura do repositório
```
dashboard-patuxa/
├── src/
│   ├── Main.java
│   ├── EstatisticaAtendimentos.java
│   ├── ExportadorHTML.java
│   ├── patuxa_dashboard_claude.html
│   └── estatistica_java.js        # gerado pelo Java
└── README.md
```

## Como executar
Requer JDK instalado (testado com JDK 21).

**Só ver o dashboard:** abra `src/patuxa_dashboard_claude.html` no navegador.

**Rodar o cálculo em Java:**
```bash
cd src
javac *.java
java Main
```
O programa imprime a estatística, atualiza o `estatistica_java.js` e abre o dashboard. Se o navegador não abrir sozinho, abra o HTML manualmente.

## Ferramentas e uso de IA
- **Claude:** gerou o código (Java, HTML, CSS e JavaScript) e o dashboard, a partir dos prompts da dupla.
- **ChatGPT:** ajudou a escrever o prompt.
- **Lovable:** usado para comparar o resultado com o do Claude. As outras ferramentas testadas não entregaram o que foi pedido (possivelmente por limite de créditos), então o Claude foi a escolhida.

## Minha participação
Trabalho em dupla. Eu dividi as etapas do trabalho entre nós e fiquei responsável por pedir à IA o que o projeto precisava (prompts, ajustes e comparação entre ferramentas).

## Aprendizados
- Dividir o trabalho em etapas antes de pedir algo à IA melhora o resultado.
- Comparar ferramentas de IA para a mesma tarefa: nem todas entregam o mesmo, e o resultado depende do prompt e dos recursos disponíveis.
- Conectar um programa Java a uma página web por meio de um arquivo gerado (`estatistica_java.js`).

## Autor
Pedro Martins Carara · [LinkedIn](https://www.linkedin.com/in/pedro-martins-013b7644a) · Estudante de ADS, UniSENAI
