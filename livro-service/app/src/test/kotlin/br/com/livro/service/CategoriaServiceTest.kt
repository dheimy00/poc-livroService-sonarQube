package br.com.livro.service

import br.com.livro.exception.ResourceAlreadyExistException
import br.com.livro.exception.ResourceNotFoundException
import br.com.livro.mapper.CategoriaMapper
import br.com.livro.model.Categoria
import br.com.livro.repository.CategoriaRepository
import br.com.livro.service.implementations.CategoriaServiceImpl
import br.com.livro.builders.CategoriaBuilder
import br.com.livro.builders.requests.CategoriaDtoBuilder
import br.com.livro.dto.CategoriaDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.context.ActiveProfiles
import java.util.*

@ActiveProfiles("Test")
@ExtendWith(MockitoExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Categoria Service Test")
class CategoriaServiceTest {

    @InjectMocks
    lateinit var categoriaService: CategoriaServiceImpl

    @Mock
    lateinit var mapper: CategoriaMapper

    @Mock
    lateinit var categoriaRepository: CategoriaRepository


    @Test
    @Order(1)
    fun `Criar categoria quando sucesso`() {

        // Given
        //
        val categoria = CategoriaDtoBuilder.categoriaRequest()
        //Save
        BDDMockito.`when`(categoriaRepository.save(ArgumentMatchers.any(Categoria::class.java)))
                .thenReturn(CategoriaBuilder.createCategoria())

        BDDMockito.`when`(mapper.convertDtoToEntity(CategoriaDtoBuilder.categoriaRequest()))
                .thenReturn(CategoriaBuilder.createCategoria())

        BDDMockito.`when`(mapper.convertEntityToDto(CategoriaBuilder.createCategoria()))
                .thenReturn(CategoriaDtoBuilder.categoriaResponse())

        // When
        //
        val saveCategoria = categoriaService.save(categoria)

        // Then
        //
        verify(categoriaRepository, Mockito.times(1)).save(CategoriaBuilder.createCategoria());
        assertNotNull(saveCategoria)
        assertNotNull(saveCategoria.id)
        assertEquals(saveCategoria.nome, CategoriaDtoBuilder.validCategoria().nome)

    }


    @Test
    @Order(2)
    fun `Atualizar categoria quando sucesso`() {

        // Given
        //
        val categoria = CategoriaDtoBuilder.categoriaRequest()
        //Save
        BDDMockito.`when`(categoriaRepository.save(ArgumentMatchers.any(Categoria::class.java)))
                .thenReturn(CategoriaBuilder.createCategoria())
        //FindById
        BDDMockito.`when`(categoriaRepository.findById(CategoriaBuilder.createCategoria().id))
                .thenReturn(Optional.of(CategoriaBuilder.createCategoria()))

        BDDMockito.`when`(mapper.convertDtoToEntity(CategoriaDtoBuilder.categoriaRequest()))
                .thenReturn(CategoriaBuilder.createCategoria())

        BDDMockito.`when`(mapper.convertEntityToDto(CategoriaBuilder.createCategoria()))
                .thenReturn(CategoriaDtoBuilder.categoriaResponse())

        // When
        //
        val saveCategoria = categoriaService.save(categoria)
        //Verificar id categoria
        val idCategoria = categoriaService.findById(saveCategoria.id)
        val saveUpdate = CategoriaDtoBuilder.updateCategoria()
        val updateCategoria = categoriaService.update(idCategoria.id, saveUpdate)

        // Then
        //
        assertNotNull(updateCategoria)
        assertEquals(updateCategoria.nome, saveCategoria.nome)
    }

    @Test
    @Order(3)
    fun `Excluir id categoria quando sucesso`() {

        BDDMockito.`when`(categoriaRepository.findById(CategoriaBuilder.createCategoria().id))
                .thenReturn(Optional.of(CategoriaBuilder.createCategoria()))

        assertDoesNotThrow { categoriaService.deleteById(CategoriaDtoBuilder.categoriaRequest().id) }

    }

    @Test
    @Order(3)
    fun `Excluir nao encontro id categoria quando sucesso`() {

        // Given
        //
        //FindById
        BDDMockito.`when`(categoriaRepository.findById(CategoriaBuilder.createCategoria().id))
                .thenReturn(Optional.empty())

        // When
        //
        val exception = assertThrows<ResourceNotFoundException> { categoriaService.deleteById(1) }

        // Then
        //
        assertEquals("Categoria não encontro id 1",exception.message)
    }

    @Test
    @Order(4)
    fun `Obter id categoria quando sucesso`() {

        // Given
        //
        val categoria = CategoriaDtoBuilder.categoriaRequest()
        //Save
        BDDMockito.`when`(categoriaRepository.save(ArgumentMatchers.any(Categoria::class.java)))
                .thenReturn(CategoriaBuilder.createCategoria())

        //FindById
        BDDMockito.`when`(categoriaRepository.findById(CategoriaBuilder.createCategoria().id))
                .thenReturn(Optional.of(CategoriaBuilder.createCategoria()))

        BDDMockito.`when`(mapper.convertDtoToEntity(CategoriaDtoBuilder.categoriaRequest()))
                .thenReturn(CategoriaBuilder.createCategoria())

        BDDMockito.`when`(mapper.convertEntityToDto(CategoriaBuilder.createCategoria()))
                .thenReturn(CategoriaDtoBuilder.categoriaResponse())

        // When
        //
        val saveCategoria = categoriaService.save(categoria)
        val idCategoria = categoriaService.findById(saveCategoria.id)

        // Then
        //
        assertEquals(idCategoria.nome,categoria.nome)
    }


    @Test
    @Order(5)
    fun `Obter nao encontro id categoria quando sucesso`() {


        BDDMockito.`when`(categoriaRepository.findById(CategoriaBuilder.createCategoria().id))
                .thenReturn(Optional.empty())

        val exception = assertThrows<ResourceNotFoundException> {
            categoriaService.findById(1)
        }

        assertEquals("Categoria não encontro id 1",exception.message)

    }


    @Test
    @Order(6)
    fun `Consultar categoria quando sucesso`() {

        // Given
        //
        val categoria = CategoriaDtoBuilder.categoriaRequest()
        //Save
        BDDMockito.`when`(categoriaRepository.save(ArgumentMatchers.any(Categoria::class.java)))
                .thenReturn(CategoriaBuilder.createCategoria())
        //FindAll
        val listCategoria: List<Categoria> =
                listOf(CategoriaBuilder.createCategoria(), CategoriaBuilder.createCategoria())
        BDDMockito.`when`(categoriaRepository.findAll()).thenReturn(listCategoria)

        BDDMockito.`when`(mapper.convertDtoToEntity(CategoriaDtoBuilder.categoriaRequest()))
                .thenReturn(CategoriaBuilder.createCategoria())

        BDDMockito.`when`(mapper.convertEntityToDto(CategoriaBuilder.createCategoria()))
                .thenReturn(CategoriaDtoBuilder.categoriaResponse())


        // When
        //
        categoriaService.save(categoria)
        val categorias = categoriaService.findAll()

        // Then
        //
        BDDMockito.verify(categoriaRepository).findAll();
        assertNotNull(categorias)
        assertEquals(categorias[0].nome,categoria.nome)
    }


    @Test
    @Order(7)
    fun `Dados se verificar existe nome da categoria  quando entao retorno ResourceNotFoundException`() {

        // Given
        //
        val categoriaRequestBuilder = CategoriaDtoBuilder.categoriaRequest()
        BDDMockito.`when`(categoriaRepository.findByNome(ArgumentMatchers.anyString())).thenReturn(
                listOf(CategoriaBuilder.validCategoria())
        )

        // When
        //
        val exception = assertThrows<ResourceAlreadyExistException> { categoriaService.save(categoriaRequestBuilder) }

        BDDMockito.verify(categoriaRepository, times(1)).findByNome("computacao");

        // Then
        //
        assertEquals("Categoria já existe nome ${categoriaRequestBuilder.nome}",exception.message)
    }


    @Test
    @Order(8)
    fun `Dados não encontro id categoria quando  entao retorno ResourceNotFoundException`() {

        // Given
        //
        //FindById
        BDDMockito.`when`(categoriaRepository.findById(CategoriaBuilder.createCategoria().id))
                .thenReturn(Optional.empty())

        // When
        //
       var categoria = CategoriaDto(1, "computacao")
        val exception = assertThrows<ResourceNotFoundException> { categoriaService.update(categoria.id, categoria) }

        // Then
        //
        assertEquals("Categoria não encontro id ${categoria.id}",exception.message)
    }
}