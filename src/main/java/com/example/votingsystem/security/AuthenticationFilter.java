package com.example.votingsystem.security;

import com.example.votingsystem.model.Response;
import com.example.votingsystem.service.UserService;
import com.example.votingsystem.service.jwt.JwtServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author samwel.wafula
 * Created on 09/01/2024
 * Time 08:39
 * Project VotingSystem
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        log.info("header {}", header);
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                new CachedBodyHttpServletRequest(request);

        if (StringUtils.isEmpty(header) || !StringUtils.startsWith(header, "Bearer")) {
            filterChain.doFilter(cachedBodyHttpServletRequest, response);
            return;
        }

        String jwtToken = header.substring(7);
        String voterIdNumber = null;
        try {
            voterIdNumber = jwtService.getUserNameFromJwtToken(jwtToken);
        } catch (Exception e) {
            Response res = Response.builder().responseDesc(e.toString()).responseCode("400").data(null).build();
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //response.setContentLength(res.toString().length());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(res));
            System.out.println("Error here " + new ObjectMapper().writeValueAsString(res));

//            response.reset();
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.setContentLength(String.valueOf(e).length());
//            response.getWriter().write(String.valueOf(e));
//            return;
        }

        if (request.getRequestURI().equals("/api/v1/vote")) {
            log.info("voter Id From Token {}", voterIdNumber);

            Object requestData = cachedBodyHttpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            JsonNode jsonNode = new ObjectMapper().readTree(requestData.toString());
            System.out.println("from node: " + jsonNode.get("voterIdNumber").asText());
            String voterSubject = jsonNode.get("voterIdNumber").asText();

            if (voterIdNumber.equals(voterSubject)) {
                getUserAndCreateSecurityContext(request, response, filterChain, voterIdNumber, jwtToken, cachedBodyHttpServletRequest);
            } else {
                Response response1 = Response.builder()
                        .responseCode("400")
                        .responseDesc("Invalid Token")
                        .data(null)
                        .build();
                response.reset();
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setContentLength(response1.toString().length());
                response.getWriter().write(new ObjectMapper().writeValueAsString(response1));
                return;
            }

        }
        getUserAndCreateSecurityContext(request, response, filterChain, voterIdNumber, jwtToken, cachedBodyHttpServletRequest);
    }

    private void getUserAndCreateSecurityContext(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String voterIdNumber, String jwtToken, CachedBodyHttpServletRequest cachedBodyHttpServletRequest) throws IOException, ServletException {
        if (StringUtils.isNotEmpty(voterIdNumber) && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userService.loadUserByUsername(voterIdNumber);
            Date date = jwtService.getExpirationDate(jwtToken);
            LocalDateTime localDateTime = LocalDateTime.now();
            Date CurrentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            log.info("current {}", CurrentDate);
            log.info("token Expiry Date {}", date);

            if (CurrentDate.after(date)) {
                log.info("token expired");
                System.out.println("expired");

            } else {
                System.out.println("valid Token");
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(auth);
                SecurityContextHolder.setContext(securityContext);
                filterChain.doFilter(cachedBodyHttpServletRequest, response);
            }

        }
    }
}
