package com.info5059.casestudy.purchase;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.info5059.casestudy.vendor.Vendor;
import com.info5059.casestudy.vendor.VendorRepository;
import com.info5059.casestudy.product.Product;
import com.info5059.casestudy.product.ProductRepository;
import com.info5059.casestudy.util.QRCodeGenerator;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.HorizontalAlignment;

import org.springframework.web.servlet.view.document.AbstractPdfView;

public abstract class PurchaseOrderPDFGenerator extends AbstractPdfView {

    public static ByteArrayInputStream generateReport(
        String popid,
        PurchaseOrderRepository purchaseOrderRepository,
        VendorRepository vendorRepository,
        ProductRepository productRepository,
        QRCodeGenerator qrGenerator
    ) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            Locale locale = Locale.of("en", "US");
            NumberFormat numberFormatter = NumberFormat.getCurrencyInstance(locale);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String poDateCreatedFormatted = "";
            String poSummary = "";
            URL imageUrl = PurchaseOrderPDFGenerator.class.getResource("/static/images/KELogo.png");
            Image img = new Image(ImageDataFactory.create(imageUrl))
            .scaleToFit(100,100)
            .setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(img);
            document.add(new Paragraph(String.format("Purchase Order #" + popid))
                    .setFont(font).setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER).setBold());

            // Table created, but not added yet
            Table table = new Table(5);
            table.setWidth(new UnitValue(UnitValue.PERCENT, 100));

            // Headers
            Cell cell = new Cell().add(new Paragraph("Product Code")
                    .setFont(font).setFontSize(12).setBold()).setTextAlignment(TextAlignment.CENTER);
            table.addCell(cell);
            cell = new Cell().add(new Paragraph("Description")
                    .setFont(font).setFontSize(12).setBold()).setTextAlignment(TextAlignment.CENTER);
            table.addCell(cell);
            cell = new Cell().add(new Paragraph("Qty Sold")
                    .setFont(font).setFontSize(12).setBold()).setTextAlignment(TextAlignment.CENTER);
            table.addCell(cell);
            cell = new Cell().add(new Paragraph("Price")
                    .setFont(font).setFontSize(12).setBold()).setTextAlignment(TextAlignment.CENTER);
            table.addCell(cell);
            cell = new Cell().add(new Paragraph("Est. Price")
                    .setFont(font).setFontSize(12).setBold()).setTextAlignment(TextAlignment.CENTER);
            table.addCell(cell);

            // Table Data

            Optional<PurchaseOrder> nullablePurchaseOrder = purchaseOrderRepository.findById(Long.parseLong(popid));
            if (nullablePurchaseOrder.isPresent()) {

                PurchaseOrder po = nullablePurchaseOrder.get();
                poDateCreatedFormatted = dateTimeFormatter.format(po.getPodate());

                Optional<Vendor> nullableVendor = vendorRepository.findById(po.getVendorid());
                if (nullableVendor.isPresent()) {

                    // Employee data
                    Vendor vendor = nullableVendor.get();
                    String vendorInfo = "Vendor: "
                        + vendor.getName()
                        + " \n"
                        + vendor.getAddress1()
                        + " \n"
                        + vendor.getCity()
                        + " "
                        + vendor.getEmail();

                    document.add(new Paragraph(vendorInfo)
                            .setFont(font).setFontSize(12).setTextAlignment(TextAlignment.CENTER).setBold());
                    //Po Summary
                    poSummary = "Summary for Purchase Order:" + po.getId()
                    + "\nDate:" + dateTimeFormatter.format(po.getPodate())
                    + "\nVendor:" + vendor.getName()
                    + "\nTotal:" + numberFormatter.format(po.getAmount());

                }

                BigDecimal totalProduct = new BigDecimal(0);
                BigDecimal subTotal= new BigDecimal(0);
                BigDecimal taxes = new BigDecimal(0);
                for (PurchaseOrderLineitem item : po.getItems()) {
                    Optional<Product> nullableProduct = productRepository.findById(item.getProductid());
                    if (!nullableVendor.isPresent()) {
                        continue;
                    }

                    Product product = nullableProduct.get();


                    String productId = "" + product.getId();
                    String productQty = " " + item.getQty();
                    String name = " " + product.getName();

                    String productAmount = numberFormatter.format(product.getCostprice());
                    String productEst= numberFormatter.format(product.getCostprice().multiply(new BigDecimal(item.getQty())));
                    subTotal = subTotal.add(product.getCostprice().multiply(new BigDecimal(item.getQty(), new MathContext(3, RoundingMode.UP))));
                    BigDecimal tax = new BigDecimal(0.13);
                    taxes = subTotal.multiply(tax, new MathContext(8, RoundingMode.UP));
                    totalProduct = subTotal.add(taxes, new MathContext(8, RoundingMode.UP));
                    // Rows
                    cell = new Cell().add(new Paragraph(productId)
                        .setFont(font).setFontSize(12).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(cell);
                    cell = new Cell().add(new Paragraph(name)
                        .setFont(font).setFontSize(12).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(cell);
                    cell = new Cell().add(new Paragraph(productQty)
                        .setFont(font).setFontSize(12).setTextAlignment(TextAlignment.CENTER));
                    table.addCell(cell);
                    cell = new Cell().add(new Paragraph(productAmount)
                        .setFont(font).setFontSize(12).setTextAlignment(TextAlignment.RIGHT));
                    table.addCell(cell);
                    cell = new Cell().add(new Paragraph(productEst)
                    .setFont(font).setFontSize(12).setTextAlignment(TextAlignment.RIGHT));
                table.addCell(cell);
                }
                cell = new Cell(1, 4).add(new Paragraph("SubTotal:")
                    .setFont(font).setFontSize(12)
                    .setBold().setTextAlignment(TextAlignment.RIGHT));
                table.addCell(cell);
            cell = new Cell().add(new Paragraph("$" + subTotal.toString())
            .setFont(font).setFontSize(12).setTextAlignment(TextAlignment.RIGHT));
            table.addCell(cell);
            cell = new Cell(1, 4).add(new Paragraph("Tax:")
            .setFont(font).setFontSize(12).setBold().setTextAlignment(TextAlignment.RIGHT));
            table.addCell(cell);
            cell = new Cell().add(new Paragraph("$" + taxes.toString())
            .setFont(font).setFontSize(12).setTextAlignment(TextAlignment.RIGHT));
            table.addCell(cell);
                cell = new Cell(1, 4).add(new Paragraph("Total:")
                    .setFont(font).setFontSize(12).setBold().setTextAlignment(TextAlignment.RIGHT));
                table.addCell(cell);
                cell = new Cell().add(new Paragraph("$" + totalProduct.toString())
                    .setFont(font).setFontSize(12)
                    .setBold().setTextAlignment(TextAlignment.RIGHT)
                    .setBackgroundColor(ColorConstants.YELLOW));
                table.addCell(cell);
            }

            document.add(new Paragraph("\n"));
            document.add(table);
            document.add(new Paragraph("\n"));


            document.add(new Paragraph(poDateCreatedFormatted)
                    .setTextAlignment(TextAlignment.CENTER));

            // QR Code Summary
            Image poQRCode = new Image(ImageDataFactory.create(qrGenerator.generateQRCode(poSummary)))
            .scaleAbsolute(100, 100)
            .setFixedPosition(460,60);
            document.add(poQRCode);

            document.close();
        }
        catch (Exception ex) {
            Logger.getLogger(PurchaseOrderPDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ByteArrayInputStream(baos.toByteArray());
    }
}
