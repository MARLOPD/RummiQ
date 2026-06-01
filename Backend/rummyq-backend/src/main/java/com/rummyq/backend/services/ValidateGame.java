package com.rummyq.backend.services;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.rummyq.backend.models.Ficha;

public class ValidateGame {

    public static final int PUNTOS_APERTURA = 30;

    public static class Resultado {
        public final boolean valido;
        public final String motivo;

        private Resultado(boolean valido, String motivo) {
            this.valido = valido;
            this.motivo = motivo;
        }

        public static Resultado ok() {
            return new Resultado(true, null);
        }

        public static Resultado error(String msg) {
            return new Resultado(false, msg);
        }
    }

    public Resultado validar(List<List<Ficha>> grupos, boolean esApertura) {
        if (grupos == null || grupos.isEmpty()) {
            return Resultado.error("Debes colocar al menos un grupo de fichas.");
        }

        for (List<Ficha> grupo : grupos) {
            Resultado r = validarGrupo(grupo);
            if (!r.valido)
                return r;
        }

        if (esApertura) {
            int total = calcularPuntos(grupos);
            if (total < PUNTOS_APERTURA) {
                return Resultado.error(
                        "La apertura debe sumar al menos " + PUNTOS_APERTURA
                                + " puntos. Tu jugada suma " + total + ".");
            }
        }

        return Resultado.ok();
    }

    public Resultado validarGrupo(List<Ficha> grupo) {
        if (grupo == null || grupo.size() < 3) {
            return Resultado.error("Cada combinación debe tener al menos 3 fichas.");
        }
        if (grupo.size() > 13) {
            return Resultado.error("Una combinación no puede tener más de 13 fichas.");
        }

        long comodines = grupo.stream().filter(Ficha::isEsComodin).count();
        List<Ficha> normales = grupo.stream()
                .filter(f -> !f.isEsComodin())
                .collect(Collectors.toList());

        if (normales.isEmpty()) {
            return Resultado.error("Un grupo no puede estar formado sólo por comodines.");
        }

        Resultado rGrupo = validarComoGrupo(normales, (int) comodines, grupo.size());
        if (rGrupo.valido)
            return rGrupo;

        Resultado rEscalera = validarComoEscalera(normales, (int) comodines, grupo.size());
        if (rEscalera.valido)
            return rEscalera;

        return Resultado.error("Combinación inválida. No es ni grupo ni escalera. " + rGrupo.motivo);
    }

    private Resultado validarComoGrupo(List<Ficha> normales, int comodines, int total) {
        if (total > 4) {
            return Resultado.error("Un grupo puede tener como máximo 4 fichas.");
        }

        int numero = Integer.parseInt(normales.get(0).getNumero());
        for (Ficha f : normales) {
            if (Integer.parseInt(f.getNumero()) != numero) {
                return Resultado.error("En un grupo todas las fichas deben tener el mismo número.");
            }
        }

        Set<Ficha.Color> colores = new HashSet<>();
        for (Ficha f : normales) {
            if (!colores.add(f.getColor())) {
                return Resultado.error("En un grupo no puede haber dos fichas del mismo color.");
            }
        }

        if (colores.size() + comodines > 4) {
            return Resultado.error("Un grupo puede tener como máximo 4 fichas (4 colores distintos).");
        }

        return Resultado.ok();
    }

    private Resultado validarComoEscalera(List<Ficha> normales, int comodines, int total) {
        Ficha.Color color = normales.get(0).getColor();
        for (Ficha f : normales) {
            if (f.getColor() != color) {
                return Resultado.error("En una escalera todas las fichas deben ser del mismo color.");
            }
        }

        List<Ficha> ordenadas = normales.stream()
                .sorted(Comparator.comparingInt(f -> Integer.parseInt(f.getNumero())))
                .collect(Collectors.toList());

        for (int i = 1; i < ordenadas.size(); i++) {
            if (Integer.parseInt(ordenadas.get(i).getNumero()) == Integer.parseInt(ordenadas.get(i - 1).getNumero())) {
                return Resultado.error("En una escalera no puede haber fichas con el mismo número.");
            }
        }

        int huecos = 0;
        for (int i = 1; i < ordenadas.size(); i++) {
            int salto = Integer.parseInt(ordenadas.get(i).getNumero())
                    - Integer.parseInt(ordenadas.get(i - 1).getNumero()) - 1;
            if (salto < 0) {
                return Resultado.error("Los números de la escalera no son válidos.");
            }
            huecos += salto;
        }

        if (huecos > comodines) {
            return Resultado.error(
                    "Faltan " + (huecos - comodines) + " ficha(s) para completar la escalera consecutiva.");
        }

        int min = Integer.parseInt(ordenadas.get(0).getNumero());
        int max = Integer.parseInt(ordenadas.get(ordenadas.size() - 1).getNumero());
        int comodinesSobrantes = comodines - huecos;
        if (min - comodinesSobrantes < 1 && max + comodinesSobrantes > 13) {
            return Resultado.error("La escalera excede el rango válido (1-13).");
        }

        return Resultado.ok();
    }

    public int calcularPuntos(List<List<Ficha>> grupos) {
        int total = 0;
        
        for (List<Ficha> grupo : grupos) {
            List<Ficha> normales = grupo.stream()
                    .filter(f -> !f.isEsComodin())
                    .collect(Collectors.toList());
            
            int comodines = (int) grupo.stream().filter(Ficha::isEsComodin).count();
            
            if (normales.isEmpty()) {
                continue;
            }
            
            int sumaGrupo = normales.stream()
                    .mapToInt(f -> Integer.parseInt(f.getNumero()))
                    .sum();
            total += sumaGrupo;
            
            if (comodines > 0) {
                int primerNumero = Integer.parseInt(normales.get(0).getNumero());
                boolean esGrupo = normales.stream()
                        .allMatch(f -> Integer.parseInt(f.getNumero()) == primerNumero);
                
                if (esGrupo) {
                    total += primerNumero * comodines;
                } else {
                    List<Integer> numeros = normales.stream()
                            .map(f -> Integer.parseInt(f.getNumero()))
                            .sorted()
                            .collect(Collectors.toList());
                    
                    int sumaComodines = calcularValorComodinesEscalera(numeros, comodines);
                    total += sumaComodines;
                }
            }
        }
        
        return total;
    }
    
    private int calcularValorComodinesEscalera(List<Integer> numeros, int comodines) {
        int huecos = 0;
        for (int i = 1; i < numeros.size(); i++) {
            int salto = numeros.get(i) - numeros.get(i - 1) - 1;
            huecos += salto;
        }
        
        int sumaComodines = 0;
        
        for (int i = 1; i < numeros.size(); i++) {
            int anterior = numeros.get(i - 1);
            int actual = numeros.get(i);
            for (int j = anterior + 1; j < actual; j++) {
                sumaComodines += j;
            }
        }
        
        int comodinesSobrantes = comodines - huecos;
        if (comodinesSobrantes > 0) {
            int max = numeros.get(numeros.size() - 1);
            for (int i = 1; i <= comodinesSobrantes; i++) {
                sumaComodines += max + i;
            }
        }
        
        return sumaComodines;
    }
}
