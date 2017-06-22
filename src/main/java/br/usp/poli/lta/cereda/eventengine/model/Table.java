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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementa uma tabela contendo atributos do tipo chave/valor.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public abstract class Table {

    // mapa de atributos da tabela
    private Map<String, Object> attributes;

    /**
     * Construtor.
     */
    public Table() {
        attributes = new HashMap<>();
    }

    /**
     * Obtém o número de elementos do mapa de atributos.
     *
     * @return Número de elementos do mapa de atributos.
     */
    public int size() {
        return attributes.size();
    }

    /**
     * Verifica se o mapa está vazio.
     *
     * @return Valor lógico informado se o mapa está vazio.
     */
    public boolean isEmpty() {
        return attributes.isEmpty();
    }

    /**
     * Verifica se a chave existe no mapa.
     *
     * @param key Chave a ser verificada.
     * @return Valor lógico indicando se a chave existe no mapa.
     */
    public boolean containsKey(String key) {
        return attributes.containsKey(key);
    }

    /**
     * Verifica se o valor existe no mapa.
     *
     * @param value Valor a ser verificado.
     * @return Valor lógico indicando se o valor informado existe no mapa.
     */
    public boolean containsValue(Object value) {
        return attributes.containsValue(value);
    }

    /**
     * Obtém o valor referente à chave informada.
     *
     * @param key Chave informada.
     * @return Valor referente à chave informada.
     */
    public Object get(String key) {
        return attributes.get(key);
    }

    /**
     * Insere o par chave/valor no mapa.
     *
     * @param key Chave.
     * @param value Valor.
     * @return Valor corrente de inserção.
     */
    public Object put(String key, Object value) {
        return attributes.put(key, value);
    }

    /**
     * Remove o objeto associado à chave informada.
     *
     * @param key Chave.
     * @return Objeto associado à chave informada.
     */
    public Object remove(String key) {
        return attributes.remove(key);
    }

    /**
     * Remove todas as entradas do mapa de atributos.
     */
    public void clear() {
        attributes.clear();
    }

    /**
     * Retorna o conjunto de chaves do mapa de atributos.
     *
     * @return Conjunto de chaves do mapa de atributos.
     */
    public Set<String> keySet() {
        return attributes.keySet();
    }

    /**
     * Retorna a coleção de valores do mapa de atributos.
     *
     * @return Coleção de valores do mapa de atributos.
     */
    public Collection<Object> values() {
        return attributes.values();
    }

    /**
     * Retorna todos os pares chave/valor do mapa de atributos.
     *
     * @return Coleção de pares chave/valor do mapa de atributos.
     */
    public Set<Map.Entry<String, Object>> entrySet() {
        return attributes.entrySet();
    }

    /**
     * Obtém o valor associado à chave informada ou um valor padrão.
     *
     * @param key Chave informada.
     * @param value Valor padrão a ser retornado.
     * @return Valor associado à chave informada ou um valor padrão.
     */
    public Object getOrDefault(String key, Object value) {
        return attributes.getOrDefault(key, value);
    }

    /**
     * Insere o valor informado caso a entrada não exista no mapa.
     *
     * @param key Chave.
     * @param value Valor.
     * @return Objeto corrente associado à chave informada.
     */
    public Object putIfAbsent(String key, Object value) {
        return attributes.putIfAbsent(key, value);
    }

    /**
     * Retorna o código hash do objeto corrente.
     *
     * @return Valor inteiro associado ao código hash do objeto corrente.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.attributes);
        return hash;
    }

    /**
     * Verifica se o objeto informado é igual ao objeto corrente.
     *
     * @param object Objeto a ser verificado.
     * @return Valor lógico que informa se o objeto é igual ao objeto corrente.
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
        final Table that = (Table) object;
        return Objects.equals(this.attributes, that.attributes);
    }

    /**
     * Verifica se o par chave/valor informado é o mesmo que está no mapa.
     *
     * @param key Chave.
     * @param value Valor.
     * @return Valor lógico que determina se o par chave/valor informado é o
     * mesmo que está no mapa de atributos.
     */
    public boolean compareAttribute(String key, Object value) {
        return containsKey(key) ? get(key).equals(value) : false;
    }

    /**
     * Retorna uma representação textual da tabela.
     *
     * @return Representação textual da tabela.
     */
    @Override
    public String toString() {
        return print("Tabela");
    }

    /**
     * Obtém o mapa de atributos.
     *
     * @return Mapa de atributos.
     */
    public Map<String, Object> getAttributes() {
        return attributes;
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
     * Retorna uma representação textual do mapa de atributos.
     *
     * @param title Título a ser incluído na representação textual.
     * @return Representação textual do mapa de atributos.
     */
    protected String print(String title) {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append(": {").append("\n");
        getAttributes().forEach((String key, Object value) -> {
            sb.append("\t").append("- ").append(key).append(" -> ");
            sb.append(value.toString()).append("\n");
        });
        sb.append("}");
        return sb.toString();
    }

}
