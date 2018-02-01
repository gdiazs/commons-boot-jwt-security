package io.gdiazs.commons.boot.security.authentication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gdiazs.commons.boot.security.TestUtil;
import io.gdiazs.commons.boot.security.WebIntegrationTestConfigAware;
import io.jsonwebtoken.Jwts;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

import static java.lang.System.out;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthenticationControllerIntegrationTest extends WebIntegrationTestConfigAware {

    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${token.expiration}")
    private String tokenExpiration;


    @Test
    public void testAuthenticationRequest() throws Exception {
        AuthenticationRequest authentication = new AuthenticationRequest();
        authentication.setUsername("admin");
        authentication.setPassword("Test1234");

        String jsonAuthentication = TestUtil.convertObjectToJsonString(authentication);

        ResultActions res = mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(jsonAuthentication));

        res.andExpect(status().isOk());

        res.andDo(response -> {

            ObjectMapper objectMapper = new ObjectMapper();
            String tokenJsonAsString = response.getResponse().getContentAsString();
            JsonNode jsonNode = objectMapper.readTree(tokenJsonAsString);

            String jwtToken = jsonNode.get("token").asText();
            out.println("JWT : " + jwtToken);

            final String secret = Base64.getEncoder().encodeToString(this.tokenSecret.getBytes());

            Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken);
            out.println("JWT : VALID" );

        });

    }


    @Test
    public void testAuthenticationRequest_with_rightRole() throws Exception {
        AuthenticationRequest authentication = new AuthenticationRequest();
        authentication.setUsername("admin");
        authentication.setPassword("Test1234");

        String jsonAuthentication = TestUtil.convertObjectToJsonString(authentication);

        ResultActions res = mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(jsonAuthentication));

        res.andExpect(status().isOk());

        res.andDo(response -> {

            ObjectMapper objectMapper = new ObjectMapper();
            String tokenJsonAsString = response.getResponse().getContentAsString();
            JsonNode jsonNode = objectMapper.readTree(tokenJsonAsString);
            String jwtToken = jsonNode.get("token").asText();

            mockMvc.perform(get("/ping").header("X-Auth-Token", jwtToken)).andDo( resp -> {
                Assert.assertTrue(resp.getResponse().getContentAsString().contains("currentTime"));
            }).andExpect(status().isOk());

        });

    }


    @Test
    public void testAuthenticationRequest_with_wrongRole() throws Exception {
        AuthenticationRequest authentication = new AuthenticationRequest();
        authentication.setUsername("admin");
        authentication.setPassword("Test1234");

        String jsonAuthentication = TestUtil.convertObjectToJsonString(authentication);

        ResultActions res = mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(jsonAuthentication));

        res.andExpect(status().isOk());

        res.andDo(response -> {

            ObjectMapper objectMapper = new ObjectMapper();
            String tokenJsonAsString = response.getResponse().getContentAsString();
            JsonNode jsonNode = objectMapper.readTree(tokenJsonAsString);
            String jwtToken = jsonNode.get("token").asText();

            mockMvc.perform(get("/pingError").header("X-Auth-Token", jwtToken)).andExpect(status().is(403));

        });

    }


}