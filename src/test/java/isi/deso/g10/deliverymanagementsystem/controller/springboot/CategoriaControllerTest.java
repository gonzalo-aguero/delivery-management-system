package isi.deso.g10.deliverymanagementsystem.controller.springboot;

import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.dto.CategoriaDTO;
import isi.deso.g10.deliverymanagementsystem.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService service;

    @Test
    public void getCategoriasEncontradas() throws Exception {
        List<CategoriaDTO> mockCategorias = List.of(
                new CategoriaDTO(new Categoria()),
                new CategoriaDTO(new Categoria())
        );
        Mockito.when(service.getAll()).thenReturn(mockCategorias);
        mockMvc.perform(MockMvcRequestBuilders.get("/categoria").
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void getCategoriasNoEncontradas() throws Exception {
        Mockito.when(service.getAll()).thenThrow(new ChangeSetPersister.NotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/categoria").
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

    @Test
    public void getCategoriasRuntimeException() throws Exception {
        Mockito.when(service.getAll()).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.get("/categoria").
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isInternalServerError());
    }
}
