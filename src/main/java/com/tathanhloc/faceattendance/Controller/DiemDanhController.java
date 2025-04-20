package com.tathanhloc.faceattendance.Controller;

import com.tathanhloc.faceattendance.DTO.*;
import com.tathanhloc.faceattendance.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diemdanh")
@RequiredArgsConstructor
public class DiemDanhController {

    private final DiemDanhService diemDanhService;

    @GetMapping
    public List<DiemDanhDTO> getAll() {
        return diemDanhService.getAll();
    }

    @GetMapping("/{id}")
    public DiemDanhDTO getById(@PathVariable Long id) {
        return diemDanhService.getById(id);
    }

    @PostMapping
    public DiemDanhDTO create(@RequestBody DiemDanhDTO dto) {
        return diemDanhService.create(dto);
    }

    @PutMapping("/{id}")
    public DiemDanhDTO update(@PathVariable Long id, @RequestBody DiemDanhDTO dto) {
        return diemDanhService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        diemDanhService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-masv/{maSv}")
    public ResponseEntity<List<DiemDanhDTO>> getByMaSv(@PathVariable String maSv) {
        return ResponseEntity.ok(diemDanhService.getByMaSv(maSv));
    }

    @GetMapping("/by-malich/{maLich}")
    public ResponseEntity<List<DiemDanhDTO>> getByMaLich(@PathVariable String maLich) {
        return ResponseEntity.ok(diemDanhService.getByMaLich(maLich));
    }

}
