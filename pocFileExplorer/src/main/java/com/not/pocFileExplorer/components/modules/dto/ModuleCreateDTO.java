package com.not.pocFileExplorer.components.modules.dto;

public record ModuleCreateDTO(
        String title,
        String description,
        Integer type,
        String url,
        Integer iconId,
        Integer parentId,
        String file
) {
}
