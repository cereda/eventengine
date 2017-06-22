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
package br.usp.poli.lta.cereda.eventengine.model;

import br.usp.poli.lta.cereda.eventengine.execution.Interpreter;
import br.usp.poli.lta.cereda.eventengine.execution.Transformer;
import br.usp.poli.lta.cereda.eventengine.policies.DefaultEventComparator;
import br.usp.poli.lta.cereda.eventengine.policies.DefaultRuleResolver;
import br.usp.poli.lta.cereda.eventengine.policies.RuleResolver;
import br.usp.poli.lta.cereda.eventengine.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementa o motor de eventos.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.2
 * @since 1.0
 */
public class Engine {

    // identificador do motor de eventos
    private final String identifier;

    // configuração e coleção de regras
    private Configuration configuration;
    private Collection<Rule> rules;

    // lista de eventos de saída, potencialmente vazia
    private final List<Event> outputEvents;

    // resolução de regras válidas
    private RuleResolver resolver;

    // comparador de eventos de saída
    private Comparator<Event> outputComparator;

    // mapa do ambiente de execução
    private Map<String, Object> environment;

    // mapa dos métodos
    private Map<String, Object> methods;

    // motor de eventos para
    // manipulação de eventos de saída
    private Optional<Engine> pipeline;

    /**
     * Construtor.
     *
     * @param identifier Identificador do motor de eventos.
     */
    public Engine(String identifier) {

        // atribui a identificação ao
        // novo motor de eventos
        this.identifier = identifier;

        // cria uma nova lista de
        // eventos de saída
        outputEvents = new ArrayList<>();

        // atribui a resolução padrão de
        // regras ao motor de eventos
        resolver = new DefaultRuleResolver();

        // define a comparação padrão de eventos
        // a ser aplicada na lista de eventos de saída
        outputComparator = new DefaultEventComparator();

        // define um novo mapa contendo
        // o ambiente de execução
        environment = new HashMap<>();

        // define um novo mapa
        // contendo os métodos
        methods = new HashMap<>();

        // define o motor de eventos de
        // saída como vazio
        pipeline = Optional.empty();

    }

    /**
     * Define a configuração corrente do motor de eventos.
     *
     * @param configuration Configuração.
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Define a coleção de regras do motor de eventos.
     *
     * @param rules Coleção de regras.
     */
    public void setRules(Collection<Rule> rules) {
        this.rules = rules;
    }

