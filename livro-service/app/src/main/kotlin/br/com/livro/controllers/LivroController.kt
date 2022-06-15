package br.com.livro.controllers

import br.com.livro.dto.LivroDto
import br.com.livro.service.LivroService
import br.com.livro.utils.GeneralMessage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Pattern

@Api("Livros")
@RestController
@RequestMapping("/v1/api/livros")
class LivroController(private val livroService: LivroService) {

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Cacheable(value = ["listaLivro"])
    @RequestMapping(method = [RequestMethod.GET])
    @ApiOperation(value = "Consulta do livro, retornado todas em uma lista")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro livro pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun findAll(): ResponseEntity<List<LivroDto>> {
        val livros = livroService.findAll()
        if (livros.isEmpty()) {
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.ok(livros)
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional
    @CacheEvict(value = ["listaLivro"], allEntries = true)
    @RequestMapping(
        method = [RequestMethod.POST],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ApiOperation(value = "Novo cadastro do livro")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 201, message = "Cadastro realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro livro pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun save(@Valid @RequestBody livroDto: LivroDto): ResponseEntity<LivroDto> {

        val livroResponse = livroService.save(livroDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(livroResponse)
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional
    @CacheEvict(value = ["listaLivro"], allEntries = true)
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
    @ApiOperation(value = "Atualiza dados de livro")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Atualizado realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro livro pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody livroDto: LivroDto
    ): ResponseEntity<GeneralMessage> {
        livroService.update(id, livroDto)
        return ResponseEntity.status(HttpStatus.OK).body(GeneralMessage("Atualizo livro com sucesso"))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    @ApiOperation(value = "Consulta do livro pelo id")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro livro pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun findById(@PathVariable("id") id: Long): ResponseEntity<LivroDto> {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.findById(id))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional
    @CacheEvict(value = ["listaLivro"], allEntries = true)
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    @ApiOperation(value = "Deleta id livro")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro livro pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun deleteById(@PathVariable("id") id: Long): ResponseEntity<GeneralMessage> {
        livroService.deleteById(id)
        return ResponseEntity.status(HttpStatus.OK).body(GeneralMessage("Deletando livro com sucesso"))
    }

}