package com.seafta.meterhj.domain.service.measurement.image;

import com.seafta.meterhj.domain.dto.measurement.image.MeasurementImageDetails;
import com.seafta.meterhj.domain.persistence.model.measurement.image.MeasurementImage;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.imaging.ImageReadException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface MeasurementImageService {

    MeasurementImageDetails createMeasurementImage(@NotNull UUID meterId, MultipartFile file) throws IOException, TesseractException, ImageReadException;
    MeasurementImageDetails getMeasurementImage(@NotNull UUID imageId);
    List<MeasurementImageDetails> getMeasurementImages();
    void deleteMeasurementImage(@NotNull UUID imageId);
    MeasurementImage getRawMeasurementImage(@NotNull UUID imageId);
    String getDownloadUri(@NotNull UUID imageId);
    MeasurementImageDetails buildMeasurementImageDetails(@NotNull MeasurementImage image);

}
