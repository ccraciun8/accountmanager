package com.assignment.accountmgr;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.classes.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}