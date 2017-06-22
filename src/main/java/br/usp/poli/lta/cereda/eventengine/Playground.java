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
package br.usp.poli.lta.cereda.eventengine;

import br.usp.poli.lta.cereda.eventengine.model.Configuration;
import br.usp.poli.lta.cereda.eventengine.model.Engine;
import br.usp.poli.lta.cereda.eventengine.model.Event;
import br.usp.poli.lta.cereda.eventengine.model.Rule;
import java.util.Arrays;

/**
 * Classe principal para testes do motor de eventos.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class Playground {

    /**
     * Classe principal.
     *
     * @param args Argumentos de linha de comando.
     */
    public static void main(String[] args) {

        Rule r1 = new Rule();
        r1.addCondition("configuration['state'] == 1");
        r1.addCondition("event['symbol'] == 'a'");
        r1.addAction("configuration['state'] = 2");
        
        Rule r2 = new Rule();
        r2.addCondition("configuration['state'] == 2");
        r2.addCondition("event['symbol'] == 'b'");
        r2.addAction("configuration['state'] = 1");
        
        Configuration c = new Configuration();
        c.put("state", 1);
        
        Engine e = new Engine("ab*a");
        e.setConfiguration(c);
        e.setRules(Arrays.asList(r1, r2));
        
        System.out.println(e);
        System.out.println(separator());
        
        Event e1 = new Event();
        e1.put("symbol", "a");
        
        Event e2 = new Event();
        e2.put("symbol", "b");
        
        Event e3 = new Event();
        e3.put("symbol", "a");
        
        Arrays.asList(e1, e2, e3).forEach((Event event) -> {
            
            System.out.println("Evento a ser consumido:");
            System.out.println(event);
            
            System.out.println(separator());
            System.out.print("INFO: ");
            
            boolean result = e.consume(event);
            if (result) {
                System.out.println("Consegui consumir o evento corrente.");
            }
            else {
                System.out.println("Não consegui consumir o evento corrente.");
            }
            
            System.out.println(separator());
        });
        
        System.out.println("Configuração final:");
        System.out.println(e.getConfiguration());
    }
    
    /**
     * Exibe um separador de linhas.
     * @return Separador de linhas.
     */
    private static String separator() {
        return new StringBuilder().append("\n").
                append(repeat('.', 60)).append("\n").
                toString();
    }

    /**
     * Repete o símbolo informado um determinado número de vezes.
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
}
