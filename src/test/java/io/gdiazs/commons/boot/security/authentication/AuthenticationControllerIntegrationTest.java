package io.gdiazs.commons.boot.security.authentication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.gdiazs.commons.boot.security.TestUtil;
import io.gdiazs.commons.boot.security.WebIntegrationTestConfigAware;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


public class AuthenticationControllerIntegrationTest extends WebIntegrationTestConfigAware {


    @Test
    public void testAuthenticationRequest() throws Exception {
        AuthenticationRequest authentication = new AuthenticationRequest();
        authentication.setUsername("admin");
        authentication.setPassword("Test1234");

        String jsonAuthentication = TestUtil.convertObjectToJsonString(authentication);

        ResultActions res = mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(jsonAuthentication));

        res.andExpect(status().isOk());
        res.andDo( result -> {

           System.out.println(result.getResponse().getContentAsString());
        });


    }

}