package mx.kinich49.expensetracker.controllers;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.rest.ApiResponse;
import mx.kinich49.expensetracker.models.web.CommercialEstablishmentWebModel;
import mx.kinich49.expensetracker.models.web.requests.CommercialEstablishmentRequest;
import mx.kinich49.expensetracker.services.CommercialEstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CommercialEstablishmentController {

    private final CommercialEstablishmentService commercialEstablishmentService;

    @Autowired
    public CommercialEstablishmentController(CommercialEstablishmentService commercialEstablishmentService) {
        this.commercialEstablishmentService = commercialEstablishmentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CommercialEstablishmentWebModel>>> getAll() {
        return Optional.of(commercialEstablishmentService.findAll())
                .map(ApiResponse::new)
                .map(apiResponse -> new ResponseEntity<>(apiResponse, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<ApiResponse<CommercialEstablishmentWebModel>> getById(@PathVariable long id) {
        return commercialEstablishmentService.findById(id)
                .map(ApiResponse::new)
                .map(apiResponse -> new ResponseEntity<>(apiResponse, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        commercialEstablishmentService.deleteById(id);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CommercialEstablishmentWebModel>>
    updateCommercialEstablishment(@RequestBody CommercialEstablishmentRequest request) {
        try {
            var webModel = commercialEstablishmentService.update(request);
            var apiResponse = new ApiResponse<>(webModel);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CommercialEstablishmentWebModel>>
    addCommercialEstablishment(@RequestBody CommercialEstablishmentRequest request) {
        try {
            var webModel = commercialEstablishmentService.add(request);
            var apiResponse = new ApiResponse<>(webModel);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
