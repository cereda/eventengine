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
package br.usp.poli.lta.cereda.eventengine.policies;

import br.usp.poli.lta.cereda.eventengine.model.Event;
import java.util.Comparator;

/**
 * Implementa o comparador padrão de eventos.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class DefaultEventComparator implements Comparator<Event> {

    /**
     * Compara dois eventos e determina a prioridade entre eles. Neste caso, a
     * ordem não será alterada, isto é, ambos eventos têm mesma prioridade.
     *
     * @param a Primeiro evento.
     * @param b Segundo evento.
     * @return Zero, significando que ambos eventos têm mesma prioridade.F
     */
    @Override
    public int compare(Event a, Event b) {
        return 0;
    }

    /**
     * Obtém uma representação textual do comparador de eventos.
     * @return 
     */
    @Override
    public String toString() {
        return "Padrão (mesma prioridade)";
    }

}
