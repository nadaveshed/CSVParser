package com.example.csvparser;

import java.io.IOException;
import java.util.*;

import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    // RESTful API methods for Retrieval operations
    @GetMapping("/products")
    public List<Player> list() {
        return service.listAll();
    }

    @GetMapping("/player")
    public List<String[]> getPlayerData() throws IOException, CsvException {
        return service.fileList();
    }

//    @GetMapping("/products/{id}")
//    public ResponseEntity<Player> get(@PathVariable Integer id) {
//        try {
//            Player product = service.get(id);
//            return new ResponseEntity<>(product, HttpStatus.OK);
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    // RESTful API method for Create operation
//    @PostMapping("/products")
//    public void add(@RequestBody Player product) {
//        service.save(product);
//    }

//    // RESTful API method for Update operation
//    @PutMapping("/products/{id}")
//    public ResponseEntity<?> update(@RequestBody Player product, @PathVariable Integer id) {
//        try {
//            Player existProduct = service.get(id);
//            product.setId(existProduct.getId());
//            service.save(product);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


}