    /**
     * Define a resolução de regras do motor de eventos.
     *
     * @param resolver Resolução de regras.
     */
    public void setResolver(RuleResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * Define a comparação de eventos de saída.
     *
     * @param outputComparator Comparação de eventos de saída.
     */
    public void setOutputComparator(Comparator<Event> outputComparator) {
        this.outputComparator = outputComparator;
    }

    /**
     * Obtém o identificador do motor de eventos.
     *
     * @return Identificador do motor de eventos.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Obtém a configuração corrente do motor de eventos.
     *
     * @return Configuração corrente do motor de eventos.
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Obtém a coleção de regras do motor de eventos.
     *
     * @return Coleção de regras do motor de eventos.
     */
    public Collection<Rule> getRules() {
        return rules;
    }

    /**
     * Obtém a lista de eventos de saída.
     *
     * @return Lista de eventos de saída.
     */
    public List<Event> getOutputEvents() {
        return outputEvents;
    }

    /**
     * Remove todos os elementos da lista de eventos de saída.
     */
    public void clearOutputEvents() {
        outputEvents.clear();
    }

    /**
     * Calcula a próxima configuração, dados a configuração e evento correntes.
     *
     * @param event Evento a ser analisado.
     * @return Próxima configuração do motor de eventos.
     */
    private Pair<Configuration, Boolean> delta(Event event) {

        // analisa quais regras se aplicam, dados
        // a configuração e eventos correntes
        Collection<Rule> matches = rules.stream().filter((Rule rule) -> rule.
                getConditions().stream().allMatch(new Interpreter(configuration,
                        event)::apply)).collect(Collectors.toList());

        // não há regras a aplicar, a configuração
        // corrente se mantém e é informado que o
        // motor de eventos não pôde consumir o
        // evento corrente
        if (matches.isEmpty()) {
            return new Pair<>(configuration, false);
        }

        // obtém a regra escolhida de acordo com
        // a resolução de regras definida
        Rule rule = resolver.select(matches);

        // instancia uma transformação de configuração,
        // dados a configuração, evento e conjunto de ações
        // da regra correntes
        Transformer transformer = new Transformer(configuration,
                event, rule.getActions(), environment, methods);

        // efetivamente aplica a transformação,
        // gerando uma nova configuração para
        // o motor de eventos
        transformer.transform();

        // se existem eventos de saída, estes são
        // adicionados à lista existente e ordenados
        // de acordo com o comparador definido
        if (!transformer.getOutputEvents().isEmpty()) {
            outputEvents.addAll(transformer.getOutputEvents());
            Collections.sort(outputEvents, outputComparator);

            // se existe motor de eventos para
            // manipulação de eventos de saída,
            // dispara a avaliação
            pipeline.ifPresent((Engine engine) -> {
                outputEvents.forEach(engine::consume);
                outputEvents.clear();
            });
        }

        // retorna a nova configuração e um sinalizador
        // informando de que o passo computacional foi
        // realizado com sucesso
        return new Pair(transformer.getConfiguration(), true);
    }

    /**
     * Consome o evento.
     *
     * @param event Evento a ser consumido pelo motor de eventos.
     * @return Valor lógico indicando se o consumo do evento ocorreu
     * corretamente, ou se o motor de eventos não pôde processar o evento
     * informado.
     */
    public boolean consume(Event event) {

        // aplica-se a função de delta,
        // utilizando o evento corrente
        // como parâmetro
        Pair<Configuration, Boolean> pair = delta(event);

        // atualiza a configuração corrente
        // (pode, potencialmente, ser a mesma)
        configuration = pair.getFirst();

        // retorna o resultado da aplicação
        // do passo computacional
        return pair.getSecond();
    }

    /**
     * Obtém uma representação textual do motor de eventos.
     *
     * @return Representação textual do motor de eventos.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // motor de eventos
        sb.append("Motor de eventos '").append(identifier);
        sb.append("'").append("\n");
        sb.append(repeat('=', 60)).append("\n");

        // conjunto de regras
        sb.append("Conjunto de regras:").append("\n");
        if (rules.isEmpty()) {
            sb.append(repeat('-', 60)).append("\n");
            sb.append("<não há regras>").append("\n");
        } else {
            rules.forEach((Rule rule) -> {
                sb.append(repeat('-', 60)).append("\n");
                sb.append(rule).append("\n");
            });
        }
        sb.append(repeat('=', 60)).append("\n");

        // configuração corrente
        sb.append("Configuração corrente:").append("\n");
        sb.append(repeat('-', 60)).append("\n");
        sb.append(configuration).append("\n");
        sb.append(repeat('=', 60)).append("\n");

        // eventos de saída
        sb.append("Eventos de saída:").append("\n");
        if (outputEvents.isEmpty()) {
            sb.append(repeat('-', 60)).append("\n");
            sb.append("<não há eventos de saída>").append("\n");
        } else {
            outputEvents.forEach((Event event) -> {
                sb.append(repeat('-', 60)).append("\n");
                sb.append(event).append("\n");
            });
        }
        sb.append(repeat('=', 60)).append("\n");

        // políticas de execução
        sb.append("Políticas de execução:").append("\n");
        sb.append(repeat('-', 60)).append("\n");
        sb.append("- Resolução de regras: ").append(resolver).append("\n");
        sb.append("- Comparação de eventos: ");
        sb.append(outputComparator).append("\n");
        sb.append(repeat('=', 60));

        // retorna a representação textual
        return sb.toString();
    }

    /**
     * Repete o símbolo informado um determinado número de vezes.
     *
     * @param symbol Símbolo a ser repetido.
     * @param times Número de repetições.
     * @return Símbolo repetido.
     */
    private String repeat(char symbol, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= times; i++) {
            sb.append(symbol);
        }
        return sb.toString();
    }

    /**
     * Obtém o mapa do ambiente de execução.
     *
     * @return Mapa do ambiente de execução.
     */
    public Map<String, Object> getEnvironment() {
        return environment;
    }

    /**
     * Define o mapa do ambiente de execução.
     *
     * @param environment Mapa do ambiente de execução.
     */
    public void setEnvironment(Map<String, Object> environment) {
        this.environment = environment;
    }

    /**
     * Obtém o mapa de métodos.
     *
     * @return Mapa de métodos.
     */
    public Map<String, Object> getMethods() {
        return methods;
    }

    /**
     * Define o mapa de métodos.
     *
     * @param methods Mapa de métodos.
     */
    public void setMethods(Map<String, Object> methods) {
        this.methods = methods;
    }

    /**
     * Obtém o motor de eventos para eventos de saída.
     *
     * @return Motor de eventos para eventos de saída.
     */
    public Optional<Engine> getPipeline() {
        return pipeline;
    }

    /**
     * Define o motor de eventos para eventos de saída.
     *
     * @param pipeline Motor de eventos para eventos de saída.
     */
    public void setPipeline(Engine pipeline) {
        this.pipeline = Optional.of(pipeline);
    }

}
