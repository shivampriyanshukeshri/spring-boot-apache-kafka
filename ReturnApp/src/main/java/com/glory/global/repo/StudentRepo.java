package com.glory.global.repo;

import com.glory.global.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long>{
    Optional<Student> findByMobileNo(Long mobileNo);
}