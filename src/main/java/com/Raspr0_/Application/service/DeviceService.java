package com.Raspr0_.Application.service;

import com.Raspr0_.Application.dao.DeviceDao;
import com.Raspr0_.Application.dao.UserDao;
import com.Raspr0_.Application.dto.CreateDeviceDto;
import com.Raspr0_.Application.dto.PutDeviceDto;
import com.Raspr0_.Application.model.DeviceModel;
import com.Raspr0_.Application.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceDao deviceDao;
    private final UserDao userDao;

    @Autowired
    public DeviceService(DeviceDao deviceDao, UserDao userDao) {
        this.deviceDao = deviceDao;
        this.userDao = userDao;
    }

    public DeviceModel getDevice(final Long id) {
        return deviceDao.findById(id).orElse(null);
    }

    public List<DeviceModel> getDevices() {
        return deviceDao.findAll();
    }

    public void createDevice(CreateDeviceDto deviceDto) {
        UserModel user = userDao.findById(deviceDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User with id " + deviceDto.getUserId() + " not found."));

        DeviceModel device = new DeviceModel();
        device.setModel(deviceDto.getModel());
        device.setVersion(deviceDto.getVersion());
        device.setSerialNumber(deviceDto.getSerialNumber());
        device.setUser(user);

        deviceDao.save(device);
    }

    public void deleteDevice(Long id) {
        deviceDao.deleteById(id);
    }

    public void editDevice(PutDeviceDto deviceDto) {
        DeviceModel device = deviceDao.findById(deviceDto.getId())
                .orElseThrow(() -> new RuntimeException("Device with id " + deviceDto.getId() + " not found."));

        device.setModel(deviceDto.getModel());
        device.setVersion(deviceDto.getVersion());
        device.setSerialNumber(deviceDto.getSerialNumber());

        userDao.findById(deviceDto.getUserId()).ifPresent(device::setUser);

        deviceDao.save(device);
    }
}
