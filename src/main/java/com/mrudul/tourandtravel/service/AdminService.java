package com.mrudul.tourandtravel.service;


import com.mrudul.tourandtravel.dto.request.LoginRequest;
import com.mrudul.tourandtravel.dto.response.LoginResponse;
import com.mrudul.tourandtravel.entity.Admin;
import com.mrudul.tourandtravel.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    public LoginResponse login(LoginRequest request) {

        Admin admin = adminRepository
                .findByUsername(request.getUsername())
                .orElse(null);

        if (admin == null) {
            return new LoginResponse(
                    false,
                    "Invalid username or password"
            );
        }

        if (!admin.getPassword().equals(request.getPassword())) {
            return new LoginResponse(
                    false,
                    "Invalid username or password"
            );
        }

        return new LoginResponse(
                true,
                "Login successful"
        );
    }
}
