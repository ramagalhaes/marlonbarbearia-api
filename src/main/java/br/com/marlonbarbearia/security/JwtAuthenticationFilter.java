package br.com.marlonbarbearia.security;

import br.com.marlonbarbearia.account.Account;
import br.com.marlonbarbearia.exceptions.AuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)  {
        try {
            LoginRequest credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            credentials.phoneNumber(), credentials.password(), new ArrayList<>());

            return authenticationManager.authenticate(authenticationToken);

        } catch (IOException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    @Override
    public void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authentication) throws IOException, ServletException {

        String username = ((Account) authentication.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        response.addHeader("Authorization", "Bearer " + token);

    }

}
