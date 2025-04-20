package com.tathanhloc.faceattendance.Service;


import com.tathanhloc.faceattendance.DTO.*;
import com.tathanhloc.faceattendance.Model.*;
import com.tathanhloc.faceattendance.Repository.*;
import com.tathanhloc.faceattendance.Util.AutoLogUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SinhVienService {

    private final SinhVienRepository sinhVienRepository;
    private final LopRepository lopRepository;

    public List<SinhVienDTO> getAll() {
        return sinhVienRepository.findAll().stream().map(this::toDTO).toList();
    }

    public SinhVienDTO getById(String id) {
        return toDTO(sinhVienRepository.findById(id).orElseThrow());
    }

    public SinhVienDTO create(SinhVienDTO dto) {
        SinhVien entity = toEntity(dto);
        return toDTO(sinhVienRepository.save(entity));
    }

    public SinhVienDTO update(String id, SinhVienDTO dto) {
        SinhVien sv = sinhVienRepository.findById(id).orElseThrow();
        sv.setHoTen(dto.getHoTen());
        sv.setGioiTinh(dto.getGioiTinh());
        sv.setNgaySinh(dto.getNgaySinh());
        sv.setEmail(dto.getEmail());
        sv.setHinhAnh(dto.getHinhAnh());
        sv.setEmbedding(dto.getEmbedding());
        sv.setIsActive(dto.getIsActive());
        sv.setLop(lopRepository.findById(dto.getMaLop()).orElseThrow());
        return toDTO(sinhVienRepository.save(sv));
    }

    public void delete(String id) {
        sinhVienRepository.deleteById(id);
    }

    private SinhVienDTO toDTO(SinhVien sv) {
        return SinhVienDTO.builder()
                .maSv(sv.getMaSv())
                .hoTen(sv.getHoTen())
                .gioiTinh(sv.getGioiTinh())
                .ngaySinh(sv.getNgaySinh())
                .email(sv.getEmail())
                .hinhAnh(sv.getHinhAnh())
                .embedding(sv.getEmbedding())
                .isActive(sv.getIsActive())
                .maLop(sv.getLop().getMaLop())
                .build();
    }

    private SinhVien toEntity(SinhVienDTO dto) {
        return SinhVien.builder()
                .maSv(dto.getMaSv())
                .hoTen(dto.getHoTen())
                .gioiTinh(dto.getGioiTinh())
                .ngaySinh(dto.getNgaySinh())
                .email(dto.getEmail())
                .hinhAnh(dto.getHinhAnh())
                .embedding(dto.getEmbedding())
                .isActive(dto.getIsActive())
                .lop(lopRepository.findById(dto.getMaLop()).orElseThrow())
                .build();
    }

    //getByMaSv
    public SinhVienDTO getByMaSv(String maSv) {
        SinhVien sinhVien = sinhVienRepository.findById(maSv).orElseThrow();
        return toDTO(sinhVien);
    }
}
