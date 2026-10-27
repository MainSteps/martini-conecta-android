package com.mainsteps.martiniconecta.repository

import com.mainsteps.martiniconecta.R
import com.mainsteps.martiniconecta.model.Contenido
import com.mainsteps.martiniconecta.model.Dificultad
import com.mainsteps.martiniconecta.model.Division
import com.mainsteps.martiniconecta.model.Ingrediente

object CatalogoRepository {

    private val contenidos = listOf(
        Contenido(
            id = "medialunas-mantequilla",
            titulo = "Medialunas de hojaldre",
            descripcion = "Medialunas doradas, con capas definidas y un interior suave.",
            division = Division.PANADERIA_PASTELERIA,
            categoria = "Bollería",
            dificultad = Dificultad.AVANZADO,
            productosMartini = listOf("Margarina Gran Master Hoja"),
            rindePorciones = 16,
            ingredientes = listOf(
                Ingrediente("Harina de fuerza", 500.0, "g"),
                Ingrediente("Margarina Gran Master Hoja", 250.0, "g"),
                Ingrediente("Leche", 250.0, "ml"),
                Ingrediente("Levadura seca", 8.0, "g"),
                Ingrediente("Azúcar", 60.0, "g")
            ),
            pasos = listOf(
                "Mezcla la harina, la leche, la levadura y el azúcar hasta formar una masa lisa.",
                "Refrigera la masa, incorpora la margarina y realiza tres vueltas simples.",
                "Corta triángulos, forma las medialunas y deja fermentar hasta que aumenten su volumen.",
                "Hornea a 190 °C hasta que estén doradas."
            ),
            imagenResId = R.drawable.receta_panaderia,
            esVegano = false
        ),
        Contenido(
            id = "pan-brioche",
            titulo = "Pan brioche para vitrina",
            descripcion = "Pan tierno y aromático, pensado para desayunos y sándwiches dulces.",
            division = Division.PANADERIA_PASTELERIA,
            categoria = "Panadería",
            dificultad = Dificultad.INTERMEDIO,
            productosMartini = listOf("Manteca Unigral"),
            rindePorciones = 12,
            ingredientes = listOf(
                Ingrediente("Harina", 500.0, "g"),
                Ingrediente("Manteca Unigral", 120.0, "g"),
                Ingrediente("Huevos", 4.0, "unidades"),
                Ingrediente("Leche", 100.0, "ml"),
                Ingrediente("Levadura seca", 8.0, "g")
            ),
            pasos = listOf(
                "Amasa la harina, los huevos, la leche y la levadura durante diez minutos.",
                "Agrega la manteca en porciones y continúa amasando hasta integrarla.",
                "Deja fermentar, divide en porciones y acomoda en moldes.",
                "Hornea a 180 °C hasta que la superficie esté dorada."
            ),
            imagenResId = R.drawable.receta_panaderia,
            esVegano = false
        ),
        Contenido(
            id = "torta-chocolate-avellana",
            titulo = "Torta de chocolate y avellana",
            descripcion = "Torta húmeda con relleno cremoso y terminación brillante.",
            division = Division.PANADERIA_PASTELERIA,
            categoria = "Pastelería",
            dificultad = Dificultad.INTERMEDIO,
            productosMartini = listOf("Chocolate Caravella", "Monnalisa"),
            rindePorciones = 14,
            ingredientes = listOf(
                Ingrediente("Harina", 240.0, "g"),
                Ingrediente("Chocolate Caravella", 220.0, "g"),
                Ingrediente("Crema Monnalisa", 300.0, "g"),
                Ingrediente("Huevos", 4.0, "unidades"),
                Ingrediente("Azúcar", 180.0, "g")
            ),
            pasos = listOf(
                "Prepara un bizcocho de chocolate y déjalo enfriar por completo.",
                "Corta el bizcocho en tres discos del mismo grosor.",
                "Rellena cada capa con crema de avellana Monnalisa.",
                "Cubre con chocolate fundido y refrigera antes de porcionar."
            ),
            imagenResId = R.drawable.receta_panaderia,
            esVegano = false
        ),
        Contenido(
            id = "gelato-chocolate-intenso",
            titulo = "Gelato de chocolate intenso",
            descripcion = "Gelato cremoso con sabor profundo a cacao y textura estable.",
            division = Division.GELATERIA,
            categoria = "Gelato",
            dificultad = Dificultad.INTERMEDIO,
            productosMartini = listOf("Chocolate Ariba"),
            rindePorciones = 10,
            ingredientes = listOf(
                Ingrediente("Leche entera", 700.0, "ml"),
                Ingrediente("Chocolate Ariba", 220.0, "g"),
                Ingrediente("Crema", 150.0, "ml"),
                Ingrediente("Azúcar", 120.0, "g")
            ),
            pasos = listOf(
                "Calienta la leche, la crema y el azúcar sin dejar que hiervan.",
                "Vierte la mezcla sobre el chocolate y revuelve hasta homogeneizar.",
                "Enfría la base durante cuatro horas.",
                "Procesa en la máquina de helado y conserva congelado."
            ),
            imagenResId = R.drawable.receta_gelateria,
            esVegano = false
        ),
        Contenido(
            id = "helado-avellana",
            titulo = "Helado de avellana italiana",
            descripcion = "Helado suave de avellana, ideal para servir solo o en copa.",
            division = Division.GELATERIA,
            categoria = "Helado artesanal",
            dificultad = Dificultad.BASICO,
            productosMartini = listOf("Monnalisa"),
            rindePorciones = 10,
            ingredientes = listOf(
                Ingrediente("Leche entera", 750.0, "ml"),
                Ingrediente("Crema Monnalisa", 200.0, "g"),
                Ingrediente("Azúcar", 130.0, "g"),
                Ingrediente("Crema de leche", 100.0, "ml")
            ),
            pasos = listOf(
                "Mezcla todos los ingredientes hasta disolver el azúcar.",
                "Refrigera la base durante al menos tres horas.",
                "Procesa en la máquina de helado hasta obtener una textura cremosa.",
                "Congela durante una hora antes de servir."
            ),
            imagenResId = R.drawable.receta_gelateria,
            esVegano = false
        ),
        Contenido(
            id = "gelato-vegano-cacao",
            titulo = "Gelato vegano de cacao",
            descripcion = "Alternativa vegetal cremosa, sin ingredientes de origen animal.",
            division = Division.GELATERIA,
            categoria = "Gelato vegano",
            dificultad = Dificultad.BASICO,
            productosMartini = listOf("Orasì"),
            rindePorciones = 8,
            ingredientes = listOf(
                Ingrediente("Bebida vegetal Orasì", 800.0, "ml"),
                Ingrediente("Cacao amargo", 70.0, "g"),
                Ingrediente("Azúcar", 140.0, "g"),
                Ingrediente("Maicena", 20.0, "g")
            ),
            pasos = listOf(
                "Disuelve la maicena y el cacao en una parte de la bebida vegetal.",
                "Calienta el resto con el azúcar y agrega la mezcla de cacao.",
                "Cocina hasta espesar levemente y enfría por completo.",
                "Procesa en la máquina de helado y congela."
            ),
            imagenResId = R.drawable.receta_gelateria,
            esVegano = true
        ),
        Contenido(
            id = "brownie-cafeteria",
            titulo = "Brownie para cafetería",
            descripcion = "Brownie de centro húmedo, fácil de porcionar y mantener en vitrina.",
            division = Division.FOOD_SERVICE,
            categoria = "Cafetería",
            dificultad = Dificultad.BASICO,
            productosMartini = listOf("Chocolate Caravella"),
            rindePorciones = 20,
            ingredientes = listOf(
                Ingrediente("Chocolate Caravella", 300.0, "g"),
                Ingrediente("Mantequilla", 220.0, "g"),
                Ingrediente("Huevos", 5.0, "unidades"),
                Ingrediente("Azúcar", 280.0, "g"),
                Ingrediente("Harina", 140.0, "g")
            ),
            pasos = listOf(
                "Funde el chocolate junto con la mantequilla.",
                "Incorpora el azúcar y los huevos sin agregar demasiado aire.",
                "Añade la harina y vierte en una bandeja forrada.",
                "Hornea a 175 °C y enfría antes de cortar."
            ),
            imagenResId = R.drawable.receta_food_service,
            esVegano = false
        ),
        Contenido(
            id = "mousse-chocolate",
            titulo = "Mousse de chocolate para banquetería",
            descripcion = "Postre aireado que puede prepararse con anticipación en porciones individuales.",
            division = Division.FOOD_SERVICE,
            categoria = "Postres",
            dificultad = Dificultad.INTERMEDIO,
            productosMartini = listOf("Chocolate Ariba", "Bravo Crem"),
            rindePorciones = 18,
            ingredientes = listOf(
                Ingrediente("Chocolate Ariba", 350.0, "g"),
                Ingrediente("Bravo Crem", 500.0, "ml"),
                Ingrediente("Claras de huevo", 6.0, "unidades"),
                Ingrediente("Azúcar", 100.0, "g")
            ),
            pasos = listOf(
                "Funde el chocolate y déjalo bajar de temperatura.",
                "Bate Bravo Crem hasta obtener una textura firme.",
                "Incorpora el chocolate y luego las claras batidas con movimientos suaves.",
                "Sirve en vasos y refrigera por cuatro horas."
            ),
            imagenResId = R.drawable.receta_food_service,
            esVegano = false
        ),
        Contenido(
            id = "lasana-vegetal-cremosa",
            titulo = "Lasaña vegetal cremosa",
            descripcion = "Lasaña de verduras con salsa vegetal, adecuada para servicio de casino.",
            division = Division.FOOD_SERVICE,
            categoria = "Plato principal",
            dificultad = Dificultad.INTERMEDIO,
            productosMartini = listOf("Orasì"),
            rindePorciones = 12,
            ingredientes = listOf(
                Ingrediente("Bebida vegetal Orasì", 1000.0, "ml"),
                Ingrediente("Láminas de lasaña", 500.0, "g"),
                Ingrediente("Zapallo italiano", 600.0, "g"),
                Ingrediente("Champiñones", 400.0, "g"),
                Ingrediente("Harina", 80.0, "g")
            ),
            pasos = listOf(
                "Saltea el zapallo italiano y los champiñones.",
                "Prepara una salsa blanca con la bebida vegetal Orasì y la harina.",
                "Arma capas de pasta, verduras y salsa en una fuente.",
                "Hornea a 190 °C hasta que la pasta esté cocida."
            ),
            imagenResId = R.drawable.receta_food_service,
            esVegano = true
        ),
        Contenido(
            id = "galletas-chips-chocolate",
            titulo = "Galletas con chips de chocolate",
            descripcion = "Galletas caseras de borde crocante y centro blando.",
            division = Division.RETAIL,
            categoria = "Galletas",
            dificultad = Dificultad.BASICO,
            productosMartini = listOf("Chocolate Caravella"),
            rindePorciones = 24,
            ingredientes = listOf(
                Ingrediente("Harina", 300.0, "g"),
                Ingrediente("Chocolate Caravella", 180.0, "g"),
                Ingrediente("Mantequilla", 160.0, "g"),
                Ingrediente("Azúcar rubia", 150.0, "g"),
                Ingrediente("Huevo", 1.0, "unidad")
            ),
            pasos = listOf(
                "Bate la mantequilla con el azúcar e incorpora el huevo.",
                "Agrega la harina y luego el chocolate picado.",
                "Forma porciones y distribúyelas en una bandeja.",
                "Hornea a 180 °C durante doce minutos."
            ),
            imagenResId = R.drawable.receta_retail,
            esVegano = false
        ),
        Contenido(
            id = "muffins-cacao",
            titulo = "Muffins de cacao",
            descripcion = "Muffins simples para preparar en casa con chocolate de sabor intenso.",
            division = Division.RETAIL,
            categoria = "Repostería",
            dificultad = Dificultad.BASICO,
            productosMartini = listOf("Chocolate Ariba"),
            rindePorciones = 12,
            ingredientes = listOf(
                Ingrediente("Harina", 250.0, "g"),
                Ingrediente("Chocolate Ariba", 150.0, "g"),
                Ingrediente("Leche", 180.0, "ml"),
                Ingrediente("Huevos", 2.0, "unidades"),
                Ingrediente("Azúcar", 140.0, "g")
            ),
            pasos = listOf(
                "Mezcla la harina y el azúcar en un recipiente.",
                "Añade la leche, los huevos y el chocolate fundido.",
                "Reparte la mezcla en doce cápsulas.",
                "Hornea a 180 °C durante veinte minutos."
            ),
            imagenResId = R.drawable.receta_retail,
            esVegano = false
        ),
        Contenido(
            id = "batido-vegano-cacao",
            titulo = "Batido vegano de cacao",
            descripcion = "Bebida fría y rápida, sin lácteos y con textura cremosa.",
            division = Division.RETAIL,
            categoria = "Bebidas",
            dificultad = Dificultad.BASICO,
            productosMartini = listOf("Orasì"),
            rindePorciones = 2,
            ingredientes = listOf(
                Ingrediente("Bebida vegetal Orasì", 500.0, "ml"),
                Ingrediente("Cacao amargo", 30.0, "g"),
                Ingrediente("Plátano congelado", 1.0, "unidad"),
                Ingrediente("Hielo", 6.0, "cubos")
            ),
            pasos = listOf(
                "Pon todos los ingredientes en la licuadora.",
                "Procesa hasta obtener una mezcla homogénea.",
                "Ajusta la textura con más bebida vegetal si es necesario.",
                "Sirve inmediatamente."
            ),
            imagenResId = R.drawable.receta_retail,
            esVegano = true
        )
    )

    fun obtenerTodos(): List<Contenido> = contenidos

    fun obtenerPorId(id: String): Contenido? = contenidos.find { it.id == id }
}
