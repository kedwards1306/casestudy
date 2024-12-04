INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email)VALUES ('123 Maple
St','London','On', 'N1N-1N1','(555)555-5555','Trusted','ABC Supply Co.','abc@supply.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('543
Sycamore Ave','Toronto','On', 'N1P-1N1','(999)555-5555','Trusted','Big Bills
Depot','bb@depot.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('922 Oak
St','London','On', 'N1N-1N1','(555)555-5599','Untrusted','Shady Sams','ss@underthetable.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('828 Freedom
St','London','On', 'N1F-1N1','(555)555-5500','Untrusted','Keith Edwards','ke@mart.com');
--Adding some products to the table
INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
Values ('Boo', 1, 'Paper 90', 0.1, 0.1, 100, 100, 100, 100, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
Values ('KE345', 1, 'Tennis', 0.12, 0.12, 50, 50, 70, 70, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
Values ('KE56', 1, 'Games', 0.15, 0.15, 40, 40, 40, 40, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
Values ('E890', 1, 'Computer', 0.4, 0.4, 120, 120, 120, 120, NULL, NULL);
INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
VALUES ('P123', 2, 'Office Chair', 50.00, 75.00, 20, 50, 30, 10, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
VALUES ('P124', 2, 'Desk Lamp', 15.00, 25.00, 10, 30, 15, 5, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
VALUES ('P125', 3, 'Wireless Mouse', 10.00, 20.00, 25, 50, 40, 10, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
VALUES ('P126', 3, 'Bluetooth Speaker', 20.00, 35.00, 15, 40, 25, 5, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
VALUES ('P127', 1, 'Notebook', 0.50, 1.00, 100, 200, 150, 50, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
VALUES ('P128', 4, 'Smartphone', 200.00, 300.00, 10, 20, 12, 8, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
VALUES ('P129', 4, 'Tablet', 150.00, 250.00, 5, 15, 7, 3, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
VALUES ('P130', 2, 'Printer', 100.00, 150.00, 10, 25, 15, 5, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
VALUES ('P131', 3, 'Headphones', 25.00, 40.00, 20, 60, 30, 10, NULL, NULL);

INSERT INTO Product (ID, VendorID, Name, CostPrice, MSRP, ROP, EOQ, QOH, QOO, QRCode, QRCodeTxt)
VALUES ('P132', 4, 'Calculator', 8.00, 15.00, 50, 100, 75, 20, NULL, NULL);