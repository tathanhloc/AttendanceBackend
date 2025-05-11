package com.tathanhloc.faceattendance.Controller;

import com.tathanhloc.faceattendance.DTO.JwtAuthResponse;
import com.tathanhloc.faceattendance.DTO.TaiKhoanDTO;
import com.tathanhloc.faceattendance.DTO.UserProfileDTO;
import com.tathanhloc.faceattendance.Model.LoginRequest;
import com.tathanhloc.faceattendance.Model.TaiKhoan;
import com.tathanhloc.faceattendance.Security.CustomUserDetails;
import com.tathanhloc.faceattendance.Security.JwtTokenProvider;
import com.tathanhloc.faceattendance.Service.TaiKhoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TaiKhoanService taiKhoanService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Đăng nhập với username: {}", request.getUsername());

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = tokenProvider.generateToken(auth);

        TaiKhoan user = ((CustomUserDetails) auth.getPrincipal()).getTaiKhoan();
        TaiKhoanDTO userDTO = taiKhoanService.convertToDTO(user);

        return ResponseEntity.ok(new JwtAuthResponse(jwt, userDTO));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            log.warn("Người dùng chưa được xác thực");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        log.info("Lấy thông tin người dùng hiện tại: {}", userDetails.getUsername());

        TaiKhoan tk = userDetails.getTaiKhoan();

        String hoTen = null;
        String maSo = null;
        String email = null;

        if (tk.getSinhVien() != null) {
            hoTen = tk.getSinhVien().getHoTen();
            maSo = tk.getSinhVien().getMaSv();
            email = tk.getSinhVien().getEmail();
        } else if (tk.getGiangVien() != null) {
            hoTen = tk.getGiangVien().getHoTen();
            maSo = tk.getGiangVien().getMaGv();
            email = tk.getGiangVien().getEmail();
        }

        return ResponseEntity.ok(UserProfileDTO.builder()
                .id(tk.getId())
                .username(tk.getUsername())
                .vaiTro(tk.getVaiTro().getValue())
                .isActive(tk.getIsActive())
                .hoTen(hoTen)
                .maSo(maSo)
                .email(email)
                .build());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String username) {
        log.info("Yêu cầu đặt lại mật khẩu cho username: {}", username);
        taiKhoanService.resetPassword(username);
        return ResponseEntity.ok("Mật khẩu mới đã được tạo và gửi đến email (nếu có)");
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        log.info("Đổi mật khẩu cho người dùng: {}", userDetails.getUsername());

        if (!passwordEncoder.matches(oldPassword, userDetails.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("❌ Mật khẩu cũ không đúng");
        }

        TaiKhoan updated = taiKhoanService.changePassword(userDetails.getUsername(), newPassword);

        String hoTen = null, maSo = null, email = null;
        if (updated.getSinhVien() != null) {
            hoTen = updated.getSinhVien().getHoTen();
            maSo = updated.getSinhVien().getMaSv();
            email = updated.getSinhVien().getEmail();
        } else if (updated.getGiangVien() != null) {
            hoTen = updated.getGiangVien().getHoTen();
            maSo = updated.getGiangVien().getMaGv();
            email = updated.getGiangVien().getEmail();
        }

        return ResponseEntity.ok(UserProfileDTO.builder()
                .id(updated.getId())
                .username(updated.getUsername())
                .vaiTro(updated.getVaiTro().getValue())
                .isActive(updated.getIsActive())
                .hoTen(hoTen)
                .maSo(maSo)
                .email(email)
                .build());
    }
}
