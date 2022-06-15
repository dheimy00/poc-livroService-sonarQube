package br.com.livro.service

import br.com.livro.dto.AutorDto
import br.com.livro.exception.ResourceAlreadyExistException
import br.com.livro.exception.ResourceNotFoundException
import br.com.livro.mapper.AutorMapper
import br.com.livro.model.Autor
import br.com.livro.repository.AutorRepository
import br.com.livro.service.implementations.AutorServiceImpl
import br.com.livro.builders.AutorBuilder
import br.com.livro.builders.requests.AutorDtoBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import org.springframework.test.context.ActiveProfiles
import java.util.*

@ActiveProfiles("Test")
@ExtendWith(MockitoExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Autor Service Test")
class AutorServiceTest {


    @InjectMocks
    lateinit var autorService: AutorServiceImpl

    @Mock
    lateinit var mapper: AutorMapper

    @Mock
    lateinit var autorRepository: AutorRepository


    @Test
    @Order(1)
    fun `Criar autor quando sucesso`() {

        // Given
        //
        val autorBuilder = AutorDtoBuilder.autorRequest()

        BDDMockito.`when`(autorRepository.save(AutorBuilder.createAutor()))
                .thenReturn(AutorBuilder.createAutor())

        BDDMockito.`when`(mapper.convertDtoToEntity(AutorDtoBuilder.autorRequest()))
                .thenReturn(AutorBuilder.createAutor())

        BDDMockito.`when`(mapper.convertEntityToDto(AutorBuilder.createAutor()))
                .thenReturn(AutorDtoBuilder.autorResponse())

        // When
        //
        val autor = autorService.save(autorBuilder)

        // Then
        //
        assertNotNull(autor)
        assertEquals(autor.id, autorBuilder.id)
        assertEquals(autor.nome, AutorDtoBuilder.validAutor().nome)

    }


    @Test
    @Order(2)
    fun `Atualizar autor quando sucesso`() {

        // Given
        //
        val autor = AutorDtoBuilder.autorRequest()
        //Save
        BDDMockito.`when`(autorRepository.save(ArgumentMatchers.any(Autor::class.java)))
                .thenReturn(AutorBuilder.createAutor())
        //FindById
        BDDMockito.`when`(autorRepository.findById(AutorDtoBuilder.autorRequest().id))
                .thenReturn(Optional.of(AutorBuilder.createAutor()))

        BDDMockito.`when`(mapper.convertDtoToEntity(AutorDtoBuilder.autorRequest()))
                .thenReturn(AutorBuilder.createAutor())

        BDDMockito.`when`(mapper.convertEntityToDto(AutorBuilder.createAutor()))
                .thenReturn(AutorDtoBuilder.autorResponse())

        // When
        //
        val saveAutor = autorService.save(autor)
        //Verificar id autor
        val idAutor = autorService.findById(saveAutor.id)
        val saveUpdate = AutorDtoBuilder.updateAutor()
        val updateAutor = autorService.update(idAutor.id, saveUpdate)

        // Then
        //
        assertNotNull(updateAutor)
        assertEquals(updateAutor.nome, saveAutor.nome)
    }

    @Test
    @Order(3)
    fun `Excluir id autor quando sucesso`() {

        BDDMockito.`when`(autorRepository.findById(AutorBuilder.createAutor().id))
                .thenReturn(Optional.of(AutorBuilder.createAutor()))

        assertDoesNotThrow { autorService.deleteById(AutorDtoBuilder.autorRequest().id) }


    }

    @Test
    @Order(3)
    fun `Excluir nao encontro id autor quando sucesso`() {

        BDDMockito.`when`(autorRepository.findById(AutorBuilder.createAutor().id))
                .thenReturn(Optional.empty())

        val exception = assertThrows <ResourceNotFoundException>{
            autorService.deleteById(1)
        }

        assertEquals("Autor não encontro id 1",exception.message)

    }

    @Test
    @Order(4)
    fun `Obter id autor quando sucesso`() {

        // Given
        //
        val autor = AutorDtoBuilder.autorRequest()
        //Save
        BDDMockito.`when`(autorRepository.save(ArgumentMatchers.any(Autor::class.java)))
                .thenReturn(AutorBuilder.createAutor())
        //FindById
        BDDMockito.`when`(autorRepository.findById(AutorDtoBuilder.autorRequest().id))
                .thenReturn(Optional.of(AutorBuilder.createAutor()))

        BDDMockito.`when`(mapper.convertDtoToEntity(AutorDtoBuilder.autorRequest()))
                .thenReturn(AutorBuilder.createAutor())

        BDDMockito.`when`(mapper.convertEntityToDto(AutorBuilder.createAutor()))
                .thenReturn(AutorDtoBuilder.autorResponse())

        // When
        //
        val saveAutor = autorService.save(autor)
        val idAutor = autorService.findById(saveAutor.id!!)

        // Then
        //
        verify(autorRepository, times(1)).findById(1);
        assertEquals(idAutor.nome, autor.nome)
    }


    @Test
    @Order(5)
    fun `Obter nao encontro id autor quando sucesso`() {

        BDDMockito.`when`(autorRepository.findById(AutorBuilder.createAutor().id))
                .thenReturn(Optional.empty())

        val exception = assertThrows <ResourceNotFoundException>{
            autorService.findById(1)
        }

        assertEquals("Autor não encontro id 1",exception.message)

    }


    @Test
    @Order(6)
    fun `Consultar autor quando sucesso`() {

        // Given
        //
        val autor = AutorDtoBuilder.autorRequest()
        //Save
        BDDMockito.`when`(autorRepository.save(ArgumentMatchers.any(Autor::class.java)))
                .thenReturn(AutorBuilder.createAutor())
        //FindAll
        val listAutores: List<Autor> = listOf(AutorBuilder.createAutor(), AutorBuilder.createAutor())
        BDDMockito.`when`(autorRepository.findAll()).thenReturn(listAutores)

        BDDMockito.`when`(mapper.convertDtoToEntity(AutorDtoBuilder.autorRequest()))
                .thenReturn(AutorBuilder.createAutor())

        BDDMockito.`when`(mapper.convertEntityToDto(AutorBuilder.createAutor()))
                .thenReturn(AutorDtoBuilder.autorResponse())

        // When
        //
        val saveAutor = autorService.save(autor)
        val autores = autorService.findAll()

        // Then
        //
        verify(autorRepository).findAll();
        assertEquals(autores[0].nome, autor.nome)
    }


    @Test
    @Order(7)
    fun `Dados verificar autor já existe email quando entao retorno ResourceNotFoundException`() {

        val autorBuilder = AutorDtoBuilder.autorRequest()
        BDDMockito.`when`(autorRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(
                Optional.of(AutorBuilder.vaildAutor())
        )

        assertThrows(ResourceAlreadyExistException::class.java,
                { autorService.save(autorBuilder) },
                "Autor já existe email ${autorBuilder.email}")
    }

    @Test
    @Order(8)
    fun `Dados não encontro id autor quando entao retorno ResourceNotFoundException`() {

        BDDMockito.`when`(autorRepository.findById(AutorBuilder.createAutor().id))
                .thenReturn(Optional.of(AutorBuilder.createAutor()))

        val autorUpdate = AutorDto(2, "Teste", "teste_descricao", "teste@gmail.com")

        val exception = assertThrows <ResourceNotFoundException>{
            autorService.update(autorUpdate.id, autorUpdate)
        }

        assertEquals("Autor não encontro id 2",exception.message)
    }


}

