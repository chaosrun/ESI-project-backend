package esi.project.ils.materials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin
@RestController
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @PostMapping("/material")
    public ResponseEntity<Material> addMaterial(@Valid @RequestBody Material material) {
        Material newMaterial = materialService.addMaterial(material);
        return new ResponseEntity<>(newMaterial, HttpStatus.CREATED);
    }

    @RequestMapping("/material/{material_id}")
    public ResponseEntity<Material> getMaterialById(@PathVariable String material_id) {

        Optional<Material> material = materialService.getMaterialWithId(Integer.parseInt(material_id));

        if (material.isPresent()) {
            return new ResponseEntity<>(material.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/material/{material_id}")
    public ResponseEntity<Material> updateMaterial(@RequestBody Material material, @PathVariable String material_id) {
        Optional<Material> result = materialService.updateMaterial(Integer.parseInt(material_id), material);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/material/{material_id}")
    public ResponseEntity<Material> deleteMaterialById(@PathVariable String material_id) {
        materialService.deleteMaterialWithId(Integer.parseInt(material_id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
