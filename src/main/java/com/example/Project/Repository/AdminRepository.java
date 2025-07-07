package com.example.Project.Repository;

import com.example.Project.domain.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer> {

    Admin findByUsername(String username);
}
