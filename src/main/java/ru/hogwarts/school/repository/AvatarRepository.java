package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Optional<Avatar> findByStudentId(Long studentId);

    List<Avatar> getPaginatedAvatar(int pageNumber, int pageSize);
}
