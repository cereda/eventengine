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

import br.usp.poli.lta.cereda.eventengine.model.Configuration;
import br.usp.poli.lta.cereda.eventengine.model.Engine;
import br.usp.poli.lta.cereda.eventengine.model.Rule;
import br.usp.poli.lta.cereda.eventengine.conversions.yaml.YAMLEngine;
import br.usp.poli.lta.cereda.eventengine.conversions.yaml.YAMLRule;
import br.usp.poli.lta.cereda.eventengine.model.Event;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

/**
 * Implementa um leitor de uma especificação YAML de um motor de eventos.
 *
 * @author Paulo Roberto Massa Cereda
 * @version 1.0
 * @since 1.0
 */
public class YAMLReader implements EngineReader {

    /**
     * Obtém um motor de eventos a partir de uma especificação YAML.
     *
     * @param file Arquivo YAML.
     * @return Motor de eventos.
     */
    @Override
    public Engine toEngine(File file) {

        try {

            // instancia o parser YAML
            Yaml yaml = new Yaml();

            // carrega o motor YAML
            YAMLEngine yengine = yaml.loadAs(new FileReader(file),
                    YAMLEngine.class);

            // instancia o novo motor
            Engine engine = new Engine(yengine.getIdentifier());

            // obtém o conjunto de regras
            Set<Rule> rules = yengine.getRules().
                    stream().map((YAMLRule yrule) -> {
                        Rule rule = new Rule();
                        yrule.getConditions().forEach(rule::addCondition);
                        yrule.getActions().forEach(rule::addAction);
                        yrule.getAttributes().
                                forEach(rule.getAttributes()::put);
                        return rule;
                    }).collect(Collectors.toSet());

            // adiciona o conjunto de
            // regras ao motor de eventos
            engine.setRules(rules);

            // cria uma nova configuração e
            // a adiciona ao motor de eventos
            Configuration configuration = new Configuration();
            configuration.setAttributes(yengine.getConfiguration());
            engine.setConfiguration(configuration);

            // o novo motor é retornado
            return engine;
        } catch (FileNotFoundException nothandled1) {
            System.out.println("O arquivo não foi encontrado.");
        } catch (YAMLException nothandled2) {
            System.out.println("O arquivo YAML está incorreto.");
        }

        // ocorreu um erro, retornar
        // uma referência inválida
        return null;
    }

    /**
     * Obtém uma lista de eventos a partir de um arquivo informado contendo a
     * especificação em YAML.
     *
     * @param file Arquivo informado.
     * @return Uma lista de eventos.
     */
    @Override
    public List<Event> toEvents(File file) {
        
        try {
            
            // instancia o parser YAML
            Yaml yaml = new Yaml();
            
            // transforma o arquivo YAML em
            // uma lista de objetos
            List<Object> list = yaml.loadAs(new FileReader(file), List.class);
            
            // verifica se cada elemento da
            // lista de objetos é, de fato,
            // um mapa (classe concreta)
            if (list.stream().anyMatch((Object t) -> !(t instanceof HashMap))) {
                throw new YAMLException("A map is required.");
            }
            
            // retorna uma nova lista, transformando
            // todos os objetos em eventos correspondentes
            return list.stream().map((Object element) -> {
                Event event = new Event();
                HashMap map = (HashMap) element;
                map.forEach((Object key, Object value) -> {
                    event.put(key.toString(), value);
                });
                return event;
            }).collect(Collectors.toList());
        } catch (FileNotFoundException nothandled1) {
            System.out.println("O arquivo não foi encontrado.");
        } catch (YAMLException nothandled2) {
            System.out.println("O arquivo YAML está incorreto.");
        }

        // ocorreu um erro, retornar
        // uma referência inválida
        return null;
    }

}
