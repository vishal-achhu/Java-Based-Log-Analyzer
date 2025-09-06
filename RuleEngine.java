package com.example.loganalyzer;

import java.io.*;
import java.util.*;
import org.yaml.snakeyaml.Yaml;

@SuppressWarnings("unchecked")
public class RuleEngine {
    private final List<Map<String,Object>> rules = new ArrayList<>();

    public RuleEngine(String rulesFile) throws Exception {
        if (rulesFile == null) return;
        Yaml yaml = new Yaml();
        Iterable<Object> docs = yaml.loadAll(new FileReader(rulesFile));
        for (Object o : docs) {
            if (o instanceof Map) rules.add((Map<String,Object>)o);
        }
    }

    public List<String> apply(String line) {
        List<String> out = new ArrayList<>();
        for (Map<String,Object> r : rules) {
            String id = (String) r.get("id");
            String name = (String) r.get("name");
            String keyword = (String) r.get("keyword");
            if (keyword != null && line.contains(keyword)) {
                out.add("[" + id + "] " + name + " matched: " + line);
            }
        }
        return out;
    }
}
