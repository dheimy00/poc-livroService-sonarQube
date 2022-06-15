package br.com.livro.builders.requests

import br.com.livro.dto.LivroDto
import java.util.*

class LivroDtoBuilder {

    companion object {

        fun newlivroRequest(): LivroDto {
            return LivroDto(
                1L,
                "Teste1",
                "resumo1",
                "teste_isbn1",
                "teste_sumario1",
                100,
                Date(),
                AutorDtoBuilder.autorRequest().id,
                CategoriaDtoBuilder.categoriaRequest().id
            )
        }

        fun livroRequest(): LivroDto {
            return LivroDto(
                1L,
                "Teste",
                "resumo",
                "teste_isbn",
                "teste_sumario",
                100,
                Date(),
                AutorDtoBuilder.autorRequest().id,
                CategoriaDtoBuilder.categoriaRequest().id
            )
        }

        fun updateLivro(): LivroDto {

            return LivroDto(
                1L,
                "Teste",
                "resumo",
                "teste_isbn",
                "teste_sumario",
                100,
                Date(),
                AutorDtoBuilder.autorRequest().id,
                CategoriaDtoBuilder.categoriaRequest().id
            )
        }

        fun livroResponse(): LivroDto {
            return LivroDto(
                1L,
                "Teste",
                "resumo",
                "teste_isbn",
                "teste_sumario",
                100,
                Date(),
                AutorDtoBuilder.autorRequest().id,
                CategoriaDtoBuilder.categoriaRequest().id
            )
        }

    }
}