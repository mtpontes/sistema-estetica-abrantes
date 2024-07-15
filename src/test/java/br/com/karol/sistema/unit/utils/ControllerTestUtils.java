package br.com.karol.sistema.unit.utils;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.karol.sistema.domain.enums.UserRole;

public class ControllerTestUtils {

    public static ResultActions postRequest(MockMvc mvc, String url, String requestBody) throws Exception {
        return mvc.perform(
            MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
    }
    public static ResultActions putRequest(MockMvc mvc, String url, String requestBody) throws Exception {
        return mvc.perform(
            MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
    }
    public static ResultActions patchRequest(MockMvc mvc, String url, String requestBody) throws Exception {
        return mvc.perform(
            MockMvcRequestBuilders.patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
    }
    public static ResultActions deleteMapping(MockMvc mvc, String url) throws Exception {
        return mvc.perform(
            MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON));
    }
    public static ResultActions getRequest(MockMvc mvc, String url) throws Exception {
        return mvc.perform(
            MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON));
    }

    public static void withMockUserManual(String role) {
        var user = UsuarioUtils.getUsuario();
        user.setRole(UserRole.fromString(role));
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }
}