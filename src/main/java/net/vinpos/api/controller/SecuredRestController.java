package net.vinpos.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
public interface SecuredRestController {}
