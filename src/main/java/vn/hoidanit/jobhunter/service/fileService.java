package vn.hoidanit.jobhunter.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class fileService {
    @Value("${hoidanit.upload-file.base-uri}")
    private String baseURI;

    public void createDirectory(String folder) throws Exception {
        // create directory
        URI uri = new URI(folder);
        Path path = Paths.get(uri);
        File tmpDir = new File(path.toString());
        if (!tmpDir.exists()) {
            try {
                Files.createDirectories(tmpDir.toPath());
                System.out.println(">>> Directory created: " + path);
            } catch (IOException e) {
                throw new Exception(">>> Directory creation failed: " + path);
            }
        } else {
            System.out.println(">>> Directory existed: " + path);
        }
    }

    public String store(MultipartFile file, String folder) throws Exception, IOException {
        // store file
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        URI uri = new URI(baseURI + folder + "/" + fileName);
        Path path = Paths.get(uri);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(">>> File stored: " + path);
        }
       return fileName;
    }

    public long getFileLength(String folder, String fileName) throws Exception {
        // get file length
        URI uri = new URI(baseURI + folder + "/" + fileName);
        Path path = Paths.get(uri);
        File file = new File(path.toString());
        if (!file.exists() || file.isDirectory()) {
            return 0;
        }
        return file.length();
    }

    public InputStreamResource getResource(String folder, String fileName) throws Exception {
        // get file resource
        URI uri = new URI(baseURI + folder + "/" + fileName);
        Path path = Paths.get(uri);
        File file = new File(path.toString());
     
        return new InputStreamResource(new FileInputStream(file));
    }


    
}
