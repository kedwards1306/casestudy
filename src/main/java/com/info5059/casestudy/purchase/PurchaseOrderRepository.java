package com.info5059.casestudy.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "purchaseorders", path = "purchaseorders")
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findByVendorid(Long id);
}
