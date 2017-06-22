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
package br.usp.poli.lta.cereda.eventengine.util;

import br.usp.poli.lta.cereda.eventengine.model.Engine;
import br.usp.poli.lta.cereda.eventengine.model.Event;
import java.io.File;
import java.util.List;

/**
 * Interface para definição de um leitor de motor de eventos.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public interface EngineReader {

    /**
     * Retorna um motor de eventos a partir do arquivo informado.
     *
     * @param file Arquivo informado.
     * @return Motor de eventos.
     */
    public Engine toEngine(File file);

    /**
     * Retorna uma lista de eventos a partir do arquivo informado.
     *
     * @param file Arquivo informado.
     * @return Lista de eventos.
     */
    public List<Event> toEvents(File file);

}
