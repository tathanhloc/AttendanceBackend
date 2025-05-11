package com.tathanhloc.faceattendance.Repository;

import com.tathanhloc.faceattendance.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CameraRepository extends JpaRepository<Camera, Long>, JpaSpecificationExecutor<Camera> {

}