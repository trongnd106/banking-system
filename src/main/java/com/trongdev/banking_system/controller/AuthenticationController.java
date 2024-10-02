package com.trongdev.banking_system.controller;

import com.nimbusds.jose.JOSEException;
import com.trongdev.banking_system.dto.request.AuthenticationRequest;
import com.trongdev.banking_system.dto.request.IntrospectRequest;
import com.trongdev.banking_system.dto.request.LogoutRequest;
import com.trongdev.banking_system.dto.request.RefreshRequest;
import com.trongdev.banking_system.dto.response.ApiResponse;
import com.trongdev.banking_system.dto.response.AuthenticationResponse;
import com.trongdev.banking_system.dto.response.IntrospectResponse;
import com.trongdev.banking_system.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Authentication Controller")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @Operation(summary = "Generate token", description = "Send a request via this API to get token")
    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(2000)
                .result(result)
                .build();
    }

    @Operation(summary = "Introspect", description = "Send a request via this API to authenticated token")
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .code(2000)
                .result(authenticationService.introspect(request))
                .build();
    }

    @Operation(summary = "Logout", description = "Send a request via this API to logout")
    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .code(2000)
                .message("Logout successfully")
                .build();
    }

    @Operation(summary = "Refresh token", description = "Send a request via this API to refresh new token")
    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .code(2000)
                .result(authenticationService.refreshToken(request))
                .message("Request to refresh token succesfully!")
                .build();
    }
}
