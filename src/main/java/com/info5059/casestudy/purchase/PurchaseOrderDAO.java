package com.info5059.casestudy.purchase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.info5059.casestudy.product.Product;
import com.info5059.casestudy.product.ProductRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Component
@CrossOrigin
public class PurchaseOrderDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public PurchaseOrder create(PurchaseOrder poFromClient) {
        PurchaseOrder realPO = new PurchaseOrder();

        BigDecimal totalCost = new BigDecimal(0);

        realPO.setPodate(LocalDateTime.now());
        realPO.setVendorid(poFromClient.getVendorid());
        System.out.println("!!!!!");
        entityManager.persist(realPO);
        System.out.println("!!!!!");
        for (PurchaseOrderLineitem item : poFromClient.getItems()) {

            PurchaseOrderLineitem realItem = new PurchaseOrderLineitem();
            Product productToUpdate = productRepository.getReferenceById(item.getProductid());
            BigDecimal productCostPrice = productToUpdate.getCostprice();
            System.out.println(realPO.getId());
            realItem.setPoid(realPO.getId());
            realItem.setProductid(item.getProductid());
            realItem.setQty(item.getQty());
            realItem.setPrice(productCostPrice);

            totalCost = totalCost.add(productCostPrice.multiply(new BigDecimal(item.getQty())));

            productToUpdate.setQoo(productToUpdate.getQoo() + realItem.getQty());
            productRepository.saveAndFlush(productToUpdate);

            entityManager.persist(realItem);

        }
        totalCost = totalCost.multiply(new BigDecimal(1.13));
        realPO.setAmount(totalCost);

        entityManager.flush();
        entityManager.refresh(realPO);
        return realPO;
    }
}
