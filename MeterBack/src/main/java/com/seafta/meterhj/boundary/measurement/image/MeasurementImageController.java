package com.seafta.meterhj.boundary.measurement.image;

import com.seafta.meterhj.domain.dto.measurement.image.MeasurementImageDetails;
import com.seafta.meterhj.domain.persistence.model.measurement.image.MeasurementImage;
import com.seafta.meterhj.domain.service.measurement.image.MeasurementImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.imaging.ImageReadException;
import org.owasp.security.logging.SecurityMarkers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MeasurementImageController implements MeasurementImageApi {

    private final MeasurementImageService service;

    @Override
    public ResponseEntity<String> createMeasurementImage(UUID meterId, MultipartFile file) throws IOException, TesseractException, ImageReadException {
        log.trace("Image Controller: Uploading image {receiver: {}, image name: {}", meterId, file.getName());
        MeasurementImageDetails result = service.createMeasurementImage(meterId, file);
        log.debug("Image Controller: Uploaded image {receiver: {}. image name: {}", meterId, result);
        return ResponseEntity.status(HttpStatus.OK).body(new String("Uploaded" + result.getImageId()));
    }

    @Override
    public ResponseEntity<byte[]> getMeasurementImage(UUID imageId) {
        log.trace("Image Controller: Getting image by id {image ID: {}}", imageId);
        MeasurementImage details = service.getRawMeasurementImage(imageId);
        log.debug("Image Controller: Got image by id {image ID: {}, image name: {}}", imageId, details.getName());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; imagename =\"" + details.getName() + "\"")
                .body(details.getData());
    }

    @Override
    public List<MeasurementImageDetails> getMeasurementImages() {
        log.trace("Image Controller: Getting all images");
        List<MeasurementImageDetails> result = service.getMeasurementImages();
        log.debug("Image Controller: Uploaded image {image name: {}", result);
        return result;
        //todo Wyciaganie jednego obrazka z mapowania onetoone jako details z url
    }
}
