package com.Raspr0_.Application.service;


import com.Raspr0_.Application.dao.UserDao;
import com.Raspr0_.Application.dto.CreateUserDto;
import com.Raspr0_.Application.dto.PutUserDto;
import com.Raspr0_.Application.model.DeviceModel;
import com.Raspr0_.Application.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao UserDao;

    @Autowired
    public UserService(UserDao UserDao) {
        this.UserDao = UserDao;
    }

    public void createUser(CreateUserDto UserDto) {
        // Converte o DTO em um UserModel e salva
        UserModel User = new UserModel();
        User.setName(UserDto.getName());
        User.setCpf(UserDto.getCpf());
        User.setEmail(UserDto.getEmail());
        User.setPhone(UserDto.getPhone());
        UserDao.save(User);
    }

    public List<UserModel> getAllUsers() {
        // Retorna todos os usuários
        return UserDao.findAll();
    }

    public UserModel getUser(Long id) {
        // Recupera um usuário pelo ID
        return UserDao.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        // Remove um usuário do sistema
        if (UserDao.existsById(id)) {
            UserDao.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public void editUser(PutUserDto UserDto) {
        // Atualiza as informações de um usuário existente
        Optional<UserModel> optionalUser = UserDao.findById(UserDto.getId());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado para atualização");
        }
        UserModel User = optionalUser.get();
        User.setName(UserDto.getName());
        User.setCpf(UserDto.getCpf());
        User.setEmail(UserDto.getEmail());
        User.setPhone(UserDto.getPhone());
        UserDao.save(User);
    }

    public void addDeviceToUser(Long UserId, DeviceModel Device) {
        // Adiciona um Device a um usuário

        UserModel User = getUser(UserId);
        if (User == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        User.getDevices().add(Device);
        Device.setUser(User);
        UserDao.save(User);
    }

    public void removeDeviceFromUser(Long UserId, DeviceModel Device) {
        // Remove um Device do usuário

        UserModel User = getUser(UserId);
        if (User == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        User.getDevices().remove(Device);
        Device.setUser(null);
        UserDao.save(User);
    }

    public boolean isDeviceAlreadyAdopted(int DeviceId) {
        // Verifica se um Device já foi adotado
        return UserDao.findAll().stream()
                .anyMatch(User -> User.getDevices().stream()
                        .anyMatch(Device -> Device.getId() == DeviceId));
    }
}