package com.example.backend.repo.user;

import com.example.backend.domain.user.CommentOnStudent;
import com.example.backend.repo.SoftDeleteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentOnStudentRepo extends SoftDeleteJpaRepository<CommentOnStudent> {

    List<CommentOnStudent> findAllByStudentId(Long studentId);
}
