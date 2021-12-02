package com.mouri.file.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mouri.file.upload.entity.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Integer> {

}
