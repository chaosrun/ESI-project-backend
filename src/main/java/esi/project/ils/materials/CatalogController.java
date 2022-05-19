package esi.project.ils.materials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class CatalogController {
    @Autowired
    private MaterialService materialService;

    @RequestMapping("/catalogs")
    public ResponseEntity<List<Material>> getAllCatalogMaterials() {
        List<Material> materialList = materialService.getAllMaterials();
        if (materialList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(materialList, HttpStatus.OK);
    }

    @RequestMapping("/catalog/type/{type}")
    public ResponseEntity<List<Material>> getCatalogByType(@PathVariable String type) {

        List<Material> material = materialService.getMaterialWithTitle(type);
        if (material.size() > 0) {
            return new ResponseEntity<>(material, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/catalog/title/{title}")
    public ResponseEntity<List<Material>> getCatalogByTitle(@PathVariable String title) {

        List<Material> material = materialService.getMaterialWithTitle(title);
        if (material.size() > 0) {
            return new ResponseEntity<>(material, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/catalog/author/{author}")
    public ResponseEntity<List<Material>> getCatalogByAuthor(@PathVariable String author) {

        List<Material> materials = materialService.getMaterialWithAuthor(author);
        if (materials.size() > 0) {
            return new ResponseEntity<>(materials, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/catalog/call-number/{call_number}")
    public ResponseEntity<Material> getMaterialByCallNumber(@PathVariable String call_number) {

        Optional<Material> material = materialService.getMaterialWithCallNumber(call_number);
        if (material.isPresent()) {
            return new ResponseEntity<>(material.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
