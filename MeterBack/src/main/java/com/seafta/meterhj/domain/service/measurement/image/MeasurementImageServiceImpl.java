package com.seafta.meterhj.domain.service.measurement.image;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.seafta.meterhj.domain.dto.measurement.MeasurementDetails;
import com.seafta.meterhj.domain.dto.measurement.image.MeasurementImageDetails;
import com.seafta.meterhj.domain.persistence.model.measurement.Measurement;
import com.seafta.meterhj.domain.persistence.model.measurement.image.MeasurementImage;
import com.seafta.meterhj.domain.persistence.repository.measurement.MeasurementRepository;
import com.seafta.meterhj.domain.persistence.repository.measurement.image.MeasurementImageRepository;
import com.seafta.meterhj.domain.service.measurement.MeasurementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MeasurementImageServiceImpl implements MeasurementImageService {

    private final MeasurementImageRepository repository;
    private final MeasurementRepository measurementRepository;
    private final MeasurementService service;

    @Override
    public MeasurementImageDetails createMeasurementImage(@NotNull UUID meterId, @NotNull MultipartFile file) throws IOException, TesseractException, ImageReadException {
        log.info("Service: Creating measurement by image  {id: {}, name: {}}", meterId, file.getName());
        List<String> longLat = latitudeAndLongitude(convert(file));
        String address;
        if(longLat.isEmpty()) {
            address = "Address not found";
        } else {
            address = getLocationInfoFromApi(longLat.get(0), longLat.get(1));
        }
        MeasurementDetails details = service.createMeasurement(meterId, readValueFromImage(file.getBytes()), address);
        Measurement measurement = measurementRepository.findById(details.getMeasurementId()).get();
        MeasurementImage image = repository.save(MeasurementImage.buildMeasurementImage(file, measurement));
        MeasurementImageDetails result = buildMeasurementImageDetails(image);
        log.debug("Service: Created measurement image {name: {}}", image.getName());
        return result;
    }

    @Override
    public MeasurementImageDetails getMeasurementImage(@NotNull UUID imageId) {
        log.info("Service: Getting image {id: {}", imageId);
        MeasurementImageDetails result = buildMeasurementImageDetails(repository.findById(imageId).get());
        log.debug("Got image {id: {}, name: {}}", imageId, result.getImageName());
        return result;
    }

    @Override
    public List<MeasurementImageDetails> getMeasurementImages() {
        log.info("Service: Getting images y");
        List<MeasurementImageDetails> result = repository.findAll().stream()
                .map(this::buildMeasurementImageDetails).collect(Collectors.toList());
        log.debug("Service: Got images {result: {}}", result);
        return result;
    }

    @Override
    public void deleteMeasurementImage(@NotNull UUID imageId) {
        log.info("Service: Deleting image by id {id: {}}", imageId);
        repository.deleteById(imageId);
        log.debug("Service: Deleted image by id {id: {}}", imageId);
    }

    public MeasurementImage getRawMeasurementImage(@NotNull UUID imageId) {
        log.info("Service: Getting raw image {id: {}", imageId);
        MeasurementImage result = repository.findById(imageId).get();
        log.debug("Got raw image {id: {}, name: {}}", imageId, result.getName());
        return result;
    }

    @Override
    public String getDownloadUri(UUID imageId) {
        log.info("Service: Creating download uri for image{id: {}}", imageId);
        String downloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/image/")
                .path(imageId.toString())
                .toUriString();
        log.debug("Service: Created download uri {uri: {}}", downloadUri);
        return downloadUri;
    }

    @Override
    public MeasurementImageDetails buildMeasurementImageDetails(MeasurementImage image) {
        return MeasurementImageDetails.builder()
                .imageId(image.getId())
                .imageName(image.getName())
                .downloadUri(getDownloadUri(image.getId()))
                .build();
    }

    public float readValueFromImage(byte[] data) throws TesseractException {
        Tesseract tesseract = new Tesseract();
        BufferedImage image = createImageFromBytes(data);
        tesseract.setDatapath("src/main/resources");
        tesseract.setVariable("tessedit_char_whitelist", "0123456789");
        tesseract.setLanguage("pol");
        String result = validResultFromOCR(tesseract.doOCR(tresholdImage(image, 120)));
        log.info("result read value{}", result);
        return Float.parseFloat(result)/1000000;
    }

    private BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String validResultFromOCR(String result) {
        Pattern p = Pattern.compile("^\\d{7}$", Pattern.MULTILINE);
        Matcher matcher = p.matcher(result);
        if(!matcher.find()) {
            throw new RuntimeException("OCR did not find correct value" + result);
        }
        return matcher.group(0);
    }
    private BufferedImage tresholdImage(BufferedImage image, int threshold) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        result.getGraphics().drawImage(image, 0, 0, null);
        WritableRaster raster = result.getRaster();
        int[] pixels = new int[image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++) {
            raster.getPixels(0, y, image.getWidth(), 1, pixels);
            for (int i = 0; i < pixels.length; i++) {
                if (pixels[i] < threshold) pixels[i] = 0;
                else pixels[i] = 255;
            }
            raster.setPixels(0, y, image.getWidth(), 1, pixels);
        }
        return result;
    }

    private List<String> latitudeAndLongitude (File file) throws IOException, ImageReadException {
        List<String> gpsList = new ArrayList<>();
        final ImageMetadata metaData = Imaging.getMetadata(file);
        final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metaData;
        final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
        if (null != exifMetadata) {
            final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
            if (null != gpsInfo) {
                double longitude = gpsInfo.getLongitudeAsDegreesEast();
                double latitude = gpsInfo.getLatitudeAsDegreesNorth();
                gpsList.add(Double.toString(longitude));
                gpsList.add(Double.toString(latitude));
            }
        }
        return gpsList;
    }

    private String getLocationInfoFromApi (String longtitude, String latitude) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("https://us1.locationiq.com/v1/reverse?key=pk.dfc8d842c17c35e748779f219267176e&lat="+latitude+"&lon="+longtitude+"&format=json" ,String.class);
        String myJSONString = response.getBody();
        JsonObject jsonObject = new Gson().fromJson(myJSONString, JsonObject.class);

        return jsonObject.get("display_name").toString();
    }

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
