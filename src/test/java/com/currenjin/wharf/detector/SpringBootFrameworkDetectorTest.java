package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.Framework;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SpringBootFrameworkDetectorTest {
    private final FrameworkDetector detector = new SpringBootFrameworkDetector();

    @Test
    void detectSpringBoot() {
        Path path = Path.of("src/test/resources/spring-project");

        Framework detected = detector.detect(path);

        assertThat(detected).isEqualTo(Framework.SPRING_BOOT);
    }

    @Test
    void detectUnknown() {
        Path path = Path.of("src/test/resources/non-spring-project");

        Framework detected = detector.detect(path);

        assertThat(detected).isEqualTo(Framework.UNKNOWN);
    }

    @Test
    void detectUnknownWhenNoGradleFile() {
        Path path = Path.of("src/test/resources/empty-project");

        Framework detected = detector.detect(path);

        assertThat(detected).isEqualTo(Framework.UNKNOWN);
    }

    @Test
    void detectUnknownWhenIOException(@TempDir Path tempDir) throws IOException {
        Path buildGradlePath = tempDir.resolve("build.gradle");
        Files.writeString(buildGradlePath, "content");
        Files.setPosixFilePermissions(buildGradlePath, Collections.emptySet());

        Framework detected = detector.detect(tempDir);

        assertThat(detected).isEqualTo(Framework.UNKNOWN);
    }
}
