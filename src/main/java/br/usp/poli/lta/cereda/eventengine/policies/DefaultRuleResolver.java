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

import br.usp.poli.lta.cereda.eventengine.model.Rule;
import java.util.Collection;

/**
 * Implementa a resolução padrão de regras.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class DefaultRuleResolver implements RuleResolver {

    /**
     * Determina qual das regras válidas será aplicada no contexto corrente.
     * Neste caso, caso existam várias regras, uma regra arbitrária da coleção
     * será retornada (decisão dependente da coleção subjacente).
     *
     * @param matches Coleção de regras.
     * @return Regra a ser aplicada no contexto corrente.
     */
    @Override
    public Rule select(Collection<Rule> matches) {
        return matches.iterator().next();
    }

    /**
     * Obtém a representação textual da resolução padrão de regras.
     * @return Representação textual da resolução padrão de regras.
     */
    @Override
    public String toString() {
        return "Padrão (elemento arbitrário)";
    }

}
