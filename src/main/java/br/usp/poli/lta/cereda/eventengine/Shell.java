/**
 * ------------------------------------------------------
 *    Laboratório de Linguagens e Técnicas Adaptativas
 *       Escola Politécnica, Universidade São Paulo
 * ------------------------------------------------------
 *
 * This program is free software: you can redistribute it
 * and/or modify  it under the  terms of the  GNU General
 * Public  License  as  published by  the  Free  Software
 * Foundation, either  version 3  of the License,  or (at
 * your option) any later version.
 *
 * This program is  distributed in the hope  that it will
 * be useful, but WITHOUT  ANY WARRANTY; without even the
 * implied warranty  of MERCHANTABILITY or FITNESS  FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 *
 */
package br.usp.poli.lta.cereda.eventengine;

import br.usp.poli.lta.cereda.eventengine.model.Engine;
import br.usp.poli.lta.cereda.eventengine.model.Event;
import br.usp.poli.lta.cereda.eventengine.util.EngineReader;
import br.usp.poli.lta.cereda.eventengine.util.Pair;
import br.usp.poli.lta.cereda.eventengine.util.TableUtils;
import br.usp.poli.lta.cereda.eventengine.util.YAMLReader;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.mvel2.MVEL;

/**
 * Implementa uma sessão de consulta.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class Shell {

    // tipos de ações da sessão
    public enum Action {
        READ_ENGINE,
        READ_EVENTS
    }

    /**
     * Método principal.
     *
     * @param args Argumentos de linha de comando.
     */
    public static void main(String[] args) {

        // motor de eventos da sessão
        Engine engine = null;

        // novo leitor de linhas do terminal
        Scanner scanner = new Scanner(System.in);

        // variáveis auxiliares
        boolean status;
        long counter = 0;

        // imprime cabeçalho
        System.out.println(draw());
        System.out.println("Laboratório de Técnicas e Linguagens Adaptativas");
        System.out.println("Escola Politécnica, Universidade de São Paulo");
        System.out.println("Versão 1.0\n");

        // verifica se não
        // existem parâmetros
        ensure(args);

        // sessão do terminal
        do {

            // faz a leitura do comando
            // e o processa ad nauseam
            Pair<Boolean, Engine> pair
                    = handle(prompt(scanner, ++counter), engine);

            // atribui o status e
            // o novo motor de eventos
            status = pair.getFirst();
            engine = pair.getSecond();

        } while (status);

        // mensagem final
        System.out.println("Isso é tudo, pessoal!");

    }

    /**
     * Exibe o prompt de comando.
     *
     * @param scanner Leitor da linha.
     * @param counter Contador da linha.
     * @return Prompt de comando.
     */
    private static String prompt(Scanner scanner, long counter) {
        System.out.print(String.format("[%d] > ", counter));
        return scanner.nextLine();
    }

    /**
     * Realiza a manipulação da linha de comando.
     *
     * @param line Linha de comando.
     * @param engine Motor de eventos.
     * @return Valor lógico indicando se a sessão deve continuar e uma nova
     * instância do motor de eventos.
     */
    private static Pair<Boolean, Engine> handle(String line, Engine engine) {

        // caso seja um comando para
        // sair da sessão, encerra
        if (line.equals(":quit")) {
            return new Pair<>(false, engine);
        }

        try {

            // define os métodos de carregamento
            // de um arquivo YAML para dentro
            // da sessão do usuário
            Map<String, Object> map = new HashMap<>();
            map.put("load", Shell.class.getMethod("load", String.class));
            map.put("query", Shell.class.getMethod("query", String.class));

            // verifica se o motor de eventos
            // é uma referência válida
            if (engine != null) {

                // insere os atributos do motor
                // de eventos para análise textual
                map.put("engine", engine);
                map.put("rules", engine.getRules());
                map.put("configuration", engine.getConfiguration());
                map.put("events", engine.getOutputEvents());
            }

            // executa a avaliação MVEL e
            // imprime a linha de resultado
            Object result = MVEL.eval(line, map);
            System.out.println(prettify(result));

            // se o resultado da execução for um
            // par ordenado, estamos na operação
            // de carregamento de arquivo
            if (result instanceof Pair) {

                // obtém a referência
                // do par ordenado
                Pair<Shell.Action, File> pair
                        = (Pair<Shell.Action, File>) result;

                // obtém a referência ao
                // arquivo retornado no par
                File file = pair.getSecond();

                // classe para leitura da
                // especificação ddo motor
                // de eventos
                EngineReader reader = new YAMLReader();

                // verifica qual operação
                // será realizada
                switch (pair.getFirst()) {

                    // leitura da especificação
                    // de um motor de eventos
                    case READ_ENGINE:

                        // realiza a leitura da
                        // especificação e cria
                        // um motor de eventos
                        // temporário
                        Engine attempt = reader.toEngine(file);

                        // o novo motor de eventos
                        // está pronto para uso
                        if (attempt != null) {

                            // atribui o novo motor
                            // de eventos à referência
                            engine = attempt;

                            // imprime mensagem de sucesso
                            System.out.println("Novo motor de eventos"
                                    + " carregado com sucesso.");
                        } else {

                            // ocorreu um erro
                            System.out.println("Ocorreu um erro no"
                                    + " carregamento do novo motor"
                                    + " de eventos.");
                        }

                        // encerra o bloco de leitura
                        // de um arquivo contendo a
                        // especificação de um motor
                        // de eventos
                        break;

                    // leitura de um arquivo contendo
                    // a especificação de uma lista
                    // de eventos
                    case READ_EVENTS:

                        // é necessário que exista uma
                        // referência válida de um motor
                        // de eventos
                        if (engine != null) {

                            // realiza a leitura da
                            // lista de eventos
                            List<Event> events = reader.toEvents(file);

                            // exibe mensagem
                            System.out.println("Lista de eventos carregada"
                                    + " com sucesso.");

                            // exibe quantos eventos serão processados
                            System.out.println(String.format("Processando %d"
                                    + " evento%s, aguarde...", events.size(),
                                    (events.size() != 1 ? "s" : "")));

                            // pula uma linha para
                            // desenhar a tabela de
                            // execução
                            System.out.println();

                            // imprime o cabeçalho da
                            // tabela de execução
                            System.out.println(TableUtils.header());

                            // contador do evento
                            // corrente no motor
                            int counter = 0;

                            // submete cada evento ao
                            // motor de eventos existente
                            for (Event event : events) {

                                // consome o evento corrente
                                boolean task = engine.consume(event);

                                // exibe a célula com o
                                // resultado parcial
                                System.out.println(TableUtils.line(++counter,
                                        event.getAttributes().toString(),
                                        task));
                            }

                            // pula uma linha para
                            // separar a saída
                            System.out.println();

                        } else {

                            // motor de eventos não foi
                            // definido, reportar erro
                            System.out.println("Não é possível processar a"
                                    + " lista de eventos (motor não"
                                    + " definido).");

                        }

                        // encerra o bloco de leitura
                        // da especificação de uma
                        // lista de eventos
                        break;
                }

            }

            // se a referência ao motor
            // de eventos é válida, é
            // possível submeter eventos
            if (engine != null) {

                // se o resultado da avaliação
                // for um mapa, temos um evento
                // potencial a processar
                if (result instanceof HashMap) {

                    // cria-se um novo evento
                    Event event = new Event();

                    // preenche os atributos
                    // do evento a partir da
                    // avaliação anterior
                    ((HashMap) result).forEach((Object key, Object value) -> {
                        event.put(key.toString(), value);
                    });

                    // exibe mensagem de submissão
                    System.out.println("Submetendo evento ao"
                            + " motor de eventos...");

                    // processa o evento propriamente
                    // dito pelo motor de eventos
                    boolean consume = engine.consume(event);

                    // imprime o resultado do
                    // processamento do evento
                    System.out.println(String.format("O motor de eventos"
                            + " %s processar o evento informado.",
                            consume ? "conseguiu" : "não conseguiu"));
                }
            }
        } catch (Exception nothandled) {

            // ocorreu um erro na avaliação MVEL
            // da linha digitada na sessão
            System.out.println("Você tem certeza de que"
                    + " a linha está correta?");
        }

        // prosseguir com a interação
        // com o usuário
        return new Pair<>(true, engine);
    }

    /**
     * Retorna o arquivo de acordo com a referência informada e ação de sessão
     * correspondente para leitura de uma especificação de motor de eventos.
     *
     * @param reference Referência.
     * @return Arquivo correspondente e ação de sessão para motor de eventos.
     */
    public static Pair<Shell.Action, File> load(String reference) {
        return new Pair<>(Shell.Action.READ_ENGINE, new File(reference));
    }

    /**
     * Retorna o arquivo de acordo com a referência informada e ação de sessão
     * correspondente para leitura de uma especificação de lista de eventos.
     *
     * @param reference Referência.
     * @return Arquivo correspondente e ação de sessão para lista de eventos.
     */
    public static Pair<Shell.Action, File> query(String reference) {
        return new Pair<>(Shell.Action.READ_EVENTS, new File(reference));
    }

    /**
     * Obtém o logotipo da sessão.
     *
     * @return Logotipo da sessão.
     */
    private static String draw() {
        StringBuilder sb = new StringBuilder();
        sb.append("                        __                      _          \n");
        sb.append("  ___ _   _____  ____  / /_   ___  ____  ____ _(_)___  ___ \n");
        sb.append(" / _ \\ | / / _ \\/ __ \\/ __/  / _ \\/ __ \\/ __ `/ / __ \\/ _ \\\n");
        sb.append("/  __/ |/ /  __/ / / / /_   /  __/ / / / /_/ / / / / /  __/\n");
        sb.append("\\___/|___/\\___/_/ /_/\\__/   \\___/_/ /_/\\__, /_/_/ /_/\\___/ \n");
        sb.append("                                      /____/               ");
        return sb.toString();
    }

    /**
     * Obtém uma representação mais amigável do objeto retornado na avaliação.
     *
     * @param result Objeto retornado na avaliação.
     * @return Representação mais amigável, contendo o par ordenado tipo/valor.
     */
    private static Pair<String, Object> prettify(Object result) {
        return new Pair<>(result.getClass().getSimpleName().toUpperCase(),
                result.toString());
    }

    /**
     * Verifica se não existem argumentos.
     *
     * @param args Argumentos.
     */
    private static void ensure(String[] args) {

        // verifica se existem argumentos
        // de linha de comando
        if (args.length != 0) {

            // imprime mensagem de erro
            System.out.println("A sessão do usuário não admite"
                    + " parâmetros de linha de comando.");

            // encerra o programa
            System.exit(0);
        }
    }

}
