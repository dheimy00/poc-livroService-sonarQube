package br.com.livro.builders

import br.com.livro.model.Categoria
import java.util.*

class CategoriaBuilder {

    companion object {
        fun createCategoria(): Categoria {
            return Categoria(1L, "computacao")
        }

        fun validCategoria(): Categoria {
            return Categoria(1L, "computacao1")
        }

        fun updateCategoria(): Categoria {
            return Categoria(1L, "computacao")
        }

        fun fieldCategoria() : Categoria{
            return Categoria(1L, " ")
        }
    }
}