package com.example.producto3

data class SnakeGameState(
    val snake: List<Pair<Int, Int>>, // Lista de posiciones (x, y) de la lombriz
    val food: Pair<Int, Int>, // Posición de la comida
    val direction: Direction, // Dirección actual de la lombriz
    val isGameOver: Boolean // Estado del juego
)

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}
