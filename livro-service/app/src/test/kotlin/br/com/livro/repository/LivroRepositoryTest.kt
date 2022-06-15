package br.com.livro.repository

import br.com.livro.model.Livro
import br.com.livro.builders.AutorBuilder
import br.com.livro.builders.CategoriaBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ActiveProfiles("Test")
@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@DisplayName("Livro Repository Test")
class LivroRepositoryTest {

    @Autowired
    lateinit var livroRepository: LivroRepository

    @Autowired
    lateinit var categoriaRepository: CategoriaRepository

    @Autowired
    lateinit var autorRepository: AutorRepository

    @Test
    fun `Criar livro quando sucesso`() {

        //Categoria
        val categoriaBuilder = CategoriaBuilder.createCategoria()
        val categoria = categoriaRepository.save(categoriaBuilder)
        val idCategoria = categoriaRepository.findById(categoria.id!!)
        //Autor
        val autorBuilder = AutorBuilder.createAutor()
        val autor = autorRepository.save(autorBuilder)
        val idAutor = autorRepository.findById(autor.id!!)


        //Livro
        val livroBuilder = Livro(
                1,
                "Teste",
                "resumo",
                "teste_isbn",
                "teste_sumario",
                100,
                Date(),
                idCategoria.get(),
                idAutor.get()
        )
        val livro = livroRepository.save(livroBuilder)

        Assertions.assertThat(livro).isNotNull
        Assertions.assertThat(livro.id).isNotNull
        Assertions.assertThat(livro.titulo).isEqualTo(livroBuilder.titulo)

    }


    @Test
    fun `Atualizar livro quando sucesso`() {


        //Categoria
        val categoriaBuilder = CategoriaBuilder.createCategoria()
        val categoria = categoriaRepository.save(categoriaBuilder)
        val idCategoria = categoriaRepository.findById(categoria.id!!)
        //Autor
        val autorBuilder = AutorBuilder.createAutor()
        val autor = autorRepository.save(autorBuilder)
        val idAutor = autorRepository.findById(autor.id!!)
        //Livro
        val livroBuilder = Livro(
                1,
                "Teste",
                "resumo",
                "teste_isbn",
                "teste_sumario",
                100,
                Date(),
                idCategoria.get(),
                idAutor.get()
        )

        // When
        //
        val saveLivro = livroRepository.save(livroBuilder)
        saveLivro.titulo = "Teste"
        val updateCategoria = livroRepository.save(saveLivro)

        // Then
        //
        Assertions.assertThat(updateCategoria.id).isNotNull
        Assertions.assertThat(updateCategoria.titulo).isEqualTo(saveLivro.titulo)
    }

    @Test
    fun `Excluir livro quando sucesso`() {

        //Categoria
        val categoriaBuilder = CategoriaBuilder.createCategoria()
        val categoria = categoriaRepository.save(categoriaBuilder)
        val idCategoria = categoriaRepository.findById(categoria.id!!)
        //Autor
        val autorBuilder = AutorBuilder.createAutor()
        val autor = autorRepository.save(autorBuilder)
        val idAutor = autorRepository.findById(autor.id!!)
        //Livro
        val livroBuilder = Livro(
                1,
                "Teste",
                "resumo",
                "teste_isbn",
                "teste_sumario",
                100,
                Date(),
                idCategoria.get(),
                idAutor.get()
        )
        val saveLivro = livroRepository.save(livroBuilder)

        livroRepository.deleteById(saveLivro.id!!)
        val livro = livroRepository.findById(saveLivro.id!!)

        Assertions.assertThat(livro.isEmpty).isTrue
    }


    @Test
    fun `Obter id livro quando sucesso`() {

        val categoriaBuilder = CategoriaBuilder.createCategoria()
        val categoria = categoriaRepository.save(categoriaBuilder)
        val idCategoria = categoriaRepository.findById(categoria.id!!)
        //Autor
        val autorBuilder = AutorBuilder.createAutor()
        val autor = autorRepository.save(autorBuilder)
        val idAutor = autorRepository.findById(autor.id!!)
        //Livro
        val livroBuilder = Livro(
                1,
                "Teste",
                "resumo",
                "teste_isbn",
                "teste_sumario",
                100,
                Date(),
                idCategoria.get(),
                idAutor.get()
        )
        val saveLivro = livroRepository.save(livroBuilder)

        val livro = livroRepository.findById(saveLivro.id!!)

        Assertions.assertThat(livro.get().titulo).isEqualTo(livroBuilder.titulo)
    }

    @Test
    fun `Consultar livro quando sucesso`() {

        //Categoria
        val categoriaBuilder = CategoriaBuilder.createCategoria()
        val categoria = categoriaRepository.save(categoriaBuilder)
        val idCategoria = categoriaRepository.findById(categoria.id!!)
        //Autor
        val autorBuilder = AutorBuilder.createAutor()
        val autor = autorRepository.save(autorBuilder)
        val idAutor = autorRepository.findById(autor.id!!)
        //Livro
        val livroBuilder = Livro(
                1,
                "Teste",
                "resumo",
                "teste_isbn",
                "teste_sumario",
                100,
                Date(),
                idCategoria.get(),
                idAutor.get()
        )
        val saveLivro = livroRepository.save(livroBuilder)

        val livros = livroRepository.findAll()

        Assertions.assertThat(livros).isNotEmpty
        Assertions.assertThat(livros[0].titulo).isEqualTo(saveLivro.titulo)
    }
}