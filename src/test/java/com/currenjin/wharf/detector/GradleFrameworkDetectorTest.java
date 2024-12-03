package com.currenjin.wharf.detector;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.currenjin.wharf.domain.Framework;

public class GradleFrameworkDetectorTest {
	private final FrameworkDetector detector = new GradleFrameworkDetector();

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
}
