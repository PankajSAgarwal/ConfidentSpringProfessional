package com.pankaj.myfancypdfinvoices.web;

import com.pankaj.myfancypdfinvoices.dto.InvoiceDto;
import com.pankaj.myfancypdfinvoices.model.Invoice;
import com.pankaj.myfancypdfinvoices.services.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@Validated
public class InvoicesController {
    private final InvoiceService invoiceService;

    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    // @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    @ResponseBody
    public List<Invoice> invoices(){
        return invoiceService.findAll();
    }

    @PostMapping("/invoices")
    public Invoice createInvoice(@RequestParam("user_id") @NotBlank String userId,
                                 @RequestParam @Min(10) @Max(50) Integer amount){
        return invoiceService.create(userId,amount);

    }

}
