package br.ufrn.ieee.database.evento.utils;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class VToolsCategoryMapper {

    private static final Map<String, String> CATEGORIES = new HashMap<>();
    private static final Map<String, String> SUBCATEGORIES = new HashMap<>();

    static {
        // CATEGORIAS
        CATEGORIES.put("1", "Professional");
        CATEGORIES.put("2", "Technical");
        CATEGORIES.put("3", "Nontechnical");
        CATEGORIES.put("4", "Administrative");
        CATEGORIES.put("5", "Humanitarian");
        CATEGORIES.put("6", "Pre-U STEM Program");

        // SUBCATEGORIAS
        SUBCATEGORIES.put("1", "Continuing Education");
        SUBCATEGORIES.put("2", "Professional Development");
        SUBCATEGORIES.put("3", "Industry Relations");
        SUBCATEGORIES.put("4", "Professional (Other)");
        SUBCATEGORIES.put("5", "Social");
        SUBCATEGORIES.put("6", "Awards Dinner");
        SUBCATEGORIES.put("7", "Pre-University Activities");
        SUBCATEGORIES.put("8", "Nontechnical (Other)");
        SUBCATEGORIES.put("9", "ExCom");
        SUBCATEGORIES.put("10", "Officer Training");
        SUBCATEGORIES.put("11", "SIGHT");
        SUBCATEGORIES.put("12", "Other (Humanitarian)");
        SUBCATEGORIES.put("13", "Camp");
        SUBCATEGORIES.put("14", "Career Day");
        SUBCATEGORIES.put("15", "Competition/STEM Fairs");
        SUBCATEGORIES.put("16", "Girls in STEM");
        SUBCATEGORIES.put("17", "Industry/Company Tour");
        SUBCATEGORIES.put("18", "Mentoring");
        SUBCATEGORIES.put("19", "Parent Program");
        SUBCATEGORIES.put("20", "Student Workshop");
        SUBCATEGORIES.put("21", "Teacher Workshop");
    }

    public String traduzirCategoria(String id) {
        if (id == null)
            return "Importado do vTools";
        return CATEGORIES.getOrDefault(id, "Categoria ID: " + id);
    }

    public String traduzirSubcategoria(String id) {
        if (id == null)
            return null;
        return SUBCATEGORIES.getOrDefault(id, "Subcategoria ID: " + id);
    }
}