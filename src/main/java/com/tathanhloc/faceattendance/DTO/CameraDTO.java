package com.tathanhloc.faceattendance.DTO;


import lombok.Data;

import java.io.Serializable;

@Data
@io.swagger.annotations.ApiModel("")
public class CameraDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private String tenCamera;

    private String ipAddress;

    private String maPhong;

    private String vungIn;

    private String vungOut;

    private Boolean active;

}
