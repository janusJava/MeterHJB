package com.seafta.meterhj.boundary.measurement.image;


import com.seafta.meterhj.domain.dto.measurement.image.MeasurementImageDetails;
import com.seafta.meterhj.domain.persistence.model.measurement.image.MeasurementImage;
import io.swagger.v3.oas.annotations.Operation;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.imaging.ImageReadException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/image")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public interface MeasurementImageApi {

    @Operation(summary = "CreateImage", description = "IMAGE_CREATE_POST")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    ResponseEntity<String> createMeasurementImage(@RequestParam("meterId") UUID meterId, @RequestParam(value = "file") MultipartFile file) throws IOException, TesseractException, ImageReadException;

    @Operation(summary = "GetImage", description = "IMAGE_GET")
    @GetMapping(value = "/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(OK)
    ResponseEntity<byte []> getMeasurementImage(@PathVariable("imageId")UUID imageId);

    @Operation(summary = "GetImages", description = "IMAGES_GET")
    @GetMapping(value = "/all}")
    @ResponseStatus(OK)
    List<MeasurementImageDetails> getMeasurementImages();
}
