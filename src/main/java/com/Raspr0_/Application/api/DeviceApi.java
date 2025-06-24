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
    private final DeviceService deviceService;

    @Autowired
    public DeviceApi(final DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Operation(summary = "Buscar um aparelho por ID", description = "Retorna os detalhes do aparelho com base no ID fornecido.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getDevice(@PathVariable Long id) {
        DeviceModel device = deviceService.getDevice(id);
        if (device == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O aparelho com ID " + id + " não existe.");
        }
        return ResponseEntity.ok(device);
    }

    @Operation(summary = "Listar todos os aparelhos", description = "Recuperar uma lista de todos os aparelhos registrados no sistema.")
    @GetMapping(produces = {"application/json", "application/xml"})
    public List<DeviceModel> getDevices() {
        return deviceService.getDevices();
    }

    @Operation(summary = "Criar um novo aparelho", description = "Adicionar um novo aparelho ao sistema com os dados fornecidos.")
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createDevice(@RequestBody CreateDeviceDto device) {
        deviceService.createDevice(device);
        return ResponseEntity.status(HttpStatus.CREATED).body("Device criado com sucesso.");
    }

    @Operation(summary = "Deletar um aparelho", description = "Remover o aparelho com o ID fornecido do sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable Long id) {
        // Verificar se o aparelho com o ID fornecido existe
        DeviceModel device = deviceService.getDevice(id);
        if (device == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: O aparelho com ID " + id + " não existe.");
        }

        // Se o aparelho existir, procede com a exclusão
        try {
            deviceService.deleteDevice(id);
            return ResponseEntity.ok("Device com ID " + id + " deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar deletar o aparelho com ID " + id + ": " + e.getMessage());
        }
    }

    @Operation(summary = "Editar um aparelho", description = "Atualizar os dados de um aparelho com base nas informações fornecidas utilizando o ID.")
    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> editDevice(@RequestBody PutDeviceDto device) {
        // Verificar se o aparelho com o ID fornecido existe
        DeviceModel existingDevice = deviceService.getDevice(device.getId());
        if (existingDevice == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: O aparelho com ID " + device.getId() + " não existe.");
        }

        // Se o aparelho existir, procede com a atualização
        try {
            // Atualizar o aparelho no serviço
            deviceService.editDevice(device);
            return ResponseEntity.ok("Device com ID " + device.getId() + " atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar atualizar o aparelho com ID " + device.getId() + ": " + e.getMessage());
        }
    }
}