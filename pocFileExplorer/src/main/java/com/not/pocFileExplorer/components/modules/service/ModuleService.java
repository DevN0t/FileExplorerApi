package com.not.pocFileExplorer.components.modules.service;

import com.not.pocFileExplorer.components.modules.dto.ModuleCreateDTO;
import com.not.pocFileExplorer.components.modules.dto.ModuleListShort;
import com.not.pocFileExplorer.components.modules.dto.ModuleUpdateDTO;
import com.not.pocFileExplorer.components.modules.infra.model.Module;
import com.not.pocFileExplorer.components.modules.infra.repository.ModuleRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }


    public List<ModuleListShort> findAll(){
        return moduleRepository.findShortAll();
    }

    public Module findById(Integer id){
        return moduleRepository.findById(id).orElse(null);
    }

    public List<ModuleListShort> findChilds(Integer id){
        return moduleRepository.findListShortByParentId(id);
    }

    public Integer findParent(Integer id){
        List<ModuleListShort> module = moduleRepository.findShortByParentId(id);
        if (module == null){
            return null;
        }
        var parentId = module.get(0).parentId();
        return parentId;
    }

    public ResponseEntity<String> createModule(
            ModuleCreateDTO moduleDTO
    ) {
        try {
            Module module = new Module();
            module.setTitle(moduleDTO.title());
            module.setDescription(moduleDTO.description());
            module.setType(moduleDTO.type());
            module.setUrl(moduleDTO.url());
            module.setIcon(moduleDTO.iconId());
            module.setParentId(moduleDTO.parentId());
            module.setFile(moduleDTO.file());
            module.setStatus(true);
            module.setDeleted(false);
            moduleRepository.save(module);
            return ResponseEntity.ok("Module created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    public ResponseEntity<String> updateModule(ModuleUpdateDTO updateDTO, Integer id) {
        try {
            moduleRepository.findById(id).ifPresent(module -> {
                module.setTitle(updateDTO.title());
                module.setDescription(updateDTO.description());
                module.setIcon(updateDTO.iconId());
                module.setUrl(updateDTO.url());
                module.setFile(updateDTO.file());
                moduleRepository.save(module);
            });
            return ResponseEntity.ok().body("Module updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @Transactional
    public ResponseEntity<String> deleteModule(Integer id) {
        try {
            moduleRepository.findById(id).ifPresent(module -> {
                module.setDeleted(true);
                module.setStatus(false);

                if (module.getType() == 2) {
                    deleteRelatedItems(module.getId());
                }
            });
            return ResponseEntity.ok("Module deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }


    private void deleteRelatedItems(Integer id) {
        var folders = moduleRepository.findByParentId(id);
        for (var folder : folders) {
            if (folder.getType() == 2) {
                folder.setDeleted(true);
                folder.setStatus(false);
                moduleRepository.save(folder);
                deleteRelatedItems(folder.getId());
            } else if (folder.getType() == 1 || folder.getType() == 3) {
                folder.setDeleted(true);
                folder.setStatus(false);
                moduleRepository.save(folder);
            }
        }
    }


    @Transactional
    public ResponseEntity<String> changeStateModule(Integer id) {
        try {
            var module = moduleRepository.findById(id).orElse(null);

                if (module.getStatus() == true) {
                    module.setStatus(false);
                } else if (module.getStatus() == false) {
                    module.setStatus(true);
                }
                if (module.getType() == 2) {
                    changeStateRelatedItems(module.getId(), module.getStatus());
                }
                moduleRepository.save(module);
                return ResponseEntity.ok("Module Changed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    private void changeStateRelatedItems(Integer id, Boolean status) {
        var folders = moduleRepository.findByParentId(id);
        for (var folder : folders) {
            if (folder.getType() == 2) {
                folder.setStatus(status);
                moduleRepository.save(folder);
                changeStateRelatedItems(folder.getId(), status);
            } else if (folder.getType() == 3 || folder.getType() == 1) {
                folder.setStatus(status);
                moduleRepository.save(folder);
            }
        }

    }

}
