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

/**
 * Implementa uma classe com métodos utilitários para manipulação da
 * representação textual de uma tabela.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class TableUtils {

    /**
     * Abrevia o texto de acordo com o comprimento especificado.
     *
     * @param text Texto
     * @param length Comprimento.
     * @return Texto abreviado.
     */
    private static String abbreviate(String text, int length) {
        return text.length() > length
                ? text.substring(0, length - 3).concat("...")
                : fill(text, length);
    }

    /**
     * Preenche o texto de acordo o comprimento especificado.
     *
     * @param text Texto.
     * @param length Comprimento.
     * @return Texto preenchido.
     */
    private static String fill(String text, int length) {
        int times = length - text.length();
        while (times-- > 0) {
            text = text.concat(" ");
        }
        return text;
    }

    /**
     * Repete o símbolo um determinado número de vezes.
     *
     * @param symbol Símbolo a ser repetido.
     * @param times Número de repetições.
     * @return Símbolo repetido.
     */
    private static String repeat(char symbol, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= times; i++) {
            sb.append(symbol);
        }
        return sb.toString();
    }

    /**
     * Desenha o cabeçalho da tabela textual.
     *
     * @return Cabeçalho da tabela textual.
     */
    public static String header() {
        StringBuilder sb = new StringBuilder();
        sb.append(separator()).append("\n");
        sb.append(content("NUM", "EVENTO", "PRO")).append("\n");
        sb.append(separator());
        return sb.toString();
    }

    /**
     * Preenche o conteúdo de uma linha da tabela.
     *
     * @param one Primeiro parâmetro.
     * @param two Segundo parâmetro.
     * @param three Terceiro parâmetro.
     * @return Conteúdo de uma linha.
     */
    private static String content(String one, String two, String three) {
        StringBuilder sb = new StringBuilder();
        sb.append("| ").append(abbreviate(one, 3)).append(" | ");
        sb.append(abbreviate(two, 50)).append(" | ");
        sb.append(abbreviate(three, 3));
        sb.append(" |");
        return sb.toString();
    }

    /**
     * Imprime um separador de linhas.
     *
     * @return Separador de linhas.
     */
    private static String separator() {
        StringBuilder sb = new StringBuilder();
        sb.append("+").append(repeat('-', 5));
        sb.append("+").append(repeat('-', 52));
        sb.append("+").append(repeat('-', 5));
        sb.append("+");
        return sb.toString();
    }

    /**
     * Imprime uma linha da tabela textual.
     *
     * @param number Número de identificação.
     * @param event Evento.
     * @param result Resultado do processamento.
     * @return Linha da tabela textual.
     */
    public static String line(int number, String event, boolean result) {
        StringBuilder sb = new StringBuilder();
        sb.append(content(String.format("%03d", number), event,
                answer(result)));
        sb.append("\n").append(separator());
        return sb.toString();
    }

    /**
     * Obtém uma representação textual de um valor lógico.
     *
     * @param value Valor lógico.
     * @return Representação textual.
     */
    private static String answer(boolean value) {
        return value ? "sim" : "não";
    }

}
