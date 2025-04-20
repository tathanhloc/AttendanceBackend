package com.tathanhloc.faceattendance.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "diemdanh")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiemDanh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ngay_diem_danh")
    private LocalDate ngayDiemDanh;

    @Column(name = "trang_thai")
    private String trangThai;

    @Column(name = "thoi_gian_vao")
    private LocalTime thoiGianVao;

    @Column(name = "thoi_gian_ra")
    private LocalTime thoiGianRa;

    @ManyToOne
    @JoinColumn(name = "ma_lich")
    private LichHoc lichHoc;

    @ManyToOne
    @JoinColumn(name = "ma_sv")
    private SinhVien sinhVien;
}