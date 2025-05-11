package com.tathanhloc.faceattendance.Controller;

import com.tathanhloc.faceattendance.DTO.CameraDTO;
import com.tathanhloc.faceattendance.Service.CameraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.persistence.NotNull;
import jakarta.persistence.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "")
@Validated
@RestController
@RequestMapping("/camera")
public class CameraController {

    @Autowired
    private CameraService cameraService;

    @PostMapping
    @ApiOperation("Save ")
    public String save(@Valid @RequestBody CameraVO vO) {
        return cameraService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete(@Valid @NotNull @PathVariable("id") Long id) {
        cameraService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update(@Valid @NotNull @PathVariable("id") Long id,
                       @Valid @RequestBody CameraUpdateVO vO) {
        cameraService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public CameraDTO getById(@Valid @NotNull @PathVariable("id") Long id) {
        return cameraService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<CameraDTO> query(@Valid CameraQueryVO vO) {
        return cameraService.query(vO);
    }
}
