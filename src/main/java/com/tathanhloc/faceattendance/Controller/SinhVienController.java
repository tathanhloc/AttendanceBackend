package com.tathanhloc.faceattendance.Controller;

import com.tathanhloc.faceattendance.DTO.SinhVienDTO;
import com.tathanhloc.faceattendance.Service.SinhVienService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sinhvien")
@RequiredArgsConstructor
public class SinhVienController {

    private final SinhVienService sinhVienService;

    @GetMapping
    public List<SinhVienDTO> getAll() {
        return sinhVienService.getAll();
    }

    @GetMapping("/{id}")
    public SinhVienDTO getById(@PathVariable String id) {
        return sinhVienService.getById(id);
    }

    @PostMapping
    public SinhVienDTO create(@RequestBody SinhVienDTO dto) {
        return sinhVienService.create(dto);
    }

    @PutMapping("/{id}")
    public SinhVienDTO update(@PathVariable String id, @RequestBody SinhVienDTO dto) {
        return sinhVienService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        sinhVienService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-masv/{maSv}")
    public ResponseEntity<SinhVienDTO> getByMaSv(@PathVariable String maSv) {
        return ResponseEntity.ok(sinhVienService.getByMaSv(maSv));
    }
}

