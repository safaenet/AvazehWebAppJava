package com.safadana.AvazehRetailManagement.Controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safadana.AvazehRetailManagement.DataLibraryCore.Services.InvoiceItemService;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.InvoiceItemModel;
import com.safadana.AvazehRetailManagement.SharedLibrary.DalModels.RecentSellPriceModel;

@RestController
@RequestMapping("/InvoiceItem")
public class InvoiceItemController {
    @Autowired
    InvoiceItemService service;

    @GetMapping("/Id/{id}")
    public CompletableFuture<InvoiceItemModel> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping("/CreateUpdate")
    public CompletableFuture<InvoiceItemModel> createUpdate(@RequestBody InvoiceItemModel item) {
        return service.createUpdateProduct(item);
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable int id) {
        service.deleteById(id);
    }

    @GetMapping("/RecentSellPrices/{customerId}/{productId}/{maxRecord}")
    public CompletableFuture<List<RecentSellPriceModel>> getRecentSellPrices(@PathVariable int customerId, @PathVariable int productId, @PathVariable int maxRecord) {
        return service.getRecentSellPrices(customerId, productId, maxRecord);
    }
}