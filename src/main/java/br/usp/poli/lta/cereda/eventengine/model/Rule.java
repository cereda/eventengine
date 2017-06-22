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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Implementa uma regra do motor de eventos.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class Rule extends Table {

    // conjunto de condições
    private Set<String> conditions;

    // conjunto de ações
    private Set<String> actions;

    /**
     * Construtor.
     */
    public Rule() {
        conditions = new HashSet<>();
        actions = new HashSet<>();
    }

    /**
     * Obtém as condições da regra.
     *
     * @return Condições da regra.
     */
    public Set<String> getConditions() {
        return conditions;
    }

    /**
     * Define as condições da regra.
     *
     * @param conditions Condições da regra.
     */
    public void setConditions(Set<String> conditions) {
        this.conditions = conditions;
    }

    /**
     * Obtém as ações da regra.
     *
     * @return Ações da regra.
     */
    public Set<String> getActions() {
        return actions;
    }

    /**
     * Define as ações da regra.
     *
     * @param actions Ações da regra.
     */
    public void setActions(Set<String> actions) {
        this.actions = actions;
    }

    /**
     * Adiciona uma condição ao conjunto de condições.
     *
     * @param condition Condição a ser adicionada ao conjunto de condições.
     * @return Valor lógico indicando se a condição foi adicionada corretamente
     * no conjunto de condições.
     */
    public boolean addCondition(String condition) {
        return conditions.add(condition);
    }

    /**
     * Remove uma condição do conjunto de condições.
     *
     * @param condition Condição a ser removida do conjunto de condições.
     * @return Valor lógico indicando se a condição foi removida corretamente do
     * conjunto de condições.
     */
    public boolean removeCondition(String condition) {
        return conditions.remove(condition);
    }

    /**
     * Remove todas as condições do conjunto de condições.
     */
    public void clearConditions() {
        conditions.clear();
    }

    /**
     * Verifica se o conjunto de condições contém a condição informada.
     *
     * @param condition Condição a ser verificada.
     * @return Valor lógico indicando se a condição informada pertence ao
     * conjunto de condições.
     */
    public boolean containsCondition(String condition) {
        return conditions.contains(condition);
    }

    /**
     * Adiciona uma ação ao conjunto de ações.
     *
     * @param action Ação a ser adicionada.
     * @return Valor lógico indicando se a ação foi adicionada corretamente ao
     * conjunto de ações.
     */
    public boolean addAction(String action) {
        return actions.add(action);
    }

    /**
     * Remove a ação do conjunto de ações.
     *
     * @param action Ação a ser removida do conjunto de ações.
     * @return Valor lógico indicando se a ação foi removida corretamente do
     * conjunto de ações.
     */
    public boolean removeAction(String action) {
        return actions.remove(action);
    }

    /**
     * Remove todas as ações do conjunto de ações.
     */
    public void clearActions() {
        actions.clear();
    }

    /**
     * Verifica se a ação informada está presente no conjunto de ações.
     *
     * @param action Ação a ser verificada.
     * @return Valor lógico indicando se a ação informada está presente no
     * conjunto de ações.
     */
    public boolean containsAction(String action) {
        return actions.contains(action);
    }

    /**
     * Obtém a representação textual de uma regra.
     *
     * @return Representação textual de uma regra.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Regra: {").append("\n");

        // condições
        sb.append("\t").append("condições: [").append("\n");
        if (conditions.isEmpty()) {
            sb.append("\t\t").append("<não há condições>").append("\n");
        } else {
            conditions.forEach((String condition) -> {
                sb.append("\t\t").append("- ");
                sb.append(condition).append("\n");
            });
        }
        sb.append("\t").append("]").append("\n");

        // ações
        sb.append("\t").append("ações: [").append("\n");
        if (actions.isEmpty()) {
            sb.append("\t\t").append("<não há ações>").append("\n");
        } else {
            actions.forEach((String action) -> {
                sb.append("\t\t").append("- ");
                sb.append(action).append("\n");
            });
        }
        sb.append("\t").append("]").append("\n");

        // atributos
        sb.append("\t").append("atributos: [").append("\n");
        if (getAttributes().isEmpty()) {
            sb.append("\t\t").append("<não há atributos>").append("\n");
        } else {
            getAttributes().forEach((String key, Object value) -> {
                sb.append("\t\t").append("- ").append(key);
                sb.append(" -> ").append(value).append("\n");
            });
        }
        sb.append("\t").append("]").append("\n");
        sb.append("}");

        return sb.toString();
    }

    /**
     * Determina o código hash do objeto corrente.
     *
     * @return Valor inteiro que determina o código hash do objeto corrente.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.conditions);
        hash = 59 * hash + Objects.hashCode(this.actions);
        return hash;
    }

    /**
     * Verifica se o objeto informado é igual ao objeto corrente.
     *
     * @param object Objeto a ser verificado.
     * @return Valor lógico que determina se o objeto informado é igual ao
     * objeto corrente.
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
        final Rule that = (Rule) object;
        if (!Objects.equals(this.conditions, that.conditions)) {
            return false;
        }
        return Objects.equals(this.actions, that.actions);
    }

}
