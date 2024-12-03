package com.currenjin.wharf.detector;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.currenjin.wharf.domain.Framework;

public class NodeFrameworkDetectorTest {
	private final FrameworkDetector detector = new NodeFrameworkDetector();

	@Test
	void detectNodeJs() {
		Path path = Path.of("src/test/resources/node-project");

		Framework detected = detector.detect(path);

		assertThat(detected).isEqualTo(Framework.NODE_JS);
	}

	@Test
	void detectUnknown() {
		Path path = Path.of("src/test/resources/empty-project");

		Framework detected = detector.detect(path);

		assertThat(detected).isEqualTo(Framework.UNKNOWN);
	}
}
