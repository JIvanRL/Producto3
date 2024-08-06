package com.example.producto3

data class Movie(
    val imageResId: Int,
    val title: String,
    val description: String,
    val releaseDate: String,
    val videoUrl: String, // Añadido para la URL del video,
)
// Lista de imágenes simulada
val movieList = listOf(
    Movie(
        R.drawable.imagen2,
        "Título 1",
        "Descripción 1",
        "Fecha de lanzamiento 1",
        videoUrl = ""

    ),
    Movie(
        R.drawable.imagen3,
        "Título 1",
        "Descripción 1",
        "Fecha de lanzamiento 1",
        videoUrl = ""
    ),
    Movie(
        R.drawable.imagen4,
        "Unidos",
        "Ambientada en un mundo suburbano de fantasía, UNIDOS, de Disney y Pixar, nos presenta a dos hermanos elfos adolescentes (voces en inglés de Chris Pratt y Tom Holland) que se embarcan en una extraordinaria aventura para descubrir si aún queda un poco de magia allí afuera. La nueva película original de Pixar Animation Studios es dirigida por Dan Scanlon y producida por Kori Rae, el equipo detrás de MONSTERS UNIVERSITY. Disponible ahora en Disney+.",
        "Fecha de lanzamiento: 4 de marzo de 2020",
        videoUrl = ""
    ),
    Movie(
        R.drawable.imagen5,
        "Ralph el demoledor",
        "Ralph es un personaje de videojuegos que se ha caracterizado por ser el antagonista del juego llamado “Repara Félix Junior”. Sin embargo, Ralph tiene otras aspiraciones y poco a poco se ha cansado de ser el villano de la historia, luego de llevar más de 30 años haciendo el mismo trabajo, el de destruir las construcciones que reparará Félix. Durante una reunión de “Villanos Anónimos, Ralph escucha a sus compañeros, antagonistas también, afirmando que el ser villano no quiere decir que sea malo en realidad. Sin embargo, el personaje ya no desea ser el malo lo que representa romper con las normas de los programas y el funcionamiento del mundo digital. Pero eso no le importará luego de no ser invitado a la fiesta del trigésimo aniversario. Decidido a ganar una medalla para convertirse en un héroe, pronto pondrá todo en problemas cuando destruye el mundo de otros videojuegos y libera a un peligroso villano. Con riesgo de desaparecer, incluso de su propio juego, Ralph se unirá a otro personaje, Vanellope, quien tiene sus propios planes, en busca de salvar el universo de los videojuegos.",
        "Fecha de lanzamiento: 2 de noviembre de 2012 al cine | 1h 48min | Aventura, Animación, Comedia, Familia",
        videoUrl = "https://www.youtube.com/watch?v=5agfN_13crk&ab_channel=DisneyStudiosLA"
    ),
    Movie(
        R.drawable.imagen6,
        "Título 1",
        "Descripción 1",
        "Fecha de lanzamiento 1",
        videoUrl = ""
    ),
    Movie(
        R.drawable.imagen7,
        "Título 1",
        "Descripción 1",
        "Fecha de lanzamiento 1",
        videoUrl = ""
    ),
)




