package com.currenjin.wharf.analyzer;

import java.nio.file.Path;

import com.currenjin.wharf.domain.Project;

public interface ProjectAnalyzer {
	Project analyze(Path projectPath);
}
