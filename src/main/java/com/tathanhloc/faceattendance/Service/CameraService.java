package com.tathanhloc.faceattendance.Service;

import com.tathanhloc.faceattendance.Camera;
import com.tathanhloc.faceattendance.DTO.CameraDTO;
import com.tathanhloc.faceattendance.Repository.CameraRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CameraService {

    @Autowired
    private CameraRepository cameraRepository;

    public Long save(CameraVO vO) {
        Camera bean = new Camera();
        BeanUtils.copyProperties(vO, bean);
        bean = cameraRepository.save(bean);
        return bean.getId();
    }

    public void delete(Long id) {
        cameraRepository.deleteById(id);
    }

    public void update(Long id, CameraUpdateVO vO) {
        Camera bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        cameraRepository.save(bean);
    }

    public CameraDTO getById(Long id) {
        Camera original = requireOne(id);
        return toDTO(original);
    }

    public Page<CameraDTO> query(CameraQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private CameraDTO toDTO(Camera original) {
        CameraDTO bean = new CameraDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Camera requireOne(Long id) {
        return cameraRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
