package mx.kinich49.expensetracker.controllers;

import mx.kinich49.expensetracker.exceptions.BusinessException;
import mx.kinich49.expensetracker.models.web.ApiResponse;
import mx.kinich49.expensetracker.models.web.PaymentMethodWebModel;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/paymentmethods")
public class PaymentMethodController {

    private final PaymentMethodService service;

    @Autowired
    public PaymentMethodController(PaymentMethodService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentMethodWebModel>>> findAll() {
        return Optional.of(service.findAll())
                .map(ApiResponse::new)
                .map(apiResponse -> new ResponseEntity<>(apiResponse, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<PaymentMethodWebModel>> findById(@PathVariable(name = "id")
                                                                        long paymentMethodId) {
        return service.findById(paymentMethodId)
                .map(ApiResponse::new)
                .map(apiResponse -> new ResponseEntity<>(apiResponse, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentMethodWebModel>> postPaymentMethod(@RequestBody PaymentMethodRequest request) {
        try {
            PaymentMethodWebModel webModel = service.addPaymentMethod(request);
            ApiResponse<PaymentMethodWebModel> response = new ApiResponse<>(webModel);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PaymentMethodWebModel>> updatePaymentMethod(
            @RequestBody PaymentMethodRequest request) {
        return service.update(request)
                .map(ApiResponse::new)
                .map(apiResponse -> new ResponseEntity<>(apiResponse, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePaymentMethod(@PathVariable(name = "id") long paymentMethodId) {
        service.delete(paymentMethodId);
    }
}
