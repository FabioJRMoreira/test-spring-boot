import com.example.testspringboot.controller.FilmController;
import com.example.testspringboot.dto.FilmDto;
import com.example.testspringboot.exception.GlobalExceptionHandler;
import com.example.testspringboot.service.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FilmControllerTest {
    @InjectMocks
    private FilmController filmController;

    @Mock
    private FilmService filmService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(filmController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testAddFilm() throws Exception {
        FilmDto film = new FilmDto();
        film.setTitre("Test Title");
        film.setDescription("Test Description");

        when(filmService.saveFilm(any(FilmDto.class))).thenReturn(film);

        mockMvc.perform(post("/api/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titre\":\"Test Title\",\"description\":\"Test Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titre").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test Description"));

        verify(filmService, times(1)).saveFilm(any(FilmDto.class));
    }

    @Test
    public void testAddFilmThrowsException() throws Exception {
        when(filmService.saveFilm(any(FilmDto.class))).thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(post("/api/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titre\":\"Test Title\",\"description\":\"Test Description\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Service error"));

        verify(filmService, times(1)).saveFilm(any(FilmDto.class));
    }

    @Test
    public void testGetAllFilms() throws Exception {
        FilmDto film1 = new FilmDto();
        film1.setTitre("Film 1");
        film1.setDescription("Description 1");

        FilmDto film2 = new FilmDto();
        film2.setTitre("Film 2");
        film2.setDescription("Description 2");

        when(filmService.getAllFilms()).thenReturn(List.of(film1, film2));

        mockMvc.perform(get("/api/film"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titre").value("Film 1"))
                .andExpect(jsonPath("$[1].titre").value("Film 2"));

        verify(filmService, times(1)).getAllFilms();
    }

    @Test
    public void testGetAllFilmsThrowsException() throws Exception {
        when(filmService.getAllFilms()).thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(get("/api/film"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Service error"));

        verify(filmService, times(1)).getAllFilms();
    }

    @Test
    public void testGetFilmById() throws Exception {
        FilmDto film = new FilmDto();
        film.setId(1L);
        film.setTitre("Test Title");
        film.setDescription("Test Description");

        when(filmService.getFilmById(anyLong())).thenReturn(film);

        mockMvc.perform(get("/api/film/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titre").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test Description"));

        verify(filmService, times(1)).getFilmById(1L);
    }

    @Test
    public void testGetFilmByIdNotFound() throws Exception {
        when(filmService.getFilmById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/film/2"))
                .andExpect(status().isNotFound());

        verify(filmService, times(1)).getFilmById(2L);
    }

    @Test
    public void testGetFilmByIdThrowsException() throws Exception {
        when(filmService.getFilmById(anyLong())).thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(get("/api/film/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Service error"));

        verify(filmService, times(1)).getFilmById(1L);
    }
}
