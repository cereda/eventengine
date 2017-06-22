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
package br.usp.poli.lta.cereda.eventengine.execution;

import br.usp.poli.lta.cereda.eventengine.model.Configuration;
import br.usp.poli.lta.cereda.eventengine.model.Event;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;

/**
 * Transformador de configurações.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class Transformer {

    // configuração a ser analisada
    private Configuration configuration;

    // evento a ser analisado
    private final Event event;

    // coleção de ações a serem aplicadas
    // sobre a configuração corrente
    private final Collection<String> actions;

    // lista de eventos de saída
    private List<Event> outputEvents;
    
    // mapa contendo dados
    // do ambiente de execução
    private Map<String, Object> environment;
    
    // mapa contendo métodos
    // adicionais
    private Map<String, Object> methods;

    /**
     * Construtor.
     *
     * @param configuration Configuração corrente.
     * @param event Evento corrente.
     * @param actions Coleção de ações obtidas a partir da regra escolhida.
     * @param environment Mapa do ambiente de execução.
     * @param methods Mapa de métodos.
     */
    public Transformer(Configuration configuration,
            Event event, Collection<String> actions,
            Map<String, Object> environment,
            Map<String, Object> methods) {
        this.configuration = configuration;
        this.event = event;
        this.actions = actions;
        this.outputEvents = new ArrayList<>();
        this.environment = environment;
        this.methods = methods;
    }

    /**
     * Realiza a transformação da configuração corrente em uma nova configuração
     * e potencialmente gera eventos de saída.
     */
    public void transform() {
        Map<String, Object> map = new HashMap<>();
        map.put("configuration", configuration.getAttributes());
        map.put("event", event.getAttributes());
        map.put("output", outputEvents);
        map.put("environment", environment);
        
        methods.forEach(map::put);

        VariableResolverFactory variables
                = new MapVariableResolverFactory(map);
        actions.stream().forEach((String expression) -> {
            MVEL.eval(expression, variables);
        });

        configuration = new Configuration();
        configuration.setAttributes((Map<String, Object>) variables.
                getVariableResolver("configuration").getValue());

        outputEvents = (List<Event>) variables.
                getVariableResolver("output").getValue();
    }

    /**
     * Obtém a configuração corrente, potencialmente transformada.
     *
     * @return Configuração corrente, potencialmente transformada.
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Obtém a lista de eventos de saída, potencialmente vazia.
     *
     * @return Lista de eventos de saída, potencialmente vazia.
     */
    public List<Event> getOutputEvents() {
        return outputEvents;
    }

}
