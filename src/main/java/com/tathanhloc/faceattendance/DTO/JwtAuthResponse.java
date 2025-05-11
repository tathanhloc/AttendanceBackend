package com.tathanhloc.faceattendance.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private TaiKhoanDTO user;

    public JwtAuthResponse(String accessToken, TaiKhoanDTO user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}
