package com.tathanhloc.faceattendance.Controller;

import com.tathanhloc.faceattendance.DTO.TaiKhoanDTO;
import com.tathanhloc.faceattendance.DTO.UserProfileDTO;
import com.tathanhloc.faceattendance.Model.LoginRequest;
import com.tathanhloc.faceattendance.Model.TaiKhoan;
import com.tathanhloc.faceattendance.Security.CustomUserDetails;
import com.tathanhloc.faceattendance.Service.TaiKhoanService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TaiKhoanService taiKhoanService;
    private final PasswordEncoder passwordEncoder;

    // ✅ Đăng nhập (xử lý login bằng API - AJAX)
    @PostMapping("/login")
    public ResponseEntity<TaiKhoanDTO> login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        TaiKhoan user = ((CustomUserDetails) auth.getPrincipal()).getTaiKhoan();

        return ResponseEntity.ok(toDTO(user));
    }


    // ✅ Thông tin tài khoản hiện tại
    @GetMapping("/me")
    public UserProfileDTO me(@AuthenticationPrincipal CustomUserDetails userDetails) {
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

        return UserProfileDTO.builder()
                .id(tk.getId())
                .username(tk.getUsername())
                .vaiTro(tk.getVaiTro())
                .isActive(tk.getIsActive())
                .hoTen(hoTen)
                .maSo(maSo)
                .email(email)
                .build();
    }


    // 🔁 Quên mật khẩu
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String username) {
        taiKhoanService.resetPassword(username); // gửi mail/tạo mật khẩu mới
        return ResponseEntity.ok("Mật khẩu mới đã được tạo và gửi đến email (nếu có)");
    }

    // 🔒 Đổi mật khẩu
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        // So sánh mật khẩu cũ với hiện tại
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
                .vaiTro(updated.getVaiTro())
                .isActive(updated.getIsActive())
                .hoTen(hoTen)
                .maSo(maSo)
                .email(email)
                .build());
    }


    // 🚪 Đăng xuất (nếu xài session)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Đăng xuất thành công");
    }

    // ✅ Helper
    private TaiKhoanDTO toDTO(TaiKhoan tk) {
        return TaiKhoanDTO.builder()
                .id(tk.getId())
                .username(tk.getUsername())
                .vaiTro(tk.getVaiTro())
                .isActive(tk.getIsActive())
                .createdAt(tk.getCreatedAt())
                .maSv(tk.getSinhVien() != null ? tk.getSinhVien().getMaSv() : null)
                .maGv(tk.getGiangVien() != null ? tk.getGiangVien().getMaGv() : null)
                .build();
    }
}
