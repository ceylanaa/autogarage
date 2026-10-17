package nl.novi.autogarage.services;

import nl.novi.autogarage.exceptions.BadRequestException;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path uploadMap;

    public FileStorageService(@Value("${upload.dir}") String uploadDir) {
        this.uploadMap = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(uploadMap);
        } catch (IOException e) {
            throw new RuntimeException("Kan upload map niet aanmaken", e);
        }
    }

    public String slaBestandOp(MultipartFile bestand, String bestandsNaam) {
        try {
            Path doel = uploadMap.resolve(bestandsNaam);
            Files.copy(bestand.getInputStream(), doel, StandardCopyOption.REPLACE_EXISTING);
            return bestandsNaam;
        } catch (IOException e) {
            throw new BadRequestException("Bestand kon niet worden opgeslagen");
        }
    }

    public Resource laadBestand(String bestandsNaam) {
        try {
            Path pad = uploadMap.resolve(bestandsNaam).normalize();
            Resource resource = new UrlResource(pad.toUri());
            if (!resource.exists()) {
                throw new RecordNotFoundException("Bestand " + bestandsNaam + " niet gevonden");
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new RecordNotFoundException("Bestand " + bestandsNaam + " niet gevonden");
        }
    }
}
