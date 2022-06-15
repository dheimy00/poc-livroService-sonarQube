package br.com.livro.service

import br.com.livro.exception.ResourceAlreadyExistException
import br.com.livro.exception.ResourceNotFoundException
import br.com.livro.mapper.LivroMapper
import br.com.livro.model.Autor
import br.com.livro.model.Categoria
import br.com.livro.model.Livro
import br.com.livro.repository.AutorRepository
import br.com.livro.repository.CategoriaRepository
import br.com.livro.repository.LivroRepository
import br.com.livro.service.implementations.LivroServiceImpl
import br.com.livro.builders.AutorBuilder
import br.com.livro.builders.CategoriaBuilder
import br.com.livro.builders.LivroBuilder
import br.com.livro.builders.requests.LivroDtoBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.context.ActiveProfiles
import java.util.*

@ActiveProfiles("Test")
@ExtendWith(MockitoExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Livro Service Test")
class LivroServiceTest {

    @InjectMocks
    lateinit var livroService: LivroServiceImpl

    @Mock
    lateinit var livroRepository: LivroRepository

    @Mock
    lateinit var autorRepository: AutorRepository

    @Mock
    lateinit var categoriaRepository: CategoriaRepository

    @Mock
    lateinit var mapper: LivroMapper

    @BeforeEach
    fun setUp() {

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Categoria
        //
        //Save
        BDDMockito.`when`(categoriaRepository.save(ArgumentMatchers.any(Categoria::class.java)))
            .thenReturn(CategoriaBuilder.createCategoria())
        //FindById
        BDDMockito.`when`(categoriaRepository.findById(CategoriaBuilder.createCategoria().id))
            .thenReturn(Optional.of(CategoriaBuilder.createCategoria()))


        /* Entity */
        //Categoria
        var categoria = CategoriaBuilder.createCategoria()
        categoria = categoriaRepository.save(categoria)
        categoriaRepository.findById(categoria.id!!)


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Autor
        //
        //Save
        BDDMockito.`when`(autorRepository.save(ArgumentMatchers.any(Autor::class.java)))
            .thenReturn(AutorBuilder.createAutor())
        //FindById
        BDDMockito.`when`(autorRepository.findById(AutorBuilder.createAutor().id))
            .thenReturn(Optional.of(AutorBuilder.createAutor()))

        /* Entity */
        //Autor
        var autor = AutorBuilder.createAutor()
        autor = autorRepository.save(autor)
        autorRepository.findById(autor.id!!)

    }

    @Test
    @Order(1)
    fun `Criar livro quando sucesso`() {

        // Given
        //LivroRequest
        val livroRequestBuilder = LivroDtoBuilder.livroRequest()
        //Livro
        val livroBuilder = LivroBuilder.createLivro()
        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)

        BDDMockito.`when`(mapper.convertDtoToEntity(livroRequestBuilder))
            .thenReturn(livroBuilder)

        BDDMockito.`when`(mapper.convertEntityToDto(livroBuilder))
            .thenReturn(livroRequestBuilder)

        // When
        //
        val saveLivro = livroService.save(livroRequestBuilder)

        // Then
        //
        assertNotNull(saveLivro)
        assertNotNull(saveLivro.id)
        assertEquals(saveLivro.titulo,livroRequestBuilder.titulo)

    }


    @Test
    @Order(2)
    fun `Verifique o título já existe no livro  quando sucesso`() {

        // Given
        //
        //LivroRequest
        val livroRequestBuilder = LivroDtoBuilder.livroRequest()
        //Livro
        val livroBuilder = LivroBuilder.createLivro()

        //FindByTitulo
        BDDMockito.`when`(livroRepository.findByTitulo(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(livroBuilder))

        // When
        //
        val exception = assertThrows<ResourceAlreadyExistException> { livroService.save(livroRequestBuilder) }

        // Then
        //
        assertEquals("Livro já existe título Teste",exception.message)

    }


    @Test
    @Order(3)
    fun `Atualizar livro quando sucesso`() {


        //Given
        //Updatelivro
        val updatelivroRequestBuilder = LivroDtoBuilder.updateLivro()
        //Livro
        val livroBuilder = LivroBuilder.createLivro()
        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)
        //FindById
        BDDMockito.`when`(livroRepository.findById(LivroBuilder.createLivro().id))
            .thenReturn(Optional.of(livroBuilder))

        BDDMockito.`when`(mapper.convertEntityToDto(livroBuilder))
            .thenReturn(LivroDtoBuilder.livroResponse())

        // When
        //
        val Updatelivro = livroService.update(updatelivroRequestBuilder.id, updatelivroRequestBuilder)

        // Then
        //
        assertNotNull(Updatelivro.id)
        assertEquals(Updatelivro.titulo,"Teste")
    }

    @Test
    @Order(4)
    fun `Excluir livro quando sucesso`() {

        BDDMockito.`when`(livroRepository.findById(LivroBuilder.createLivro().id))
                .thenReturn(Optional.of(LivroBuilder.createLivro()))

        assertDoesNotThrow{ livroService.deleteById(LivroDtoBuilder.livroRequest().id)}

    }

    @Test
    @Order(5)
    fun `Obter id livro quando sucesso`() {

        // Given
        //
        //FindById
        BDDMockito.`when`(livroRepository.findById(LivroBuilder.createLivro().id))
                .thenReturn(Optional.of(LivroBuilder.createLivro()))

        BDDMockito.`when`(mapper.convertEntityToDto(LivroBuilder.createLivro()))
                .thenReturn(LivroDtoBuilder.livroResponse())

        // When
        //
        val livro = livroService.findById(LivroDtoBuilder.livroRequest().id)

        // Then
        //
        assertEquals(livro.titulo,"Teste")
    }

    @Test
    @Order(6)
    fun `Consultar livro quando sucesso`() {

        // Given
        //
        //livroRequest
        val livroRequestBuilder = LivroDtoBuilder.livroRequest()
        //Livro
        val livroBuilder = LivroBuilder.createLivro()

        //Save
        BDDMockito.`when`(livroRepository.save(ArgumentMatchers.any(Livro::class.java))).thenReturn(livroBuilder)
        //FindAll
        val listLivros: List<Livro> = listOf(livroBuilder, livroBuilder)
        BDDMockito.given(livroRepository.findAll()).willReturn(listLivros)

        BDDMockito.`when`(mapper.convertDtoToEntity(livroRequestBuilder))
            .thenReturn(livroBuilder)

        BDDMockito.`when`(mapper.convertEntityToDto(livroBuilder))
            .thenReturn(livroRequestBuilder)


        // When
        //
        livroService.save(livroRequestBuilder)
        val livros = livroService.findAll()

        // Then
        //
        assertNotNull(livros)
    }


    @Test
    @Order(7)
    fun `Dados se verificar existe titulo do livro  quando entao retorno ResourceNotFoundException`() {

        // Given
        //
        //livroRequest
        val livroRequestBuilder = LivroDtoBuilder.livroRequest()
        //Livro
        val livroBuilder = LivroBuilder.createLivro()

        BDDMockito.`when`(livroRepository.findByTitulo(ArgumentMatchers.anyString())).thenReturn(
            Optional.of(livroBuilder)
        )

        // When
        //
        val exception = assertThrows<ResourceAlreadyExistException> { livroService.save(livroRequestBuilder) }

        // Then
        //
        assertEquals("Livro já existe título ${livroRequestBuilder.titulo}",exception.message)
    }


    @Test
    @Order(8)
    fun `Dados não encontro id livro quando chamar alterar livro entao retorno ResourceNotFoundException`() {

        // Given
        //
        BDDMockito.`when`(livroRepository.findById(LivroBuilder.createLivro().id)).thenReturn(Optional.empty())

        // When
        //
        val livro = LivroDtoBuilder.updateLivro()
        val exception = assertThrows<ResourceNotFoundException> { livroService.update(livro.id, livro) }

        // Then
        //
        assertEquals("Livro não encontro id ${livro.id}",exception.message)
    }

    @Test
    @Order(9)
    fun `Dados livro não encontro id autor quando entao retorno ResourceNotFoundException`() {

        // Given
        //
        BDDMockito.`when`(autorRepository.findById(AutorBuilder.createAutor().id)).thenReturn(Optional.empty())

        // When
        //
        val livro = LivroDtoBuilder.livroRequest()
        val exception = assertThrows<ResourceNotFoundException> { livroService.save(livro) }

        // Then
        //
        assertEquals("Autor não encontro id ${livro.id_autor}",exception.message)
    }


    @Test
    @Order(10)
    fun `Dados livro não encontro id categoria quando entao retorno ResourceNotFoundException`() {

        // Given
        //
        BDDMockito.`when`(categoriaRepository.findById(CategoriaBuilder.createCategoria().id)).thenReturn(Optional.empty())

        // When
        //
        val livro = LivroDtoBuilder.livroRequest()
        val exception = assertThrows<ResourceNotFoundException> { livroService.save(livro) }

        // Then
        //
        assertEquals("Categoria não encontro id ${livro.id_categoria}",exception.message)
    }


    @Test
    @Order(11)
    fun `Dados  não encontro id livro quando chamar findByID entao retorno ResourceNotFoundException`() {

        // Given
        //
        BDDMockito.`when`(livroRepository.findById(LivroBuilder.createLivro().id)).thenReturn(Optional.empty())

        // When
        //
        val livro = LivroDtoBuilder.livroRequest()
        val exception = assertThrows<ResourceNotFoundException> { livroService.findById(livro.id) }

        // Then
        //
        assertEquals("Livro não encontro id ${livro.id}",exception.message)
    }

    @Test
    @Order(12)
    fun `Dados  não encontro id livro quando chamar deleteById entao retorno ResourceNotFoundException`() {

        // Given
        //
        BDDMockito.`when`(livroRepository.findById(LivroBuilder.createLivro().id)).thenReturn(Optional.empty())

        // When
        //
        val livro = LivroDtoBuilder.livroRequest()
        val exception = assertThrows<ResourceNotFoundException> { livroService.deleteById(livro.id) }

        // Then
        //
        assertEquals("Livro não encontro id ${livro.id}",exception.message)
    }

}