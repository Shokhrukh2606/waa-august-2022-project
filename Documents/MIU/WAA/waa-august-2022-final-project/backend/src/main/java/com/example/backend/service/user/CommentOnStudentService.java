package com.example.backend.service.user;

import com.example.backend.domain.user.CommentOnStudent;
import com.example.backend.dto.user.CommentOnStudentCreateRequest;
import com.example.backend.dto.user.CommentOnStudentDto;
import com.example.backend.mapper.CommentOnStudentMapper;
import com.example.backend.repo.user.CommentOnStudentRepo;
import com.example.backend.repo.user.FacultyRepo;
import com.example.backend.repo.user.StudentRepo;
import com.example.backend.service.security.Security;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentOnStudentService implements CommentOnStudents {

    private final CommentOnStudentRepo repo;
    private final CommentOnStudentMapper mapper;
    private final StudentRepo studentRepo;
    private final Security security;
    private final FacultyRepo facultyRepo;

    @Transactional
    @Override
    public CommentOnStudentDto create(CommentOnStudentCreateRequest request) {

        CommentOnStudent comment = new CommentOnStudent();

        comment.setStudent(studentRepo.findById(request.getStudentId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("No student found with id: %s", request.getStudentId().toString()))));

        comment.setFacultyMember(facultyRepo.findByEmail(security.getCurrentUser().getEmail())
                .orElseThrow(() -> new AccessDeniedException("You have to be a faculty member in order to create a comment")));

        comment.setComment(request.getComment());

        return mapper.toDto(repo.save(comment));
    }

    @Override
    public List<CommentOnStudentDto> getAllByStudentId(Long id) {
        if (facultyRepo.findByEmail(security.getCurrentUser().getEmail()).isEmpty()) {
            throw new AccessDeniedException("You have to be a faculty member in order to get comments");
        }
        return repo.findAllByStudentId(id).stream().map(mapper::listByStudent).collect(Collectors.toList());
    }
}
