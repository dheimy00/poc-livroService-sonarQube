package br.com.livro.mapper


import br.com.livro.dto.AutorDto
import br.com.livro.model.Autor
import org.springframework.stereotype.Component
import java.util.*

@Component
class AutorMapper : Mapper<Autor, AutorDto> {

    override fun convertEntityToDto(autor: Autor): AutorDto {
      return AutorDto(autor.id,autor.nome,autor.descricao,autor.email)
    }

    override fun convertDtoToEntity(autorDto: AutorDto): Autor {
        return Autor(autorDto.id,autorDto.nome,autorDto.descricao,autorDto.email)
    }


}