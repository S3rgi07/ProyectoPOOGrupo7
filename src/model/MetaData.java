package model;

import java.util.*;

public class MetaData {

    private static final Map<String, List<String>> META_CURSOS = new HashMap<>();

    static {
        // ================= DESARROLLO PROFESIONAL =================
        put("profesional", "CU192");
        put("entrevistas", "CU192");
        put("cv", "CU192");
        put("finanzas", "CU192");
        put("comunicación", "CU199", "HL3045", "HL1004", "HL1005");

        put("inglés", "CU1100");
        put("business", "CU1100");
        put("negocios", "HL3045", "IA3041", "LI1002", "LI1001");

        put("creatividad", "CU193", "HL3042", "CP3033");
        put("design thinking", "IA2012");

        put("marketing", "CU194", "LI1001");
        put("psicología", "CU194", "CP1001", "NN3072");

        put("locución", "CU199");
        put("público", "CU199");

        // ================= MÚSICA =================
        put("música", "CP1001", "CP3033");

        // ================= ESCRITURA =================
        put("escritura", "HL3042", "EI148");

        // ================= HUMANIDADES =================
        put("mitología", "IA1003");
        put("filosofía", "IA1003");

        // ================= LIDERAZGO =================
        put("liderazgo", "IA3035", "PP198", "TD3005");

        // ================= SALUD =================
        put("salud", "NN3072", "Abordajes multidisciplinarios para la salud");

        // ================= IA / TECNOLOGÍA =================
        put("ia", "CC178");
        put("inteligencia artificial", "CC178");
        put("llm", "CC178");
    }

    private static void put(String meta, String... cursos) {
        META_CURSOS.put(meta.toLowerCase(), Arrays.asList(cursos));
    }

    public static List<String> buscarCursos(String meta) {
        return META_CURSOS.getOrDefault(meta.toLowerCase(), new ArrayList<>());
    }
}
