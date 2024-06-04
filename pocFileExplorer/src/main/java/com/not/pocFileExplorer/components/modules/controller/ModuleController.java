package com.not.pocFileExplorer.components.modules.controller;

import com.not.pocFileExplorer.components.modules.dto.ModuleCreateDTO;
import com.not.pocFileExplorer.components.modules.dto.ModuleListShort;
import com.not.pocFileExplorer.components.modules.dto.ModuleUpdateDTO;
import com.not.pocFileExplorer.components.modules.infra.model.Module;
import com.not.pocFileExplorer.components.modules.service.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("modules")
@CrossOrigin(origins = "*")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping("/getAll")
    public List<ModuleListShort> getAllModules() {
        return moduleService.findAll();
    }

    @GetMapping("/getByParentId")
    public Integer getByParentId(@RequestParam int parentId) {
        return moduleService.findParent(parentId);
    }
    @GetMapping("/getById")
    public Module getModuleById(@RequestParam Integer id) {
        return moduleService.findById(id);
    }

    @GetMapping("/getChildren")
    public List<ModuleListShort> findChildren(@RequestParam Integer id) {
        return moduleService.findChilds(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createModule(@RequestBody ModuleCreateDTO module) {
        return moduleService.createModule(module);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateModule(
            @RequestBody ModuleUpdateDTO module,
            @RequestParam Integer id
    ) {
        return moduleService.updateModule(module, id);
    }

    @DeleteMapping("/status")
    public ResponseEntity<String> disableModule(@RequestParam Integer id) {
        return moduleService.changeStateModule(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteModule(@RequestParam Integer id) {
        return moduleService.deleteModule(id);
    }
}
