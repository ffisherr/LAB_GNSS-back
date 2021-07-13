package space.ffisherr.labgnss.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@ConditionalOnProperty("gnss.files.scanner.schedule.enabled")
@RequiredArgsConstructor
public class GnssFileScanner {

    private final GnssFileService service;
    private static final FilenameFilter filter = (dir, name) -> name.endsWith(".map") || name.endsWith(".par");

    @Value("${gnss.files.scanner.base-dir}")
    private String baseDirPath;

    @Scheduled(fixedRateString = "${gnss.files.scanner.schedule.rate:500000}")
    public void execute() {
        log.info("Executing files scanning");
        final File baseDir = new File(baseDirPath);
        if (!baseDir.exists()) {
            log.error("No folder {} found", baseDirPath);
            return;
        }
        log.debug("Base directory {} exists", baseDirPath);
        final List<String> loadedFiles = service.readAllPath();
        scanFilesInDir(baseDir, loadedFiles);
        log.info("Finished loading files");
    }

    private void scanFilesInDir(File dir, List<String> loadedFiles) {
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                scanFilesInDir(file, loadedFiles);
            }
        }
        for (File file : Objects.requireNonNull(dir.listFiles(filter))) {
            final String absolutePath = file.getAbsolutePath();
            log.debug("File found for scanning: {}", absolutePath);
            if (loadedFiles.contains(absolutePath)) {
                log.debug("File {} is already loaded", absolutePath);
                return;
            }
            log.info("Loading file {}", absolutePath);
            final Boolean isLoadingSucceeded = service.loadFile(file);
            if (isLoadingSucceeded) {
                log.info("Loading file {} successfully finished", absolutePath);
            }
        }
    }

}
