package isi.deso.g10.deliverymanagementsystem.controller.springboot;

import isi.deso.g10.deliverymanagementsystem.exception.NotFoundException;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.dto.*;
import isi.deso.g10.deliverymanagementsystem.service.ClienteService;
import isi.deso.g10.deliverymanagementsystem.service.ItemMenuService;
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

@WebMvcTest(ItemMenuController.class)
public class ItemMenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemMenuService itemMenuService;

    private final String requestItemMenuDto = """
            {
            "id":1,
            "nombre":"Nuevo Item",
            "precio":15.0
            }
            """;

    @Test
    public void getItemMenuIdOk() throws Exception {
        ItemMenuDTO itemMenuDTO = new ItemMenuDTO();
        itemMenuDTO.setId(1);
        itemMenuDTO.setTipo(ItemMenuDTO.Tipo.BEBIDA);
        itemMenuDTO.setNombre("Test");
        itemMenuDTO.setDescripcion("Test");
        itemMenuDTO.setPrecio(100.00);
        itemMenuDTO.setCalorias(200);
        itemMenuDTO.setAptoVegetariano(true);
        itemMenuDTO.setAptoCeliaco(true);
        itemMenuDTO.setAptoVegano(true);
        itemMenuDTO.setPeso(500.00);
        itemMenuDTO.setVolumenEnMl(500.00);
        itemMenuDTO.setGraduacionAlcoholica(0.00);

        itemMenuDTO.setCategoria( new CategoriaDTO());
        itemMenuDTO.setCategoriaId(1);
        itemMenuDTO.getCategoria().setId(1);
        itemMenuDTO.getCategoria().setDescripcion("Test");
        itemMenuDTO.getCategoria().setTipoItem(Categoria.TipoItem.BEBIDA);

        itemMenuDTO.setVendedor(new VendedorDTO());
        itemMenuDTO.setVendedorId(1);
        itemMenuDTO.getVendedor().setId(1);
        itemMenuDTO.getVendedor().setNombre("Test");
        itemMenuDTO.getVendedor().setDireccion("Test");
        itemMenuDTO.getVendedor().setCoordenadas(new CoordenadaDTO(1,2));



        Mockito.when(itemMenuService.getById(1)).thenReturn(itemMenuDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/itemmenu/1")
                    .accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void getItemMenuIdNotFound() throws Exception {
        ItemMenuDTO itemMenuDTO = new ItemMenuDTO();

        Mockito.when(itemMenuService.getById(1)).thenThrow(new NotFoundException("no se ha encontrado el item menu"));
        mockMvc.perform(MockMvcRequestBuilders.get("/itemmenu/1")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getItemMenuRuntimeErr() throws Exception {
        ItemMenuDTO itemMenuDTO = new ItemMenuDTO();
        Mockito.when(itemMenuService.getById(1)).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.get("/itemmenu/1")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getItemsMenuOk() throws Exception {
        ItemMenuDTO item1 = new ItemMenuDTO();
        item1.setId(1);
        item1.setNombre("Item 1");
        item1.setPrecio(10.0);

        ItemMenuDTO item2 = new ItemMenuDTO();
        item2.setId(2);
        item2.setNombre("Item 2");
        item2.setPrecio(20.0);

        List<ItemMenuDTO> itemMenuList = List.of(item1, item2);

        Mockito.when(itemMenuService.getAll()).thenReturn(itemMenuList);

        mockMvc.perform(MockMvcRequestBuilders.get("/itemmenu")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void getItemsMenuNotFound() throws Exception {
        Mockito.when(itemMenuService.getAll()).thenThrow(new NotFoundException("No se encontraron items menu"));
        mockMvc.perform(MockMvcRequestBuilders.get("/itemmenu")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void getItemsByVendedorIdOk() throws Exception {
        ItemMenuDTO item1 = new ItemMenuDTO();
        item1.setId(1);
        item1.setVendedor(new VendedorDTO());
        item1.setVendedorId(1);
        item1.getVendedor().setId(1);

        ItemMenuDTO item2 = new ItemMenuDTO();
        item2.setId(2);
        item2.setVendedor(new VendedorDTO());
        item2.getVendedor().setId(1);
        item2.setVendedorId(1);

        List<ItemMenuDTO> itemMenuList = List.of(item1, item2);

        Mockito.when(itemMenuService.getByVendedorId(1)).thenReturn(itemMenuList);

        mockMvc.perform(MockMvcRequestBuilders.get("/itemmenu/by-vendedor")
                        .param("vendedorId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void getItemsByVendedorIdNotFound() throws Exception {
        Mockito.when(itemMenuService.getByVendedorId(1)).thenThrow(new NotFoundException("No se ha encontrado el item menu con ese id"));

        mockMvc.perform(MockMvcRequestBuilders.get("/itemmenu/by-vendedor")
                        .param("vendedorId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void getItemsByVendedorIdRuntimeException() throws Exception {
        Mockito.when(itemMenuService.getByVendedorId(1)).thenThrow(new RuntimeException());

        // Realiza la solicitud y verifica el estado de la respuesta
        mockMvc.perform(MockMvcRequestBuilders.get("/itemmenu/by-vendedor")
                        .param("vendedorId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void crearItemMenuOk() throws Exception {

        ItemMenuDTO responseItemMenuDTO = new ItemMenuDTO();
        responseItemMenuDTO.setId(1);
        responseItemMenuDTO.setNombre("Nuevo Item");
        responseItemMenuDTO.setPrecio(15.0);

        Mockito.when(itemMenuService.saveItemMenu(Mockito.any(ItemMenuDTO.class)))
                .thenReturn(responseItemMenuDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/itemmenu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestItemMenuDto)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void deleteItemMenuOk() throws Exception {
        Mockito.doNothing().when(itemMenuService).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/itemmenu/delete/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteItemMenuNotFound() throws Exception {
        Mockito.doThrow(new NotFoundException("No se ha encontrado el item pedido a elimiar"))
                .when(itemMenuService).deleteById(1);

        // Realiza la solicitud DELETE
        mockMvc.perform(MockMvcRequestBuilders.delete("/itemmenu/delete/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteItemMenuInternalServerError() throws Exception {
        Mockito.doThrow(new RuntimeException())
                .when(itemMenuService).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/itemmenu/delete/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void updateItemMenuOk() throws Exception {

        ItemMenuDTO responseItemMenuDTO = new ItemMenuDTO();
        responseItemMenuDTO.setId(2);
        responseItemMenuDTO.setNombre("Item Actualizado");
        responseItemMenuDTO.setPrecio(20.0);

        Mockito.when(itemMenuService.updateItemMenu(Mockito.any(ItemMenuDTO.class)))
                .thenReturn(responseItemMenuDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/itemmenu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestItemMenuDto)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Item Actualizado"))
                .andExpect(jsonPath("$.precio").value(20.0));
    }

    @Test
    public void updateItemMenuNotFound() throws Exception {

        Mockito.when(itemMenuService.updateItemMenu(Mockito.any(ItemMenuDTO.class)))
                .thenThrow(new NotFoundException("No se ha encontrado el item menu para actualizar"));

        mockMvc.perform(MockMvcRequestBuilders.put("/itemmenu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestItemMenuDto)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateItemMenuInternalServerError() throws Exception {

        Mockito.when(itemMenuService.updateItemMenu(Mockito.any(ItemMenuDTO.class)))
                .thenThrow(new RuntimeException());

        // Realiza la solicitud PUT
        mockMvc.perform(MockMvcRequestBuilders.put("/itemmenu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestItemMenuDto)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

}
