package com.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.dto.CredentialDto;
import com.model.dto.TokenSetDto;
import com.model.table.User;
import com.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/credentials")
public class CredentialController {
    @Autowired
    private CredentialService credService;
    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        return ResponseEntity.ok().body(credService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(credService.findById(id));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialDto cred) {
        return ResponseEntity.ok().body(credService.login(cred));
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenSetDto oldToken) throws JsonProcessingException {
        return ResponseEntity.ok().body(credService.refresh(oldToken));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody User User) {
        return ResponseEntity.ok().body(credService.update(id, User));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        credService.delete(id);
        return ResponseEntity.ok().body("Deleted successfully...!");
    }
}

