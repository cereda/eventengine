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
import java.util.HashMap;
import java.util.Map;
import org.mvel2.MVEL;

/**
 * Implementa o interpretador de condições.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class Interpreter {

    // configuração a ser analisada
    private final Configuration configuration;

    // evento a ser analisado
    private final Event event;

    /**
     * Construtor.
     *
     * @param configuration Configuração do motor de eventos.
     * @param event Evento corrente a ser analisado.
     */
    public Interpreter(Configuration configuration, Event event) {
        this.configuration = configuration;
        this.event = event;
    }

    /**
     * Verifica se a condição especificada está de acordo com a configuração e
     * evento disponibilizados na instanciação do interpretador.
     *
     * @param condition Condição a ser analisada.
     * @return Valor lógico informando se a condição foi satisfeita.
     */
    public boolean apply(String condition) {
        Map<String, Object> input = new HashMap<>();
        input.put("configuration", configuration.getAttributes());
        input.put("event", event.getAttributes());
        return MVEL.evalToBoolean(condition, input);
    }

}
