package isi.deso.g10.deliverymanagementsystem.controller.springboot;

import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.dto.ClienteDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.CoordenadaDTO;
import isi.deso.g10.deliverymanagementsystem.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    private final String clienteRequestJson = """
        {
            "id":1,
            "cuit": "32405393942",
            "email": "email@email.com",
            "nombre": "nombre",
            "direccion": "direccion",
            "coordenadas": {
                "lat": 1,
                "lon": 2
            }
        }
    """;


    @Test
    public void getClienteIdOk() throws Exception {
        ClienteDTO mockCliente = new ClienteDTO();
        mockCliente.setId(1);
        mockCliente.setCuit("32405393942");
        mockCliente.setEmail("email@email.com");
        mockCliente.setNombre("nombre");
        mockCliente.setDireccion("direccion");
        mockCliente.setCoordenadas(new CoordenadaDTO(1,2));

        Mockito.when(clienteService.getById(1)).thenReturn(mockCliente);

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/1").
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void getClienteIdNotFound() throws Exception {
        Mockito.when(clienteService.getById(1)).thenThrow(new ChangeSetPersister.NotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/1").accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

    @Test
    public void getClienteIdRuntimeException() throws Exception {
        Mockito.when(clienteService.getById(1)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/1").accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isInternalServerError());
    }

    @Test
    public void getClientesOk() throws Exception {
        ClienteDTO cliente1 = new ClienteDTO();
        cliente1.setId(1);
        cliente1.setCuit("32405393942");
        cliente1.setEmail("email1@email.com");
        cliente1.setNombre("nombre1");
        cliente1.setDireccion("direccion1");
        cliente1.setCoordenadas(new CoordenadaDTO(1,2));

        ClienteDTO cliente2 = new ClienteDTO();
        cliente2.setId(2);
        cliente2.setCuit("32405393943");
        cliente2.setEmail("email2@email.com");
        cliente2.setNombre("nombre2");
        cliente2.setDireccion("direccion2");
        cliente2.setCoordenadas(new CoordenadaDTO(1,2));

        List<ClienteDTO> mockClientes = List.of(cliente1, cliente2);

        Mockito.when(clienteService.getAll()).thenReturn(mockClientes);

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }


    @Test
    public void getClientesNotFound() throws Exception {
        Mockito.when(clienteService.getAll()).thenThrow(new ChangeSetPersister.NotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getClientesInternalServerError() throws Exception {
        Mockito.when(clienteService.getAll()).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void crearClienteOk() throws Exception {

        ClienteDTO clienteResponse = new ClienteDTO();
        clienteResponse.setId(1);
        clienteResponse.setCuit("32405393942");
        clienteResponse.setEmail("email@email.com");
        clienteResponse.setNombre("nombre");
        clienteResponse.setDireccion("direccion");
        clienteResponse.setCoordenadas(new CoordenadaDTO(1, 2));

        Mockito.when(clienteService.saveCliente(Mockito.any(ClienteDTO.class))).thenReturn(clienteResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteRequestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void deleteClienteOk() throws Exception {
        Mockito.doNothing().when(clienteService).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/cliente/delete/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteClienteNotFound() throws Exception {
        Mockito.doThrow(new RuntimeException("Cliente no encontrado")).when(clienteService).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/cliente/delete/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateClienteOk() throws Exception {

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(2);
        clienteDTO.setCuit("32405393942");
        clienteDTO.setEmail("email@email.com");
        clienteDTO.setNombre("nombre");
        clienteDTO.setDireccion("direccion");
        clienteDTO.setCoordenadas(new CoordenadaDTO(1, 2));

        Mockito.when(clienteService.updateCliente(Mockito.any(ClienteDTO.class))).thenReturn(clienteDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteRequestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    public void updateClienteNotFound() throws Exception {
        // Simular que el servicio lanza NotFoundException
        Mockito.doThrow(new ChangeSetPersister.NotFoundException()).when(clienteService).updateCliente(Mockito.any(ClienteDTO.class));

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1);
        clienteDTO.setCuit("32405393942");
        clienteDTO.setEmail("email@email.com");
        clienteDTO.setNombre("nombre");
        clienteDTO.setDireccion("direccion");
        clienteDTO.setCoordenadas(new CoordenadaDTO(1, 2));

        mockMvc.perform(MockMvcRequestBuilders.put("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteRequestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateClienteInternalServerError() throws Exception {
        // Simular que el servicio lanza RuntimeException
        Mockito.doThrow(new RuntimeException("Error en el servidor")).when(clienteService).updateCliente(Mockito.any(ClienteDTO.class));

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1);
        clienteDTO.setCuit("32405393942");
        clienteDTO.setEmail("email@email.com");
        clienteDTO.setNombre("nombre");
        clienteDTO.setDireccion("direccion");
        clienteDTO.setCoordenadas(new CoordenadaDTO(1, 2));

        mockMvc.perform(MockMvcRequestBuilders.put("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteRequestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

}
