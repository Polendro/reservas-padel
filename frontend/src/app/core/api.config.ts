// URL fija en vez de un sistema de environments completo: para un proyecto de este
// tamaño, con un solo backend y sin despliegue a varios entornos todavía, añadir
// environment.ts / environment.prod.ts sería complejidad sin beneficio real.
export const API_URL = 'http://localhost:8080/api';
