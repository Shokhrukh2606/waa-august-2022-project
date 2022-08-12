package com.example.backend.service.job;

import com.example.backend.domain.job.JobAdvertisement;
import com.example.backend.domain.job.JobApplicant;
import com.example.backend.domain.user.Student;
import com.example.backend.dto.job.JobApplicantDto;
import com.example.backend.dto.messaging.Note;
import com.example.backend.dto.user.StudentDto;
import com.example.backend.exceptions.ErrorCode;
import com.example.backend.exceptions.LocalizedApplicationException;
import com.example.backend.mapper.JobAdvertisementMapper;
import com.example.backend.mapper.JobApplicantMapper;
import com.example.backend.mapper.StudentMapper;
import com.example.backend.repo.job.JobAdvertisementRepo;
import com.example.backend.repo.job.JobApplicantRepo;
import com.example.backend.repo.user.StudentRepo;
import com.example.backend.service.messaging.FirebaseMessagingService;
import com.example.backend.service.security.Security;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class JobApplicantService implements JobApplicants {

    private final JobApplicantRepo repo;
    private final JobAdvertisementRepo jobAdvertisementRepo;

    private final StudentRepo studentRepo;
    private final StudentMapper studentMapper;
    private final Security security;

    private final JobApplicantMapper mapper;
    private final JobAdvertisementMapper advertisementMapper;

    private final FirebaseMessagingService messagingService;

    @Override
    public List<StudentDto> getAllByJobAdvertisementId(Long adId) {
        var advertisement = jobAdvertisementRepo.findById(adId)
                .orElseThrow(() -> new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, "JobAdvertisements ID"));

        if (!advertisement.getCreator().getId().equals(security.getCurrentUser().getId())) {
            throw new LocalizedApplicationException(ErrorCode.FORBIDDEN, "This information can be reached only by creators of the Advertisement");
        }

        var applicants = repo.findAllByJobAdvertisementId(adId);

        return applicants.stream().map(JobApplicant::getStudent).map(studentMapper::toDtoWithCV).collect(Collectors.toList());
    }

    @Override
    public JobApplicantDto applyToJob(Long adId) {
        var advertisement = jobAdvertisementRepo.findById(adId)
                .orElseThrow(() -> new LocalizedApplicationException(ErrorCode.ENTITY_NOT_FOUND, "JobAdvertisements ID"));

        var student = studentRepo.findByEmail(security.getCurrentUser().getEmail()).orElseThrow(EntityNotFoundException::new);

        if (repo.findByJobAdvertisementIdAndStudentId(adId, student.getId()).isPresent()) {
            throw new EntityExistsException("You already have applied to this Job");
        }

        var result = repo.save(new JobApplicant(advertisement, student));

        sendNotification(advertisement, student);

        return mapper.toDtoForAdvertisement(result);
    }

    @SneakyThrows
    private void sendNotification(JobAdvertisement advertisement, Student student) {
        if (ObjectUtils.isEmpty(student.getFirebaseToken())) {
            log.warn("Student: {}, has no firebase token", student);
            return;
        }

        var data = new HashMap<String, String>();
        data.put("student", new ObjectMapper().writeValueAsString(studentMapper.toDto(student)));
        data.put("advertisement", new ObjectMapper().writeValueAsString(advertisementMapper.toDto(advertisement)));

        var note = Note.builder()
                .content("A new user has applied to your job advertisement")
                .data(data)
                .build();

        messagingService.sendNotifications(note, List.of(student.getFirebaseToken()));
    }
}
