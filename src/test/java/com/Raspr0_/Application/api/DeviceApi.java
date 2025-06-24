package com.Raspr0_.Application.api;

import com.Raspr0_.Application.dto.CreateDeviceDto;
import com.Raspr0_.Application.dto.PutDeviceDto;
import com.Raspr0_.Application.model.DeviceModel;
import com.Raspr0_.Application.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceApi {
    private final DeviceService DeviceService;

    @Autowired
    public DeviceApi(final DeviceService DeviceService) {
        this.DeviceService = DeviceService;
    }

    @Operation(summary = "Search a Device for it's ID", description = "Return Device details with the available ID.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getDevice(@PathVariable Integer id) {
        DeviceModel Device = DeviceService.getDevice(id);
        if (Device == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device ID: " + id + " does not exists.");
        }
        return ResponseEntity.ok(Device);
    }

    @Operation(summary = "Listar todos os devices", description = "Recuperar uma lista de todos os devices registrados no sistema.")
    @GetMapping(produces = {"application/json", "application/xml"})
    public List<DeviceModel> getDevices() {
        return DeviceService.getDevices();
    }

    @Operation(summary = "Criar um novo Device", description = "Adicionar um novo Device ao sistema com os dados fornecidos.")
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createDevice(@RequestBody CreateDeviceDto Device) {
        DeviceService.createDevice(Device);
        return ResponseEntity.status(HttpStatus.CREATED).body("Device criado com sucesso.");
    }

    @Operation(summary = "Deletar um Device", description = "Remover o Device com o ID fornecido do sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable Integer id) {
        // Verificar se o Device com o ID fornecido existe
        DeviceModel Device = DeviceService.getDevice(id);
        if (Device == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: O Device com ID " + id + " não existe.");
        }

        // Se o Device existir, procede com a exclusão
        try {
            DeviceService.deleteDevice(id);
            return ResponseEntity.ok("Device com ID " + id + " deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar deletar o Device com ID " + id + ": " + e.getMessage());
        }
    }

    @Operation(summary = "Editar um Device", description = "Atualizar os dados de um Device com base nas informações fornecidas utilizando o ID.")
    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> editDevice(@RequestBody PutDeviceDto Device) {
        // Verificar se o Device com o ID fornecido existe
        DeviceModel existingDevice = DeviceService.getDevice(Device.getId());
        if (existingDevice == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: O Device com ID " + Device.getId() + " não existe.");
        }

        // Se o Device existir, procede com a atualização
        try {
            // Atualizar o Device no serviço
            DeviceService.editDevice(Device);
            return ResponseEntity.ok("Device com ID " + Device.getId() + " atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar atualizar o Device com ID " + Device.getId() + ": " + e.getMessage());
        }
    }
}