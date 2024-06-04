package com.not.pocFileExplorer.components.modules.dto;

public record ModuleListShort(
        Integer id,
        String title,
        String description,
        Integer type,
        Boolean status,
        Integer parentId,
        String url,
        String file
) {
}
