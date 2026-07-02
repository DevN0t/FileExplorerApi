package com.not.pocFileExplorer.components.modules.infra.repository;

import com.not.pocFileExplorer.components.modules.dto.ModuleListShort;
import com.not.pocFileExplorer.components.modules.infra.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

    @Query("SELECT module FROM Module module where module.parentId = :parentId")
    List<Module> findByParentId(Integer parentId);

    @Query("SELECT new com.not.pocFileExplorer.components.modules.dto.ModuleListShort(module.id, module.title, module.description, module.type, module.status, module.parentId, module.url, module.file) FROM Module module where module.id = :parentId")
    List<ModuleListShort> findShortByParentId(Integer parentId);

    @Query("SELECT new com.not.pocFileExplorer.components.modules.dto.ModuleListShort(module.id, module.title, module.description, module.type, module.status, module.parentId, module.url, module.file) FROM Module module where module.parentId = :parentId")
    List<ModuleListShort> findListShortByParentId(Integer parentId);

    @Query("SELECT new com.not.pocFileExplorer.components.modules.dto.ModuleListShort(module.id, module.title, module.description, module.type, module.status, module.parentId, module.url, module.file) FROM Module module WHERE module.parentId is null")
    List<ModuleListShort> findShortAll();
}
