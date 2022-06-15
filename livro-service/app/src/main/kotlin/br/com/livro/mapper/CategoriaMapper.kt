package br.com.livro.mapper

import br.com.livro.dto.CategoriaDto
import br.com.livro.model.Categoria
import org.springframework.stereotype.Component
import java.util.*

@Component
class CategoriaMapper: Mapper <Categoria,CategoriaDto>  {

    override fun convertEntityToDto(categoria: Categoria): CategoriaDto {
       return  CategoriaDto(categoria.id,categoria.nome)
    }

    override fun convertDtoToEntity(categoriaDto: CategoriaDto): Categoria {
        return  Categoria(categoriaDto.id,categoriaDto.nome)
    }

}
