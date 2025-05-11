package com.tathanhloc.faceattendance.Controller;

import com.tathanhloc.faceattendance.DTO.GiangVienDTO;
import com.tathanhloc.faceattendance.DTO.SinhVienDTO;
import com.tathanhloc.faceattendance.Service.GiangVienService;
import com.tathanhloc.faceattendance.Service.SinhVienService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/python")
@RequiredArgsConstructor
@Slf4j
public class PythonApiController {

    private final SinhVienService sinhVienService;
    private final GiangVienService giangVienService;

    @GetMapping("/embeddings")
    public ResponseEntity<List<Map<String, Object>>> getAllEmbeddings() {
        log.info("Getting all embeddings for Python API");
        List<Map<String, Object>> allEmbeddings = new ArrayList<>();

        // Get student embeddings
        List<Map<String, Object>> studentEmbeddings = sinhVienService.getAllEmbeddings();
        allEmbeddings.addAll(studentEmbeddings);

        // Get teacher embeddings
        List<Map<String, Object>> teacherEmbeddings = giangVienService.getAllEmbeddings();
        allEmbeddings.addAll(teacherEmbeddings);

        return ResponseEntity.ok(allEmbeddings);
    }

    @GetMapping("/students/{maSv}/embedding")
    public ResponseEntity<Map<String, Object>> getStudentEmbeddingByMaSv(@PathVariable String maSv) {
        log.info("Getting embedding for student: {}", maSv);
        return ResponseEntity.ok(sinhVienService.getEmbeddingByMaSv(maSv));
    }

    @GetMapping("/teachers/{maGv}/embedding")
    public ResponseEntity<Map<String, Object>> getTeacherEmbeddingByMaGv(@PathVariable String maGv) {
        log.info("Getting embedding for teacher: {}", maGv);
        return ResponseEntity.ok(giangVienService.getEmbeddingByMaGv(maGv));
    }

    @PostMapping("/students/{maSv}/embedding")
    public ResponseEntity<SinhVienDTO> saveStudentEmbedding(
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

    @PostMapping("/teachers/{maGv}/embedding")
    public ResponseEntity<GiangVienDTO> saveTeacherEmbedding(
            @PathVariable String maGv,
            @RequestBody Map<String, Object> requestBody) {
        log.info("Saving embedding for teacher: {}", maGv);

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

        return ResponseEntity.ok(giangVienService.saveEmbedding(maGv, embedding));
    }

    @PostMapping("/attendance")
    public ResponseEntity<?> recordAttendance(@RequestBody Map<String, Object> attendanceData) {
        log.info("Recording attendance: {}", attendanceData);
        // Implement attendance recording logic
        return ResponseEntity.ok(Map.of("success", true, "message", "Attendance recorded"));
    }
}
