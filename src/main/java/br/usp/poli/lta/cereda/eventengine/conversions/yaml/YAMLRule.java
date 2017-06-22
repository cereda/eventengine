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
package br.usp.poli.lta.cereda.eventengine.conversions.yaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementa uma regra YAML do motor de eventos YAML.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class YAMLRule {

    // lista de condições
    private List<String> conditions;

    // lista de ações
    private List<String> actions;

    // lista de atributos
    private Map<String, Object> attributes;

    /**
     * Construtor.
     */
    public YAMLRule() {
    }

    /**
     * Obtém a lista de condições.
     *
     * @return Lista de condições.
     */
    public List<String> getConditions() {
        return conditions == null ? new ArrayList<>() : conditions;
    }

    /**
     * Define a lista de condições.
     *
     * @param conditions Lista de condições.
     */
    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    /**
     * Obtém a lista de ações.
     *
     * @return Lista de ações.
     */
    public List<String> getActions() {
        return actions == null ? new ArrayList<>() : actions;
    }

    /**
     * Define a lista de ações.
     *
     * @param actions Lista de ações.
     */
    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    /**
     * Obtém o mapa de atributos.
     *
     * @return Mapa de atributos.
     */
    public Map<String, Object> getAttributes() {
        return attributes == null ? new HashMap<>() : attributes;
    }

    /**
     * Define o mapa de atributos.
     *
     * @param attributes Mapa de atributos.
     */
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * Obtém o código hash.
     *
     * @return Código hash.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.conditions);
        hash = 37 * hash + Objects.hashCode(this.actions);
        hash = 37 * hash + Objects.hashCode(this.attributes);
        return hash;
    }

    /**
     * Verifica se dois objetos são iguais.
     *
     * @param object Objeto a ser verificado.
     * @return Valor lógico indicando se os dois objetos são iguais.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        final YAMLRule that = (YAMLRule) object;
        if (!Objects.equals(this.conditions, that.conditions)) {
            return false;
        }
        if (!Objects.equals(this.actions, that.actions)) {
            return false;
        }
        return Objects.equals(this.attributes, that.attributes);
    }

}
