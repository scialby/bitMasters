package com.pizzagpt.marchesini.json;

import java.util.List;

// Interfaccia che generalizza i file Json (user e dataset degli esercizi)

public interface JsonInterface {
    List<?> getCategories();
}