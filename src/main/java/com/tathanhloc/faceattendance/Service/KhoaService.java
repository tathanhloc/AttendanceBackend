package com.tathanhloc.faceattendance.Service;

import com.tathanhloc.faceattendance.DTO.KhoaDTO;
import com.tathanhloc.faceattendance.Model.Khoa;
import com.tathanhloc.faceattendance.Repository.KhoaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhoaService {

    private final KhoaRepository khoaRepository;

    public List<KhoaDTO> getAll() {
        return khoaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public KhoaDTO getById(String maKhoa) {
        Khoa khoa = khoaRepository.findById(maKhoa)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));
        return toDTO(khoa);
    }

    @Transactional
    public KhoaDTO create(KhoaDTO dto) {
        if (khoaRepository.existsById(dto.getMaKhoa())) {
            throw new RuntimeException("Mã khoa đã tồn tại");
        }
        Khoa khoa = toEntity(dto);
        return toDTO(khoaRepository.save(khoa));
    }

    @Transactional
    public KhoaDTO update(String maKhoa, KhoaDTO dto) {
        Khoa existing = khoaRepository.findById(maKhoa)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));

        existing.setTenKhoa(dto.getTenKhoa());
        existing.setIsActive(dto.getIsActive());

        return toDTO(khoaRepository.save(existing));
    }

    public void delete(String maKhoa) {
        if (!khoaRepository.existsById(maKhoa)) {
            throw new RuntimeException("Không tìm thấy khoa để xóa");
        }
        khoaRepository.deleteById(maKhoa);
    }

    // Mapper
    private KhoaDTO toDTO(Khoa khoa) {
        return KhoaDTO.builder()
                .maKhoa(khoa.getMaKhoa())
                .tenKhoa(khoa.getTenKhoa())
                .isActive(khoa.getIsActive())
                .build();
    }

    private Khoa toEntity(KhoaDTO dto) {
        return Khoa.builder()
                .maKhoa(dto.getMaKhoa())
                .tenKhoa(dto.getTenKhoa())
                .isActive(dto.getIsActive())
                .build();
    }

    public KhoaDTO getByMaKhoa(String maKhoa) {
        return toDTO(khoaRepository.findById(maKhoa)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa")));
    }

}
