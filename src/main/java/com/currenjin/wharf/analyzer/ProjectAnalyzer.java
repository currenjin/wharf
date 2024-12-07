package com.currenjin.wharf.analyzer;

import com.currenjin.wharf.domain.Project;

import java.nio.file.Path;

public interface ProjectAnalyzer {
    Project analyze(Path projectPath);
}
