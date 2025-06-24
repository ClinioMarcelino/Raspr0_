package com.Raspr0_.Application.api;

import com.Raspr0_.Application.dto.CreateUserDto;
import com.Raspr0_.Application.dto.PutUserDto;
import com.Raspr0_.Application.model.UserModel;
import com.Raspr0_.Application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Users")
public class UserApi {
    private final UserService UserService;

    @Autowired
    public UserApi(final UserService UserService) {
        this.UserService = UserService;
    }

    @Operation(summary = "Obter Usuário por ID", description = "Recuperar as informações de um usuário específico")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserModel User = UserService.getUser(id);
        if (User == null) {
            // Resposta mais detalhada em caso de erro, indicando que o ID não foi encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário com ID " + id + " não encontrado.");
        }
        return ResponseEntity.ok(User);
    }


    @Operation(summary = "Obter Todos os Usuário", description = "Recuperar uma lista de todos os usuário registrados.")
    @GetMapping(produces = {"application/json", "application/xml"})
    public List<UserModel> getUsers() {
        return UserService.getAllUsers();
    }

    @Operation(summary = "Criar um novo Usuário", description = "Adicionar um novo usuário ao sistema.")
    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createUser(@RequestBody @Validated CreateUserDto User) {
        try {
            // Verifica se já existe um usuário com o mesmo ID
            if (UserService.getUser(User.getId()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Já existe um usuário com o ID " + User.getId());
            }

            UserService.createUser(User);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário com ID " + User.getId() + " criado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao criar o usuário com ID " + User.getId() + ": " + e.getMessage());
        }
    }

    @Operation(summary = "Deletar um Usuário", description = "Remover um usuário do sistema utilizando o ID fornecido.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            UserModel User = UserService.getUser(id);
            if (User == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário com ID " + id + " não encontrado para exclusão.");
            }

            UserService.deleteUser(id);
            return ResponseEntity.ok("Usuário com ID " + id + " deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar excluir o usuário com ID " + id + ": " + e.getMessage());
        }
    }

    @Operation(summary = "Atualizar um Usuário", description = "Modificar as informações de um usuário existente utilizando o ID fornecido.")
    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> editUser(@RequestBody PutUserDto User) {
        try {
            if (UserService.getUser(User.getId()) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário com ID " + User.getId() + " não encontrado para atualização.");
            }

            UserService.editUser(User);
            return ResponseEntity.ok("Usuário com ID " + User.getId() + " atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o usuário com ID " + User.getId() + ": " + e.getMessage());
        }
    }
}
