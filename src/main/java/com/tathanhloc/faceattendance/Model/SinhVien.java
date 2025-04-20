package com.tathanhloc.faceattendance.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "sinhvien")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SinhVien {
    @Id
    @Column(name = "ma_sv")
    private String maSv;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "gioi_tinh")
    private String gioiTinh;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "email")
    private String email;

    @Column(name = "hinh_anh")
    private String hinhAnh;

    @Column(name = "embedding")
    private String embedding;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "ma_lop")
    private Lop lop;
}