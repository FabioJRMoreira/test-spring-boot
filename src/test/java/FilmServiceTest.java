import com.example.testspringboot.converters.FilmMapper;
import com.example.testspringboot.dto.FilmDto;
import com.example.testspringboot.exception.InvalidFilmException;
import com.example.testspringboot.exception.NullActeurException;
import com.example.testspringboot.model.Acteur;
import com.example.testspringboot.model.Film;
import com.example.testspringboot.repository.FilmRepository;
import com.example.testspringboot.service.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class FilmServiceTest {
    @Mock
    private FilmRepository filmRepository;

    @Mock
    private FilmMapper filmMapper;

    @InjectMocks
    private FilmService filmService;

    private Film film;
    private FilmDto filmDto;
    private Acteur acteur;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        acteur = new Acteur();
        acteur.setId(1L);
        acteur.setNom("Moreira");
        acteur.setPrenom("Fabio");

        film = new Film();
        film.setId(1L);
        film.setTitre("Test Title");
        film.setDescription("Test Description");
        film.setActeurs(Collections.singletonList(acteur));

        filmDto = new FilmDto();
        filmDto.setId(1L);
        filmDto.setTitre("Test Title");
        filmDto.setDescription("Test Description");
        filmDto.setActeurs(Collections.singletonList(acteur));
    }

    @Test
    public void testSaveFilm() {
        when(filmMapper.dtoToModel(any(FilmDto.class))).thenReturn(film);
        when(filmRepository.save(any(Film.class))).thenReturn(film);
        when(filmMapper.modelToDto(any(Film.class))).thenReturn(filmDto);

        FilmDto savedFilmDto = filmService.saveFilm(filmDto);

        assertEquals(filmDto, savedFilmDto);
        verify(filmRepository, times(1)).save(film);
    }

    @Test
    public void testSaveFilmInvalidTitle() {
        filmDto.setTitre(null);

        assertThrows(InvalidFilmException.class, () -> {
            filmService.saveFilm(filmDto);
        });

        verify(filmRepository, never()).save(any(Film.class));
    }

    @Test
    public void testSaveFilmInvalidDescription() {
        filmDto.setDescription(null);

        assertThrows(InvalidFilmException.class, () -> {
            filmService.saveFilm(filmDto);
        });

        verify(filmRepository, never()).save(any(Film.class));
    }

    @Test
    public void testSaveFilmNullActeur() {
        filmDto.setActeurs(null);

        assertThrows(NullActeurException.class, () -> {
            filmService.saveFilm(filmDto);
        });

        verify(filmRepository, never()).save(any(Film.class));
    }

    @Test
    public void testSaveFilmEmptyActeurs() {
        filmDto.setActeurs(Collections.emptyList());

        assertThrows(NullActeurException.class, () -> {
            filmService.saveFilm(filmDto);
        });

        verify(filmRepository, never()).save(any(Film.class));
    }

    @Test
    public void testGetAllFilms() {
        when(filmRepository.findAll()).thenReturn(Arrays.asList(film));
        when(filmMapper.modelToDto(any(Film.class))).thenReturn(filmDto);

        List<FilmDto> films = filmService.getAllFilms();

        assertEquals(1, films.size());
        assertEquals(filmDto, films.get(0));
        verify(filmRepository, times(1)).findAll();
    }

    @Test
    public void testGetFilmById() {
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
        when(filmMapper.modelToDto(any(Film.class))).thenReturn(filmDto);

        FilmDto foundFilmDto = filmService.getFilmById(1L);

        assertEquals(filmDto, foundFilmDto);
        verify(filmRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetFilmByIdNotFound() {
        when(filmRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidFilmException.class, () -> {
            filmService.getFilmById(1L);
        });

        verify(filmRepository, times(1)).findById(1L);
    }
}
