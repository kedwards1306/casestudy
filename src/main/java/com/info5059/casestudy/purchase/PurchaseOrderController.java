package com.info5059.casestudy.purchase;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.info5059.casestudy.product.ProductRepository;
import com.info5059.casestudy.util.QRCodeGenerator;
import com.info5059.casestudy.vendor.VendorRepository;
import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletRequest;

// import com.info5059.casestudy.product.ProductRepository;


@CrossOrigin
@RestController
public class PurchaseOrderController {
@Autowired
private PurchaseOrderDAO purchaseOrderDAO;
@Autowired
    private ProductRepository productRepository;

@Autowired
private VendorRepository vendorRepository;

@Autowired
private QRCodeGenerator qrGenerator;


@PostMapping("/api/purchaseorders")
public ResponseEntity<PurchaseOrder> addOne(@RequestBody PurchaseOrder purchaseOrder) {
    System.out.println("!!!!!");

    return new ResponseEntity<PurchaseOrder>(purchaseOrderDAO.create(purchaseOrder), HttpStatus.OK);
}
@Autowired
private PurchaseOrderRepository purchaseOrderRepository;
@GetMapping("/api/purchaseorders")
public ResponseEntity<Iterable<PurchaseOrder>> findAll() {
    Iterable<PurchaseOrder> pos = purchaseOrderRepository.findAll();
    return new ResponseEntity<Iterable<PurchaseOrder>>(pos, HttpStatus.OK);
}

@GetMapping(value = "/POPDF", produces = MediaType.APPLICATION_PDF_VALUE)
public ResponseEntity<InputStreamResource> streamPDF(HttpServletRequest request) throws IOException {
    String popid = request.getParameter("poid");
    // System.out.println("popid!!!!!");
    // System.out.println(request.getParameter("poId"));
    // popid = "1";
    // get formatted pdf as a stream
    ByteArrayInputStream bis = PurchaseOrderPDFGenerator.generateReport(popid,
            purchaseOrderRepository,
            vendorRepository,
            productRepository,
            qrGenerator);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=examplereport.pdf");

    // dump stream to browser
    return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
}
@GetMapping("/api/purchaseorders/{id}")
public ResponseEntity<Iterable<PurchaseOrder>> findByEmployee(@PathVariable Long id) {
return new ResponseEntity<Iterable<PurchaseOrder>>(purchaseOrderRepository.findByVendorid(id), HttpStatus.OK);
}

}
