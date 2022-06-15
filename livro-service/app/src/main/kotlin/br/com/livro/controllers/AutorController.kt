package br.com.livro.controllers


import br.com.livro.dto.AutorDto
import br.com.livro.service.AutorService
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

@Api("Autores")
@RestController
@RequestMapping("/v1/api/autores")
class AutorController(private val autorService: AutorService) {

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(method = [RequestMethod.GET])
    @Cacheable(value = ["listaAutor"])
    @ApiOperation(value = "Consulta dos autores, retornado todas em uma lista")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro autor pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun findAll(): ResponseEntity<List<AutorDto>> {
        val autores = autorService.findAll()
        if (autores.isEmpty()) {
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.ok(autores)
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(method = [RequestMethod.POST])
    @Transactional
    @CacheEvict(value = ["listaAutor"], allEntries = true)
    @ApiOperation(value = "Novo cadastro do autor")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 201, message = "Cadastro realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro autor pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun save(@Valid @RequestBody autorDto: AutorDto): ResponseEntity<AutorDto> {

        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.save(autorDto))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
    @Transactional
    @CacheEvict(value = ["listaAutor"], allEntries = true)
    @ApiOperation(value = "Atualiza dados de autor")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Atualizado realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro autor pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody autorDto: AutorDto
    ): ResponseEntity<GeneralMessage> {
        autorService.update(id, autorDto)
        return ResponseEntity.status(HttpStatus.OK).body(GeneralMessage("Atualizo autor com sucesso"))
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    @ApiOperation(value = "Consulta do autor pelo id")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro autor pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun findById(@PathVariable("id") id: Long): ResponseEntity<AutorDto> {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.findById(id))
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/search/{nome_autor}"], method = [RequestMethod.GET])
    @ApiOperation(value = "Consulta do autor pelo nome")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro autor pelo autor"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun findByNome(@PathVariable("nome_autor") nome: String): ResponseEntity<List<AutorDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.findByNome(nome))
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    @Transactional
    @CacheEvict(value = ["listaAutor"], allEntries = true)
    @ApiOperation(value = "Deleta id livro")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "Operação realizada com sucesso"),
            ApiResponse(code = 404, message = "Não encontro autor pelo id"),
            ApiResponse(code = 500, message = "Foi gerada uma exceção")
        )
    )
    fun deleteById(@PathVariable("id") id: Long): ResponseEntity<GeneralMessage> {
        autorService.deleteById(id)
        return ResponseEntity.status(HttpStatus.OK).body(GeneralMessage("Deletando autor com sucesso"))
    }


}