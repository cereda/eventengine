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
 * Esboço do motor de eventos a partir de uma especificação YAML.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class YAMLEngine {

    // identificador
    private String identifier;

    // lista de regras
    private List<YAMLRule> rules;

    // mapa de configuração
    private Map<String, Object> configuration;

    /**
     * Construtor.
     */
    public YAMLEngine() {
    }

    /**
     * Obtém o identificador.
     *
     * @return Identificador.
     */
    public String getIdentifier() {
        return identifier == null ? "foo" : identifier;
    }

    /**
     * Define o identificador.
     *
     * @param identifier Identificador.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Obtém a lista de regras.
     *
     * @return Lista de regras.
     */
    public List<YAMLRule> getRules() {
        return rules == null ? new ArrayList<>() : rules;
    }

    /**
     * Define a lista de regras.
     *
     * @param rules Lista de regras.
     */
    public void setRules(List<YAMLRule> rules) {
        this.rules = rules;
    }

    /**
     * Obtém o mapa de configuração.
     *
     * @return Mapa de configuração.
     */
    public Map<String, Object> getConfiguration() {
        return configuration == null ? new HashMap<>() : configuration;
    }

    /**
     * Define o mapa de configuração.
     *
     * @param configuration Mapa de configuração.
     */
    public void setConfiguration(Map<String, Object> configuration) {
        this.configuration = configuration;
    }

    /**
     * Obtém o código hash.
     *
     * @return Código hash.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.identifier);
        hash = 17 * hash + Objects.hashCode(this.rules);
        hash = 17 * hash + Objects.hashCode(this.configuration);
        return hash;
    }

    /**
     * Verifica se dois objetos são iguais.
     *
     * @param object Objeto a ser comparado.
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
        final YAMLEngine that = (YAMLEngine) object;
        if (!Objects.equals(this.identifier, that.identifier)) {
            return false;
        }
        if (!Objects.equals(this.rules, that.rules)) {
            return false;
        }
        return Objects.equals(this.configuration, that.configuration);
    }

}
