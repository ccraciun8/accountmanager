package com.assignment.accountmgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.accountmgr.classes.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}