package com.notes.services.note;

import com.notes.core.BaseCrudService;
import com.notes.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService extends BaseCrudService<NoteModel, NoteEntity, Long> {

    @Autowired
    private AccountService accountService;

    @Value("${spring.assets.static.path}")
    private String uploadPath;

    public NoteService() {
        super(NoteModel.class, NoteEntity.class);
    }

    NoteModel createForCurrentUser(NoteModel newNote) {

        newNote.setAccountId(accountService.getCurrentUserAccountId());
        newNote.setNoteCreatedTime(LocalDateTime.now());

        return create(newNote);
    }

    Iterable<NoteModel> findAllForCurrentUser(int page, int pageSize) {
        NoteModel search = new NoteModel();
        search.setAccountId(accountService.getCurrentUserAccountId());

        return this.findall(search, page, pageSize);
    }

    boolean isImage(MultipartFile file) {
        try (InputStream input = file.getInputStream()) {
            try {
                ImageIO.read(input).toString();
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (IOException ioe) {
            return false;
        }
    }

    void saveUploadedFiles(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            saveUploadedFile(file);
        }
    }

    void saveUploadedFile(MultipartFile file) throws IOException {

        if (file.isEmpty() || !isImage(file)) {
            return;
        }

        byte[] bytes = file.getBytes();

        createDirectory(uploadPath);
        Path path = Paths.get(String.format("%s/%s", uploadPath, file.getOriginalFilename()));
        Files.write(path, bytes);
    }

    Resource loadFileAsResource(String fileName) {
        try {

            Path path = Paths.get(String.format("%s/%s", uploadPath, fileName));
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
        return null;
    }


    private static void createDirectory(String target) {
        final Path path = Paths.get(target);

        if (Files.exists(path)) {
            return;
        }

        try {
            Files.createDirectories(path);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
