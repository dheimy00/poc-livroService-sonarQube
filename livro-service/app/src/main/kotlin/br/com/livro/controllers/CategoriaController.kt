package br.com.livro.controllers

import br.com.livro.dto.CategoriaDto
import br.com.livro.service.CategoriaService
import br.com.livro.utils.GeneralMessage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Pattern

@Api("Categorias")
@RestController
@RequestMapping("/v1/api/categorias")
class CategoriaController(private val categoriaService: CategoriaService) {


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Cacheable(value = ["listaCategoria"])
    @RequestMapping(method = [RequestMethod.GET])
    @ApiOperation(value = "Consulta dos categorias, retornado todas em uma lista")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro categoria pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun findAll(): ResponseEntity<List<CategoriaDto>> {
        val categorias = categoriaService.findAll()
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.ok(categorias)
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional
    @CacheEvict(value = ["listaCategoria"], allEntries = true)
    @RequestMapping(method = [RequestMethod.POST])
    @ApiOperation(value = "Novo cadastro do categoria")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 201, message = "Cadastro realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro categoria pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun save(@Valid @RequestBody categoriaDto: CategoriaDto): ResponseEntity<CategoriaDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(categoriaDto))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    @Transactional
    @CacheEvict(value = ["listaCategoria"], allEntries = true)
    @ApiOperation(value = "Deleta id categoria")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro categoria pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun deleteById(@PathVariable("id") id: Long): ResponseEntity<GeneralMessage> {
        categoriaService.deleteById(id)
        return ResponseEntity.status(HttpStatus.OK).body(GeneralMessage("Deletando categoria com sucesso"))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional
    @CacheEvict(value = ["listaCategoria"], allEntries = true)
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
    @ApiOperation(value = "Atualiza dados de categoria")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Atualizado realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro categoria pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody categoriaDto: CategoriaDto
    ): ResponseEntity<GeneralMessage> {
        categoriaService.update(id, categoriaDto)
        return ResponseEntity.status(HttpStatus.OK).body(GeneralMessage("Atualizo categoria com sucesso"))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    @ApiOperation(value = "Consulta do categoria pelo id")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro categoria pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun findById(@PathVariable("id") id: Long): ResponseEntity<CategoriaDto> {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.findById(id))
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/search/{nome_categoria}"], method = [RequestMethod.GET])
    @ApiOperation(value = "Consulta categoria pelo nome")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro categoria pelo nome"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun findByNome(@PathVariable("nome_categoria") nome: String): ResponseEntity<List<CategoriaDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.findByNome(nome))
    }


}