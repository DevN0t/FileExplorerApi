package com.not.pocFileExplorer.components.modules.dto;

public record ModuleUpdateDTO(
        String title,
        String description,
        String url,
        Integer iconId,
        String file
) {
}
