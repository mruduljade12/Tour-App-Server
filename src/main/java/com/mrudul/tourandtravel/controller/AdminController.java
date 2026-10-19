package com.mrudul.tourandtravel.controller;


import com.mrudul.tourandtravel.dto.request.LoginRequest;
import com.mrudul.tourandtravel.dto.response.LoginResponse;
import com.mrudul.tourandtravel.service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return adminService.login(request);
    }
}

