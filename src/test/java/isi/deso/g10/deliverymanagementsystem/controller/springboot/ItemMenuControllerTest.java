package isi.deso.g10.deliverymanagementsystem.controller.springboot;

import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.dto.CategoriaDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.CoordenadaDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.ItemMenuDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.VendedorDTO;
import isi.deso.g10.deliverymanagementsystem.service.ClienteService;
import isi.deso.g10.deliverymanagementsystem.service.ItemMenuService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteControllerTest.class)
public class ItemMenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemMenuService itemMenuService;


}
