package com.controller;


import com.model.table.User;
import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }
    @GetMapping("/username/{userName}")
    public ResponseEntity<?> findByUsername(@PathVariable String userName, @RequestParam(name = "limit", required = false) Integer limit) {
        return ResponseEntity.ok().body(userService.findByUsername(userName,limit!=null?limit:0));
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody User User) {
        return ResponseEntity.ok().body(userService.save(User));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody User User) {
        return ResponseEntity.ok().body(userService.update(id, User));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok().body("Deleted successfully...!");
    }
}

