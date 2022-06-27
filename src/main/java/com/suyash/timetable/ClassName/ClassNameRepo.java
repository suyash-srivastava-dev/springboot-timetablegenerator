package com.suyash.timetable.ClassName;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassNameRepo extends JpaRepository<ClassName,String>  {
    boolean findByClassValue(String classValue);
    List<ClassName> findAllByClassValue(String classValue);

    boolean existsByClassValue(String classValue);
}
