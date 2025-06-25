package com.steampals.steampals.controller.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/steam")
public class SteamLoginController {

    private static final String STEAM_OPENID_URL = "https://steamcommunity.com/openid/login";
    private static final String REALM = "http://localhost:8080"; // Cambia a tu dominio real
    private static final String RETURN_TO = REALM + "/auth/steam/callback";

    @GetMapping("/login")
    public void loginSteam(HttpServletResponse response) throws IOException {
        String redirectUrl = STEAM_OPENID_URL + "?" +
                "openid.ns=http://specs.openid.net/auth/2.0" +
                "&openid.mode=checkid_setup" +
                "&openid.return_to=" + URLEncoder.encode(RETURN_TO, StandardCharsets.UTF_8) +
                "&openid.realm=" + URLEncoder.encode(REALM, StandardCharsets.UTF_8) +
                "&openid.identity=http://specs.openid.net/auth/2.0/identifier_select" +
                "&openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select";

        response.sendRedirect(redirectUrl);
    }

    @GetMapping("/callback")
    public ResponseEntity<String> callbackSteam(@RequestParam Map<String, String> params) throws IOException, InterruptedException {

        // 1. Extraer el claimed_id
        String claimedId = params.get("openid.claimed_id");
        if (claimedId == null || !claimedId.startsWith("https://steamcommunity.com/openid/id/")) {
            return ResponseEntity.badRequest().body("Respuesta inválida: no se encontró claimed_id");
        }

        // 2. Validar la respuesta enviando un POST a Steam con openid.mode=check_authentication
        boolean valid = validarOpenId(params);

        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Fallo la validación OpenID con Steam.");
        }

        // 3. Extraer SteamID64 del claimed_id
        String steamId = claimedId.substring(claimedId.lastIndexOf("/") + 1);

        // Aquí puedes continuar con tu lógica, crear sesión, JWT, etc.
        return ResponseEntity.ok("Autenticado correctamente con SteamID: " + steamId);
    }

    private boolean validarOpenId(Map<String, String> params) throws IOException, InterruptedException {
        // Preparamos parámetros para la validación (copiar todo y cambiar openid.mode)
        Map<String, String> validationParams = new LinkedHashMap<>(params);
        validationParams.put("openid.mode", "check_authentication");

        // Construir body x-www-form-urlencoded
        StringBuilder body = new StringBuilder();
        for (Map.Entry<String, String> entry : validationParams.entrySet()) {
            if (!body.isEmpty()) body.append("&");
            body.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            body.append("=");
            body.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }

        // Usamos HttpClient Java 11+
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STEAM_OPENID_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // La respuesta debe contener "is_valid:true"
        return response.body().contains("is_valid:true");
    }
}