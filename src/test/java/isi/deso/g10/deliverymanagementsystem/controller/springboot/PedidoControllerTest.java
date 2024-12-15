package isi.deso.g10.deliverymanagementsystem.controller.springboot;

import isi.deso.g10.deliverymanagementsystem.model.dto.PedidoDTO;
import isi.deso.g10.deliverymanagementsystem.repository.PedidoRepository;
import isi.deso.g10.deliverymanagementsystem.service.PedidoService;
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


@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Test
    public void getPedidoOk() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(1);

        Mockito.when(pedidoService.getById(1)).thenReturn(pedidoDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/pedido/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void getPedidoNotFound() throws Exception {
        Mockito.when(pedidoService.getById(1)).thenThrow(new ChangeSetPersister.NotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/pedido/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPedidoInternalServerError() throws Exception {
        Mockito.when(pedidoService.getById(1)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/pedido/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getPedidosOk() throws Exception {
        // Configuración de la lista simulada de PedidoDTO
        PedidoDTO pedido1 = new PedidoDTO();
        pedido1.setId(1);

        PedidoDTO pedido2 = new PedidoDTO();
        pedido2.setId(2);


        List<PedidoDTO> pedidos = List.of(pedido1, pedido2);

        // Simulación del comportamiento del servicio
        Mockito.when(pedidoService.getAll()).thenReturn(pedidos);

        // Realiza la solicitud GET y verifica la respuesta
        mockMvc.perform(MockMvcRequestBuilders.get("/pedido")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void getPedidosNotFound() throws Exception {
        Mockito.when(pedidoService.getAll()).thenThrow(new ChangeSetPersister.NotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get("/pedido")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPedidosInternalServerError() throws Exception {
        Mockito.when(pedidoService.getAll()).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/pedido")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
    
}
