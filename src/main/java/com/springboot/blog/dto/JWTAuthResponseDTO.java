package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "JWTAuthResponseDTO Model Information")
public class JWTAuthResponseDTO {
     @Schema(description = "JWT Access Token ")
     private String accessToken;

     @Schema(description = "Token Type")
     private final String tokenType = "Bearer";
}
