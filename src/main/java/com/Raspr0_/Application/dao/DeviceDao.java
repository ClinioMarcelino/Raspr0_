package com.Raspr0_.Application.dao;

import com.Raspr0_.Application.model.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceDao extends JpaRepository<DeviceModel, Long> {
}
