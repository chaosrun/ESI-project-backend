package esi.project.ils.materials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @PostMapping("/material")
    public ResponseEntity<Material> addMaterial(@Valid @RequestBody Material material) {
        Material newMaterial = materialService.addMaterial(material);
        return new ResponseEntity<>(newMaterial, HttpStatus.CREATED);
    }

    @RequestMapping("/materials")
    public ResponseEntity<List<Material>> getAllMaterials() {
        List<Material> materialList = materialService.getAllMaterials();
        if (materialList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(materialList, HttpStatus.OK);
    }

    @RequestMapping("/material/{material_id}")
    public ResponseEntity<Material> getMaterialById(@PathVariable String material_id) {

        Optional<Material> material = materialService.getMaterialWithId(Integer.parseInt(material_id));

        if (material.isPresent()) {
            return new ResponseEntity<>(material.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/materials/title/{title}")
    public ResponseEntity<List<Material>> getMaterialByTitle(@PathVariable String title) {

        List<Material> material = materialService.getMaterialWithTitle(title);
        if (material.size() > 0) {
            return new ResponseEntity<>(material, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/materials/author/{author}")
    public ResponseEntity<List<Material>> getMaterialByAuthor(@PathVariable String author) {

        List<Material> materials = materialService.getMaterialWithAuthor(author);
        if (materials.size() > 0) {
            return new ResponseEntity<>(materials, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/materials/call-number/{call_number}")
    public ResponseEntity<Material> getMaterialByCallNumber(@PathVariable String call_number) {

        Optional<Material> material = materialService.getMaterialWithCallNumber(call_number);
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
