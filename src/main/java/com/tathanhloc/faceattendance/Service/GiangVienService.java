package com.tathanhloc.faceattendance.Service;

import com.tathanhloc.faceattendance.DTO.GiangVienDTO;
import com.tathanhloc.faceattendance.Model.GiangVien;
import com.tathanhloc.faceattendance.Model.Khoa;
import com.tathanhloc.faceattendance.Repository.GiangVienRepository;
import com.tathanhloc.faceattendance.Repository.KhoaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiangVienService {

    private final GiangVienRepository giangVienRepository;
    private final KhoaRepository khoaRepository;

    public List<GiangVienDTO> getAll() {
        return giangVienRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public GiangVienDTO getById(String maGv) {
        GiangVien gv = giangVienRepository.findById(maGv)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên"));
        return toDTO(gv);
    }

    @Transactional
    public GiangVienDTO create(GiangVienDTO dto) {
        if (giangVienRepository.existsById(dto.getMaGv())) {
            throw new RuntimeException("Mã giảng viên đã tồn tại");
        }
        GiangVien gv = toEntity(dto);
        return toDTO(giangVienRepository.save(gv));
    }

    @Transactional
    public GiangVienDTO update(String maGv, GiangVienDTO dto) {
        GiangVien existing = giangVienRepository.findById(maGv)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên"));

        existing.setHoTen(dto.getHoTen());
        existing.setEmail(dto.getEmail());
        existing.setIsActive(dto.getIsActive());

        if (!existing.getKhoa().getMaKhoa().equals(dto.getMaKhoa())) {
            Khoa khoa = khoaRepository.findById(dto.getMaKhoa())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));
            existing.setKhoa(khoa);
        }

        return toDTO(giangVienRepository.save(existing));
    }

    public void delete(String maGv) {
        if (!giangVienRepository.existsById(maGv)) {
            throw new RuntimeException("Không tìm thấy để xóa");
        }
        giangVienRepository.deleteById(maGv);
    }

    // Mapping
    private GiangVienDTO toDTO(GiangVien gv) {
        return GiangVienDTO.builder()
                .maGv(gv.getMaGv())
                .hoTen(gv.getHoTen())
                .email(gv.getEmail())
                .isActive(gv.getIsActive())
                .maKhoa(gv.getKhoa().getMaKhoa())
                .embedding(gv.getEmbedding())
                .build();
    }

    private GiangVien toEntity(GiangVienDTO dto) {
        Khoa khoa = khoaRepository.findById(dto.getMaKhoa())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));

        return GiangVien.builder()
                .maGv(dto.getMaGv())
                .hoTen(dto.getHoTen())
                .email(dto.getEmail())
                .isActive(dto.getIsActive())
                .khoa(khoa)
                .embedding(dto.getEmbedding())
                .build();
    }

    public GiangVienDTO getByMaGv(String maGv) {
        GiangVien gv = giangVienRepository.findById(maGv)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên"));
        return toDTO(gv);
    }

    @Transactional
    public GiangVienDTO saveEmbedding(String maGv, String embedding) {
        GiangVien giangVien = giangVienRepository.findById(maGv)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên với mã: " + maGv));

        giangVien.setEmbedding(embedding);
        giangVienRepository.save(giangVien);

        return toDTO(giangVien);
    }

    // Add a method to get embedding for a teacher
    public Map<String, Object> getEmbeddingByMaGv(String maGv) {
        GiangVien giangVien = giangVienRepository.findById(maGv)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên với mã: " + maGv));

        if (giangVien.getEmbedding() == null || giangVien.getEmbedding().isEmpty()) {
            throw new RuntimeException("Giảng viên chưa có dữ liệu embedding");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("teacherId", giangVien.getMaGv());
        result.put("name", giangVien.getHoTen());
        result.put("embedding", giangVien.getEmbedding());
        return result;
    }

    // Add a method to get all teacher embeddings
    public List<Map<String, Object>> getAllEmbeddings() {
        return giangVienRepository.findAll().stream()
                .filter(gv -> gv.getIsActive() != null && gv.getIsActive())
                .filter(gv -> gv.getEmbedding() != null && !gv.getEmbedding().isEmpty())
                .map(gv -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("teacherId", gv.getMaGv());
                    result.put("name", gv.getHoTen());
                    result.put("embedding", gv.getEmbedding());
                    return result;
                })
                .collect(Collectors.toList());
    }
}
