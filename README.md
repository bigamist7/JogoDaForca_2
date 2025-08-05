# JogoDaForca2

**JogoDaForca2** é uma versão melhorada do clássico jogo da forca em Java, jogado via consola. Esta versão inclui registo de estatísticas individuais e gerais, cálculo de tempo de jogo, e destaque do melhor jogador com base na performance.

---

##  Novidades na versão 2

-  Registo de estatísticas individuais por jogador
-  Ficheiro de estatísticas gerais com histórico de todos os jogos
-  Destaque automático do melhor jogador (vitória mais rápida)
-  Cálculo e exibição do tempo de jogo por partida
-  Validação de letras e palavras
-  Possibilidade de adicionar novas palavras ao jogo

---

##  Estrutura do Projeto


```

JogoDaForca2/ ├── src/ │ ├── Main.java │ ├── JogoForca.java │ ├── GestorPalavras.java │ ├── palavras.txt │ ├── estatisticas_gerais.txt │ └── estatisticas_.txt └── README.md

```

---

## ▶️ Como executar

1. Certifica-te de que tens o **Java JDK** instalado.
2. Abre o terminal ou consola.
3. Navega até à pasta do projeto.
4. Compila os ficheiros:
  ```
   bash
   javac src/*.java
```

5.  Executa o jogo:
    
    ```bash
    java -cp src Main
    
    ```
    

----------

##  Estatísticas

### Estatísticas individuais (`estatisticas_<nome>.txt`)

-   Palavra jogada
-   Resultado (vitória ou derrota)
-   Letras tentadas
-   Número de letras corretas e incorretas
-   Tempo de jogo em segundos

### Estatísticas gerais (`estatisticas_gerais.txt`)

-   Histórico de todos os jogadores
-   Registo de tempo de jogo por partida
-   Apenas o melhor jogador (vitória mais rápida) é mostrado no início

----------

##  Funcionalidades adicionais

-   Validação de entrada (apenas letras)
-   Evita letras repetidas
-   Mostra palavra parcialmente revelada
-   Permite adicionar novas palavras ao ficheiro `palavras.txt` diretamente pelo menu

----------

##  Requisitos

-   Java 8 ou superior
-   Ambiente de execução via consola
-   Ficheiros de texto devem estar na pasta `src`

----------

##  Autor

Desenvolvido por **João Pinto**  
Versão: **JogoDaForca2**  
Data: **Agosto 2025**

----------

##  Contribuições

Sugestões, melhorias ou dúvidas? Sinta-se à vontade para contribuir ou enviar feedback.

