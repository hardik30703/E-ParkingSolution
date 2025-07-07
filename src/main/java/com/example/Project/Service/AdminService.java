package com.example.Project.Service;

import com.example.Project.Repository.AdminRepository;
import com.example.Project.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
