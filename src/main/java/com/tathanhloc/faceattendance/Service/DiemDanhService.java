package com.tathanhloc.faceattendance.Service;

import com.tathanhloc.faceattendance.DTO.DiemDanhDTO;
import com.tathanhloc.faceattendance.Model.DiemDanh;
import com.tathanhloc.faceattendance.Model.LichHoc;
import com.tathanhloc.faceattendance.Model.SinhVien;
import com.tathanhloc.faceattendance.Repository.DiemDanhRepository;
import com.tathanhloc.faceattendance.Repository.LichHocRepository;
import com.tathanhloc.faceattendance.Repository.SinhVienRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiemDanhService {

    private final DiemDanhRepository diemDanhRepository;
    private final LichHocRepository lichHocRepository;
    private final SinhVienRepository sinhVienRepository;

    public List<DiemDanhDTO> getAll() {
        return diemDanhRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public DiemDanhDTO getById(Long id) {
        DiemDanh entity = diemDanhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi điểm danh"));
        return toDTO(entity);
    }

    @Transactional
    public DiemDanhDTO create(DiemDanhDTO dto) {
        DiemDanh entity = toEntity(dto);
        entity.setId(null); // auto-generated
        return toDTO(diemDanhRepository.save(entity));
    }

    @Transactional
    public DiemDanhDTO update(Long id, DiemDanhDTO dto) {
        DiemDanh existing = diemDanhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi điểm danh"));

        existing.setNgayDiemDanh(dto.getNgayDiemDanh());
        existing.setTrangThai(dto.getTrangThai());
        existing.setThoiGianVao(dto.getThoiGianVao());
        existing.setThoiGianRa(dto.getThoiGianRa());

        if (!existing.getLichHoc().getMaLich().equals(dto.getMaLich())) {
            existing.setLichHoc(lichHocRepository.findById(dto.getMaLich())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch học")));
        }

        if (!existing.getSinhVien().getMaSv().equals(dto.getMaSv())) {
            existing.setSinhVien(sinhVienRepository.findById(dto.getMaSv())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên")));
        }

        return toDTO(diemDanhRepository.save(existing));
    }

    public void delete(Long id) {
        if (!diemDanhRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy bản ghi để xóa");
        }
        diemDanhRepository.deleteById(id);
    }

    // Mapping
    private DiemDanhDTO toDTO(DiemDanh d) {
        return DiemDanhDTO.builder()
                .id(d.getId())
                .ngayDiemDanh(d.getNgayDiemDanh())
                .trangThai(d.getTrangThai())
                .thoiGianVao(d.getThoiGianVao())
                .thoiGianRa(d.getThoiGianRa())
                .maLich(d.getLichHoc().getMaLich())
                .maSv(d.getSinhVien().getMaSv())
                .build();
    }

    private DiemDanh toEntity(DiemDanhDTO dto) {
        LichHoc lichHoc = lichHocRepository.findById(dto.getMaLich())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch học"));

        SinhVien sinhVien = sinhVienRepository.findById(dto.getMaSv())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

        return DiemDanh.builder()
                .id(dto.getId())
                .ngayDiemDanh(dto.getNgayDiemDanh() != null ? dto.getNgayDiemDanh() : LocalDate.now())
                .trangThai(dto.getTrangThai())
                .thoiGianVao(dto.getThoiGianVao())
                .thoiGianRa(dto.getThoiGianRa())
                .lichHoc(lichHoc)
                .sinhVien(sinhVien)
                .build();
    }

    public List<DiemDanhDTO> getByMaSv(String maSv) {
        return diemDanhRepository.findBySinhVienMaSv(maSv).stream()
                .map(this::toDTO).toList();
    }

    public List<DiemDanhDTO> getByMaLich(String maLich) {
        return diemDanhRepository.findByLichHocMaLich(maLich).stream()
                .map(this::toDTO).toList();
    }
}
