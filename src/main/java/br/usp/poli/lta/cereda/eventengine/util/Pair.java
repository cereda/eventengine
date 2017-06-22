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

import java.util.Objects;

/**
 * Implementa um par ordenado.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 * @param <A> Tipo do primeiro elemento do par.
 * @param <B> Tipo do segundo elemento do par.
 */
public class Pair<A, B> {

    // primeiro elemento
    private A first;

    // segundo elemento
    private B second;

    /**
     * Construtor.
     */
    public Pair() {
    }

    /**
     * Construtor paramétrico.
     *
     * @param first Primeiro elemento.
     * @param second Segundo elemento.
     */
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Obtém o primeiro elemento.
     *
     * @return Primeiro elemento.
     */
    public A getFirst() {
        return first;
    }

    /**
     * Define o primeiro elemento.
     *
     * @param first Primeiro elemento.
     */
    public void setFirst(A first) {
        this.first = first;
    }

    /**
     * Obtém o segundo elemento.
     *
     * @return Segundo elemento.
     */
    public B getSecond() {
        return second;
    }

    /**
     * Define o segundo elemento.
     *
     * @param second Segundo elemento.
     */
    public void setSecond(B second) {
        this.second = second;
    }

    /**
     * Obtém o código hash referente ao objeto corrente.
     *
     * @return Valor inteiro referente ao código hash do objeto corrente.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.first);
        hash = 43 * hash + Objects.hashCode(this.second);
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
        final Pair<?, ?> that = (Pair<?, ?>) object;
        if (!Objects.equals(this.first, that.first)) {
            return false;
        }
        return Objects.equals(this.second, that.second);
    }

    /**
     * Obtém a representação textual do par ordenado.
     *
     * @return Representação textual do par ordenado.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(first);
        sb.append(", ").append(second);
        sb.append(")");
        return sb.toString();
    }

}
