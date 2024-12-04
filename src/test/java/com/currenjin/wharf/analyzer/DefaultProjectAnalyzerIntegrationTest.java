package com.currenjin.wharf.analyzer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.currenjin.wharf.detector.FrameworkDetector;
import com.currenjin.wharf.detector.SpringBootFrameworkDetector;
import com.currenjin.wharf.detector.NodeFrameworkDetector;
import com.currenjin.wharf.domain.Framework;
import com.currenjin.wharf.domain.Project;

@ExtendWith(MockitoExtension.class)
class DefaultProjectAnalyzerIntegrationTest {

	@Mock
	private FrameworkDetector frameworkDetector;

	@Test
	void analyze() {
		given(frameworkDetector.detect(any(Path.class))).willReturn(Framework.SPRING_BOOT);
		ProjectAnalyzer analyzer = new DefaultProjectAnalyzer(List.of(frameworkDetector));
		Path projectPath = Path.of("sample/spring-project");

		Project project = analyzer.analyze(projectPath);

		assertThat(project.getFramework()).isEqualTo(Framework.SPRING_BOOT);
	}

	private final ProjectAnalyzer analyzer = new DefaultProjectAnalyzer(
		List.of(new SpringBootFrameworkDetector(), new NodeFrameworkDetector())
	);

	@Test
	void detectSpringBootFromGradle() {
		Project project = analyzer.analyze(Path.of("src/test/resources/spring-project"));

		assertThat(project.getFramework()).isEqualTo(Framework.SPRING_BOOT);
	}

	@Test
	void detectNodeJs() {
		Project project = analyzer.analyze(Path.of("src/test/resources/node-project"));

		assertThat(project.getFramework()).isEqualTo(Framework.NODE_JS);
	}
}
