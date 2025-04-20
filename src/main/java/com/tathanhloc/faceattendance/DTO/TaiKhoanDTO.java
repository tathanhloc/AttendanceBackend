package com.tathanhloc.faceattendance.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoanDTO {
    private Long id;
    private String username;
    private String passwordHash;
    private String vaiTro;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private String maSv;
    private String maGv;
}