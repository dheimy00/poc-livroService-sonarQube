package br.com.livro.builders.requests

import br.com.livro.dto.CategoriaDto

class CategoriaDtoBuilder {

    companion object {
        fun categoriaRequest(): CategoriaDto {
            return CategoriaDto(1L, "computacao")
        }

        fun validCategoria(): CategoriaDto {
            return CategoriaDto(1L, "computacao")
        }


        fun updateCategoria(): CategoriaDto {
            return CategoriaDto(1L, "computacao")
        }


        fun categoriaResponse(): CategoriaDto {
            return CategoriaDto(1L, "computacao")
        }
    }
}