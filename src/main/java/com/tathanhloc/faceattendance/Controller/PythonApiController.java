package com.tathanhloc.faceattendance.Controller;

import com.tathanhloc.faceattendance.DTO.SinhVienDTO;
import com.tathanhloc.faceattendance.Service.SinhVienService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/python")
@RequiredArgsConstructor
@Slf4j
public class PythonApiController {

    private final SinhVienService sinhVienService;

    @GetMapping("/embeddings")
    public ResponseEntity<List<Map<String, Object>>> getAllEmbeddings() {
        log.info("Getting all embeddings for Python API");
        return ResponseEntity.ok(sinhVienService.getAllEmbeddings());
    }

    @GetMapping("/students/{maSv}/embedding")
    public ResponseEntity<Map<String, Object>> getEmbeddingByMaSv(@PathVariable String maSv) {
        log.info("Getting embedding for student: {}", maSv);
        return ResponseEntity.ok(sinhVienService.getEmbeddingByMaSv(maSv));
    }

    @PostMapping("/students/{maSv}/embedding")
    public ResponseEntity<SinhVienDTO> saveEmbedding(
            @PathVariable String maSv,
            @RequestBody Map<String, Object> requestBody) {
        log.info("Saving embedding for student: {}", maSv);

        Object embeddingObj = requestBody.get("embedding");
        String embedding;

        if (embeddingObj instanceof List) {
            // Nếu embedding là một mảng, chuyển đổi thành chuỗi JSON
            embedding = embeddingObj.toString();
        } else {
            embedding = String.valueOf(embeddingObj);
        }

        if (embedding == null || embedding.isEmpty()) {
            throw new RuntimeException("Embedding không được để trống");
        }

        return ResponseEntity.ok(sinhVienService.saveEmbedding(maSv, embedding));
    }

    @PostMapping("/attendance")
    public ResponseEntity<?> recordAttendance(@RequestBody Map<String, Object> attendanceData) {
        log.info("Recording attendance: {}", attendanceData);
        // Implement attendance recording logic
        return ResponseEntity.ok(Map.of("success", true, "message", "Attendance recorded"));
    }
}
