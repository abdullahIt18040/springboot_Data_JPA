package com.sdlcpro.sdlc_pro_spring_data_jpa_app.dao;

import com.sdlcpro.sdlc_pro_spring_data_jpa_app.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<Student,Integer> {

}
