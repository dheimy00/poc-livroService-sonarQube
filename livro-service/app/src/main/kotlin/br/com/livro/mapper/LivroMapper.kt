package br.com.livro.mapper

import br.com.livro.dto.LivroDto
import br.com.livro.model.Livro
import br.com.livro.repository.AutorRepository
import br.com.livro.repository.CategoriaRepository
import org.springframework.stereotype.Component

@Component
class LivroMapper(private val autorRepository: AutorRepository,
                  private val categoriaRepository: CategoriaRepository
): Mapper <Livro,LivroDto> {

    override fun convertEntityToDto(livro: Livro): LivroDto {
       return LivroDto(livro.id,livro.titulo,livro.resumo,livro.isbn,livro.sumario,livro.pagina,livro.dataPublicacao,livro.autor.id,livro.categoria.id,)
    }

    override fun convertDtoToEntity(livroDto: LivroDto): Livro {
        val autor = autorRepository.findById(livroDto.id_autor)
        val categoria = categoriaRepository.findById(livroDto.id_categoria)
        return Livro(livroDto.id,livroDto.titulo,livroDto.resumo,livroDto.isbn,livroDto.sumario,livroDto.pagina,livroDto.dataPublicacao,categoria.get(),autor.get())
    }



}